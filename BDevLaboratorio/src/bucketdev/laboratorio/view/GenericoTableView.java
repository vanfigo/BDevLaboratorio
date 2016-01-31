package bucketdev.laboratorio.view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public abstract class GenericoTableView<A> extends TableView<A> {
	
	public GenericoTableView() {
		this(true);
	}
	
	public GenericoTableView(boolean _loadOnRender) {
		if(_loadOnRender){
			cargar();
		}
	}
	
	public abstract int cargar();
	
	public A getSeleccionado() {
		return getSelectionModel().getSelectedItem();
	}

	protected <B> void estilizarColumna(final TableColumn<A, B> _columna, String _id, double _ancho) {
		_columna.setCellValueFactory(new PropertyValueFactory<A, B>(_id));
		_columna.setId(_id);
		redimensionaColumna(_columna, _ancho);
		getColumns().add(_columna);
	}
	
	protected <B> void redimensionaColumna(final TableColumn<A, B> _columna, double _ancho){
		_columna.minWidthProperty().bind(this.widthProperty().multiply(_ancho));
		_columna.prefWidthProperty().bind(this.widthProperty().multiply(_ancho));
	}

}
