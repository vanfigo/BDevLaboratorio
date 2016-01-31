package bucketdev.laboratorio.exception;

import bucketdev.laboratorio.type.BDevTipoMensaje;
import lombok.Getter;

public class BDevException extends Exception {

	private static final long serialVersionUID = 5418519061391489493L;
	private @Getter String codigoError;
	private @Getter BDevTipoMensaje tipoMensaje;
	
	public BDevException() {
		super();
	}
	
	public BDevException(String _codigoError) {
		this(_codigoError, BDevTipoMensaje.INFORMACION);
	}
	
	public BDevException(String _codigoError, BDevTipoMensaje _tipoMensaje) {
		codigoError = _codigoError;
		tipoMensaje = _tipoMensaje;
	}

}
