package bucketdev.laboratorio.instalaciones.view.pane;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.bean.InstalacionFiltroBean;
import bucketdev.laboratorio.busqueda.event.BusquedaCatalogoEvent;
import bucketdev.laboratorio.busqueda.event.ElementoCatalogoEvent;
import bucketdev.laboratorio.busqueda.view.pane.BusquedaCatalogo;
import bucketdev.laboratorio.consumibles.view.ConsumibleCombo;
import bucketdev.laboratorio.equipos.view.EquipoElementoCatalogoMensajePopUp;
import bucketdev.laboratorio.equipos.view.pane.EquipoElementoCatalogoBorder;
import bucketdev.laboratorio.instalaciones.event.InstalacionEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class InstalacionFiltroGridPane extends GridPane {

	private BusquedaCatalogo<EquipoBean> busquedaEquipo;
	private ConsumibleCombo consumibleCombo;
	private CheckBox chPorCaducar;
	private Button btLimpiar;
	private Button btFiltrar;
	
	private EquipoElementoCatalogoMensajePopUp mensajeEquipo;
	private EquipoElementoCatalogoBorder equipoCatalogo;
	
	public InstalacionFiltroGridPane() {
		busquedaEquipo = new BusquedaCatalogo<>();
		consumibleCombo = new ConsumibleCombo();
		chPorCaducar = new CheckBox("Por caducar");
		btFiltrar = new Button("Buscar", BDev.creaImagen("boton.img.buscar"));
		btLimpiar = new Button("Limpiar", BDev.creaImagen("boton.img.nuevo"));
		
		init();
		
		atarEventos();
	}
	
	public void init() {
		busquedaEquipo.setMinWidth(200);
		busquedaEquipo.setMaxWidth(200);
		consumibleCombo.setMinWidth(200);
		consumibleCombo.setMaxWidth(200);
		btFiltrar.getStyleClass().add("info");
		setHgap(20);
		getStyleClass().add("wall-pane");
		setPadding(new Insets(10));
		
		add(new Label("Equipo:"), 0, 0);
		add(busquedaEquipo, 1, 0);
		add(new Label("Consumible:"), 2, 0);
		add(consumibleCombo, 3, 0);
		add(chPorCaducar, 4, 0);
		add(btLimpiar, 5, 0);
		add(btFiltrar, 6, 0);
	}
	
	@SuppressWarnings("rawtypes")
	private void atarEventos() {
		
		busquedaEquipo.addEventHandler(BusquedaCatalogoEvent.SOLICITAR_BUSQUEDA, new EventHandler<BusquedaCatalogoEvent>() {

			@Override
			public void handle(BusquedaCatalogoEvent event) {
				mostrarMensajeEquipo(event.getTextValue());
			}
		});
		
		busquedaEquipo.addEventHandler(BusquedaCatalogoEvent.VALOR_MODIFICADO, new EventHandler<BusquedaCatalogoEvent>() {

			@Override
			public void handle(BusquedaCatalogoEvent event) {
				consumibleCombo.setEquipoBean((EquipoBean) event.getValue());
			}
		});
		
		btFiltrar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				fireEvent(new InstalacionEvent(InstalacionEvent.FILTRAR_INSTALACION, getValues()));
			}
		});
		
		btLimpiar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				busquedaEquipo.clear();
				consumibleCombo.setValue(null);
				chPorCaducar.setSelected(false);
				fireEvent(new InstalacionEvent(InstalacionEvent.FILTRAR_INSTALACION, getValues()));
			}
		});
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
	
	public InstalacionFiltroBean getValues() {
		InstalacionFiltroBean filtroBean = new InstalacionFiltroBean();
		filtroBean.setEquipoBean(busquedaEquipo.getValue());
		filtroBean.setConsumibleBean(consumibleCombo.getValue());
		filtroBean.setPorCaducar(chPorCaducar.isSelected());
		
		return filtroBean;
	}

}
