package bucketdev.laboratorio.consumibles.view;

import java.util.List;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.ConsumibleBean;
import bucketdev.laboratorio.consumibles.viewmodel.ConsumibleViewModel;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.GenericoTableView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.control.TableColumn;

public class ConsumibleTableView extends GenericoTableView<ConsumibleBean> {

	private ConsumibleViewModel viewModel;
	private List<ConsumibleBean> listaConsumibles;

	private TableColumn<ConsumibleBean, String> tcClave;
	private TableColumn<ConsumibleBean, String> tcNombre;
	private TableColumn<ConsumibleBean, String> tcEquipo;
	private TableColumn<ConsumibleBean, String> tcCaducidad;

	public ConsumibleTableView() {
		viewModel = new ConsumibleViewModel();

		tcNombre = new TableColumn<>("Nombre");
		estilizarColumna(tcNombre, "nombre", 0.25);
		tcClave = new TableColumn<>("Clave");
		estilizarColumna(tcClave, "clave", 0.24);
		tcEquipo = new TableColumn<>("Equipo");
		estilizarColumna(tcEquipo, "nombreEquipo", 0.25);
		tcCaducidad = new TableColumn<>("Caducidad");
		estilizarColumna(tcCaducidad, "caducidad", 0.25);
	}

	@Override
	public int cargar() {
		Platform.runLater(new Runnable() {

			public void run() {
				Service<Object> srvConsumibles = new Service<Object>() {

					@Override
					protected Task<Object> createTask() {
						return new Task<Object>() {

							@Override
							protected Object call() throws Exception {
								try {
									listaConsumibles = viewModel.consulta(null);
								} catch (BDevException bde) {
									BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()),
											bde.getTipoMensaje());
								} catch (Exception e) {
									BDev.getLogger().error(BDev.getMensaje("consumibles.consulta.error"), e);
									BDevMain.mostrarMensaje(BDev.getMensaje("consumibles.consulta.error"),
											BDevTipoMensaje.ERROR);
								}
								return null;
							}

						};
					}

				};

				srvConsumibles.stateProperty().addListener(new ChangeListener<Worker.State>() {

					public void changed(ObservableValue<? extends State> arg0, State arg1, State newState) {
						if (newState.equals(State.SUCCEEDED) || newState.equals(State.FAILED)) {
							if (newState.equals(State.SUCCEEDED)) {
								getItems().clear();
								getItems().addAll(listaConsumibles);
							}
						}
					}
				});

				srvConsumibles.start();
			}
		});
		return getItems().size();
	}

}
