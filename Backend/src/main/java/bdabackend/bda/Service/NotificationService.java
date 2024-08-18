package bdabackend.bda.Service;

import bdabackend.bda.Utils.WebSocketSessionRegistry;


import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void enviarMensajeAUsuariosActivos(String mensaje) {
        WebSocketSessionRegistry.broadcastMessageToActiveUsers(mensaje);
    }

}
