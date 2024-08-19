package bdabackend.bda.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import bdabackend.bda.Entity.VoluntarioEntity;
import bdabackend.bda.Observer.VoluntarioAceptadoObserver;
import bdabackend.bda.Repository.VoluntarioRepository;
import bdabackend.bda.Utils.WebSocketSessionRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.concurrent.ScheduledExecutorService;

import io.jsonwebtoken.JwtException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Value("${jwt.secret_key}")
    private String secretKey;

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    private final List<VoluntarioAceptadoObserver> observers = new ArrayList<>();

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // Mapa de sesiones activas
    private final Map<String, WebSocketSession> activeSessions = new ConcurrentHashMap<>();

    private final Map<String, Boolean> canRespond = new ConcurrentHashMap<>(); // Para rastrear si un usuario aún puede
                                                                               // responder

    // Lista para almacenar los IDs de los usuarios que aceptaron
    private final List<String> voluntariosAceptados = new ArrayList<>()

    ;

    public void registerObserver(VoluntarioAceptadoObserver observer) {
        observers.add(observer);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = getTokenFromUri(session.getUri().toString());
        if (token != null) {
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(getSignInKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String userId = claims.getSubject();
                // Procede con la conexión si el token es válido
                System.out.println("Usuario autenticado conectado: " + userId);

                activeSessions.put(session.getId(), session);
                canRespond.put(session.getId(), true); // Inicialmente, el usuario puede responder

                System.out.println("Sesiones activas: " + activeSessions.size());
                System.out.println("Usuario autenticado conectado: " + userId);

                // Almacena el userId en la sesión WebSocket
                session.getAttributes().put("userId", userId);

            } catch (JwtException e) {
                // Cierra la conexión si el token no es válido
                System.out.println("Token inválido");
                session.close(CloseStatus.NOT_ACCEPTABLE);
            }
        } else {
            session.close(CloseStatus.NOT_ACCEPTABLE); // Cierra la conexión si el usuario no está autenticado
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status)
            throws Exception {
        String userId = getUserIdFromSession(session);
        if (userId != null) {
            WebSocketSessionRegistry.removeSession(userId);
            activeSessions.remove(session.getId());
            System.out.println("Usuario desconectado: " + userId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String sessionId = session.getId();
        String payload = message.getPayload();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(payload);

        if (canRespond.getOrDefault(sessionId, false)) {
            String tipo = jsonNode.get("tipo").asText();

            if ("respuesta".equals(tipo)) {
                String seleccion = jsonNode.get("seleccion").asText();
                String idVoluntario = obtenerUserIdDesdeSesion(session);

                if ("aceptar".equalsIgnoreCase(seleccion)) {
                    VoluntarioEntity voluntario = voluntarioRepository.findByCorreo(idVoluntario);
                    // Agregar el ID del voluntario a la lista
                    System.out.println("Voluntario " + voluntario);
                    if (voluntario != null) {
                        voluntariosAceptados.add(voluntario.getId());
                    }
                    System.out.println("Voluntarios aceptados: " + voluntariosAceptados);
                    System.out.println("Voluntario " + voluntario.getId() + " ha aceptado la tarea.");
                    // Notificar a los observadores
                    for (VoluntarioAceptadoObserver observer : observers) {
                        observer.onVoluntarioAceptado(idVoluntario);
                    }
                }

                // Bloquear futuras respuestas de este usuario
                canRespond.put(sessionId, false);
            }
        } else {
            session.sendMessage(new TextMessage("No puedes responder, el tiempo ha expirado."));
        }
    }

    // Método para obtener la lista de voluntarios que aceptaron
    public List<String> getVoluntariosAceptados() {
        System.out.println("Voluntarios aceptados 1: " + voluntariosAceptados);
        return new ArrayList<>(voluntariosAceptados); // Retornar una copia de la lista
    }

    // Método para obtener el identificador de usuario desde la sesión
    private String obtenerUserIdDesdeSesion(WebSocketSession session) {
        return session.getAttributes().get("userId").toString();
    }

    /*
     * // Ejemplo: Enviar un mensaje a todos los usuarios activos
     * scheduler.schedule(() -> {
     * try {
     * System.out.println("Enviando mensaje a usuarios activos...");
     * session.sendMessage(new TextMessage("Hola, usuarios activos! (retrasado 5 )"
     * ));
     * }catch (Exception e) {
     * e.printStackTrace();
     * }
     * 
     * }, 5, TimeUnit.SECONDS);
     * 
     * //Enviar un segundo mensaje
     * scheduler.schedule(() -> {
     * try {
     * System.out.println("Enviando mensaje a usuarios activos...");
     * session.sendMessage(new TextMessage("Hola, usuarios activos! (retrasado 10)"
     * ));
     * }catch (Exception e) {
     * e.printStackTrace();
     * }
     * 
     * }, 10, TimeUnit.SECONDS);
     */

    private String getTokenFromUri(String uri) {
        // Extraer el token de la URI
        // Ejemplo: ws://localhost:8080/websocket?token=tu_jwt_token
        String[] parts = uri.split("\\?token=");
        return parts.length > 1 ? parts[1] : null;
    }

    private String getUserIdFromSession(WebSocketSession session) {
        // Este método se usa para obtener el userId almacenado en la sesión WebSocket
        // Este valor fue almacenado cuando se estableció la conexión
        Object userId = session.getAttributes().get("userId");
        return userId != null ? userId.toString() : "unknown";
    }

    // Método para enviar un mensaje a todas las sesiones activas
    public void mensajeInstitucionesCreadas(String mensaje) {
        activeSessions.forEach((id, session) -> {
            try {
                session.sendMessage(new TextMessage(mensaje));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Método para enviar un mensaje y luego borrarlo
    public void enviarMensajeConBorradoPosterior(String mensaje, long delayInSeconds) {
        // Enviar el mensaje a todas las sesiones activas
        activeSessions.forEach((id, session) -> {
            try {
                session.sendMessage(new TextMessage(mensaje));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Programar el borrado del mensaje después del tiempo especificado
        scheduler.schedule(() -> {
            activeSessions.forEach((id, session) -> {
                try {
                    session.sendMessage(new TextMessage("El mensaje anterior ha sido borrado."));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }, delayInSeconds, TimeUnit.SECONDS);
    }

    public void enviarMensajeConOpciones(WebSocketSession session) throws Exception {
        String opcionesMensaje = "{\"tipo\":\"opciones\", \"mensaje\":\"¡Tarea activa entrante!:\", \"opciones\":[\"Aceptar \", \"Rechazar\", \"Opción 3\"]}";
        session.sendMessage(new TextMessage(opcionesMensaje));
    }

    // Método para enviar un mensaje con opciones y configurar un bloqueo después de
    // un tiempo
    public void enviarMensajeConOpcionesYBloqueo(String mensaje, long delayInSeconds) {
        activeSessions.forEach((id, session) -> {
            try {
                session.sendMessage(new TextMessage(mensaje));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    // Este método debe llamarse cuando se envían las opciones
    public void programarBloqueoYRespuestaPredeterminada(long delayInSeconds) {
        scheduler.schedule(() -> {
            activeSessions.forEach((id, session) -> {
                if (canRespond.getOrDefault(id, false)) { // Si el usuario aún no ha respondido, envía una respuesta
                                                          // predeterminada
                    canRespond.put(id, false); // Bloquea la capacidad de responder
                    try {
                        session.sendMessage(
                                new TextMessage("No respondiste a tiempo, tarea rechazada por defecto."));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }, delayInSeconds, TimeUnit.SECONDS);
    }

    public void enviarMensajeConOpcionesYProgramarBloqueo(String mensaje, long delayInSeconds) {
        activeSessions.forEach((id, session) -> {
            try {
                canRespond.put(id, true); // Restablece la capacidad de responder
                session.sendMessage(new TextMessage(mensaje));
                programarBloqueoYRespuestaPredeterminada(delayInSeconds);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // MENSAJE PARA CUANDO SE CREA LA TAREA-----------------------

    public void enviarMensajeConOpcionesAUsuariosPermitidos(String mensaje, List<String> listaUsuariosPermitidos,
            long delayInSeconds) {
        activeSessions.forEach((id, session) -> {
            // Suponiendo que tienes un método para obtener el identificador del usuario
            // desde la sesión
            String userId = obtenerUserIdDesdeSesion(session);

            // Verificar si el usuario está en la lista de usuarios permitidos
            if (listaUsuariosPermitidos.contains(userId)) {
                try {
                    // Restablecer el estado de respuesta a `true` para las sesiones permitidas
                    canRespond.put(id, true);

                    // Enviar el mensaje de opciones a cada usuario conectado que esté en la lista
                    session.sendMessage(new TextMessage(mensaje));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Programar el bloqueo de respuestas y envío de respuesta predeterminada para
        // los usuarios permitidos
        scheduler.schedule(() -> {
            activeSessions.forEach((id, session) -> {
                String userId = obtenerUserIdDesdeSesion(session);
                if (listaUsuariosPermitidos.contains(userId) && canRespond.getOrDefault(id, false)) {
                    canRespond.put(id, false); // Bloquea la capacidad de responder
                    try {
                        session.sendMessage(
                                new TextMessage("No respondiste a tiempo, seleccionando Opción por defecto."));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }, delayInSeconds, TimeUnit.SECONDS);
    }

}
