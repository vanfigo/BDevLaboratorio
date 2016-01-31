package bucketdev.laboratorio.ubicaciones.view;

import java.util.ArrayList;
import java.util.List;

import bucketdev.laboratorio.bean.AccionBean;
import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.bean.UbicacionBean;
import bucketdev.laboratorio.modulo.controller.ModuloBorderView;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.ubicaciones.event.UbicacionEvent;
import bucketdev.laboratorio.ubicaciones.view.pane.UbicacionGridPane;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;

public class UbicacionBorderView extends ModuloBorderView {
	
	private UbicacionTableView tableView;
	private UbicacionMensajePopUp mensajeUbicacion;
	private UbicacionGridPane ubicacionGridPane;
	
	public UbicacionBorderView(ModuloBean _moduloBean) {
		super(_moduloBean);
	}
	
	@Override
	public void init() {
		super.init();
		
		tableView = new UbicacionTableView();
		setCenter(tableView);
	}
	
	public void mostrarMensajeUsuario(UbicacionBean _ubicacionBean) {
		String titulo = _ubicacionBean == null ? "Agregar" : "Editar";
		BDevTipoMensaje tipoMensaje = _ubicacionBean == null ? BDevTipoMensaje.AGREGAR : BDevTipoMensaje.EDITAR;
		ubicacionGridPane = new UbicacionGridPane(_ubicacionBean);
		mensajeUbicacion = new UbicacionMensajePopUp(titulo, tipoMensaje, ubicacionGridPane);
		mensajeUbicacion.show();
		
		mensajeUbicacion.addEventHandler(UbicacionEvent.ACTUALIZAR_UBICACION, new EventHandler<UbicacionEvent>() {

			@Override
			public void handle(UbicacionEvent event) {
				fireEvent(event);
			}
		});
		
		mensajeUbicacion.setOnHidden(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				tableView.cargar();
			}
		});
	}
	
	public UbicacionBean getSeleccionado() {
		return tableView.getSeleccionado();
	}

	@Override
	public void validarPermisos() {
		List<Button> listaBotones = new ArrayList<>();
		for(AccionBean accion : getModuloBean().getListaAcciones()){
			if(accion.getNombre().equals("agregar_ubicaciones")){
				listaBotones.add(getBtAgregar());
			} else if(accion.getNombre().equals("editar_ubicaciones")){
				listaBotones.add(getBtEditar());
			} else if(accion.getNombre().equals("eliminar_ubicaciones")){
				listaBotones.add(getBtEliminar());
			}
		}
		
		getCajaBotones().getChildren().addAll(0, listaBotones);
	}

	@Override
	public void cargar() {
		tableView.cargar();
	}
	
}
