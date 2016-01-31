package bucketdev.laboratorio.ubicaciones.view;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.UbicacionBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoBotonesMensaje;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.ubicaciones.event.UbicacionEvent;
import bucketdev.laboratorio.ubicaciones.view.pane.UbicacionGridPane;
import bucketdev.laboratorio.view.mensaje.MensajePopUp;

public class UbicacionMensajePopUp extends MensajePopUp {
	
	public UbicacionMensajePopUp(String _titulo, BDevTipoMensaje _tipoMensaje, UbicacionGridPane _contenido) {
		super(_titulo, _contenido, BDevTipoBotonesMensaje.GUARDAR_CANCELAR, _tipoMensaje);
	}

	@Override
	public void aceptar() {}

	@Override
	public void guardar() {
		try {
			UbicacionBean ubicacionBean = ((UbicacionGridPane) getNodeCentro()).getValues();
			fireEvent(new UbicacionEvent(UbicacionEvent.ACTUALIZAR_UBICACION, ubicacionBean));
			this.close();
		} catch (BDevException bde) {
			BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
		}
	}

}
