package bdabackend.bda.Entity;

import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;
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

    @Field("cantidadVoluntarios")
    private int cantidadVoluntarios;

    @Field("fecha")
    private LocalDate fecha;

    @Field("hora")
    private LocalDateTime hora;


    // Constructor
    public TareaEntity() {
    }

    public TareaEntity(String nombre, String descripcion, String tipo, Point zona, Long idEmergencia, String requerimientos, int cantidadVoluntarios, LocalDate fecha, LocalDateTime hora) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.zona = zona;
        this.idEmergencia = idEmergencia;
        this.requerimientos = requerimientos;
        this.cantidadVoluntarios = cantidadVoluntarios;
        this.fecha = fecha;
        this.hora = hora;
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

    public int getCantidadVoluntarios() {
        return cantidadVoluntarios;
    }

    public void setCantidadVoluntarios(int cantidadVoluntarios) {
        this.cantidadVoluntarios = cantidadVoluntarios;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDateTime getHora() {
        return hora;
    }

    public void setHora(LocalDateTime hora) {
        this.hora = hora;
    }
}
