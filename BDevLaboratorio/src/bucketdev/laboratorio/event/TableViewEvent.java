package bucketdev.laboratorio.event;

import bucketdev.laboratorio.view.GenericoTableView;
import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

public class TableViewEvent extends Event {
	
	@SuppressWarnings("rawtypes")
	private @Getter GenericoTableView tableView;
	
	private static final long serialVersionUID = -1662040925993012822L;
	public static final EventType<TableViewEvent> CARGADO = new EventType<TableViewEvent>(Event.ANY, "CARGADO");

	@SuppressWarnings("rawtypes")
	public TableViewEvent(EventType<? extends Event> eventType, GenericoTableView _tableView) {
		super(eventType);
		tableView = _tableView;
	}

}
