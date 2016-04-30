package bucketdev.laboratorio.instalaciones.view;

import java.util.List;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.InstalacionBean;
import bucketdev.laboratorio.bean.InstalacionFiltroBean;
import bucketdev.laboratorio.event.TableViewEvent;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.instalaciones.view.column.InstalacionFechaCaducidadColumn;
import bucketdev.laboratorio.instalaciones.viewmodel.InstalacionViewModel;
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

public class InstalacionTableView extends GenericoTableView<InstalacionBean> {

	private InstalacionViewModel viewModel;
	private List<InstalacionBean> listaProductos;
	private InstalacionFiltroBean filtroBean;

	private TableColumn<InstalacionBean, String> tcEquipo;
	private TableColumn<InstalacionBean, String> tcConsumible;
	private TableColumn<InstalacionBean, String> tcSerie;
	private TableColumn<InstalacionBean, String> tcUbicacion;
	private TableColumn<InstalacionBean, Integer> tcCaducidad;
	private InstalacionFechaCaducidadColumn tcFechaCaducidad;

	public InstalacionTableView() {
		viewModel = new InstalacionViewModel();

		tcEquipo = new TableColumn<>("Equipo");
		estilizarColumna(tcEquipo, "nombreEquipo", 0.15);
		tcConsumible = new TableColumn<>("Consumible");
		estilizarColumna(tcConsumible, "nombreConsumible", 0.15);
		tcSerie = new TableColumn<>("Serie");
		estilizarColumna(tcSerie, "serie", 0.15);
		tcUbicacion = new TableColumn<>("Ubicacion");
		estilizarColumna(tcUbicacion, "nombreUbicacion", 0.15);
		tcCaducidad = new TableColumn<>("Caducidad");
		estilizarColumna(tcCaducidad, "caducidad", 0.2);
		tcFechaCaducidad = new InstalacionFechaCaducidadColumn("Fecha Caducidad");
		estilizarColumna(tcFechaCaducidad, "diasCaducidad", 0.19);
	}

	@Override
	public int cargar() {
		InstalacionTableView tableView = this;
		Platform.runLater(new Runnable() {

			public void run() {
				Service<Object> srvInstalacions = new Service<Object>() {

					@Override
					protected Task<Object> createTask() {
						return new Task<Object>() {

							@Override
							protected Object call() throws Exception {
								try {
									listaProductos = viewModel.consulta(filtroBean);
								} catch (BDevException bde) {
									BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()),
											bde.getTipoMensaje());
								} catch (Exception e) {
									BDev.getLogger().error(BDev.getMensaje("Instalacions.consulta.error"), e);
									BDevMain.mostrarMensaje(BDev.getMensaje("Instalacions.consulta.error"),
											BDevTipoMensaje.ERROR);
								}
								return null;
							}

						};
					}

				};

				srvInstalacions.stateProperty().addListener(new ChangeListener<Worker.State>() {

					public void changed(ObservableValue<? extends State> arg0, State arg1, State newState) {
						if (newState.equals(State.SUCCEEDED) || newState.equals(State.FAILED)) {
							if (newState.equals(State.SUCCEEDED)) {
								getItems().clear();
								getItems().addAll(listaProductos);
								fireEvent(new TableViewEvent(TableViewEvent.CARGADO, tableView));
							}
						}
					}
				});

				srvInstalacions.start();
			}
		});
		return getItems().size();
	}
	
	public void setFiltroBean(InstalacionFiltroBean _filtroBean) {
		filtroBean = _filtroBean;
		cargar();
	}

}
