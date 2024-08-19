package bdabackend.bda.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bdabackend.bda.Entity.InstitucionEntity;
import bdabackend.bda.Repository.InstitucionRepository;

@Service
public class InstitucionService {
    @Autowired
    private InstitucionRepository institucionRepository;

    @Autowired
    private MyWebSocketHandler myWebSocketHandler;

    public void insertarInstitucion(String nombre) {
        institucionRepository.insertarInstitucion(nombre);

        // Crear el mensaje de opciones
        String mensajeOpciones = "{\"tipo\":\"opciones\", \"mensaje\":\"Selecciona una opción relacionada con la nueva institución: "
                + nombre + "\", \"opciones\":[\"Opción 1\", \"Opción 2\", \"Opción 3\"]}";

        // Enviar el mensaje a todas las sesiones activas y programar el bloqueo después
        // de 10 segundos
        myWebSocketHandler.enviarMensajeConOpcionesYProgramarBloqueo(mensajeOpciones, 10);
    }

    public void eliminarInstitucionPorId(Long id) {
        institucionRepository.eliminarInstitucionPorId(id);
    }

    public List<?> buscarInstitucionPorId(Long id) {
        return institucionRepository.buscarInstitucionPorId(id);
    }

    public List<InstitucionEntity> listaInstitucion() {
        return institucionRepository.listaInstitucion();
    }
}