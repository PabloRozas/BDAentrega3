package bdabackend.bda.Repository;

import bdabackend.bda.Entity.TareaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

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
