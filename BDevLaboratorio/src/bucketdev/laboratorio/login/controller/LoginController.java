package bucketdev.laboratorio.login.controller;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.SesionBean;
import bucketdev.laboratorio.bean.UsuarioBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.login.event.LoginEvent;
import bucketdev.laboratorio.login.view.LoginAnchorView;
import bucketdev.laboratorio.login.viewmodel.LoginViewModel;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class LoginController {

	private LoginAnchorView view;
	private LoginViewModel viewModel;

	public LoginController() {
		view = new LoginAnchorView();
		viewModel = new LoginViewModel();

		init();
		atarEventos();
	}

	public void init() {
		BDevMain.getPrimaryStage().setScene(new Scene(view, 400, 600));
	}

	private void atarEventos() {
		view.addEventHandler(LoginEvent.CANCELAR, new EventHandler<LoginEvent>() {

			@Override
			public void handle(LoginEvent event) {
				Platform.exit();
			}
		});
		view.addEventHandler(LoginEvent.INGRESAR, new EventHandler<LoginEvent>() {

			@Override
			public void handle(LoginEvent event) {
				UsuarioBean usuarioBean = view.getValues();
				if (usuarioBean == null) {
					BDev.showTooltip(view.getGpLogin().getTfUsuario(), "Debes ingresar un usuario y una contraseña");
				} else {
					try {
						usuarioBean = viewModel.validaCredenciales(usuarioBean);
						SesionBean sesionBean = new SesionBean();
						sesionBean.setUsuarioBean(usuarioBean);
						BDev.setSesionBean(sesionBean);

						viewModel.registrarSesion(usuarioBean);
						BDevMain.mostrarMenuPrincipal();
					} catch (BDevException bde) {
						BDev.showTooltip(view.getGpLogin().getTfUsuario(), BDev.getMensaje(bde.getCodigoError()));
					}
				}
			}
		});
	}
}
