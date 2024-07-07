package bdabackend.bda.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Tarea")

public class MongoTareaEntity {


    @Id
    private String id; // MongoDB usa String como tipo de id

    @Field(name = "nombre")
    private String nombre;

    @Field(name = "descripcion")
    private String descripcion;

    @Field(name = "tipo")
    private String tipo;

    @Field(name = "zona")
    private Point zona;

    // Constructor sin argumentos
    public MongoTareaEntity() {
    }

    // Constructor con argumentos
    public MongoTareaEntity(String id, String nombre, String descripcion, String tipo, Point zona) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.zona = zona;
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