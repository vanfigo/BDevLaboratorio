package bucketdev.laboratorio.type;

public enum BDevTipoBotonesMensaje {
	
	CANCELAR(1),
	ACEPTAR_CANCELAR(2),
	GUARDAR_CANCELAR(3);
	
	private int id;
	
	private BDevTipoBotonesMensaje(int _id) {
		setId(_id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public static BDevTipoBotonesMensaje getTipoBotonByID(int _id) {
		for(BDevTipoBotonesMensaje boton : values()){
			if(boton.getId() == _id){
				return boton;
			}
		}
		return null;
	}

}
