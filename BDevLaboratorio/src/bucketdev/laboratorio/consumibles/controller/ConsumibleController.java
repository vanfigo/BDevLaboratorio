package bucketdev.laboratorio.consumibles.controller;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.ConsumibleBean;
import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.busqueda.event.BusquedaCatalogoEvent;
import bucketdev.laboratorio.busqueda.event.ElementoCatalogoEvent;
import bucketdev.laboratorio.consumibles.event.ConsumibleEvent;
import bucketdev.laboratorio.consumibles.view.ConsumibleBorderView;
import bucketdev.laboratorio.consumibles.viewmodel.ConsumibleViewModel;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.modulo.controller.ModuloController;
import bucketdev.laboratorio.modulo.event.ModuloEvent;
import bucketdev.laboratorio.type.BDevTipoBotonesMensaje;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.mensaje.MensajePopUp;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class ConsumibleController extends ModuloController {

	private ConsumibleBorderView view;
	private ConsumibleViewModel viewModel;

	public ConsumibleController(ModuloBean _moduloBean) {
		view = new ConsumibleBorderView(_moduloBean);
		viewModel = new ConsumibleViewModel();

		atarEventos();
	}

	@SuppressWarnings("rawtypes")
	public void atarEventos() {
		view.addEventHandler(ModuloEvent.AGREGAR, new EventHandler<ModuloEvent>() {

			@Override
			public void handle(ModuloEvent event) {
				view.mostrarMensajeConsumible(null);
			}
		});

		view.addEventHandler(ModuloEvent.EDITAR, new EventHandler<ModuloEvent>() {

			@Override
			public void handle(ModuloEvent event) {
				ConsumibleBean consumibleBean = view.getSeleccionado();
				if (consumibleBean == null) {
					BDevMain.mostrarMensaje(BDev.getMensaje("consumibles.seleccionar.editar"));
				} else {
					view.mostrarMensajeConsumible(consumibleBean);
				}
			}
		});

		view.addEventHandler(ModuloEvent.ELIMINAR, new EventHandler<ModuloEvent>() {

			@Override
			public void handle(ModuloEvent event) {
				ConsumibleBean consumibleBean = view.getSeleccionado();
				if (consumibleBean == null) {
					BDevMain.mostrarMensaje(BDev.getMensaje("consumibles.seleccionar.eliminar"));
				} else {
					MensajePopUp mensaje = new MensajePopUp("¿Desea eliminar el consumible?",
							new Label(BDev.getMensaje("consumibles.eliminar.confirmacion")),
							BDevTipoBotonesMensaje.ACEPTAR_CANCELAR, BDevTipoMensaje.ALERTA) {

						@Override
						public void guardar() {
						}

						@Override
						public void aceptar() {
							try {
								viewModel.eliminar(consumibleBean);
								BDevMain.mostrarMensaje(BDev.getMensaje("consumibles.actualizar.correcto"),
										BDevTipoMensaje.CORRECTO);
								view.cargar();
								this.close();
							} catch (BDevException bde) {
								BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
							}
						}
					};
					mensaje.show();
				}
			}
		});

		view.addEventHandler(ConsumibleEvent.ACTUALIZAR_CONSUMIBLE, new EventHandler<ConsumibleEvent>() {

			@Override
			public void handle(ConsumibleEvent event) {
				try {
					viewModel.agregarOEditar(event.getConsumibleBean());
					BDevMain.mostrarMensaje(BDev.getMensaje("consumibles.actualizar.correcto"), BDevTipoMensaje.CORRECTO);
				} catch (BDevException bde) {
					BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
				}
			}
		});
		
		view.addEventHandler(BusquedaCatalogoEvent.SOLICITAR_BUSQUEDA, new EventHandler<BusquedaCatalogoEvent>() {

			@Override
			public void handle(BusquedaCatalogoEvent event) {
				view.mostrarMensajeEquipo(event.getTextValue());
			}
		});
		
		view.addEventHandler(ElementoCatalogoEvent.SIN_SELECCION, new EventHandler<ElementoCatalogoEvent>() {

			@Override
			public void handle(ElementoCatalogoEvent event) {
				BDevMain.mostrarMensaje(BDev.getMensaje("consumibles.equipo.sinseleccion"), BDevTipoMensaje.ALERTA);
			}
		});
		
		view.addEventHandler(ElementoCatalogoEvent.ELEMENTO_SELECCIONADO, new EventHandler<ElementoCatalogoEvent>() {

			@Override
			public void handle(ElementoCatalogoEvent event) {
				System.out.println(event);
			}
		});
	}

	@Override
	public Node getView() {
		return view;
	}

	@Override
	public void cargar() {
		view.cargar();
	}

}
