package bdabackend.bda.Entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;



@Document(collection = "ranking")
public class RankingEntity {
    @Id
    private String id;

    private Double nivel;

    private String nombreTarea;

    private String nombreVoluntario;

    private String numeroDocumentoVoluntario;

    private String idTarea;

    private String idVoluntario;

    private boolean tareaAceptada;

    // Constructor
    public RankingEntity() {
    }

    public RankingEntity(Double nivel, String nombreTarea, String nombreVoluntario, String numeroDocumentoVoluntario,
            String idTarea, String idVoluntario, boolean tareaAceptada) {
        this.nivel = nivel;
        this.nombreTarea = nombreTarea;
        this.nombreVoluntario = nombreVoluntario;
        this.numeroDocumentoVoluntario = numeroDocumentoVoluntario;
        this.idTarea = idTarea;
        this.idVoluntario = idVoluntario;
        this.tareaAceptada = tareaAceptada;
    }

    // Getters and Setters
    public String getId() {
        return this.id;
    }

    public Double getNivel() {
        return this.nivel;
    }

    public void setNivel(Double nivel) {
        this.nivel = nivel;
    }

    public String getNombreTarea() {
        return this.nombreTarea;
    }

    public void setNombreTarea(String nombreTarea) {
        this.nombreTarea = nombreTarea;
    }

    public String getNombreVoluntario() {
        return this.nombreVoluntario;
    }

    public void setNombreVoluntario(String nombreVoluntario) {
        this.nombreVoluntario = nombreVoluntario;
    }

    public String getNumeroDocumentoVoluntario() {
        return this.numeroDocumentoVoluntario;
    }

    public void setNumeroDocumentoVoluntario(String numeroDocumentoVoluntario) {
        this.numeroDocumentoVoluntario = numeroDocumentoVoluntario;
    }

    public String getTarea() {
        return this.idTarea;
    }

    public void setTarea(String tarea) {
        this.idTarea = tarea;
    }

    public String getVoluntario() {
        return this.idVoluntario;
    }

    public void setVoluntario(String voluntarioId) {
        this.idVoluntario = voluntarioId;
    }

    public boolean getTareaAceptada() {
        return this.tareaAceptada;
    }

    public void setTareaAceptada(boolean tareaAceptada) {
        this.tareaAceptada = tareaAceptada;
    }
}
