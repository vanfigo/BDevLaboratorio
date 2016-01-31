package bucketdev.laboratorio.equipos.event;

import bucketdev.laboratorio.bean.EquipoBean;
import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

public class EquipoEvent extends Event {
	
	private static final long serialVersionUID = -5227803253190387896L;
	private @Getter EquipoBean equipoBean;
	
	public static final EventType<EquipoEvent> ACTUALIZAR_EQUIPO = new EventType<EquipoEvent>(Event.ANY, "ACTUALIZAR_EQUIPO");

	public EquipoEvent(EventType<? extends Event> eventType) {
		super(eventType);
	}

	public EquipoEvent(EventType<? extends Event> eventType, EquipoBean _equipoBean) {
		super(eventType);
		equipoBean = _equipoBean;
	}

}
