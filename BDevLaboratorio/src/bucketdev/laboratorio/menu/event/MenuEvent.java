package bucketdev.laboratorio.menu.event;

import bucketdev.laboratorio.bean.ModuloBean;
import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

public class MenuEvent extends Event {
	
	private static final long serialVersionUID = -5262013366440432134L;
	private @Getter ModuloBean moduloBean;
	
	public static final EventType<MenuEvent> CERRAR = new EventType<MenuEvent>(Event.ANY, "CERRAR");
	public static final EventType<MenuEvent> MODULO_SELECCIONADO = new EventType<MenuEvent>(Event.ANY, "MODULO_SELECCIONADO");

	public MenuEvent(EventType<? extends Event> eventType) {
		super(eventType);
	}
	
	public MenuEvent(EventType<? extends Event> eventType, ModuloBean _moduloBean) {
		super(eventType);
		moduloBean = _moduloBean;
	}

}
