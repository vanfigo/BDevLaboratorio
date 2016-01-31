package bucketdev.laboratorio.consumibles.view;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.ConsumibleBean;
import bucketdev.laboratorio.consumibles.event.ConsumibleEvent;
import bucketdev.laboratorio.consumibles.view.pane.ConsumibleGridPane;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoBotonesMensaje;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.mensaje.MensajePopUp;

public class ConsumibleMensajePopUp extends MensajePopUp {
	
	public ConsumibleMensajePopUp(String _titulo, BDevTipoMensaje _tipoMensaje, ConsumibleGridPane _contenido) {
		super(_titulo, _contenido, BDevTipoBotonesMensaje.GUARDAR_CANCELAR, _tipoMensaje);
	}

	@Override
	public void aceptar() {}

	@Override
	public void guardar() {
		try {
			ConsumibleBean consumibleBean = ((ConsumibleGridPane) getNodeCentro()).getValues();
			fireEvent(new ConsumibleEvent(ConsumibleEvent.ACTUALIZAR_CONSUMIBLE, consumibleBean));
			this.close();
		} catch (BDevException bde) {
			BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
		}
	}

}
