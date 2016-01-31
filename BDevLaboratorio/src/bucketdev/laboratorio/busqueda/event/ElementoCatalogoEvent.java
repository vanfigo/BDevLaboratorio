package bucketdev.laboratorio.busqueda.event;

import bucketdev.laboratorio.bean.ElementoCatalogoBean;
import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

public class ElementoCatalogoEvent<E> extends Event {
	
	private static final long serialVersionUID = 6560414730212230547L;
	
	private @Getter E elementoOriginal;
	private @Getter ElementoCatalogoBean elemento;
	private @Getter int id;
	
	@SuppressWarnings("rawtypes")
	public static final EventType<ElementoCatalogoEvent> ELEMENTO_SELECCIONADO = new EventType<ElementoCatalogoEvent>(
			Event.ANY, "ELEMENTO_SELECCIONADO");
	@SuppressWarnings("rawtypes")
	public static final EventType<ElementoCatalogoEvent> SIN_SELECCION = new EventType<ElementoCatalogoEvent>(
			Event.ANY, "SIN_SELECCION");
	
	public ElementoCatalogoEvent(EventType<? extends Event> arg0) {
		super(arg0);
	}
	
	public ElementoCatalogoEvent(EventType<? extends Event> arg0, ElementoCatalogoBean _elemento, E _elementoOtiginal) {
		super(arg0);
		elemento = _elemento;
		elementoOriginal = _elementoOtiginal;
	}
	
	public ElementoCatalogoEvent(EventType<? extends Event> arg0, int _id) {
		super(arg0);
		id = _id;
	}
}
