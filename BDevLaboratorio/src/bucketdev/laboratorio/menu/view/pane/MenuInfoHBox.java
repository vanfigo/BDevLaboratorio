package bucketdev.laboratorio.menu.view.pane;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.menu.event.MenuEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MenuInfoHBox extends HBox {
	
	private Button btCerrar;
	
	public MenuInfoHBox() {
		btCerrar = new Button("Cerrar", BDev.creaImagen("boton.img.cerrar"));
		
		init();
		atarEventos();
	}
	
	public void init() {
		getStyleClass().add("toolbar");
		setAlignment(Pos.CENTER);
		setSpacing(10);
		
		btCerrar.getStyleClass().add("danger");
		Region spacer = new Region();
		Label lbtitulo = new Label("BDevLaboratorio");
		lbtitulo.getStyleClass().add("lbl-navegacion");
		ImageView icono = BDev.creaImagen("icono32.img");
		HBox.setHgrow(spacer, Priority.ALWAYS);
		Label lbUsuario = new Label(BDev.obtenerNombreUsuario(BDev.getSesionBean().getUsuarioBean()).toLowerCase());
		lbUsuario.getStyleClass().add("lbl-usuario");
		Label lbRol = new Label(BDev.getSesionBean().getUsuarioBean().getRolBean().getNombre().toLowerCase());
		lbRol.getStyleClass().add("lbl-rol");
		VBox cajaInfo = new VBox(5, lbUsuario, lbRol);
		
		getChildren().addAll(icono, lbtitulo, spacer, cajaInfo, btCerrar);
	}
	
	public void atarEventos() {
		btCerrar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				fireEvent(new MenuEvent(MenuEvent.CERRAR));
			}
		});
	}

}
