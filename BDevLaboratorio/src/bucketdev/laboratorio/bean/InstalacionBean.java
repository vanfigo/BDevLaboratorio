package bucketdev.laboratorio.bean;

import java.util.Date;

import bucketdev.laboratorio.type.BDevTipoEstatus;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.Data;

public @Data class InstalacionBean {
	
	private int id;
	private ConsumibleBean consumibleBean;
	private UbicacionBean ubicacionBean;
	private BDevTipoEstatus tipoEstatus;
	private int caducidad;
	private Date fechaCaducidad;
	private IntegerProperty diasCaducidad;
	
	public InstalacionBean() {
		diasCaducidad = new SimpleIntegerProperty(this, "diasCaducidad", 5);
	}
	
	public void setDiasCaducidad(int _diasCaducidad) {
		diasCaducidad.set(_diasCaducidad);
	}
	
	public int getDiasCaducidad() {
		return diasCaducidad.get();
	}
	
	public IntegerProperty diasCaducidadProperty() {
		if(diasCaducidad == null) {
			diasCaducidad = new SimpleIntegerProperty(this, "diasCaducidad");
		}
		return diasCaducidad;
	}
	
	public String getNombreConsumible() {
		return consumibleBean == null ? "" : consumibleBean.getNombre();
	}
	
	public String getNombreEquipo() {
		return consumibleBean == null ? "" : consumibleBean.getNombreEquipo();
	}
	
	public String getNombreUbicacion() {
		return ubicacionBean == null ? "" : ubicacionBean.getNombre();
	}

}
