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
/*
    @ManyToOne
    @JoinColumn(name = "id_tarea")
    private MongoTareaEntity tarea;
*/
    @Column(name = "id_tarea")
    private String tareaId;

    @ManyToOne
    @JoinColumn(name = "id_emergencia_habilidad")
    private EmergenciaHabilidadEntity emergenciaHabilidad;

    // Constructor
    public TareaHabilidadEntity() {
    }

    public TareaHabilidadEntity(String habilidadRequerida, String tarea) {
        this.habilidadRequerida = habilidadRequerida;
        this.tareaId = tarea;
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
            return this.tareaId;
        }

        public void setTarea(String tarea) {
            this.tareaId = tarea;
        }

    public EmergenciaHabilidadEntity getEmergenciaHabilidad() {
        return this.emergenciaHabilidad;
    }

    public void setEmergenciaHabilidad(EmergenciaHabilidadEntity emergenciaHabilidad) {
        this.emergenciaHabilidad = emergenciaHabilidad;
    }

}
