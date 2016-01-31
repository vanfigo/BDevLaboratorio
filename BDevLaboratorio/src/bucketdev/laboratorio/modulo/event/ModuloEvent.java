package bucketdev.laboratorio.modulo.event;

import javafx.event.Event;
import javafx.event.EventType;

public class ModuloEvent extends Event {
	
	private static final long serialVersionUID = 5447072675901265184L;
	public static final EventType<ModuloEvent> AGREGAR = new EventType<ModuloEvent>(Event.ANY, "AGREGAR");
	public static final EventType<ModuloEvent> EDITAR = new EventType<ModuloEvent>(Event.ANY, "EDITAR");
	public static final EventType<ModuloEvent> ELIMINAR = new EventType<ModuloEvent>(Event.ANY, "ELIMINAR");

	public ModuloEvent(EventType<? extends Event> eventType) {
		super(eventType);
	}

}
