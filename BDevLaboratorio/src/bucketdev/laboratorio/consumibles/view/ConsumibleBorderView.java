package bucketdev.laboratorio.consumibles.view;

import java.util.ArrayList;
import java.util.List;

import bucketdev.laboratorio.bean.AccionBean;
import bucketdev.laboratorio.bean.ConsumibleBean;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.busqueda.event.BusquedaCatalogoEvent;
import bucketdev.laboratorio.busqueda.event.ElementoCatalogoEvent;
import bucketdev.laboratorio.consumibles.event.ConsumibleEvent;
import bucketdev.laboratorio.consumibles.view.pane.ConsumibleGridPane;
import bucketdev.laboratorio.equipos.view.EquipoElementoCatalogoMensajePopUp;
import bucketdev.laboratorio.equipos.view.pane.EquipoElementoCatalogoBorder;
import bucketdev.laboratorio.modulo.controller.ModuloBorderView;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;

public class ConsumibleBorderView extends ModuloBorderView {

	private ConsumibleTableView tableView;
	private ConsumibleMensajePopUp mensajeConsumible;
	private ConsumibleGridPane consumibleGridPane;
	
	private EquipoElementoCatalogoMensajePopUp mensajeEquipo;
	private EquipoElementoCatalogoBorder equipoCatalogo;

	public ConsumibleBorderView(ModuloBean _moduloBean) {
		super(_moduloBean);
	}

	@Override
	public void init() {
		super.init();

		tableView = new ConsumibleTableView();
		setCenter(tableView);
	}

	@SuppressWarnings("rawtypes")
	public void mostrarMensajeConsumible(ConsumibleBean _consumibleBean) {
		String titulo = _consumibleBean == null ? "Agregar" : "Editar";
		BDevTipoMensaje tipoMensaje = _consumibleBean == null ? BDevTipoMensaje.AGREGAR : BDevTipoMensaje.EDITAR;
		consumibleGridPane = new ConsumibleGridPane(_consumibleBean);
		mensajeConsumible = new ConsumibleMensajePopUp(titulo, tipoMensaje, consumibleGridPane);
		mensajeConsumible.show();

		mensajeConsumible.addEventHandler(ConsumibleEvent.ACTUALIZAR_CONSUMIBLE, new EventHandler<ConsumibleEvent>() {

			@Override
			public void handle(ConsumibleEvent event) {
				fireEvent(event);
			}
		});

		mensajeConsumible.setOnHidden(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				tableView.cargar();
			}
		});

		mensajeConsumible.addEventHandler(BusquedaCatalogoEvent.SOLICITAR_BUSQUEDA, new EventHandler<BusquedaCatalogoEvent>() {

			@Override
			public void handle(BusquedaCatalogoEvent event) {
				fireEvent(event);
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
				consumibleGridPane.getBusquedaEquipo().setValue(equipoBean);
			}
		});
	}

	public ConsumibleBean getSeleccionado() {
		return tableView.getSeleccionado();
	}

	@Override
	public void validarPermisos() {
		List<Button> listaBotones = new ArrayList<>();
		for (AccionBean accion : getModuloBean().getListaAcciones()) {
			if (accion.getNombre().equals("agregar_consumibles")) {
				listaBotones.add(getBtAgregar());
			} else if (accion.getNombre().equals("editar_consumibles")) {
				listaBotones.add(getBtEditar());
			} else if (accion.getNombre().equals("eliminar_consumibles")) {
				listaBotones.add(getBtEliminar());
			}
		}

		getCajaBotones().getChildren().addAll(0, listaBotones);
	}

	@Override
	public void cargar() {
		tableView.cargar();
	}

}
