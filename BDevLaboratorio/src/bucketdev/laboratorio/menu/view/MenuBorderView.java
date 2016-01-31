package bucketdev.laboratorio.menu.view;

import java.util.List;

import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.menu.view.pane.MenuInfoHBox;
import bucketdev.laboratorio.menu.view.pane.MenuMensajeHBox;
import bucketdev.laboratorio.menu.view.pane.MenuModulosVBox;
import bucketdev.laboratorio.modulo.controller.ModuloBorderView;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.Getter;

public class MenuBorderView extends BorderPane {
	
	private MenuInfoHBox menuInfoHBox;
	private MenuModulosVBox menuModulosVBox;
	private @Getter MenuMensajeHBox menuMensajeHBox;
	private AnchorPane anchorPane;

	public MenuBorderView() {
		menuInfoHBox = new MenuInfoHBox();
		menuModulosVBox = new MenuModulosVBox();
		menuMensajeHBox = new MenuMensajeHBox();
		anchorPane = new AnchorPane();
		
		init();
	}
	
	public void init() {
		setTop(menuInfoHBox);
		setLeft(menuModulosVBox);
		setBottom(menuMensajeHBox);
		setCenter(anchorPane);
	}
	
	public void cargarModulos(List<ModuloBean> _listaModulos) {
		menuModulosVBox.cargarModulos(_listaModulos);
	}
	
	public void actualizaModulo(Node _moduloView) {
		anchorPane.getChildren().clear();
		anchorPane.getChildren().add(_moduloView);
		
		AnchorPane.setTopAnchor(_moduloView, 0.0);
		AnchorPane.setRightAnchor(_moduloView, 0.0);
		AnchorPane.setBottomAnchor(_moduloView, 0.0);
		AnchorPane.setLeftAnchor(_moduloView, 0.0);
	}
	
	public ModuloBorderView getModulo() {
		if(anchorPane.getChildren().isEmpty()) {
			return null;
		}
		return (ModuloBorderView) anchorPane.getChildren().get(0);
	}
}
