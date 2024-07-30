package bdabackend.bda.Repository;

import bdabackend.bda.Entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Map;
import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, Long> {

    @Query(value = "SELECT id, ST_AsText(geometria) as geometria, nombre FROM regiones_chile WHERE nombre = :nombre", nativeQuery = true)
    Map<String, Object> findByNombre(@Param("nombre") String nombre);

    @Query(value = "SELECT * FROM regiones_chile", nativeQuery = true)
    List<?> findAllRegiones();
}
