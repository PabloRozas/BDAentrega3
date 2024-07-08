package bdabackend.bda.Entity;

import jakarta.persistence.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Tarea")

public class TareaEntity {
    @Id
    private String id;

    @Field("nombre")
    private String nombre;

    @Field("descripcion")
    private String descripcion;

    @Field("tipo")
    private String tipo;

    @Field("zona")
    private Point zona;
    /*
    @DBRef
    private Set<RankingEntity> ranking = new HashSet<>();

    @DBRef
    private Set<TareaHabilidadEntity> tareaHabilidad = new HashSet<>();

    @DBRef
    private Set<EstadoTareaEntity> estadoTarea = new HashSet<>();
    */



    @Field("emergencia")
    private Long emergencia;





    // Constructor
    public TareaEntity() {
    }

    public TareaEntity(String nombre, String descripcion, String tipo, Point zona, Long emergencia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.zona = zona;
        this.emergencia = emergencia;
    }

    // Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
       /*

    public Set<RankingEntity> getRanking() {
        return ranking;
    }

    public void setRanking(Set<RankingEntity> ranking) {
        this.ranking = ranking;
    }


    public Set<TareaHabilidadEntity> getTareaHabilidad() {
        return tareaHabilidad;
    }

    public void setTareaHabilidad(Set<TareaHabilidadEntity> tareaHabilidad) {
        this.tareaHabilidad = tareaHabilidad;
    }

    public Set<EstadoTareaEntity> getEstadoTarea() {
        return estadoTarea;
    }

    public void setEstadoTarea(Set<EstadoTareaEntity> estadoTarea) {
        this.estadoTarea = estadoTarea;
    }

     */

    public Long getEmergencia() {
        return emergencia;
    }

    public void setEmergencia(Long emergencia) {
        this.emergencia = emergencia;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Point getZona() {
        return zona;
    }

    public void setZona(Point zona) {
        this.zona = zona;
    }
}
