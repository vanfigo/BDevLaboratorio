package bucketdev.laboratorio.bean;

import lombok.Data;

public @Data class UbicacionBean {

	private int id;
	private String nombre;
	private String direccion;
	
	public String toString() {
		return nombre;
	}
	
}
