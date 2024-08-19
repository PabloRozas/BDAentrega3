package bdabackend.bda.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bdabackend.bda.Service.NotificationService;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessageToActiveUsers(@RequestParam String message) {
        notificationService.enviarMensajeAUsuariosActivos(message);
        return ResponseEntity.ok("Mensaje enviado a todos los usuarios activos.");
    }

}
