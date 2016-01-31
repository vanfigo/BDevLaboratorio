package bucketdev.laboratorio.busqueda.view;

import bucketdev.laboratorio.bean.ElementoCatalogoBean;
import bucketdev.laboratorio.busqueda.event.ElementoCatalogoEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class ElementoCatalogoTableView<A> extends TableView<ElementoCatalogoBean> {

	private TableColumn<ElementoCatalogoBean, String> colNombre;
	private TableColumn<ElementoCatalogoBean, String> colId;

	@SuppressWarnings("unchecked")
	public ElementoCatalogoTableView(String _id, String _valor) {

		colId = new TableColumn<ElementoCatalogoBean, String>(_id);
		colId .setCellValueFactory(new PropertyValueFactory<ElementoCatalogoBean, String>("id"));
		colId.setCellFactory(getIdCellFactory());
		colId.minWidthProperty().bind(widthProperty().multiply(0.1));

		colNombre = new TableColumn<ElementoCatalogoBean, String>(_valor);
		colNombre .setCellValueFactory(new PropertyValueFactory<ElementoCatalogoBean, String>("valor"));
		colNombre.setCellFactory(getNombreCellFactory());
		colNombre.minWidthProperty().bind(widthProperty().multiply(0.85));
		setMaxHeight(250);

		getColumns().addAll(colId, colNombre);
	}

	private <S> Callback<TableColumn<ElementoCatalogoBean, String>, TableCell<ElementoCatalogoBean, String>> getNombreCellFactory() {
		return new Callback<TableColumn<ElementoCatalogoBean, String>, TableCell<ElementoCatalogoBean, String>>() {

			@Override
			public TableCell<ElementoCatalogoBean, String> call(TableColumn<ElementoCatalogoBean, String> arg0) {
				final TableCell<ElementoCatalogoBean, String> cell = new TableCell<ElementoCatalogoBean, String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
					}

					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}
				};
				cell.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2) {
							fireEvent(new ElementoCatalogoEvent<A>(ElementoCatalogoEvent.ELEMENTO_SELECCIONADO, cell.getIndex()));
						}
					}
				});
				return cell;
			}
		};
	}

	private <S> Callback<TableColumn<ElementoCatalogoBean, String>, TableCell<ElementoCatalogoBean, String>> getIdCellFactory() {
		return new Callback<TableColumn<ElementoCatalogoBean, String>, TableCell<ElementoCatalogoBean, String>>() {

			@Override
			public TableCell<ElementoCatalogoBean, String> call(TableColumn<ElementoCatalogoBean, String> arg0) {
				final TableCell<ElementoCatalogoBean, String> cell = new TableCell<ElementoCatalogoBean, String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setText(empty ? null : getString());
						setGraphic(null);
					}

					private String getString() {
						return getItem() == null ? "" : getItem().toString();
					}
				};
				cell.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2) {
							fireEvent(new ElementoCatalogoEvent<A>(ElementoCatalogoEvent.ELEMENTO_SELECCIONADO, cell.getIndex()));
						}
					}
				});
				return cell;
			}
		};
	}
}
