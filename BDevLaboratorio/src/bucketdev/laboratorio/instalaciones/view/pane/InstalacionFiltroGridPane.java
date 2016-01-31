package bucketdev.laboratorio.instalaciones.view.pane;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.bean.InstalacionFiltroBean;
import bucketdev.laboratorio.consumibles.view.ConsumibleCombo;
import bucketdev.laboratorio.equipos.view.EquipoCombo;
import bucketdev.laboratorio.instalaciones.event.InstalacionEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class InstalacionFiltroGridPane extends GridPane {
	
	private EquipoCombo equipoCombo;
	private ConsumibleCombo consumibleCombo;
	private CheckBox chPorCaducar;
	private Button btLimpiar;
	private Button btFiltrar;
	
	public InstalacionFiltroGridPane() {
		equipoCombo = new EquipoCombo();
		consumibleCombo = new ConsumibleCombo();
		chPorCaducar = new CheckBox("Por caducar");
		btFiltrar = new Button("Buscar", BDev.creaImagen("boton.img.buscar"));
		btLimpiar = new Button("Limpiar", BDev.creaImagen("boton.img.nuevo"));
		
		init();
		
		atarEventos();
	}
	
	public void init() {
		equipoCombo.setMinWidth(200);
		equipoCombo.setMaxWidth(200);
		consumibleCombo.setMinWidth(200);
		consumibleCombo.setMaxWidth(200);
		btFiltrar.getStyleClass().add("info");
		setHgap(20);
		getStyleClass().add("wall-pane");
		setPadding(new Insets(10));
		
		add(new Label("Equipo:"), 0, 0);
		add(equipoCombo, 1, 0);
		add(new Label("Consumible:"), 2, 0);
		add(consumibleCombo, 3, 0);
		add(chPorCaducar, 4, 0);
		add(btLimpiar, 5, 0);
		add(btFiltrar, 6, 0);
	}
	
	private void atarEventos() {
		equipoCombo.valueProperty().addListener(new ChangeListener<EquipoBean>() {

			@Override
			public void changed(ObservableValue<? extends EquipoBean> observable, EquipoBean oldValue,
					EquipoBean newValue) {
				consumibleCombo.setEquipoBean(newValue);
			}
		});
		
		btFiltrar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				fireEvent(new InstalacionEvent(InstalacionEvent.FILTRAR_INSTALACION, getValues()));
			}
		});
		
		btLimpiar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				equipoCombo.setValue(null);
				consumibleCombo.setValue(null);
				chPorCaducar.setSelected(false);
				fireEvent(new InstalacionEvent(InstalacionEvent.FILTRAR_INSTALACION, getValues()));
			}
		});
	}
	
	public InstalacionFiltroBean getValues() {
		InstalacionFiltroBean filtroBean = new InstalacionFiltroBean();
		filtroBean.setEquipoBean(equipoCombo.getValue());
		filtroBean.setConsumibleBean(consumibleCombo.getValue());
		filtroBean.setPorCaducar(chPorCaducar.isSelected());
		
		return filtroBean;
	}

}
