package bdabackend.bda.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import bdabackend.bda.Entity.VoluntarioEntity;

@Repository
public interface VoluntarioRepository extends MongoRepository<VoluntarioEntity, String> {
        VoluntarioEntity findByCorreo(String correo);

}
