package bucketdev.laboratorio.bean;

import lombok.Data;

public @Data class UsuarioBean {
	private int id;
	private RolBean rolBean;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String usuario;
	private String contrasena;
}
