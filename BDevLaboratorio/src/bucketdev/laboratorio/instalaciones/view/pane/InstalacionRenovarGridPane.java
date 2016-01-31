package bucketdev.laboratorio.instalaciones.view.pane;

import bucketdev.laboratorio.bean.ConsumibleBean;
import bucketdev.laboratorio.bean.InstalacionBean;
import bucketdev.laboratorio.consumibles.view.ConsumibleCombo;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.NumberField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class InstalacionRenovarGridPane extends GridPane {

	private InstalacionBean instalacionBean;
	
	private ConsumibleCombo cbConsumible;
	private NumberField nfCaducidad;
	
	public InstalacionRenovarGridPane(InstalacionBean _instalacionBean) {
		instalacionBean = _instalacionBean;
		
		cbConsumible = new ConsumibleCombo(instalacionBean.getConsumibleBean().getEquipoBean());
		nfCaducidad = new NumberField();
		
		init();
		
		atarEventos();
		
		llenarCampos();
	}
	
	public void init() {
		cbConsumible.setMinWidth(200);
		cbConsumible.setMaxWidth(200);
		setVgap(10);
		add(new Label("Consumible: "), 0, 0);
		add(cbConsumible, 1, 0);
		add(new Label("Caducidad: "), 0, 1);
		add(nfCaducidad, 1, 1);
	}
	
	public void atarEventos() {
		cbConsumible.valueProperty().addListener(new ChangeListener<ConsumibleBean>() {

			@Override
			public void changed(ObservableValue<? extends ConsumibleBean> observable, ConsumibleBean oldValue,
					ConsumibleBean newValue) {
				nfCaducidad.setText(String.valueOf(newValue.getCaducidad()));
			}
		});
	}
	
	public boolean isValid() throws BDevException {
		if(cbConsumible.getValue() == null) {
			throw new BDevException("instalacions.equipo.error", BDevTipoMensaje.ALERTA);
		}
		ConsumibleBean consumibleBean = cbConsumible.getValue();
		instalacionBean.setConsumibleBean(consumibleBean);
		int caducidad = nfCaducidad.getTextValue().trim().isEmpty() ? 0 : Integer.valueOf(nfCaducidad.getTextValue().trim());
		if( caducidad == 0 ) {
			throw new BDevException("instalacions.caducidad.error", BDevTipoMensaje.ALERTA);
		}
		instalacionBean.setCaducidad(caducidad);
		return true;
	}
	
	public InstalacionBean getValues() throws BDevException {
		if(isValid()) {
			return instalacionBean;
		}
		return null;
	}
	
	private void llenarCampos() {
		if(instalacionBean != null) {
			cbConsumible.setValue(instalacionBean.getConsumibleBean());
		}
	}

}
