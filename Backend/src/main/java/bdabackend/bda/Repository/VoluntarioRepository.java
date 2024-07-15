package bdabackend.bda.Repository;

import bdabackend.bda.Entity.TareaEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import bdabackend.bda.Entity.VoluntarioEntity;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public interface VoluntarioRepository extends MongoRepository<VoluntarioEntity, String> {
        VoluntarioEntity findByCorreo(String correo);
}
