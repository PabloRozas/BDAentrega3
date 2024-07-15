package bdabackend.bda.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import bdabackend.bda.Entity.RankingEntity;

@Repository
public interface RankingRepository extends MongoRepository<RankingEntity, String> {
        // Crear
        RankingEntity insert(RankingEntity ranking);

        // Leer
        List<RankingEntity> findAll();

        RankingEntity findByid(String id);

        // Delete
        @Query(value = "{'id': ?0}", delete = true)
        void deleteByid(String id);

}
