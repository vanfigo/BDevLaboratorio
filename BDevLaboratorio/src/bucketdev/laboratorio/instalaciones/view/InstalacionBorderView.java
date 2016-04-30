package bucketdev.laboratorio.instalaciones.view;

import java.util.ArrayList;
import java.util.List;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.bean.AccionBean;
import bucketdev.laboratorio.bean.InstalacionBean;
import bucketdev.laboratorio.bean.InstalacionFiltroBean;
import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.instalaciones.event.InstalacionEvent;
import bucketdev.laboratorio.instalaciones.view.pane.InstalacionEditarGridPane;
import bucketdev.laboratorio.instalaciones.view.pane.InstalacionFiltroGridPane;
import bucketdev.laboratorio.instalaciones.view.pane.InstalacionGridPane;
import bucketdev.laboratorio.instalaciones.view.pane.InstalacionRenovarGridPane;
import bucketdev.laboratorio.modulo.controller.ModuloBorderView;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.NumberField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.WindowEvent;
import lombok.Getter;

public class InstalacionBorderView extends ModuloBorderView {
	
	private InstalacionFiltroGridPane filtroGridPane;
	private @Getter InstalacionTableView tableView;
	private @Getter NumberField nfDiasCaducidad;
	
	private InstalacionMensajePopUp mensajeInstalacion;
	private InstalacionRenovarMensajePopUp mensajeRenovarInstalacion;
	private InstalacionEditarMensajePopUp mensajeEditarInstalacion;
	private InstalacionGridPane instalacionGridPane;
	private InstalacionRenovarGridPane instalacionRenovarGridPane;
	private InstalacionEditarGridPane instalacionEditarGridPane;
	
	private Button btRenovar;
	private Button btExportarExcel;
	
	public InstalacionBorderView(ModuloBean _moduloBean) {
		super(_moduloBean);
		
	}
	
	@Override
	public void init() {
		super.init();
		filtroGridPane = new InstalacionFiltroGridPane();
		tableView = new InstalacionTableView();
		nfDiasCaducidad = new NumberField();
		Label lbDiasCaducidad =  new Label("Días por caducar:");
		lbDiasCaducidad.getStyleClass().add("lbl-resaltar");
		HBox cajaDiasCaducidad = new HBox(20, lbDiasCaducidad, nfDiasCaducidad);
		cajaDiasCaducidad.setAlignment(Pos.CENTER_RIGHT);
		cajaDiasCaducidad.getStyleClass().add("toolbar");

		setCenter(new BorderPane(tableView, filtroGridPane, null, cajaDiasCaducidad, null));
	}
	
	@Override
	public void atarEventos() {
		super.atarEventos();

		btRenovar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				fireEvent(new InstalacionEvent(InstalacionEvent.RENOVAR));
			}
		});
		
		btExportarExcel.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				fireEvent(new InstalacionEvent(InstalacionEvent.EXPORTAR_EXCEL));
				
			}
		});
	}
	
	public void mostrarMensajeProducto() {
		instalacionGridPane = new InstalacionGridPane();
		mensajeInstalacion = new InstalacionMensajePopUp("Agregar", BDevTipoMensaje.AGREGAR, instalacionGridPane);
		mensajeInstalacion.show();
		
		mensajeInstalacion.addEventHandler(InstalacionEvent.ACTUALIZAR_INSTALACION, new EventHandler<InstalacionEvent>() {

			@Override
			public void handle(InstalacionEvent event) {
				fireEvent(event);
			}
		});
		
		mensajeInstalacion.setOnHidden(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				tableView.cargar();
			}
		});
	}
	
	public void mostrarMensajeEditarProducto(InstalacionBean _instalacionBean){
		instalacionEditarGridPane = new InstalacionEditarGridPane(_instalacionBean);
		mensajeEditarInstalacion = new InstalacionEditarMensajePopUp(instalacionEditarGridPane);
		
		mensajeEditarInstalacion.show();
		
		mensajeEditarInstalacion.addEventHandler(InstalacionEvent.ACTUALIZAR_INSTALACION, new EventHandler<InstalacionEvent>() {

			@Override
			public void handle(InstalacionEvent event) {
				fireEvent(event);
			}
		});
		
		mensajeEditarInstalacion.setOnHidden(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				tableView.cargar();
			}
		});
	}
	
	public void mostrarMensajeRenovarInstalacion(InstalacionBean _instalacionBean) {
		instalacionRenovarGridPane = new InstalacionRenovarGridPane(_instalacionBean);
		mensajeRenovarInstalacion = new InstalacionRenovarMensajePopUp("Renovar salida", BDevTipoMensaje.BUSCAR, instalacionRenovarGridPane);
		mensajeRenovarInstalacion.show();
		
		mensajeRenovarInstalacion.addEventHandler(InstalacionEvent.RENOVAR_INSTALACION, new EventHandler<InstalacionEvent>() {

			@Override
			public void handle(InstalacionEvent event) {
				fireEvent(event);
			}
		});
		
		mensajeRenovarInstalacion.setOnHidden(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				tableView.cargar();
			}
		});
	}
	
	public InstalacionBean getSeleccionado() {
		return tableView.getSeleccionado();
	}
	
	public void setFiltroBean(InstalacionFiltroBean _filtroBean) {
		tableView.setFiltroBean(_filtroBean);
	}

	@Override
	public void validarPermisos() {
		btRenovar = new Button("Renovar", BDev.creaImagen("boton.img.renovar"));
		btExportarExcel = new Button("Exportar", BDev.creaImagen("boton.img.excel"));
		
		List<Button> listaBotones = new ArrayList<>();
		listaBotones.add(btExportarExcel);
		for(AccionBean accion : getModuloBean().getListaAcciones()){
			if(accion.getNombre().equals("agregar_instalaciones")){
				listaBotones.add(getBtAgregar());
			} else if(accion.getNombre().equals("editar_instalaciones")){
				listaBotones.add(getBtEditar());
			} else if(accion.getNombre().equals("eliminar_instalaciones")){
				listaBotones.add(getBtEliminar());
			} else if(accion.getNombre().equals("renovar_instalaciones")){
				listaBotones.add(btRenovar);
			} //else if(accion.getNombre().equals("excel_instalaciones")){
//				listaBotones.add(btExportarExcel);
//			}
		}
		
		getCajaBotones().getChildren().addAll(0, listaBotones);
	}

	@Override
	public void cargar() {
		tableView.cargar();
	}

}
