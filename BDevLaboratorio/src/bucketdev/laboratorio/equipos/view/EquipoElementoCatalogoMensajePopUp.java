package bucketdev.laboratorio.equipos.view;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.busqueda.event.ElementoCatalogoEvent;
import bucketdev.laboratorio.equipos.view.pane.EquipoElementoCatalogoBorder;
import bucketdev.laboratorio.type.BDevTipoBotonesMensaje;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.mensaje.MensajePopUp;
import javafx.event.EventHandler;

public class EquipoElementoCatalogoMensajePopUp extends MensajePopUp {
	
	@SuppressWarnings("rawtypes")
	public EquipoElementoCatalogoMensajePopUp(EquipoElementoCatalogoBorder _contenido) {
		super("Búsqueda de Equipo", _contenido, BDevTipoBotonesMensaje.ACEPTAR_CANCELAR, BDevTipoMensaje.BUSCAR);

		addEventHandler(ElementoCatalogoEvent.SIN_SELECCION, new EventHandler<ElementoCatalogoEvent>() {

			@Override
			public void handle(ElementoCatalogoEvent event) {
				BDevMain.mostrarMensaje(BDev.getMensaje("consumibles.equipo.sinseleccion"), BDevTipoMensaje.ALERTA);
			}
		});
	}

	@Override
	public void aceptar() {
		((EquipoElementoCatalogoBorder) getNodeCentro()).solicitarSeleccionar();

	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub

	}

}
