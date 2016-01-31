package bucketdev.laboratorio.bean;

import lombok.Data;

public @Data class ConsumibleBean {
	
	private int id;
	private EquipoBean equipoBean;
	private String nombre;
	private String clave;
	private int caducidad;
	
	public String toString() {
		return nombre;
	}
	
	public String getNombreEquipo() {
		return equipoBean == null ? "" : equipoBean.getNombre();
	}
}
