package bucketdev.laboratorio.bean;

import lombok.Data;

public @Data class InstalacionFiltroBean {

	private EquipoBean equipoBean;
	private ConsumibleBean consumibleBean;
	private int diasCaducidad;
	private boolean porCaducar;
	
}
