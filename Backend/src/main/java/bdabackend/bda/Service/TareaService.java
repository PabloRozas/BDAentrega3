package bdabackend.bda.Service;


import bdabackend.bda.Entity.TareaEntity;
import bdabackend.bda.Repository.TareaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {
    private static final Logger logger = LoggerFactory.getLogger(TareaService.class);

    @Autowired
    private TareaRepository mongoTareaRepository;

    // Guardar una nueva tarea o actualizar una existente
    public TareaEntity insertarTarea(String nombreTarea, String descripcionTarea, String tipoTarea, Point zona, Long emergencia) {
        TareaEntity tarea = new TareaEntity(nombreTarea, descripcionTarea, tipoTarea, zona, emergencia);
        logger.info("Guardando tarea: {}", tarea);
        return mongoTareaRepository.save(tarea);
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



}
