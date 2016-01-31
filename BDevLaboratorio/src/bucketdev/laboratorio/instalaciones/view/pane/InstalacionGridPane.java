package bucketdev.laboratorio.instalaciones.view.pane;

import bucketdev.laboratorio.bean.ConsumibleBean;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.bean.InstalacionBean;
import bucketdev.laboratorio.busqueda.event.BusquedaCatalogoEvent;
import bucketdev.laboratorio.busqueda.event.ElementoCatalogoEvent;
import bucketdev.laboratorio.busqueda.view.pane.BusquedaCatalogo;
import bucketdev.laboratorio.equipos.view.EquipoElementoCatalogoMensajePopUp;
import bucketdev.laboratorio.equipos.view.pane.EquipoElementoCatalogoBorder;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.usuarios.view.UbicacionCombo;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class InstalacionGridPane extends GridPane {

	private InstalacionBean instalacionBean;

	private UbicacionCombo cbUbicacion;
	private BusquedaCatalogo<EquipoBean> busquedaEquipo;
	
	private EquipoElementoCatalogoMensajePopUp mensajeEquipo;
	private EquipoElementoCatalogoBorder equipoCatalogo;

	public InstalacionGridPane() {
		busquedaEquipo = new BusquedaCatalogo<EquipoBean>();
		cbUbicacion = new UbicacionCombo();

		init();
		
		atarEventos();
	}

	public void init() {
		busquedaEquipo.setMinWidth(200);
		busquedaEquipo.setMaxWidth(200);
		cbUbicacion.setMinWidth(200);
		cbUbicacion.setMaxWidth(200);
		setVgap(10);
		add(new Label("Ubicacion: "), 0, 0);
		add(cbUbicacion, 1, 0);
		add(new Label("Equipo: "), 0, 1);
		add(busquedaEquipo, 1, 1);
	}
	
	@SuppressWarnings("rawtypes")
	public void atarEventos() {
		
		busquedaEquipo.addEventHandler(BusquedaCatalogoEvent.SOLICITAR_BUSQUEDA, new EventHandler<BusquedaCatalogoEvent>() {

			@Override
			public void handle(BusquedaCatalogoEvent event) {
				mostrarMensajeEquipo(event.getTextValue());
			}
		});
	}

	public boolean isValid() throws BDevException {
		instalacionBean = new InstalacionBean();
		if (cbUbicacion.getValue() == null) {
			throw new BDevException("instalaciones.ubicacion.error", BDevTipoMensaje.ALERTA);
		}		
		instalacionBean.setUbicacionBean(cbUbicacion.getValue());
		
		if (busquedaEquipo.getValue() == null) {
			throw new BDevException("instalaciones.equipo.error", BDevTipoMensaje.ALERTA);
		}
		ConsumibleBean consumibleBean = new ConsumibleBean();
		consumibleBean.setEquipoBean(busquedaEquipo.getValue());
		
		instalacionBean.setConsumibleBean(consumibleBean);
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public void mostrarMensajeEquipo(String _textValue) {
		equipoCatalogo = new EquipoElementoCatalogoBorder(_textValue);
		mensajeEquipo = new EquipoElementoCatalogoMensajePopUp(equipoCatalogo);
		mensajeEquipo.show();

		mensajeEquipo.addEventHandler(ElementoCatalogoEvent.ELEMENTO_SELECCIONADO, new EventHandler<ElementoCatalogoEvent>() {

			@Override
			public void handle(ElementoCatalogoEvent event) {
				EquipoBean equipoBean = ((EquipoBean) event.getElementoOriginal());
				busquedaEquipo.setValue(equipoBean);
			}
		});
	}

	public InstalacionBean getValues() throws BDevException {
		if (isValid()) {
			return instalacionBean;
		}
		return null;
	}

}
