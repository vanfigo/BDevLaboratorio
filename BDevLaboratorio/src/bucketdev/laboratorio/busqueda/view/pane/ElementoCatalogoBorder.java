package bucketdev.laboratorio.busqueda.view.pane;

import java.util.ArrayList;
import java.util.List;

import bucketdev.laboratorio.bean.ElementoCatalogoBean;
import bucketdev.laboratorio.busqueda.event.ElementoCatalogoEvent;
import bucketdev.laboratorio.busqueda.facade.ElementoCatalogoFacade;
import bucketdev.laboratorio.busqueda.view.ElementoCatalogoTableView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;
import lombok.Setter;

public abstract class ElementoCatalogoBorder<A> extends BorderPane implements ElementoCatalogoFacade<A> {

	private @Getter @Setter List<A> listaElementosOriginales;
	private @Getter @Setter List<ElementoCatalogoBean> listaElementos;
	private @Getter @Setter ObservableList<ElementoCatalogoBean> elementosFiltrados = FXCollections.observableArrayList();
	
	private @Getter TextField txtBuscar;
	private ElementoCatalogoTableView<ElementoCatalogoBean> tbElementos;

	private @Getter String filtro;
	
	public ElementoCatalogoBorder(String _filtro, Object... params) {
		this(_filtro, "ID", "Valor", params);
	}

	public ElementoCatalogoBorder(String _filtro, String _thId, String _thValor, Object... params) {
		filtro = _filtro;
		setTop(buildTop());
		setCenter(buildCenter(_thId, _thValor));
		
		listaElementos = new ArrayList<>();

		guardarParams(params);

		obtenerElementos();

		atarEventos();

		tbElementos.setItems(elementosFiltrados);
		txtBuscar.setText(_filtro);
	}

	/*
	 * @see cayp.generico.control.busqueda.facade.ControlBusquedaPaneFacade#filtrarElementos()
	 */
	@Override
	public void filtrarElementos() {
		elementosFiltrados.clear();
		for (ElementoCatalogoBean elemento : listaElementos) {
			if (isEquals(elemento)) {
				elementosFiltrados.add(elemento);
			}
		}
	}

	protected void guardarParams(Object... _params) {}

	private Parent buildTop() {
		HBox cajaTxtBuscar = new HBox();
		cajaTxtBuscar.setPadding(new Insets(15L));

		txtBuscar = new TextField();
		txtBuscar.getStyleClass().add("txt-buscar");

		cajaTxtBuscar.getChildren().add(txtBuscar);
		HBox.setHgrow(txtBuscar, Priority.ALWAYS);

		return cajaTxtBuscar;
	}

	private ElementoCatalogoTableView<ElementoCatalogoBean> buildCenter(String _thId, String _thValor) {
		tbElementos = new ElementoCatalogoTableView<ElementoCatalogoBean>(_thId, _thValor);
		return tbElementos;
	}

	public void solicitarCancelar() {
		getScene().getWindow().hide();
	}

	public void solicitarSeleccionar() {
		dispararEvento();
	}

	@SuppressWarnings("rawtypes")
	private void atarEventos() {
		txtBuscar.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> ov, String viejo, String nuevo) {
				filtrarElementos();
			}
		});
		
		txtBuscar.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent evento) {
				if (evento.getCode().equals(KeyCode.DOWN) && !elementosFiltrados.isEmpty()) {
					tbElementos.requestFocus();
					tbElementos.getSelectionModel().selectFirst();
				}
			}
		});

		tbElementos.addEventHandler(ElementoCatalogoEvent.ELEMENTO_SELECCIONADO,
				new EventHandler<ElementoCatalogoEvent>() {

			@Override
			public void handle(ElementoCatalogoEvent evento) {
				evento.consume();
				dispararEvento();
			}
		});

		tbElementos.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER) && !elementosFiltrados.isEmpty()) {
					dispararEvento();
				}
			}
		});
	}

	private void dispararEvento() {
		ElementoCatalogoBean elemento = tbElementos.getSelectionModel().getSelectedItem();
		if (elemento != null) {
			fireEvent(new ElementoCatalogoEvent<A>(ElementoCatalogoEvent.ELEMENTO_SELECCIONADO, elemento, getBeanSeleccionado(elemento)));
			getScene().getWindow().hide();
		} else {
			fireEvent(new ElementoCatalogoEvent<A>(ElementoCatalogoEvent.SIN_SELECCION));
		}
	}

}
