package bucketdev.laboratorio.menu.view;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.menu.event.MenuEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ModuloToggleButton extends ToggleButton {
	
	private ModuloBean moduloBean;

	private String styleBotonActivo;
	private String styleBoton;
	private ImageView icono;

	public ModuloToggleButton(ModuloBean _moduloBean) {
		getStyleClass().add("boton-proceso");
		setMinWidth(220);

		setText(_moduloBean.getNombre());
		setAlignment(Pos.CENTER_LEFT);
		setEllipsisString("");

		setStyleBoton(_moduloBean);
		setIcono(_moduloBean);
		moduloBean = _moduloBean;
		setEstadoInicial();

		atarEventos();
	}

	private void atarEventos() {
		setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				fireEvent(new MenuEvent(MenuEvent.MODULO_SELECCIONADO, moduloBean));
			}
		});
		
		setOnMouseEntered(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				((ModuloToggleButton) event.getSource()).setEstadoActivo();
			}
		});

		setOnMouseExited(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				ModuloToggleButton boton = (ModuloToggleButton) event.getSource();
				if (!boton.isSelected()) {
					boton.setEstadoInicial();
				}
			}
		});

		selectedProperty().addListener(cambiarSeleccionProceso(this));
	}

	public void setEstadoInicial() {
		setGraphic(icono);
		setStyle(styleBoton);
	}

	public void setEstadoActivo() {
		setStyle(styleBotonActivo);
	}

	private void setStyleBoton(ModuloBean _moduloBean) {
		String strTextColor = "-fx-text-fill: #666666;";
		String strTextColorActivo = "-fx-text-fill: #ffffff;";
		String strBackgroundColor = "-fx-background-color: #ecf0f1;";
		String strBackgroundColorActivo = "-fx-background-color: ";
		strBackgroundColorActivo += (_moduloBean.getColor() != null && !_moduloBean.getColor().equals("")
				? _moduloBean.getColor() : "#BDC3C7;");
		styleBoton = strTextColor + strBackgroundColor;
		styleBotonActivo = strTextColorActivo + strBackgroundColorActivo;
	}

	private void setIcono(ModuloBean _moduloBean) {
		icono = BDev.creaImagen(_moduloBean.getIcono());
	}

	public ChangeListener<Boolean> cambiarSeleccionProceso(ModuloToggleButton boton){
		return new ChangeListener<Boolean>() {

			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue){
					boton.setEstadoInicial();
				}
			}
		};
	}

}
