package bucketdev.laboratorio.usuarios.view;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.UsuarioBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoBotonesMensaje;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.usuarios.event.UsuarioEvent;
import bucketdev.laboratorio.usuarios.view.pane.UsuarioGridPane;
import bucketdev.laboratorio.view.mensaje.MensajePopUp;

public class UsuarioMensajePopUp extends MensajePopUp {
	
	public UsuarioMensajePopUp(String _titulo, BDevTipoMensaje _tipoMensaje, UsuarioGridPane _contenido) {
		super(_titulo, _contenido, BDevTipoBotonesMensaje.GUARDAR_CANCELAR, _tipoMensaje);
	}

	@Override
	public void aceptar() {}

	@Override
	public void guardar() {
		try {
			UsuarioBean usuarioBean = ((UsuarioGridPane) getNodeCentro()).getValues();
			fireEvent(new UsuarioEvent(UsuarioEvent.ACTUALIZAR_USUARIO, usuarioBean));
			this.close();
		} catch (BDevException bde) {
			BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
		}
	}

}
