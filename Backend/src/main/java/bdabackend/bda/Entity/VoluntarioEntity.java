package bdabackend.bda.Entity;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "voluntario")
public class VoluntarioEntity {
    // Atributos
    @Id
    private String id;

    private String nombre;

    private String correo;

    private String numeroDocumento;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint zonaVivienda;

    private String contrasena;

    private String equipamiento;

    private Set<RankingEntity> ranking = new HashSet<>();

    private Set<VoluntarioHabilidadEntity> voluntarioHabilidad = new HashSet<>();

    // Constructores
    public VoluntarioEntity() {
    }

    public VoluntarioEntity(String nombre, String correo, String numeroDocumento, GeoJsonPoint zonaVivienda,
            String contrasena, String equipamiento) {
        this.nombre = nombre;
        this.correo = correo;
        this.numeroDocumento = numeroDocumento;
        this.zonaVivienda = zonaVivienda;
        this.contrasena = contrasena;
        this.equipamiento = equipamiento;
    }

    // Getters and Setters
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroDocumento() {
        return this.numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public GeoJsonPoint getZonaVivienda() {
        return this.zonaVivienda;
    }

    public void setZonaVivienda(GeoJsonPoint zonaVivienda) {
        this.zonaVivienda = zonaVivienda;
    }

    public String getContrasena() {
        return this.contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEquipamiento() {
        return this.equipamiento;
    }

    public void setEquipamiento(String equipamiento) {
        this.equipamiento = equipamiento;
    }

    public Set<RankingEntity> getRanking() {
        return this.ranking;
    }

    public void setRanking(Set<RankingEntity> ranking) {
        this.ranking = ranking;
    }

    public Set<VoluntarioHabilidadEntity> getVoluntarioHabilidad() {
        return this.voluntarioHabilidad;
    }

    public void setVoluntarioHabilidad(Set<VoluntarioHabilidadEntity> voluntarioHabilidad) {
        this.voluntarioHabilidad = voluntarioHabilidad;
    }

    public void addRanking(RankingEntity ranking) {
        this.ranking.add(ranking);
    }

    public void removeRanking(RankingEntity ranking) {
        this.ranking.remove(ranking);
    }

    public void addVoluntarioHabilidad(VoluntarioHabilidadEntity voluntarioHabilidad) {
        this.voluntarioHabilidad.add(voluntarioHabilidad);
    }

    public void removeVoluntarioHabilidad(VoluntarioHabilidadEntity voluntarioHabilidad) {
        this.voluntarioHabilidad.remove(voluntarioHabilidad);
    }
}
