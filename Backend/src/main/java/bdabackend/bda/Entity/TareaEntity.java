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

    @Field("requerimientos")
    private String requerimientos;

    @Field("tipo")
    private String tipo;

    @Field("zona")
    private Point zona;

    @Field("idEmergencia")
    private Long idEmergencia;

    // Constructor
    public TareaEntity() {
    }

    public TareaEntity(String nombre, String descripcion, String tipo, Point zona, Long idEmergencia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.zona = zona;
        this.idEmergencia = idEmergencia;
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

    public Long getEmergencia() {
        return idEmergencia;
    }

    public void setEmergencia(Long idEmergencia) {
        this.idEmergencia = idEmergencia;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(String requerimientos) {
        this.requerimientos = requerimientos;
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
