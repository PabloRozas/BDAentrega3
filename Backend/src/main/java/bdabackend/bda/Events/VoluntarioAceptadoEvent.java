package bdabackend.bda.Events;

import org.springframework.context.ApplicationEvent;

public class VoluntarioAceptadoEvent  extends ApplicationEvent{

    private final String idVoluntario;

    public VoluntarioAceptadoEvent(Object source, String idVoluntario) {
        super(source);
        this.idVoluntario = idVoluntario;
    }

    public String getIdVoluntario() {
        return idVoluntario;
    }


}
