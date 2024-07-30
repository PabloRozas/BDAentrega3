package bdabackend.bda.Controller;

import bdabackend.bda.Entity.TareaEntity;
import bdabackend.bda.Service.TareaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tarea")
public class TareaController {
    private static final Logger logger = LoggerFactory.getLogger(TareaController.class);

    @Autowired
    private TareaService mongoTareaService;

    @GetMapping("/{id}")
    public ResponseEntity<TareaEntity> getTareaById(@PathVariable String id) {
        logger.info("Recibiendo solicitud para obtener tarea con id: {}", id);
        TareaEntity tarea = mongoTareaService.buscarTareaPorId(id);
        if (tarea == null) {
            logger.warn("Tarea con id: {} no encontrada", id);
            return ResponseEntity.notFound().build();
        }
        logger.info("Tarea encontrada: {}", tarea);
        return ResponseEntity.ok(tarea);
    }

    @GetMapping("/all")
    public List<TareaEntity> getAllTareas() {
        logger.info("Recibiendo solicitud para obtener todas las tareas");
        return mongoTareaService.listaTarea();
    }

    @PostMapping("/add")
    public ResponseEntity<TareaEntity> addTarea(@RequestBody Map<String, String> body) {
        logger.info("Recibiendo solicitud para agregar una nueva tarea: {}", body);
        try {
            String nombreTarea = body.get("nombreTarea");
            String descripcionTarea = body.get("descripcionTarea");
            String tipoTarea = body.get("tipoTarea");
            Long emergencia = Long.parseLong(body.get("emergencia"));
            Double latitud = Double.parseDouble(body.get("latitud"));
            Double longitud = Double.parseDouble(body.get("longitud"));
            Point zona = new Point(longitud, latitud);

            TareaEntity nuevaTarea = mongoTareaService.insertarTarea(nombreTarea, descripcionTarea, tipoTarea, zona,
                    emergencia);
            logger.info("Tarea agregada exitosamente: {}", nuevaTarea);
            return ResponseEntity.ok(nuevaTarea);
        } catch (Exception e) {
            logger.error("Error al agregar la tarea: ", e);
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        logger.info("Recibiendo solicitud para eliminar tarea con id: {}", id);
        try {
            mongoTareaService.eliminarTareaPorId(id);
            logger.info("Tarea con id: {} eliminada exitosamente", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error al eliminar la tarea: ", e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/tareaRegion/{nombreRegion}")
    public List<TareaEntity> getTareaByRegion(@PathVariable String nombreRegion) throws Exception {
        logger.info("Recibiendo solicitud para obtener tareas en la region: {}", nombreRegion);
        return mongoTareaService.buscarTareaPorRegion(nombreRegion);
    }

}
