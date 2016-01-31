package bucketdev.laboratorio.equipos.view;

import java.util.List;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.equipos.viewmodel.EquipoViewModel;
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

public class EquipoTableView extends GenericoTableView<EquipoBean> {

	private EquipoViewModel viewModel;
	private List<EquipoBean> listaEquipos;

	private TableColumn<EquipoBean, String> tcNombre;
	private TableColumn<EquipoBean, String> tcClave;
	private TableColumn<EquipoBean, String> tcDescripcion;

	public EquipoTableView() {
		viewModel = new EquipoViewModel();

		tcNombre = new TableColumn<>("Nombre");
		estilizarColumna(tcNombre, "nombre", 0.33);
		tcClave = new TableColumn<>("Clave");
		estilizarColumna(tcClave, "clave", 0.33);
		tcDescripcion = new TableColumn<>("Descripción");
		estilizarColumna(tcDescripcion, "descripcion", 0.33);
	}

	@Override
	public int cargar() {
		Platform.runLater(new Runnable() {

			public void run() {
				Service<Object> srvEquipos = new Service<Object>() {

					@Override
					protected Task<Object> createTask() {
						return new Task<Object>() {

							@Override
							protected Object call() throws Exception {
								try {
									listaEquipos = viewModel.consulta();
								} catch (BDevException bde) {
									BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()),
											bde.getTipoMensaje());
								} catch (Exception e) {
									BDev.getLogger().error(BDev.getMensaje("equipos.consulta.error"), e);
									BDevMain.mostrarMensaje(BDev.getMensaje("equipos.consulta.error"),
											BDevTipoMensaje.ERROR);
								}
								return null;
							}

						};
					}

				};

				srvEquipos.stateProperty().addListener(new ChangeListener<Worker.State>() {

					public void changed(ObservableValue<? extends State> arg0, State arg1, State newState) {
						if (newState.equals(State.SUCCEEDED) || newState.equals(State.FAILED)) {
							if (newState.equals(State.SUCCEEDED)) {
								getItems().clear();
								getItems().addAll(listaEquipos);
							}
						}
					}
				});

				srvEquipos.start();
			}
		});
		return getItems().size();
	}

}
