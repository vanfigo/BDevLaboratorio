package bucketdev.laboratorio.instalaciones.view;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.InstalacionBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.instalaciones.event.InstalacionEvent;
import bucketdev.laboratorio.instalaciones.view.pane.InstalacionEditarGridPane;
import bucketdev.laboratorio.type.BDevTipoBotonesMensaje;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.mensaje.MensajePopUp;

public class InstalacionEditarMensajePopUp extends MensajePopUp {
	
	public InstalacionEditarMensajePopUp(InstalacionEditarGridPane _contenido) {
		super("Editar", _contenido, BDevTipoBotonesMensaje.GUARDAR_CANCELAR, BDevTipoMensaje.EDITAR);
	}

	@Override
	public void aceptar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardar() {
		try {
			InstalacionBean instalacionBean = ((InstalacionEditarGridPane) getNodeCentro()).getValues();
			fireEvent(new InstalacionEvent(InstalacionEvent.ACTUALIZAR_INSTALACION, instalacionBean));
			this.close();
		} catch (BDevException bde) {
			BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
		}
	}

}
