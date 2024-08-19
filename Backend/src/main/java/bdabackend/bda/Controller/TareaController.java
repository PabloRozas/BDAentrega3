package bdabackend.bda.Controller;

import bdabackend.bda.Entity.RankingEntity;
import bdabackend.bda.Entity.TareaEntity;
import bdabackend.bda.Events.VoluntarioAceptadoEvent;
import bdabackend.bda.Service.RankingService;
import bdabackend.bda.Service.TareaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.aggregation.DocumentOperators.Rank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/tarea")
public class TareaController {
    private static final Logger logger = LoggerFactory.getLogger(TareaController.class);

    @Autowired
    private TareaService mongoTareaService;

    @Autowired
    RankingService rankingService;

    @Autowired
    private TareaService tareaService;

    public final ApplicationEventPublisher applicationEventPublisher;

    public TareaController(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Autowired
    public TareaController(TareaService mongoTareaService, 
                           RankingService rankingService, 
                           TareaService tareaService, 
                           ApplicationEventPublisher eventPublisher) {
        this.mongoTareaService = mongoTareaService;
        this.rankingService = rankingService;
        this.tareaService = tareaService;
        this.applicationEventPublisher = eventPublisher;
    }

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

            String requerimiento = body.get("equ");

            Long emergencia = Long.parseLong(body.get("emergencia"));
            Double latitud = Double.parseDouble(body.get("latitud"));
            Double longitud = Double.parseDouble(body.get("longitud"));
            Point zona = new Point(longitud, latitud);
            int cantidadVoluntarios = Integer.parseInt(body.get("cantidadVoluntarios"));
            LocalDate fecha = LocalDate.parse(body.get("fecha"));
            LocalDateTime hora = LocalDateTime.parse(body.get("hora"));

            TareaEntity nuevaTarea = mongoTareaService.insertarTarea(nombreTarea, descripcionTarea, tipoTarea, zona,
                    emergencia, requerimiento, cantidadVoluntarios, fecha, hora);
            logger.info("Tarea agregada exitosamente: {}", nuevaTarea);

            rankingService.crearRankingTarea(nuevaTarea.getId());
            rankingService.ordenarRankingTarea(nuevaTarea.getId());
            List<RankingEntity> rankings = rankingService.obtenerCandidatos(nuevaTarea.getId());
            System.out.println("rankings: " + rankings);

            List<String> idVoluntarios = rankingService.obtenerIdVoluntarios(rankings);
            System.out.println("ahashdasjhdkajsgdjkhasgdk");

            rankingService.mensajeTareaCreada(idVoluntarios, nuevaTarea.getNombre());
            
            System.out.println("idVoluntarios: " + idVoluntarios);

            // llamar a un metodo de tareaService que obtenga los voluntarios aceptados
            List<String> idVoluntariosAceptados = tareaService.getVoluntariosCompletamenteAceptados();
            System.out.println("idVoluntariosAceptados: " + idVoluntariosAceptados);
            try {
                // Esperar 5 segundos (5000 milisegundos)
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

          

            for (String idVoluntario : idVoluntariosAceptados) {
                 // Publicar el evento con el idVoluntario y idTarea
                VoluntarioAceptadoEvent event = new VoluntarioAceptadoEvent(this, idVoluntario, nuevaTarea.getId());
                System.out.println("event: " + event);
                System.out.println("AQUÍ SE SUPONE QUE SE DISPARÓ EL EVENTO: " );
                logger.info("Publicando evento para el voluntario: " + idVoluntario + " y tarea: " + nuevaTarea.getId());
                applicationEventPublisher.publishEvent(event);
            }


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
