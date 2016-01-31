package bucketdev.laboratorio.busqueda.event;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

public class BusquedaCatalogoEvent<A> extends Event {
	
	private static final long serialVersionUID = 4109344969089216036L;
	private @Getter A value;
	private @Getter String textValue;

	@SuppressWarnings("rawtypes")
	public static final EventType<BusquedaCatalogoEvent> SOLICITAR_BUSQUEDA = new EventType<BusquedaCatalogoEvent>(
			Event.ANY, "SOLICITAR_BUSQUEDA");
	@SuppressWarnings("rawtypes")
	public static final EventType<BusquedaCatalogoEvent> VALOR_MODIFICADO = new EventType<BusquedaCatalogoEvent>(
			Event.ANY, "VALOR_MODIFICADO");
	@SuppressWarnings("rawtypes")
	public static final EventType<BusquedaCatalogoEvent> SOLICITAR_LIMPIAR = new EventType<BusquedaCatalogoEvent>(
			Event.ANY, "SOLICITAR_LIMPIAR");

	public BusquedaCatalogoEvent(EventType<? extends Event> arg0) {
		super(arg0);
	}

	public BusquedaCatalogoEvent(EventType<? extends Event> arg0, A _value) {
		super(arg0);
		value = _value;
	}

	public BusquedaCatalogoEvent(EventType<? extends Event> arg0, String _textValue) {
		super(arg0);
		textValue = _textValue;
	}
}
