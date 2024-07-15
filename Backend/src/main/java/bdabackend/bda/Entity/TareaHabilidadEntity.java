package bdabackend.bda.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tarea_habilidad")
public class TareaHabilidadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hablididad_requerida")
    private String habilidadRequerida;

    @Column(name = "id_tarea")
    private String idTarea;

    @ManyToOne
    @JoinColumn(name = "id_emergencia_habilidad")
    private EmergenciaHabilidadEntity emergenciaHabilidad;

    // Constructor
    public TareaHabilidadEntity() {
    }

    public TareaHabilidadEntity(String habilidadRequerida, String tarea) {
        this.habilidadRequerida = habilidadRequerida;
        this.idTarea = tarea;
    }

    // Getters and Setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHabilidadRequerida() {
        return this.habilidadRequerida;
    }

    public void setHabilidadRequerida(String habilidadRequerida) {
        this.habilidadRequerida = habilidadRequerida;
    }

    public String getTarea() {
        return this.idTarea;
    }

    public void setTarea(String tarea) {
        this.idTarea = tarea;
    }

    public EmergenciaHabilidadEntity getEmergenciaHabilidad() {
        return this.emergenciaHabilidad;
    }

    public void setEmergenciaHabilidad(EmergenciaHabilidadEntity emergenciaHabilidad) {
        this.emergenciaHabilidad = emergenciaHabilidad;
    }

}
