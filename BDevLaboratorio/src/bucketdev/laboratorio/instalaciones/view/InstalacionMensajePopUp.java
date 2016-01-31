package bucketdev.laboratorio.instalaciones.view;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.InstalacionBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.instalaciones.event.InstalacionEvent;
import bucketdev.laboratorio.instalaciones.view.pane.InstalacionGridPane;
import bucketdev.laboratorio.type.BDevTipoBotonesMensaje;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.mensaje.MensajePopUp;

public class InstalacionMensajePopUp extends MensajePopUp {
	
	public InstalacionMensajePopUp(String _titulo, BDevTipoMensaje _tipoMensaje, InstalacionGridPane _contenido) {
		super(_titulo, _contenido, BDevTipoBotonesMensaje.GUARDAR_CANCELAR, _tipoMensaje);
	}

	@Override
	public void aceptar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardar() {
		try {
			InstalacionBean instalacionBean = ((InstalacionGridPane) getNodeCentro()).getValues();
			fireEvent(new InstalacionEvent(InstalacionEvent.ACTUALIZAR_INSTALACION, instalacionBean));
			this.close();
		} catch (BDevException bde) {
			BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
		}
	}

}
