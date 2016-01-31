package bucketdev.laboratorio.busqueda.view.pane;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.busqueda.event.BusquedaCatalogoEvent;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;

public class BusquedaCatalogo<A> extends HBox {
	private TextField txtElemento;
	private Button btnBuscar;
	private Button btnLimpiar;
	
	private BooleanProperty valid;

	private @Getter A value;

	public BusquedaCatalogo() {
		this("Buscar...");
	}

	public BusquedaCatalogo(String _prompt) {
		valid = new SimpleBooleanProperty(false);
		txtElemento = new TextField();
		txtElemento.setPromptText(_prompt);
		btnBuscar = new Button();

		init();

		atarEventos();
	}

	public void init() {
		HBox.setHgrow(this, Priority.ALWAYS);
		setAlignment(Pos.CENTER);
		
		txtElemento.setMinHeight(30);
		txtElemento.setMaxHeight(30);
		HBox.setHgrow(txtElemento, Priority.ALWAYS);

		btnBuscar.getStyleClass().addAll("warning", "boton-min");
		btnBuscar.setGraphic(BDev.creaImagen("boton.img.buscar"));
		btnBuscar.setFocusTraversable(false);

		btnLimpiar = new Button();
		btnLimpiar.getStyleClass().addAll("danger", "boton-min");
		btnLimpiar.setGraphic(BDev.creaImagen("boton.img.eliminar"));

		getChildren().addAll(txtElemento, btnBuscar);
	}

	private void atarEventos() {
		// Coloca el control no válido en caso que se esté escribiendo en el
		txtElemento.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent evento) {
				if (evento.getCode().equals(KeyCode.ENTER)) {
					// lanza el catálogo de búsqueda mediante presionar enter
					fireEvent(new BusquedaCatalogoEvent<A>(BusquedaCatalogoEvent.SOLICITAR_BUSQUEDA, txtElemento.getText()));
				} else {
					setValue(null);
				}
			}
		});
		// lanza el catálogo de búsqueda mediante el Button
		btnBuscar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				fireEvent(new BusquedaCatalogoEvent<A>(BusquedaCatalogoEvent.SOLICITAR_BUSQUEDA, txtElemento.getText()));
			}
		});
		btnLimpiar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				clear();
			}
		});
	}

	public void toggleLimpiar() {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				if (valid.get()) {
					if (!getChildren().contains(btnLimpiar)) {
						getChildren().add(1, btnLimpiar);
					}
				} else {
					if (txtElemento.getText().equals("")) {
						if (getChildren().contains(btnLimpiar)) {
							getChildren().remove(btnLimpiar);
						}
					} else {
						if (!getChildren().contains(btnLimpiar)) {
							getChildren().add(1, btnLimpiar);
						}
					}
				}
			
			}
		});
	}

	public void clear() {
		txtElemento.clear();
		setValue(null);
		fireEvent(new BusquedaCatalogoEvent<A>(BusquedaCatalogoEvent.SOLICITAR_LIMPIAR));
	}

	public void setValue(A _value) {
		value = _value;
		valid.set(value == null ? false : true);
		if(valid.get()){
			txtElemento.requestFocus();
			txtElemento.setText(value.toString());
		}
		toggleLimpiar();
		fireEvent(new BusquedaCatalogoEvent<A>(BusquedaCatalogoEvent.VALOR_MODIFICADO, value));
	}

	public String getText() {
		return txtElemento.getText();
	}

	public void setText(String _text) {
		txtElemento.setText(_text);
	}
}
