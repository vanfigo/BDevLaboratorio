package bucketdev.laboratorio.consumibles.view.pane;

import bucketdev.laboratorio.bean.ConsumibleBean;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.busqueda.view.pane.BusquedaCatalogo;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.NumberField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Getter;

public class ConsumibleGridPane extends GridPane {

	private ConsumibleBean consumibleBean;
	
	private TextField tfClave;
	private TextField tfNombre;
	private NumberField nfCaducidad;
	private @Getter BusquedaCatalogo<EquipoBean> busquedaEquipo;
	private boolean agregar;
	
	public ConsumibleGridPane(ConsumibleBean _consumibleBean) {
		consumibleBean = _consumibleBean;
		agregar = consumibleBean == null;
		
		tfClave = new TextField();
		tfNombre = new TextField();
		nfCaducidad = new NumberField();
		busquedaEquipo = new BusquedaCatalogo<>();
		
		init();
		
		llenarCampos();
	}
	
	public void init() {
		setVgap(10);
		add(new Label("Equipo: "), 0, 0);
		add(busquedaEquipo, 1, 0);
		add(new Label("Nombre: "), 0, 1);
		add(tfNombre, 1, 1);
		add(new Label("Clave: "), 0, 2);
		add(tfClave, 1, 2);
		add(new Label("Caducidad: "), 0, 3);
		add(nfCaducidad, 1, 3);
	}
	
	public boolean isValid() throws BDevException {
		if(agregar){
			consumibleBean = new ConsumibleBean();
		}
		if(busquedaEquipo.getValue() == null) {
			throw new BDevException("consumibles.equipo.error", BDevTipoMensaje.ALERTA);
		}
		if(tfNombre.getText().trim().equals("")) {
			throw new BDevException("consumibles.nombre.error", BDevTipoMensaje.ALERTA);
		}
		EquipoBean equipoBean = busquedaEquipo.getValue();
		
		consumibleBean.setEquipoBean(equipoBean);
		consumibleBean.setClave(tfClave.getText().trim());
		consumibleBean.setNombre(tfNombre.getText().trim());
		consumibleBean.setCaducidad(nfCaducidad.getTextValue().trim().isEmpty() ? 0 : Integer.valueOf(nfCaducidad.getTextValue().trim()));
		return true;
	}
	
	public ConsumibleBean getValues() throws BDevException {
		if(isValid()) {
			return consumibleBean;
		}
		return null;
	}
	
	public void llenarCampos() {
		if(consumibleBean != null){
			tfClave.setText(consumibleBean.getClave());
			tfNombre.setText(consumibleBean.getNombre());
			nfCaducidad.setText(String.valueOf(consumibleBean.getCaducidad()));
			busquedaEquipo.setValue(consumibleBean.getEquipoBean());
		}
	}

}
