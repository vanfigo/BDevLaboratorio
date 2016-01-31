package bucketdev.laboratorio.type;

import lombok.Getter;

public enum BDevTipoEstatus {

	DISPONIBLE(1, "Disponible"), EN_USO(2, "En Uso"), CADUCADO(3, "Caducado");

	private @Getter int id;
	private @Getter String nombre;

	private BDevTipoEstatus(int _id, String _nombre) {
		id = _id;
		nombre = _nombre;
	}

	public static BDevTipoEstatus getTipoEstatusByID(int _id) {
		for (BDevTipoEstatus estatus : values()) {
			if (estatus.getId() == _id) {
				return estatus;
			}
		}
		return null;
	}

}
