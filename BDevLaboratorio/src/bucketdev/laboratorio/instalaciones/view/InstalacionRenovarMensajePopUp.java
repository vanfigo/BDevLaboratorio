package bucketdev.laboratorio.instalaciones.view;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.InstalacionBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.instalaciones.event.InstalacionEvent;
import bucketdev.laboratorio.instalaciones.view.pane.InstalacionRenovarGridPane;
import bucketdev.laboratorio.type.BDevTipoBotonesMensaje;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.mensaje.MensajePopUp;

public class InstalacionRenovarMensajePopUp extends MensajePopUp {
	
	public InstalacionRenovarMensajePopUp(String _titulo, BDevTipoMensaje _tipoMensaje, InstalacionRenovarGridPane _contenido) {
		super(_titulo, _contenido, BDevTipoBotonesMensaje.GUARDAR_CANCELAR, _tipoMensaje);
	}

	@Override
	public void aceptar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardar() {
		try {
			InstalacionBean instalacionBean = ((InstalacionRenovarGridPane) getNodeCentro()).getValues();
			fireEvent(new InstalacionEvent(InstalacionEvent.RENOVAR_INSTALACION, instalacionBean));
			this.close();
		} catch (BDevException bde) {
			BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
		}
	}

}
