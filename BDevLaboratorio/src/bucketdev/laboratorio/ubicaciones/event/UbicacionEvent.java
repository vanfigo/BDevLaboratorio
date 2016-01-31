package bucketdev.laboratorio.ubicaciones.event;

import bucketdev.laboratorio.bean.UbicacionBean;
import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

public class UbicacionEvent extends Event {
	
	private static final long serialVersionUID = 3717281229202199000L;
	private @Getter UbicacionBean ubicacionBean;
	
	public static final EventType<UbicacionEvent> ACTUALIZAR_UBICACION = new EventType<UbicacionEvent>(Event.ANY, "ACTUALIZAR_UBICACION");

	public UbicacionEvent(EventType<? extends Event> eventType) {
		super(eventType);
	}

	public UbicacionEvent(EventType<? extends Event> eventType, UbicacionBean _ubicacionBean) {
		super(eventType);
		ubicacionBean = _ubicacionBean;
	}

}
