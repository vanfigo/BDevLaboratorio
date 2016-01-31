package bucketdev.laboratorio.usuarios.view.pane;

import bucketdev.laboratorio.StringEncrypter;
import bucketdev.laboratorio.bean.UsuarioBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class UsuarioGridPane extends GridPane {

	private UsuarioBean usuarioBean;
	
	private TextField tfNombre;
	private TextField tfApellidoPaterno;
	private TextField tfApellidoMaterno;
	private TextField tfUsuario;
	private PasswordField pfContrasena;
	private boolean agregar;
	
	public UsuarioGridPane(UsuarioBean _usuarioBean) {
		usuarioBean = _usuarioBean;
		agregar = usuarioBean == null;
		
		tfNombre = new TextField();
		tfApellidoPaterno = new TextField();
		tfApellidoMaterno = new TextField();
		tfUsuario = new TextField();
		pfContrasena = new PasswordField();
		
		init();
		
		llenarCampos();
	}
	
	public void init() {
		setVgap(10);
		add(new Label("Nombre: "), 0, 0);
		add(tfNombre, 1, 0);
		add(new Label("Apellido Paterno: "), 0, 1);
		add(tfApellidoPaterno, 1, 1);
		add(new Label("Apellido Materno: "), 0, 2);
		add(tfApellidoMaterno, 1, 2);
		add(new Label("Usuario: "), 0, 3);
		add(tfUsuario, 1, 3);
		add(new Label("Contraseña: "), 0, 4);
		add(pfContrasena, 1, 4);
	}
	
	public boolean isValid() throws BDevException {
		if(agregar){
			usuarioBean = new UsuarioBean();
		}
		if(tfNombre.getText().trim().equals("")) {
			throw new BDevException("usuarios.nombre.error", BDevTipoMensaje.ALERTA);
		}
		usuarioBean.setNombre(tfNombre.getText().trim());
		if(tfApellidoPaterno.getText().trim().equals("")) {
			throw new BDevException("usuarios.apellido.error", BDevTipoMensaje.ALERTA);
		}
		usuarioBean.setApellidoPaterno(tfApellidoPaterno.getText().trim());
		usuarioBean.setApellidoMaterno(tfApellidoMaterno.getText().trim());
		if(tfUsuario.getText().trim().equals("")) {
			throw new BDevException("usuarios.usuario.error", BDevTipoMensaje.ALERTA);
		}
		usuarioBean.setUsuario(tfUsuario.getText().trim());
		if(pfContrasena.getText().trim().equals("") && usuarioBean.getId() == 0) {
			throw new BDevException("usuarios.contrasena.error", BDevTipoMensaje.ALERTA);
		}
		String contrasena = pfContrasena.getText().trim();
		usuarioBean.setContrasena(agregar ? StringEncrypter.encrypt(contrasena) : contrasena.equals("") ? null : StringEncrypter.encrypt(contrasena));
		return true;
	}
	
	public UsuarioBean getValues() throws BDevException {
		if(isValid()) {
			return usuarioBean;
		}
		return null;
	}
	
	public void llenarCampos() {
		if(usuarioBean != null){
			tfNombre.setText(usuarioBean.getNombre());
			tfApellidoPaterno.setText(usuarioBean.getApellidoPaterno());
			tfApellidoMaterno.setText(usuarioBean.getApellidoMaterno());
			tfUsuario.setText(usuarioBean.getUsuario());
		}
	}

}
