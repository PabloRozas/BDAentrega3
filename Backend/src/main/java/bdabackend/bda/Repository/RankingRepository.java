package bdabackend.bda.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import bdabackend.bda.Entity.RankingEntity;
import java.util.Optional;

@Repository
public interface RankingRepository extends MongoRepository<RankingEntity, String> {
        // Crear
        /**
         * Inserta un nuevo ranking en la base de datos
         * 
         * @param ranking ranking a insertar
         * @return ranking insertado
         */
        RankingEntity insert(RankingEntity ranking);

        // Leer

        /**
         * Obtiene todos los rankings de la base de datos
         * 
         * @return lista de rankings
         */
        List<RankingEntity> findAll();

        RankingEntity findByid(String id);

        // Delete

        /**
         * Elimina un ranking de la base de datos
         * 
         * @param ranking ranking a eliminar
         */
        @Query(value = "{'id': ?0}", delete = true)
        void deleteByid(String id);

        // FindByIdTarea

        /**
         * Obtiene el ranking de una tarea
         * 
         * @param idTarea id de la tarea
         * @return ranking de la tarea
         */

          List<RankingEntity> findByidTarea(String idTarea);

        // FindByIdVoluntario y idTarea
        Optional<RankingEntity> findByIdVoluntarioAndIdTarea(String idVoluntario, String idTarea);



}
