package bucketdev.laboratorio.equipos.view;

import java.util.ArrayList;
import java.util.List;

import bucketdev.laboratorio.bean.AccionBean;
import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.equipos.event.EquipoEvent;
import bucketdev.laboratorio.equipos.view.pane.EquipoGridPane;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.modulo.controller.ModuloBorderView;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;

public class EquipoBorderView extends ModuloBorderView {
	
	private EquipoTableView tableView;
	private EquipoMensajePopUp mensajeEquipo;
	private EquipoGridPane equipoGridPane;
	
	public EquipoBorderView(ModuloBean _moduloBean) {
		super(_moduloBean);
		
	}
	
	@Override
	public void init() {
		super.init();
		tableView = new EquipoTableView();

		setCenter(tableView);
	}
	
	public void mostrarMensajeEquipo(EquipoBean _equipoBean) {
		String titulo = _equipoBean == null ? "Agregar" : "Editar";
		BDevTipoMensaje tipoMensaje = _equipoBean == null ? BDevTipoMensaje.AGREGAR : BDevTipoMensaje.EDITAR;
		equipoGridPane = new EquipoGridPane(_equipoBean);
		mensajeEquipo = new EquipoMensajePopUp(titulo, tipoMensaje, equipoGridPane);
		mensajeEquipo.show();
		
		mensajeEquipo.addEventHandler(EquipoEvent.ACTUALIZAR_EQUIPO, new EventHandler<EquipoEvent>() {

			@Override
			public void handle(EquipoEvent event) {
				fireEvent(event);
			}
		});
		
		mensajeEquipo.setOnHidden(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				tableView.cargar();
			}
		});
	}
	
	public EquipoBean getSeleccionado() {
		return tableView.getSeleccionado();
	}

	@Override
	public void validarPermisos() {
		List<Button> listaBotones = new ArrayList<>();
		for(AccionBean accion : getModuloBean().getListaAcciones()){
			if(accion.getNombre().equals("agregar_equipos")){
				listaBotones.add(getBtAgregar());
			} else if(accion.getNombre().equals("editar_equipos")){
				listaBotones.add(getBtEditar());
			} else if(accion.getNombre().equals("eliminar_equipos")){
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
