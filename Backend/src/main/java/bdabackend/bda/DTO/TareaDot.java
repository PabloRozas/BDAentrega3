package bdabackend.bda.DTO;

import jakarta.persistence.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Field;

public class TareaDot {

    private String id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String zona;
    private String emergencia;

    // Constructor
    public TareaDot(String id, String nombre, String descripcion, String tipo, String zona, String emergencia) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.zona = zona;
        this.emergencia = emergencia;
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

    // Sobrescribir el método toString()
    @Override
    public String toString() {
        return String.format("Id: %s, Nombre: %s, Descripción: %s, Tipo: %s, Zona: %s, Emergencia: %s",
                id, nombre, descripcion, tipo, zona, emergencia);
    }

}

