package bucketdev.laboratorio.usuarios.controller;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.bean.UsuarioBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.modulo.controller.ModuloController;
import bucketdev.laboratorio.modulo.event.ModuloEvent;
import bucketdev.laboratorio.type.BDevTipoBotonesMensaje;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.usuarios.event.UsuarioEvent;
import bucketdev.laboratorio.usuarios.view.UsuarioBorderView;
import bucketdev.laboratorio.usuarios.viewmodel.UsuarioViewModel;
import bucketdev.laboratorio.view.mensaje.MensajePopUp;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class UsuarioController extends ModuloController {

	private UsuarioBorderView view;
	private UsuarioViewModel viewModel;

	public UsuarioController(ModuloBean _moduloBean) {
		view = new UsuarioBorderView(_moduloBean);
		viewModel = new UsuarioViewModel();

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
				UsuarioBean usuarioBean = view.getSeleccionado();
				if (usuarioBean == null) {
					BDevMain.mostrarMensaje(BDev.getMensaje("usuarios.seleccionar.editar"));
				} else {
					view.mostrarMensajeUsuario(usuarioBean);
				}
			}
		});

		view.addEventHandler(ModuloEvent.ELIMINAR, new EventHandler<ModuloEvent>() {

			@Override
			public void handle(ModuloEvent event) {
				UsuarioBean usuarioBean = view.getSeleccionado();
				if (usuarioBean == null) {
					BDevMain.mostrarMensaje(BDev.getMensaje("usuarios.seleccionar.eliminar"));
				} else {
					MensajePopUp mensaje = new MensajePopUp("¿Desea eliminar al usuario?",
							new Label(BDev.getMensaje("usuarios.eliminar.confirmacion")),
							BDevTipoBotonesMensaje.ACEPTAR_CANCELAR, BDevTipoMensaje.ALERTA) {

						@Override
						public void guardar() {
						}

						@Override
						public void aceptar() {
							try {
								viewModel.eliminar(usuarioBean);
								BDevMain.mostrarMensaje(BDev.getMensaje("usuarios.actualizar.correcto"),
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

		view.addEventHandler(UsuarioEvent.ACTUALIZAR_USUARIO, new EventHandler<UsuarioEvent>() {

			@Override
			public void handle(UsuarioEvent event) {
				try {
					viewModel.agregarOEditar(event.getUsuarioBean());
					BDevMain.mostrarMensaje(BDev.getMensaje("usuarios.actualizar.correcto"), BDevTipoMensaje.CORRECTO);
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
