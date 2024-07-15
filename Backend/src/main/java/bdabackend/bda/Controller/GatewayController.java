package bdabackend.bda.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import bdabackend.bda.Service.VoluntarioService;
import bdabackend.bda.Service.CoordinadorService;
import bdabackend.bda.Service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;
import org.springframework.http.ResponseEntity;

import bdabackend.bda.Entity.AuthenticationResponse;
import bdabackend.bda.Entity.LoginRequest;

@RestController
@RequestMapping("/gateway")
@CrossOrigin(origins = "*")
public class GatewayController {
    @Autowired
    private VoluntarioService voluntarioService;

    @Autowired
    private CoordinadorService coordinadorService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Map<String, String> body) {
        String correo = body.get("correo");
        String contrasena = body.get("contrasena");
        LoginRequest loginRequest = new LoginRequest(correo, contrasena);
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
