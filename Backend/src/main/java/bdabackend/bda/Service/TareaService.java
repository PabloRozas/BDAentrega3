package bdabackend.bda.Service;

import bdabackend.bda.Entity.RankingEntity;
import bdabackend.bda.Entity.RegionEntity;
import bdabackend.bda.Entity.TareaEntity;
import bdabackend.bda.Repository.TareaRepository;
import bdabackend.bda.Utils.GeoUtils;
import bdabackend.bda.Repository.RankingRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import bdabackend.bda.Repository.RegionRepository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class TareaService {
    private static final Logger logger = LoggerFactory.getLogger(TareaService.class);

    @Autowired
    private TareaRepository mongoTareaRepository;

    @Autowired
    private RegionService regionService;

    @Autowired
    private MyWebSocketHandler myWebSocketHandler;

    @Autowired
    private RankingRepository rankingRepository;



    // Guardar una nueva tarea o actualizar una existente
    public TareaEntity insertarTarea(String nombreTarea, String descripcionTarea, String tipoTarea, Point zona,
            Long emergencia, String requerimientos, int cantidadVoluntarios, LocalDate fecha, LocalDateTime hora) {
        TareaEntity tarea = new TareaEntity(nombreTarea, descripcionTarea, tipoTarea, zona, emergencia, requerimientos,
                cantidadVoluntarios, fecha, hora);
        logger.info("Guardando tarea: {}", tarea);
        TareaEntity tareaGuardada = mongoTareaRepository.save(tarea);

        // Crear el mensaje a enviar a los usuarios conectados
        String mensajeOpciones = "{\"tipo\":\"notificacion\", \"mensaje\":\"Selecciona una opción para:  " + nombreTarea
                + "\", \"opciones\":[\"Aceptar\", \"Rechazar\"]}";

        // Enviar el mensaje a todos los usuarios conectados (a través de WebSocket)
        myWebSocketHandler.enviarMensajeConOpcionesYProgramarBloqueo(mensajeOpciones, 120); // Aquí puedes ajustar el tiempo de
                                                                                   // bloqueo si es necesario

        return tareaGuardada;
    }

    //Ordenar rankingcandidatos a una tarea
    public List<RankingEntity> ordenarRankingCandidatos(String idTarea) {
        List<RankingEntity> rankings = rankingRepository.findByidTarea(idTarea);
        rankings.sort((ranking1, ranking2) -> ranking2.getNivel().compareTo(ranking1.getNivel()));
        return rankings;
    }

    //obtener candidatos para una tarea
    public void obtenerCandidatos(int cantidadVoluntarios, String idTarea) {
        List<RankingEntity> rankings = ordenarRankingCandidatos(idTarea);
        //crear lista vacía de rankings 
        List<RankingEntity> rankingsCandidatos = new ArrayList<>();


        //sacar los primeros x candidatos de rankings según cantidadVoluntarios de tarea:
        for (int i=0; i<cantidadVoluntarios; i++){
            rankingsCandidatos.add(rankings.get(i));
        }


        
    }


 




    public TareaEntity buscarTareaPorId(String id) {
        logger.info("Buscando tarea con id: {}", id);
        Optional<TareaEntity> tarea = mongoTareaRepository.findById(id);
        return tarea.orElse(null);
    }

    public List<TareaEntity> listaTarea() {
        logger.info("Listando todas las tareas");
        return mongoTareaRepository.findAll();
    }

    public void eliminarTareaPorId(String id) {
        logger.info("Eliminando tarea con id: {}", id);
        mongoTareaRepository.deleteById(id);
    }

    public String nombre(String id) {
        TareaEntity tarea = mongoTareaRepository.findNombreById(id);
        return tarea != null ? tarea.getNombre() : null;
    }

    public List<TareaEntity> tareaEmerg(Long emergenciaId) {
        return mongoTareaRepository.findByEmergenciaId(emergenciaId);
    }

    public List<TareaEntity> buscarTareaPorRegion(String region) throws Exception {
        RegionEntity regionEntity = regionService.nombre(region);
        logger.info("Buscando tareas en la región: {}", regionEntity.getNombre());
        List<TareaEntity> tareas = mongoTareaRepository.findAll();

        tareas.removeIf(tarea -> !GeoUtils.puntoEnMultiPolygon(tarea.getZona(), regionEntity.getGeometria()));

        return tareas;
    }

    //llamar a getVoluntariosAceptados de webSocketHandler
    public List<String> getVoluntariosCompletamenteAceptados() {
        return myWebSocketHandler.getVoluntariosAceptados();
    }

}
