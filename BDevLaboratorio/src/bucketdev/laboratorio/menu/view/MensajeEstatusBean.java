package bucketdev.laboratorio.menu.view;

import bucketdev.laboratorio.type.BDevTipoMensaje;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Data;

public @Data class MensajeEstatusBean {
	/**
	 * @property <ImageView> icono imagen representativa del tipo de alerta
	 */
	private ImageView icono;
	/**
	 * @property <String> mensaje etiqueta para mostrar el mensaje
	 */
	private String mensaje;
	/**
	 * @property <CaypTipoStatusMensaje> tipoStatusMensaje enumeración para el
	 *           tipo de mensaje
	 */
	private BDevTipoMensaje tipoMensaje;
	/**
	 * @property <CaypTipoStatusMensaje> tipoStatusMensaje enumeración para el
	 *           tipo de mensaje
	 */
	private boolean permanecer;
	
	public MensajeEstatusBean(String _mensaje, BDevTipoMensaje _tipoMensaje, boolean _permanecer) {
		mensaje = _mensaje;
		tipoMensaje = _tipoMensaje;
		permanecer = _permanecer;
		icono = new ImageView(new Image(getClass().getResourceAsStream(_tipoMensaje.getIcono())));
	}
	
}
