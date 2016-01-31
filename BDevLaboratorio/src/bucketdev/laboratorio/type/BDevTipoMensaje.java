package bucketdev.laboratorio.type;

import bucketdev.laboratorio.BDev;
import lombok.Getter;

public enum BDevTipoMensaje {
	
	CORRECTO(	1, "success",	1500),
	ALERTA(		2, "warning",	1500),
	INFORMACION(3, "primary",	1000),
	ERROR(		4, "danger",	2500),
	EDITAR(		5, "editar",	1000),
	AGREGAR(	6, "agregar",	1000),
	VALIDAR(	7, "validar",	1000),
	BUSCAR(		8, "buscar",	1000);

	private @Getter int id;
	private @Getter String icono;
	private @Getter String iconoGrande;

	private @Getter String clase;
	private @Getter long duracion;

	private BDevTipoMensaje(int _id, String _clase, long _duracion) {
		id = _id;
		icono = BDev.getPropiedad(String.format("status.img.%s", _clase));
		iconoGrande = BDev.getPropiedad(String.format("status.img.%s32", _clase));
		clase = _clase;
		duracion = _duracion;
	}

	public static BDevTipoMensaje getMensajeByID(int id) {
		for (BDevTipoMensaje tipoMensaje : values()) {
			if (tipoMensaje.getId() == id)
				return tipoMensaje;
		}
		return null;
	}
}
