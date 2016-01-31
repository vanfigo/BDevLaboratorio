package bucketdev.laboratorio.instalaciones.event;

import bucketdev.laboratorio.bean.InstalacionBean;
import bucketdev.laboratorio.bean.InstalacionFiltroBean;
import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

public class InstalacionEvent extends Event {
	
	private static final long serialVersionUID = -5227803253190387896L;
	private @Getter InstalacionBean instalacionBean;
	private @Getter InstalacionFiltroBean filtroBean;
	
	public static final EventType<InstalacionEvent> ACTUALIZAR_INSTALACION = new EventType<InstalacionEvent>(Event.ANY, "ACTUALIZAR_INSTALACION");
	public static final EventType<InstalacionEvent> RENOVAR = new EventType<InstalacionEvent>(Event.ANY, "RENOVAR");
	public static final EventType<InstalacionEvent> RENOVAR_INSTALACION = new EventType<InstalacionEvent>(Event.ANY, "RENOVAR_INSTALACION");
	public static final EventType<InstalacionEvent> FILTRAR_INSTALACION = new EventType<InstalacionEvent>(Event.ANY, "FILTRAR_INSTALACION");
	public static final EventType<InstalacionEvent> EXPORTAR_EXCEL = new EventType<InstalacionEvent>(Event.ANY, "EXPORTAR_EXCEL");

	public InstalacionEvent(EventType<? extends Event> eventType) {
		super(eventType);
	}

	public InstalacionEvent(EventType<? extends Event> eventType, InstalacionBean _instalacionBean) {
		super(eventType);
		instalacionBean = _instalacionBean;
	}

	public InstalacionEvent(EventType<? extends Event> eventType, InstalacionFiltroBean _filtroBean) {
		super(eventType);
		filtroBean = _filtroBean;
	}

}
