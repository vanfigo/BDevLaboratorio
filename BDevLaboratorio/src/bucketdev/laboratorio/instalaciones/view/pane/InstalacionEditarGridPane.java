package bucketdev.laboratorio.instalaciones.view.pane;

import bucketdev.laboratorio.bean.InstalacionBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class InstalacionEditarGridPane extends GridPane {
	
	private InstalacionBean instalacionBean;
	
	private TextField tfSerie;
	
	public InstalacionEditarGridPane(InstalacionBean _instalacionBean) {
		instalacionBean = _instalacionBean;
		
		tfSerie = new TextField();
		
		init();
		
		llenarCampos();
	}
	
	public void init() {
		setVgap(10);
		add(new Label("No. de Serie: "), 0, 0);
		add(tfSerie, 1, 0);
	}
	
	public boolean isValid() throws BDevException {
		if(tfSerie.getText().trim().equals("")) {
			throw new BDevException("equipos.nombre.error", BDevTipoMensaje.ALERTA);
		}
		instalacionBean.setSerie(tfSerie.getText().trim());
		return true;
	}
	
	public InstalacionBean getValues() throws BDevException {
		if(isValid()) {
			return instalacionBean;
		}
		return null;
	}
	
	public void llenarCampos() {
		if(instalacionBean != null){
			tfSerie.setText(instalacionBean.getSerie());
		}
	}

}
