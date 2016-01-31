package bucketdev.laboratorio.login.view.pane;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.StringEncrypter;
import bucketdev.laboratorio.bean.UsuarioBean;
import bucketdev.laboratorio.login.event.LoginEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.Getter;

public class LoginGridPane extends GridPane {
	
	private @Getter TextField tfUsuario;
	private PasswordField tfContrasena;
	private Button btIngresar;
	private Button btCerrar;
	
	private @Getter UsuarioBean usuarioBean;
	
	private final double MAX_WIDTH = 300;
	
	public LoginGridPane() {
		tfUsuario = new TextField();
		tfContrasena = new PasswordField();
		btIngresar = new Button("Ingresar", BDev.creaImagen("boton.img.ingresar"));
		btCerrar = new Button("Cerrar", BDev.creaImagen("boton.img.cerrar"));
		
		init();
		atarEventos();
	}
	
	public void init() {
		tfUsuario.setPromptText("Usuario");
		tfContrasena.setPromptText("Contraseña");

		btIngresar.getStyleClass().add("warning");
		btCerrar.getStyleClass().add("danger");
		
		btIngresar.setMinWidth(MAX_WIDTH);
		btCerrar.setMinWidth(MAX_WIDTH);

		add(tfUsuario, 0, 0);
		add(tfContrasena, 0, 1);
		add(btIngresar, 0, 2);
		add(btCerrar, 0, 3);
		setVgap(10);

		getColumnConstraints().addAll(new ColumnConstraints(MAX_WIDTH, MAX_WIDTH,
				USE_PREF_SIZE, Priority.ALWAYS, HPos.CENTER, true));
		setAlignment(Pos.CENTER);
	}
	
	private void atarEventos() {
		btCerrar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				fireEvent(new LoginEvent(LoginEvent.CANCELAR));
			}
		});
		
		tfContrasena.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				fireEvent(new LoginEvent(LoginEvent.INGRESAR));
			}
		});
		
		btIngresar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				fireEvent(new LoginEvent(LoginEvent.INGRESAR));
			}
		});
	}
	
	public UsuarioBean getValues() {
		if(isValid()) {
			return usuarioBean;
		}
		return null;
	}
	
	public boolean isValid() {
		usuarioBean = new UsuarioBean();
		
		if(tfUsuario.getText() == null || tfUsuario.getText().trim().equals("")) {
			return false;
		}
		usuarioBean.setUsuario(tfUsuario.getText().trim());
		
		if(tfContrasena.getText() == null || tfContrasena.getText().trim().equals("")) {
			return false;
		}
		usuarioBean.setContrasena(StringEncrypter.encrypt(tfContrasena.getText().trim()));
		return true;
	}
}
