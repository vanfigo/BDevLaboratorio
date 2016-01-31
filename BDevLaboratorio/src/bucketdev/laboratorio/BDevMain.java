package bucketdev.laboratorio;

import bucketdev.laboratorio.login.controller.LoginController;
import bucketdev.laboratorio.menu.controller.MenuController;
import bucketdev.laboratorio.menu.view.MenuBorderView;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;

/**
 * @author ISC Rodrigo Loyola
 * 
 */
public class BDevMain extends Application {

	private @Getter static Stage primaryStage;
	private static MenuBorderView menuBorderView;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage _primaryStage) throws Exception {
		primaryStage = _primaryStage;
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(BDev.getPropiedad("icono.img"))));
		primaryStage.setTitle("BDevLaboratorio");
		primaryStage.show();

		mostrarLogin();
	}

	@SuppressWarnings("unused")
	public static void mostrarLogin() {
		LoginController loginController = new LoginController();
		primaryStage.centerOnScreen();
		BDevMain.getPrimaryStage().getScene().getStylesheets()
				.add(BDevMain.class.getResource(BDev.getPropiedad("genericos.rutaCSS")).toExternalForm());
	}

	public static void mostrarMenuPrincipal() {
		MenuController menuController = new MenuController();
		menuBorderView = menuController.getView();

		primaryStage.centerOnScreen();
		BDevMain.getPrimaryStage().getScene().getStylesheets()
				.add(BDevMain.class.getResource(BDev.getPropiedad("genericos.rutaCSS")).toExternalForm());

		BDevMain.mostrarMensaje(
				String.format("¡Bienvenido %s!", BDev.obtenerNombreUsuario(BDev.getSesionBean().getUsuarioBean())));
	}

	/**
	 * Método que muestra un mensaje en la barra de estatus, por default el tipo
	 * de mensaje es de información
	 * 
	 * @param _mensaje
	 *            <String> con el mensaje a mostrar
	 */
	public static void mostrarMensaje(final String _mensaje) {
		BDevMain.mostrarMensaje(_mensaje, BDevTipoMensaje.INFORMACION, false);
	}

	/**
	 * Método que muestra un mensaje en la barra de estatus, con un tipo de
	 * mensaje, por default el mensaje se desvanece depués de un tiempo
	 * determinado
	 * 
	 * @param _mensaje
	 *            <String> con el mensaje a mostrar
	 */
	public static void mostrarMensaje(final String _mensaje, BDevTipoMensaje _tipoMensaje) {
		BDevMain.mostrarMensaje(_mensaje, _tipoMensaje, false);
	}

	/**
	 * Método que muestra un mensaje en la barra de estatus, desapareciendo o no
	 * automáticamente el mensaje, por default el mensaje se desvanece depués de
	 * un tiempo determinado
	 * 
	 * @param _mensaje
	 *            <String> con el mensaje a mostrar
	 */
	public static void mostrarMensaje(final String _mensaje, boolean _permanecer) {
		BDevMain.mostrarMensaje(_mensaje, BDevTipoMensaje.INFORMACION, _permanecer);
	}

	/**
	 * Métodoq ue muestra un mensaje en la barra de estatus, se indica que tipo
	 * de mensaje debe mostrar, esto afecta a color, icono y tiempo de mostrado
	 * 
	 * @param _mensaje
	 *            <String> con el mensaje a mostrar
	 * @param _tipoMensaje
	 *            <CaypTipoStatusMensaje> enum con el tipo de mensaje
	 */
	public static void mostrarMensaje(final String _mensaje, final BDevTipoMensaje _tipoMensaje, boolean _permanecer) {
		if (menuBorderView != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					menuBorderView.getMenuMensajeHBox().mostrarMensaje(_mensaje, _tipoMensaje, _permanecer);
				}
			});
		}
	}

}
