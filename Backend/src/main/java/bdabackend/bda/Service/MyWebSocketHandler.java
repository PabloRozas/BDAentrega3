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
import bdabackend.bda.Utils.WebSocketSessionRegistry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.stereotype.Service;

import java.security.Key;
import io.jsonwebtoken.JwtException;


@Service
public class MyWebSocketHandler extends TextWebSocketHandler  {

    @Value("${jwt.secret_key}")
    private String secretKey;

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = getTokenFromUri(session.getUri().toString());
        if (token != null) {
            try{
                Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

                String userId = claims.getSubject();
                //Procede con la conexión si el token es válido
                System.out.println("Usuario autenticado conectado: " + userId);

                // Almacena el userId en la sesión WebSocket
                session.getAttributes().put("userId", userId);
            } catch (JwtException e) {
                // Cierra la conexión si el token no es válido
                System.out.println("Token inválido");
                session.close(CloseStatus.NOT_ACCEPTABLE);
            }
        } else {
            session.close(CloseStatus.NOT_ACCEPTABLE );  // Cierra la conexión si el usuario no está autenticado
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        String userId = getUserIdFromSession(session);
        if (userId != null) {
            WebSocketSessionRegistry.removeSession(userId);
            System.out.println("Usuario desconectado: " + userId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Aquí podrías manejar mensajes entrantes de los usuarios, si es necesario
    }

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
}
