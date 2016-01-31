package bucketdev.laboratorio.equipos.view.pane;

import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class EquipoGridPane extends GridPane {

	private EquipoBean equipoBean;
	
	private TextField tfNombre;
	private TextField tfClave;
	private TextField tfDescripcion;
	private boolean agregar;
	
	public EquipoGridPane(EquipoBean _equipoBean) {
		equipoBean = _equipoBean;
		agregar = equipoBean == null;
		
		tfNombre = new TextField();
		tfClave = new TextField();
		tfDescripcion = new TextField();
		
		init();
		
		llenarCampos();
	}
	
	public void init() {
		setVgap(10);
		add(new Label("Nombre: "), 0, 0);
		add(tfNombre, 1, 0);
		add(new Label("Clave: "), 0, 1);
		add(tfClave, 1, 1);
		add(new Label("Descripción: "), 0, 2);
		add(tfDescripcion, 1, 2);
	}
	
	public boolean isValid() throws BDevException {
		if(agregar){
			equipoBean = new EquipoBean();
		}
		if(tfNombre.getText().trim().equals("")) {
			throw new BDevException("equipos.nombre.error", BDevTipoMensaje.ALERTA);
		}
		equipoBean.setNombre(tfNombre.getText().trim());
		equipoBean.setClave(tfClave.getText().trim());
		equipoBean.setDescripcion(tfDescripcion.getText().trim());
		return true;
	}
	
	public EquipoBean getValues() throws BDevException {
		if(isValid()) {
			return equipoBean;
		}
		return null;
	}
	
	public void llenarCampos() {
		if(equipoBean != null){
			tfNombre.setText(equipoBean.getNombre());
			tfClave.setText(equipoBean.getClave());
			tfDescripcion.setText(equipoBean.getDescripcion());
		}
	}

}
