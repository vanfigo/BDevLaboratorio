package bucketdev.laboratorio.consumibles.event;

import bucketdev.laboratorio.bean.ConsumibleBean;
import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

public class ConsumibleEvent extends Event {
	
	private static final long serialVersionUID = 708570551912037962L;
	private @Getter ConsumibleBean consumibleBean;
	
	public static final EventType<ConsumibleEvent> ACTUALIZAR_CONSUMIBLE = new EventType<ConsumibleEvent>(Event.ANY, "ACTUALIZAR_CONSUMIBLE");

	public ConsumibleEvent(EventType<? extends Event> eventType) {
		super(eventType);
	}

	public ConsumibleEvent(EventType<? extends Event> eventType, ConsumibleBean _consumibleBean) {
		super(eventType);
		consumibleBean = _consumibleBean;
	}

}
