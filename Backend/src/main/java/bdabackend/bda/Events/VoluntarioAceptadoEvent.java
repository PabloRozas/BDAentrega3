package bdabackend.bda.Events;

import org.springframework.context.ApplicationEvent;

public class VoluntarioAceptadoEvent  extends ApplicationEvent{

    private final String idVoluntario;
    private final String idTarea;

    public VoluntarioAceptadoEvent(Object source, String idVoluntario, String idTarea) {
        super(source);
        this.idVoluntario = idVoluntario;
        this.idTarea = idTarea;
    }

    public String getIdVoluntario() {
        return idVoluntario;
    }

    public String getIdTarea() {
        return idTarea;
    }


}
