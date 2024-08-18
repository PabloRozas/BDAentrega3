package bdabackend.bda.DTO;

import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;


import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Field;

public class TareaDot {

    private String id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String zona;
    private String emergencia;
    private int cantidadVoluntarios;
    private LocalDate fecha;
    private LocalDateTime hora;

    // Constructor
    public TareaDot(String id, String nombre, String descripcion, String tipo, String zona, String emergencia, int cantidadVoluntarios, LocalDate fecha, LocalDateTime hora) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.zona = zona;
        this.emergencia = emergencia;
        this.cantidadVoluntarios = cantidadVoluntarios;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getZona() { return zona; }
    public void setZona(String zona) { this.zona = zona; }

    public String getEmergencia() { return emergencia; }
    public void setEmergencia(String emergencia) { this.emergencia = emergencia; }

    public int getCantidadVoluntarios() { return cantidadVoluntarios; }
    public void setCantidadVoluntarios(int cantidadVoluntarios) { this.cantidadVoluntarios = cantidadVoluntarios; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalDateTime getHora() { return hora; }
    public void setHora(LocalDateTime hora) { this.hora = hora; }

    // Sobrescribir el método toString()
    @Override
    public String toString() {
        return String.format("Id: %s, Nombre: %s, Descripción: %s, Tipo: %s, Zona: %s, Emergencia: %s, Cantidad de Voluntarios: %d , Fecha: %s, Hora: %s",
                id, nombre, descripcion, tipo, zona, emergencia,cantidadVoluntarios, fecha, hora);
    }

}

