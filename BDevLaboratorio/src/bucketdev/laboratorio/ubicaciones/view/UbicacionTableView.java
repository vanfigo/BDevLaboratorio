package bucketdev.laboratorio.ubicaciones.view;

import java.util.List;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.UbicacionBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.ubicaciones.viewmodel.UbicacionViewModel;
import bucketdev.laboratorio.view.GenericoTableView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.control.TableColumn;

public class UbicacionTableView extends GenericoTableView<UbicacionBean> {

	private UbicacionViewModel viewModel;
	private List<UbicacionBean> listaUbicaciones;

	private TableColumn<UbicacionBean, String> tcNombre;
	private TableColumn<UbicacionBean, String> tcDireccion;

	public UbicacionTableView() {
		viewModel = new UbicacionViewModel();

		tcNombre = new TableColumn<>("Nombre");
		estilizarColumna(tcNombre, "nombre", 0.50);
		tcDireccion = new TableColumn<>("Dirección");
		estilizarColumna(tcDireccion, "direccion", 0.49);
	}

	@Override
	public int cargar() {
		Platform.runLater(new Runnable() {

			public void run() {
				Service<Object> srvUbicaciones = new Service<Object>() {

					@Override
					protected Task<Object> createTask() {
						return new Task<Object>() {

							@Override
							protected Object call() throws Exception {
								try {
									listaUbicaciones = viewModel.consulta();
								} catch (BDevException bde) {
									BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()),
											bde.getTipoMensaje());
								} catch (Exception e) {
									BDev.getLogger().error(BDev.getMensaje("ubicaciones.consulta.error"), e);
									BDevMain.mostrarMensaje(BDev.getMensaje("ubicaciones.consulta.error"),
											BDevTipoMensaje.ERROR);
								}
								return null;
							}

						};
					}

				};

				srvUbicaciones.stateProperty().addListener(new ChangeListener<Worker.State>() {

					public void changed(ObservableValue<? extends State> arg0, State arg1, State newState) {
						if (newState.equals(State.SUCCEEDED) || newState.equals(State.FAILED)) {
							if (newState.equals(State.SUCCEEDED)) {
								getItems().clear();
								getItems().addAll(listaUbicaciones);
							}
						}
					}
				});

				srvUbicaciones.start();
			}
		});
		return getItems().size();
	}

}
