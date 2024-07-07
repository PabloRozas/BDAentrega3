package bdabackend.bda.Service;


import bdabackend.bda.Entity.MongoTareaEntity;
import bdabackend.bda.Repository.MongoTareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MongoTareaService {
    @Autowired
    private MongoTareaRepository mongoTareaRepository;

    // Obtener todas las tareas
    public List<MongoTareaEntity> findAll() {
        return mongoTareaRepository.findAll();
    }

    // Obtener una tarea por ID
    public Optional<MongoTareaEntity> findById(String id) {
        return mongoTareaRepository.findById(id);
    }

    // Guardar una nueva tarea o actualizar una existente
    public MongoTareaEntity save(MongoTareaEntity tarea) {
        return mongoTareaRepository.save(tarea);
    }

    // Eliminar una tarea por ID
    public void deleteById(String id) {
        mongoTareaRepository.deleteById(id);
    }

    // Encontrar tareas por nombre
    public List<MongoTareaEntity> findByNombre(String nombre) {
        return mongoTareaRepository.findByNombre(nombre);
    }

    // Encontrar tareas por tipo
    public List<MongoTareaEntity> findByTipo(String tipo) {
        return mongoTareaRepository.findByTipo(tipo);
    }

    // Encontrar tareas por descripción que contengan una palabra clave
    public List<MongoTareaEntity> findByDescripcionContaining(String keyword) {
        return mongoTareaRepository.findByDescripcionContaining(keyword);
    }

}
