package bdabackend.bda.Repository;

import bdabackend.bda.Entity.MongoTareaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoTareaRepository  extends MongoRepository<MongoTareaEntity, String> {
    // Encontrar tareas por nombre
    List<MongoTareaEntity> findByNombre(String nombre);

    // Encontrar tareas por tipo
    List<MongoTareaEntity> findByTipo(String tipo);

    // Encontrar tareas por descripción que contengan una palabra clave
    List<MongoTareaEntity> findByDescripcionContaining(String keyword);

}
