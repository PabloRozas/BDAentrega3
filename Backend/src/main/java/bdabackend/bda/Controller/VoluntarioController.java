package bdabackend.bda.Controller;

import bdabackend.bda.Entity.TareaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import bdabackend.bda.Entity.LoginRequest;
import bdabackend.bda.Entity.VoluntarioEntity;
import bdabackend.bda.Service.AuthService;
import bdabackend.bda.Service.VoluntarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import bdabackend.bda.Entity.AuthenticationResponse;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/voluntario")
@CrossOrigin("*")
public class VoluntarioController {
    @Autowired
    private VoluntarioService voluntarioService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // CREAR
    /**
     * Controlador que permite registrar a un voluntario en la base de datos.
     * 
     * @param body Un Map con los datos del voluntario a registrar.
     * @return Un VoluntarioEntity con los datos del voluntario registrado.
     */
    @PostMapping("/register")
    public void register(@RequestBody Map<String, String> body) {
        // Se reciben los parametros de nombre, contraseña correo y numero de documento
        String nombre = body.get("nombre");
        String correo = body.get("correo");
        String numeroDocumento = body.get("numeroDocumento");
        Double latitud = Double.parseDouble(body.get("latitud"));
        Double longitud = Double.parseDouble(body.get("longitud"));
        String contrasena = body.get("contrasena");
        String equipamiento = body.get("equipamiento");
        // String idTarea = body.get("idTarea");

        // Se crea un VoluntarioEntity con los parametros recibidos
        VoluntarioEntity voluntario = new VoluntarioEntity(nombre, correo,
                numeroDocumento, new GeoJsonPoint(longitud, latitud),
                passwordEncoder.encode(contrasena),
                equipamiento);

        // Se guarda el voluntario en la base de datos
        voluntarioService.insertarVoluntario(voluntario);
    }

    // LEER

    /**
     * Controlador que permite logear a un voluntario según el correo y la
     * contraseña
     * proporcionados.
     * 
     * @param body Un Map con el correo y la contraseña del voluntario.
     * @return Un ResponseEntity con un AuthenticationResponse, este contiene el
     *         token que se debe usar en las demas consultas.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Map<String, String> body) {
        // Se recibe el correo y la contraseña
        String correo = body.get("correo");
        String contrasena = body.get("contrasena");
        // Se crea un LoginRequest con el correo y la contraseña
        LoginRequest loginRequest = new LoginRequest(correo, contrasena);
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    /**
     * Controlador que retorna una lista de todos los voluntarios.
     * 
     * @return Una lista de todos los voluntarios.
     */
    @GetMapping("/all")
    public List<VoluntarioEntity> tabla() {
        return voluntarioService.listaVoluntario();
    }

    /**
     * Controlador que retorna un VoluntarioEntity según el correo proporcionado.
     *
     * @param correo El correo del voluntario a buscar.
     * @return Un VoluntarioEntity con el correo proporcionado, o null si no se
     *         encuentra.
     */
    @GetMapping("/correo/{correo}")
    public VoluntarioEntity findByCorreo(@PathVariable String correo) {
        return voluntarioService.buscarPorCorreo(correo);
    }

    // ACTUALIZAR
    // ! No hay controladores para actualizar

    // BORRAR

    /**
     * Controlador que elimina un VoluntarioEntity según el id proporcionado.
     *
     * @param idVoluntario El id del voluntario a borrar.
     * @return void
     */
    @DeleteMapping("/delete/{idVoluntario}")
    public void eliminar(@PathVariable String idVoluntario) {
        voluntarioService.eliminarVoluntarioPorId(idVoluntario);
    }

    @GetMapping("/VoluntariosPorTarea")
    public List<TareaEntity> getTareasByVoluntarioId(@RequestBody Map<String, String> body) {
        String id = body.get("id");
        return voluntarioService.getTareasConVoluntarios(id);
    }

}
