package bucketdev.laboratorio.bean;

import lombok.Data;

public @Data class EquipoBean {
	private int id;
	private String nombre;
	private String clave;
	private String descripcion;
	
	public String toString() {
		return nombre;
	}
}
