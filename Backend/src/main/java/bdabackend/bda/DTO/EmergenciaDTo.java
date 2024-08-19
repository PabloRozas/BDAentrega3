package bdabackend.bda.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmergenciaDTo {
    private Long id;
    private String tipoEmergencia;
    private String condicionFisica;
    private Integer cantidadVoluntariosMin;
    private Integer cantidadVoluntariosMax;
    private String idInstitucion;

    public EmergenciaDTo(Long id, String tipoEmergencia, String condicionFisica,
            Integer cantidadVoluntariosMin, Integer cantidadVoluntariosMax, String idInstitucion) {
        this.id = id;
        this.tipoEmergencia = tipoEmergencia;
        this.condicionFisica = condicionFisica;
        this.cantidadVoluntariosMin = cantidadVoluntariosMin;
        this.cantidadVoluntariosMax = cantidadVoluntariosMax;
        this.idInstitucion = idInstitucion;
    }

    // Getters y setters
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("tipoEmergencia")
    public String getTipoEmergencia() {
        return tipoEmergencia;
    }

    @JsonProperty("condicionFisica")
    public String getCondicionFisica() {
        return condicionFisica;
    }

    @JsonProperty("cantidadVoluntariosMin")
    public Integer getCantidadVoluntariosMin() {
        return cantidadVoluntariosMin;
    }

    @JsonProperty("cantidadVoluntariosMax")
    public Integer getCantidadVoluntariosMax() {
        return cantidadVoluntariosMax;
    }

    @JsonProperty("idInstitucion")
    public String getIdInstitucion() {
        return idInstitucion;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("tipoEmergencia")
    public void setTipoEmergencia(String tipoEmergencia) {
        this.tipoEmergencia = tipoEmergencia;
    }

    @JsonProperty("condicionFisica")
    public void setCondicionFisica(String condicionFisica) {
        this.condicionFisica = condicionFisica;
    }

    @JsonProperty("cantidadVoluntariosMin")
    public void setCantidadVoluntariosMin(Integer cantidadVoluntariosMin) {
        this.cantidadVoluntariosMin = cantidadVoluntariosMin;
    }

    @JsonProperty("cantidadVoluntariosMax")
    public void setCantidadVoluntariosMax(Integer cantidadVoluntariosMax) {
        this.cantidadVoluntariosMax = cantidadVoluntariosMax;
    }

    @JsonProperty("idInstitucion")
    public void setIdInstitucion(String idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

}
