package bdabackend.bda.Repository;

import bdabackend.bda.Entity.TareaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TareaRepository extends MongoRepository<TareaEntity, String> {
    // Encontrar tareas por nombre
    List<TareaEntity> findByNombre(String nombre);

    // Encontrar tareas por tipo
    List<TareaEntity> findByTipo(String tipo);

    // Encontrar tareas por descripción que contengan una palabra clave
    List<TareaEntity> findByDescripcionContaining(String keyword);

    @Query(value = "{ '_id': ?0 }", fields = "{ 'nombre': 1 }")
    TareaEntity findNombreById(String id);

    @Query("{ 'emergencia.id': ?0 }")
    List<TareaEntity> findByEmergenciaId(Long emergenciaId);

}
