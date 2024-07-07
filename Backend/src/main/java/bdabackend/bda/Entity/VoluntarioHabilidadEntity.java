package bdabackend.bda.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "voluntario_habilidad")
public class VoluntarioHabilidadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String idVoluntario;

    @ManyToOne
    @JoinColumn(name = "id_habilidad")
    private HabilidadEntity habilidad;

    // Constructor
    public VoluntarioHabilidadEntity() {
    }

    public VoluntarioHabilidadEntity(String idVoluntario, HabilidadEntity habilidad) {
        this.idVoluntario = idVoluntario;
        this.habilidad = habilidad;
    }

    // Getters and Setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdVoluntario() {
        return this.idVoluntario;
    }

    public void setVoluntario(String idVoluntario) {
        this.idVoluntario = idVoluntario;
    }

    public HabilidadEntity getHabilidad() {
        return this.habilidad;
    }

    public void setHabilidad(HabilidadEntity habilidad) {
        this.habilidad = habilidad;
    }
}
