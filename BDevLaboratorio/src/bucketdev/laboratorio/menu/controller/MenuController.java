package bucketdev.laboratorio.menu.controller;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.login.viewmodel.LoginViewModel;
import bucketdev.laboratorio.menu.event.MenuEvent;
import bucketdev.laboratorio.menu.view.MenuBorderView;
import bucketdev.laboratorio.menu.viewmodel.MenuViewModel;
import bucketdev.laboratorio.modulo.controller.ModuloController;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import lombok.Getter;

public class MenuController {
	
	private @Getter MenuBorderView view;
	private LoginViewModel loginViewModel;
	private MenuViewModel menuViewModel;
	
	public MenuController() {
		loginViewModel = new LoginViewModel();
		menuViewModel = new MenuViewModel();
		view = new MenuBorderView();
		
		init();
		atarEventos();
	}
	
	private void init() {
		BDevMain.getPrimaryStage().setScene(new Scene(view, BDev.PANTALLA.getWidth(), BDev.PANTALLA.getHeight()));
		try {
			view.cargarModulos(menuViewModel.consultarPorUsuario(BDev.getSesionBean().getUsuarioBean()));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void atarEventos() {
		view.addEventHandler(MenuEvent.CERRAR, new EventHandler<MenuEvent>() {

			@Override
			public void handle(MenuEvent event) {
				try {
					loginViewModel.cerrarSesion(BDev.getSesionBean().getUsuarioBean());
					BDevMain.mostrarLogin();
				} catch (BDevException e) {
					//TODO mostrar mensaje en interfaz
				}
			}
		});
		
		view.addEventHandler(MenuEvent.MODULO_SELECCIONADO, new EventHandler<MenuEvent>() {

			@Override
			public void handle(MenuEvent event) {
				ModuloBean moduloBean = event.getModuloBean();
				try {
					String id = String.valueOf(moduloBean.getId());
					if (view.getModulo() == null || !view.getModulo().getId().equals(id)) {
						Class<?> clase = Class.forName(moduloBean.getAccion());
						ModuloController moduloController = (ModuloController) clase.getDeclaredConstructor(ModuloBean.class).newInstance(moduloBean);
						moduloController.getView().setId(id);
						view.actualizaModulo(moduloController.getView());
					} else {
						view.getModulo().cargar();
					}
				} catch (Exception e) {
					String mensajeUsuario = BDev.getMensaje("menu.modulo.error");
					BDev.getLogger().error(mensajeUsuario, e);
					BDevMain.mostrarMensaje(mensajeUsuario, BDevTipoMensaje.ERROR);
				}
			}
		});
	}

}
