package bucketdev.laboratorio.equipos.view;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.equipos.event.EquipoEvent;
import bucketdev.laboratorio.equipos.view.pane.EquipoGridPane;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoBotonesMensaje;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.mensaje.MensajePopUp;

public class EquipoMensajePopUp extends MensajePopUp {
	
	public EquipoMensajePopUp(String _titulo, BDevTipoMensaje _tipoMensaje, EquipoGridPane _contenido) {
		super(_titulo, _contenido, BDevTipoBotonesMensaje.GUARDAR_CANCELAR, _tipoMensaje);
	}

	@Override
	public void aceptar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardar() {
		try {
			EquipoBean equipoBean = ((EquipoGridPane) getNodeCentro()).getValues();
			fireEvent(new EquipoEvent(EquipoEvent.ACTUALIZAR_EQUIPO, equipoBean));
			this.close();
		} catch (BDevException bde) {
			BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
		}
	}

}
