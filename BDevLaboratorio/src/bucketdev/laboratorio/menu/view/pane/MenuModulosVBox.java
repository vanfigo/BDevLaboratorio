package bucketdev.laboratorio.menu.view.pane;

import java.util.List;

import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.menu.view.ModuloToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class MenuModulosVBox extends VBox {
	
	private ToggleGroup toggleGroup;
	
	public MenuModulosVBox() {
		toggleGroup = new ToggleGroup();
		init();
		
		atarEventos();
	}
	
	public void init() {
		setMinWidth(220);
		setPadding(new Insets(0));
		getStyleClass().add("menu");
	}
	
	private void atarEventos() {
		toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				if(newValue == null){
					oldValue.setSelected(true);
				}
			}
		});
	}
	
	public void cargarModulos(List<ModuloBean> _listaModulos) {
		for(ModuloBean moduloBean : _listaModulos){
			ModuloToggleButton btModulo = new ModuloToggleButton(moduloBean);
			btModulo.setToggleGroup(toggleGroup);
			getChildren().add(btModulo);
		}
	}

}
