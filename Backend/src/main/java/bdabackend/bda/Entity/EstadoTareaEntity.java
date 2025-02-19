package bdabackend.bda.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estado_tarea")
public class EstadoTareaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "id_tarea")
    private String idTarea;

    // Constructores
    public EstadoTareaEntity() {
    }

    public EstadoTareaEntity(Boolean estado, String tarea) {
        this.estado = estado;
        this.idTarea = tarea;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public String getTarea() {
        return idTarea;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public void setTarea(String tarea) {
        this.idTarea = tarea;
    }

}
