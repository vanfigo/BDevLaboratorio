package bucketdev.laboratorio.login.event;

import javafx.event.Event;
import javafx.event.EventType;

public class LoginEvent extends Event {

	private static final long serialVersionUID = 7730126054523818418L;
	
	public static final EventType<LoginEvent> CANCELAR = new EventType<LoginEvent>(Event.ANY, "CANCELAR");
	public static final EventType<LoginEvent> INGRESAR = new EventType<LoginEvent>(Event.ANY, "INGRESAR");

	public LoginEvent(EventType<? extends Event> eventType) {
		super(eventType);
	}

}
