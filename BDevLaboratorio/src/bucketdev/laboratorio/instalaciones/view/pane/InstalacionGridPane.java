package bucketdev.laboratorio.instalaciones.view.pane;

import bucketdev.laboratorio.bean.ConsumibleBean;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.bean.InstalacionBean;
import bucketdev.laboratorio.bean.UbicacionBean;
import bucketdev.laboratorio.equipos.view.EquipoCombo;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.usuarios.view.UbicacionCombo;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class InstalacionGridPane extends GridPane {

	private InstalacionBean instalacionBean;

	private EquipoCombo cbEquipo;
	private UbicacionCombo cbUbicacion;

	public InstalacionGridPane() {
		cbEquipo = new EquipoCombo();
		cbUbicacion = new UbicacionCombo();

		init();
	}

	public void init() {
		cbEquipo.setMinWidth(200);
		cbEquipo.setMaxWidth(200);
		cbUbicacion.setMinWidth(200);
		cbUbicacion.setMaxWidth(200);
		setVgap(10);
		add(new Label("Ubicacion: "), 0, 0);
		add(cbUbicacion, 1, 0);
		add(new Label("Equipo: "), 0, 1);
		add(cbEquipo, 1, 1);
	}

	public boolean isValid() throws BDevException {
		instalacionBean = new InstalacionBean();
		if (cbEquipo.getValue() == null) {
			throw new BDevException("Instalacions.equipo.error", BDevTipoMensaje.ALERTA);
		}
		EquipoBean equipoBean = cbEquipo.getValue();
		
		ConsumibleBean consumibleBean = new ConsumibleBean();
		consumibleBean.setEquipoBean(equipoBean);
		
		instalacionBean.setConsumibleBean(consumibleBean);
		if (cbUbicacion.getValue() == null) {
			throw new BDevException("Instalacions.ubicacion.error", BDevTipoMensaje.ALERTA);
		}
		UbicacionBean ubicacionBean = cbUbicacion.getValue();
		instalacionBean.setUbicacionBean(ubicacionBean);
		return true;
	}

	public InstalacionBean getValues() throws BDevException {
		if (isValid()) {
			return instalacionBean;
		}
		return null;
	}

}
