package bdabackend.bda.Entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "ranking")
public class RankingEntity {
    @Id
    private String id;

    private Integer nivel;

    private String tareaRanking;

    private String nombreVoluntario;

    private String numeroDocumentoVoluntario;

    private String idTarea;

    private String idVoluntario;

    // Constructor
    public RankingEntity() {
    }

    public RankingEntity(int nivel, String tareaRanking, String nombreVoluntario, String numeroDocumentoVoluntario,
            String idTarea, String idVoluntario) {
        this.nivel = nivel;
        this.tareaRanking = tareaRanking;
        this.nombreVoluntario = nombreVoluntario;
        this.numeroDocumentoVoluntario = numeroDocumentoVoluntario;
        this.idTarea = idTarea;
        this.idVoluntario = idVoluntario;
    }

    // Getters and Setters
    public String getId() {
        return this.id;
    }

    public int getNivel() {
        return this.nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getTareaRanking() {
        return this.tareaRanking;
    }

    public void setTareaRanking(String tareaRanking) {
        this.tareaRanking = tareaRanking;
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
}
