package bucketdev.laboratorio.usuarios.view;

import java.util.ArrayList;
import java.util.List;

import bucketdev.laboratorio.bean.AccionBean;
import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.bean.UsuarioBean;
import bucketdev.laboratorio.modulo.controller.ModuloBorderView;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.usuarios.event.UsuarioEvent;
import bucketdev.laboratorio.usuarios.view.pane.UsuarioGridPane;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;

public class UsuarioBorderView extends ModuloBorderView {
	
	private UsuarioTableView tableView;
	private UsuarioMensajePopUp mensajeUsuario;
	private UsuarioGridPane usuarioGridPane;
	
	public UsuarioBorderView(ModuloBean _moduloBean) {
		super(_moduloBean);
	}
	
	@Override
	public void init() {
		super.init();
		
		tableView = new UsuarioTableView();
		setCenter(tableView);
	}
	
	public void mostrarMensajeUsuario(UsuarioBean _usuarioBean) {
		String titulo = _usuarioBean == null ? "Agregar" : "Editar";
		BDevTipoMensaje tipoMensaje = _usuarioBean == null ? BDevTipoMensaje.AGREGAR : BDevTipoMensaje.EDITAR;
		usuarioGridPane = new UsuarioGridPane(_usuarioBean);
		mensajeUsuario = new UsuarioMensajePopUp(titulo, tipoMensaje, usuarioGridPane);
		mensajeUsuario.show();
		
		mensajeUsuario.addEventHandler(UsuarioEvent.ACTUALIZAR_USUARIO, new EventHandler<UsuarioEvent>() {

			@Override
			public void handle(UsuarioEvent event) {
				fireEvent(event);
			}
		});
		
		mensajeUsuario.setOnHidden(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				tableView.cargar();
			}
		});
	}
	
	public UsuarioBean getSeleccionado() {
		return tableView.getSeleccionado();
	}

	@Override
	public void validarPermisos() {
		List<Button> listaBotones = new ArrayList<>();
		for(AccionBean accion : getModuloBean().getListaAcciones()){
			if(accion.getNombre().equals("agregar_usuarios")){
				listaBotones.add(getBtAgregar());
			} else if(accion.getNombre().equals("editar_usuarios")){
				listaBotones.add(getBtEditar());
			} else if(accion.getNombre().equals("eliminar_usuarios")){
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
