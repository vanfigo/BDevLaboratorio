package bucketdev.laboratorio.ubicaciones.view.pane;

import bucketdev.laboratorio.bean.UbicacionBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class UbicacionGridPane extends GridPane {

	private UbicacionBean ubicacionBean;
	
	private TextField tfNombre;
	private TextField tfDireccion;
	private boolean agregar;
	
	public UbicacionGridPane(UbicacionBean _ubicacionBean) {
		ubicacionBean = _ubicacionBean;
		agregar = ubicacionBean == null;
		
		tfNombre = new TextField();
		tfDireccion = new TextField();
		
		init();
		
		llenarCampos();
	}
	
	public void init() {
		setVgap(10);
		add(new Label("Nombre: "), 0, 0);
		add(tfNombre, 1, 0);
		add(new Label("Dirección: "), 0, 1);
		add(tfDireccion, 1, 1);
	}
	
	public boolean isValid() throws BDevException {
		if(agregar){
			ubicacionBean = new UbicacionBean();
		}
		if(tfNombre.getText().trim().equals("")) {
			throw new BDevException("ubicaciones.nombre.error", BDevTipoMensaje.ALERTA);
		}
		ubicacionBean.setNombre(tfNombre.getText().trim());
		ubicacionBean.setDireccion(tfDireccion.getText().trim());
		return true;
	}
	
	public UbicacionBean getValues() throws BDevException {
		if(isValid()) {
			return ubicacionBean;
		}
		return null;
	}
	
	public void llenarCampos() {
		if(ubicacionBean != null){
			tfNombre.setText(ubicacionBean.getNombre());
			tfDireccion.setText(ubicacionBean.getDireccion());
		}
	}

}
