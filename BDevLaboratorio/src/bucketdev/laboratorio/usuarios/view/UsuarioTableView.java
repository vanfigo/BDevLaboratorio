package bucketdev.laboratorio.usuarios.view;

import java.util.List;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.UsuarioBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.usuarios.viewmodel.UsuarioViewModel;
import bucketdev.laboratorio.view.GenericoTableView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.control.TableColumn;

public class UsuarioTableView extends GenericoTableView<UsuarioBean> {

	private UsuarioViewModel viewModel;
	private List<UsuarioBean> listaUsuarios;

	private TableColumn<UsuarioBean, String> tcNombre;
	private TableColumn<UsuarioBean, String> tcApellidoPaterno;
	private TableColumn<UsuarioBean, String> tcApellidoMaterno;
	private TableColumn<UsuarioBean, String> tcUsuario;

	public UsuarioTableView() {
		viewModel = new UsuarioViewModel();

		tcNombre = new TableColumn<>("Nombre");
		estilizarColumna(tcNombre, "nombre", 0.26);
		tcApellidoPaterno = new TableColumn<>("Apellido Paterno");
		estilizarColumna(tcApellidoPaterno, "apellidoPaterno", 0.26);
		tcApellidoMaterno = new TableColumn<>("Apellido Materno");
		estilizarColumna(tcApellidoMaterno, "apellidoMaterno", 0.26);
		tcUsuario = new TableColumn<>("Usuario");
		estilizarColumna(tcUsuario, "usuario", 0.2);
	}

	@Override
	public int cargar() {
		Platform.runLater(new Runnable() {

			public void run() {
				Service<Object> srvUsuarios = new Service<Object>() {

					@Override
					protected Task<Object> createTask() {
						return new Task<Object>() {

							@Override
							protected Object call() throws Exception {
								try {
									listaUsuarios = viewModel.consulta();
								} catch (BDevException bde) {
									BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()),
											bde.getTipoMensaje());
								} catch (Exception e) {
									BDev.getLogger().error(BDev.getMensaje("usuarios.consulta.error"), e);
									BDevMain.mostrarMensaje(BDev.getMensaje("usuarios.consulta.error"),
											BDevTipoMensaje.ERROR);
								}
								return null;
							}

						};
					}

				};

				srvUsuarios.stateProperty().addListener(new ChangeListener<Worker.State>() {

					public void changed(ObservableValue<? extends State> arg0, State arg1, State newState) {
						if (newState.equals(State.SUCCEEDED) || newState.equals(State.FAILED)) {
							if (newState.equals(State.SUCCEEDED)) {
								getItems().clear();
								getItems().addAll(listaUsuarios);
							}
						}
					}
				});

				srvUsuarios.start();
			}
		});
		return getItems().size();
	}

}
