package bucketdev.laboratorio.usuarios.event;

import bucketdev.laboratorio.bean.UsuarioBean;
import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

public class UsuarioEvent extends Event {
	
	private static final long serialVersionUID = 5760323582643073760L;
	private @Getter UsuarioBean usuarioBean;
	
	public static final EventType<UsuarioEvent> ACTUALIZAR_USUARIO = new EventType<UsuarioEvent>(Event.ANY, "ACTUALIZAR_USUARIO");

	public UsuarioEvent(EventType<? extends Event> eventType) {
		super(eventType);
	}

	public UsuarioEvent(EventType<? extends Event> eventType, UsuarioBean _usuarioBean) {
		super(eventType);
		usuarioBean = _usuarioBean;
	}

}
