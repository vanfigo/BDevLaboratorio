package bucketdev.laboratorio.ubicaciones.controller;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.bean.UbicacionBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.modulo.controller.ModuloController;
import bucketdev.laboratorio.modulo.event.ModuloEvent;
import bucketdev.laboratorio.type.BDevTipoBotonesMensaje;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.ubicaciones.event.UbicacionEvent;
import bucketdev.laboratorio.ubicaciones.view.UbicacionBorderView;
import bucketdev.laboratorio.ubicaciones.viewmodel.UbicacionViewModel;
import bucketdev.laboratorio.view.mensaje.MensajePopUp;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class UbicacionController extends ModuloController {

	private UbicacionBorderView view;
	private UbicacionViewModel viewModel;

	public UbicacionController(ModuloBean _moduloBean) {
		view = new UbicacionBorderView(_moduloBean);
		viewModel = new UbicacionViewModel();

		atarEventos();
	}

	public void atarEventos() {
		view.addEventHandler(ModuloEvent.AGREGAR, new EventHandler<ModuloEvent>() {

			@Override
			public void handle(ModuloEvent event) {
				view.mostrarMensajeUsuario(null);
			}
		});

		view.addEventHandler(ModuloEvent.EDITAR, new EventHandler<ModuloEvent>() {

			@Override
			public void handle(ModuloEvent event) {
				UbicacionBean ubicacionBean = view.getSeleccionado();
				if (ubicacionBean == null) {
					BDevMain.mostrarMensaje(BDev.getMensaje("ubicaciones.seleccionar.editar"));
				} else {
					view.mostrarMensajeUsuario(ubicacionBean);
				}
			}
		});

		view.addEventHandler(ModuloEvent.ELIMINAR, new EventHandler<ModuloEvent>() {

			@Override
			public void handle(ModuloEvent event) {
				UbicacionBean ubicacionBean = view.getSeleccionado();
				if (ubicacionBean == null) {
					BDevMain.mostrarMensaje(BDev.getMensaje("ubicaciones.seleccionar.eliminar"));
				} else {
					MensajePopUp mensaje = new MensajePopUp("¿Desea eliminar la ubicación?",
							new Label(BDev.getMensaje("ubicaciones.eliminar.confirmacion")),
							BDevTipoBotonesMensaje.ACEPTAR_CANCELAR, BDevTipoMensaje.ALERTA) {

						@Override
						public void guardar() {
						}

						@Override
						public void aceptar() {
							try {
								viewModel.eliminar(ubicacionBean);
								BDevMain.mostrarMensaje(BDev.getMensaje("ubicaciones.actualizar.correcto"),
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

		view.addEventHandler(UbicacionEvent.ACTUALIZAR_UBICACION, new EventHandler<UbicacionEvent>() {

			@Override
			public void handle(UbicacionEvent event) {
				try {
					viewModel.agregarOEditar(event.getUbicacionBean());
					BDevMain.mostrarMensaje(BDev.getMensaje("ubicaciones.actualizar.correcto"), BDevTipoMensaje.CORRECTO);
				} catch (BDevException bde) {
					BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
				}
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
