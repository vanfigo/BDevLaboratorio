package bucketdev.laboratorio.modulo.controller;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.modulo.event.ModuloEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import lombok.Getter;

public abstract class ModuloBorderView extends BorderPane implements ModuloBorderFacade {
	
	private @Getter HBox cajaBotones;
	private @Getter Button btAgregar;
	private @Getter Button btEditar;
	private @Getter Button btEliminar;
	private @Getter ModuloBean moduloBean;
	
	public ModuloBorderView(){
		this(null);
	}
	
	public ModuloBorderView(ModuloBean _moduloBean){
		moduloBean = _moduloBean;
		setPadding(new Insets(10));
		
		getStyleClass().add("border-modulo");
		
		init();
		
		validarPermisos();
		
		atarEventos();
	}
	
	public void atarEventos() {
		btAgregar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				fireEvent(new ModuloEvent(ModuloEvent.AGREGAR));
			}
		});
		
		btEditar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				fireEvent(new ModuloEvent(ModuloEvent.EDITAR));
			}
		});
		
		btEliminar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				fireEvent(new ModuloEvent(ModuloEvent.ELIMINAR));
			}
		});
	}
	
	
	public void init(){
		btAgregar = new Button("Agregar", BDev.creaImagen("boton.img.agregar"));
		btAgregar.getStyleClass().add("success");

		btEditar = new Button("Editar", BDev.creaImagen("boton.img.editar"));
		btEditar.getStyleClass().add("warning");
		
		btEliminar = new Button("Eliminar", BDev.creaImagen("boton.img.eliminar"));
		btEliminar.getStyleClass().add("danger");
	
		cajaBotones = new HBox(15);
		cajaBotones.setPadding(new Insets(20));
		cajaBotones.setAlignment(Pos.CENTER_RIGHT);
		cajaBotones.getStyleClass().add("bottombar");
		
		setBottom(cajaBotones);
	}

}
