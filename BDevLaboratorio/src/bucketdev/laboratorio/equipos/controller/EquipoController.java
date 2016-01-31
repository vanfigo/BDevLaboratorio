package bucketdev.laboratorio.equipos.controller;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.equipos.event.EquipoEvent;
import bucketdev.laboratorio.equipos.view.EquipoBorderView;
import bucketdev.laboratorio.equipos.viewmodel.EquipoViewModel;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.modulo.controller.ModuloController;
import bucketdev.laboratorio.modulo.event.ModuloEvent;
import bucketdev.laboratorio.type.BDevTipoBotonesMensaje;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.mensaje.MensajePopUp;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class EquipoController extends ModuloController {
	
	private EquipoBorderView view;
	private EquipoViewModel viewModel;
	
	public EquipoController(ModuloBean _moduloBean) {
		view = new EquipoBorderView(_moduloBean);
		viewModel = new EquipoViewModel();

		atarEventos();
	}

	public void atarEventos() {
		view.addEventHandler(ModuloEvent.AGREGAR, new EventHandler<ModuloEvent>() {

			@Override
			public void handle(ModuloEvent event) {
				view.mostrarMensajeEquipo(null);
			}
		});

		view.addEventHandler(ModuloEvent.EDITAR, new EventHandler<ModuloEvent>() {

			@Override
			public void handle(ModuloEvent event) {
				EquipoBean equipoBean = view.getSeleccionado();
				if (equipoBean == null) {
					BDevMain.mostrarMensaje(BDev.getMensaje("equipos.seleccionar.editar"));
				} else {
					view.mostrarMensajeEquipo(equipoBean);
				}
			}
		});

		view.addEventHandler(ModuloEvent.ELIMINAR, new EventHandler<ModuloEvent>() {

			@Override
			public void handle(ModuloEvent event) {
				EquipoBean equipoBean = view.getSeleccionado();
				if (equipoBean == null) {
					BDevMain.mostrarMensaje(BDev.getMensaje("equipos.seleccionar.eliminar"));
				} else {
					MensajePopUp mensaje = new MensajePopUp("¿Desea eliminar el equipo?",
							new Label(BDev.getMensaje("equipos.eliminar.confirmacion")),
							BDevTipoBotonesMensaje.ACEPTAR_CANCELAR, BDevTipoMensaje.ALERTA) {

						@Override
						public void guardar() {
						}

						@Override
						public void aceptar() {
							try {
								viewModel.eliminar(equipoBean);
								BDevMain.mostrarMensaje(BDev.getMensaje("equipos.actualizar.correcto"),
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

		view.addEventHandler(EquipoEvent.ACTUALIZAR_EQUIPO, new EventHandler<EquipoEvent>() {

			@Override
			public void handle(EquipoEvent event) {
				try {
					viewModel.agregarOEditar(event.getEquipoBean());
					BDevMain.mostrarMensaje(BDev.getMensaje("equipos.actualizar.correcto"), BDevTipoMensaje.CORRECTO);
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
