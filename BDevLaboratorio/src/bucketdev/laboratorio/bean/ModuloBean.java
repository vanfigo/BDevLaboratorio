package bucketdev.laboratorio.bean;

import java.util.List;

import lombok.Data;

public @Data class ModuloBean {
	private int id;
	private String nombre;
	private String accion;
	private String icono;
	private String color;
	private List<AccionBean> listaAcciones;
}
