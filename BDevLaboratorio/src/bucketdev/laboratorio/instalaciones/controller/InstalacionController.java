package bucketdev.laboratorio.instalaciones.controller;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.InstalacionBean;
import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.event.TableViewEvent;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.instalaciones.event.InstalacionEvent;
import bucketdev.laboratorio.instalaciones.view.InstalacionBorderView;
import bucketdev.laboratorio.instalaciones.view.InstalacionTableView;
import bucketdev.laboratorio.instalaciones.viewmodel.InstalacionViewModel;
import bucketdev.laboratorio.modulo.controller.ModuloController;
import bucketdev.laboratorio.modulo.event.ModuloEvent;
import bucketdev.laboratorio.type.BDevTipoBotonesMensaje;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.mensaje.MensajePopUp;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.converter.NumberStringConverter;

public class InstalacionController extends ModuloController {

	private InstalacionBorderView view;
	private InstalacionViewModel viewModel;

	public InstalacionController(ModuloBean _moduloBean) {
		view = new InstalacionBorderView(_moduloBean);
		viewModel = new InstalacionViewModel();

		atarEventos();
	}

	public void atarEventos() {
		view.addEventHandler(ModuloEvent.AGREGAR, new EventHandler<ModuloEvent>() {

			@Override
			public void handle(ModuloEvent event) {
				view.mostrarMensajeProducto();
			}
		});

//		view.addEventHandler(ModuloEvent.EDITAR, new EventHandler<ModuloEvent>() {
//
//			@Override
//			public void handle(ModuloEvent event) {
//				InstalacionBean instalacionBean = view.getSeleccionado();
//				if (instalacionBean == null) {
//					BDevMain.mostrarMensaje(BDev.getMensaje("instalaciones.seleccionar.editar"));
//				} else {
//					view.mostrarMensajeProducto(instalacionBean);
//				}
//			}
//		});

		view.addEventHandler(ModuloEvent.ELIMINAR, new EventHandler<ModuloEvent>() {

			@Override
			public void handle(ModuloEvent event) {
				InstalacionBean instalacionBean = view.getSeleccionado();
				if (instalacionBean == null) {
					BDevMain.mostrarMensaje(BDev.getMensaje("instalaciones.seleccionar.eliminar"));
				} else {
					MensajePopUp mensaje = new MensajePopUp("¿Desea eliminar la instalación?",
							new Label(BDev.getMensaje("instalaciones.eliminar.confirmacion")),
							BDevTipoBotonesMensaje.ACEPTAR_CANCELAR, BDevTipoMensaje.ALERTA) {

						@Override
						public void guardar() {
						}

						@Override
						public void aceptar() {
							try {
								viewModel.eliminar(instalacionBean);
								BDevMain.mostrarMensaje(BDev.getMensaje("instalaciones.actualizar.correcto"),
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

		view.addEventHandler(InstalacionEvent.ACTUALIZAR_INSTALACION, new EventHandler<InstalacionEvent>() {

			@Override
			public void handle(InstalacionEvent event) {
				try {
					viewModel.agregarOEditar(event.getInstalacionBean());
					BDevMain.mostrarMensaje(BDev.getMensaje("instalaciones.actualizar.correcto"), BDevTipoMensaje.CORRECTO);
				} catch (BDevException bde) {
					BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
				}
			}
		});
		
		view.addEventHandler(InstalacionEvent.RENOVAR, new EventHandler<InstalacionEvent>() {

			@Override
			public void handle(InstalacionEvent event) {
				InstalacionBean instalacionBean = view.getSeleccionado();
				if (instalacionBean == null) {
					BDevMain.mostrarMensaje(BDev.getMensaje("instalaciones.seleccionar.renovar"));
				} else {
					view.mostrarMensajeRenovarInstalacion(instalacionBean);
				}
			}
		});

		view.addEventHandler(InstalacionEvent.RENOVAR_INSTALACION, new EventHandler<InstalacionEvent>() {

			@Override
			public void handle(InstalacionEvent event) {
				try {
					viewModel.renovar(event.getInstalacionBean());
					BDevMain.mostrarMensaje(BDev.getMensaje("instalaciones.actualizar.correcto"), BDevTipoMensaje.CORRECTO);
				} catch (BDevException bde) {
					BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
				}
			}
		});
		
		view.addEventHandler(InstalacionEvent.FILTRAR_INSTALACION, new EventHandler<InstalacionEvent>() {

			@Override
			public void handle(InstalacionEvent event) {
				if (event.getFiltroBean().isPorCaducar()) {
					event.getFiltroBean().setDiasCaducidad(Integer.valueOf(view.getNfDiasCaducidad().getTextValue()));
				}
				view.setFiltroBean(event.getFiltroBean());
			}
		});
		
		view.addEventHandler(TableViewEvent.CARGADO, new EventHandler<TableViewEvent>() {

			@Override
			public void handle(TableViewEvent event) {
				String numDiasCaducidad = view.getNfDiasCaducidad().getTextValue().isEmpty()
						? BDev.getPropiedad("configuracion.caducidad") : view.getNfDiasCaducidad().getTextValue();
				InstalacionTableView tableView = ((InstalacionTableView) event.getTableView());
				for (InstalacionBean instalacionBean : tableView.getItems()) {
					view.getNfDiasCaducidad().textProperty().bindBidirectional(instalacionBean.diasCaducidadProperty(),
							new NumberStringConverter());
				}
				view.getNfDiasCaducidad().setText(numDiasCaducidad);
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
