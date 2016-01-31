package bucketdev.laboratorio.login.view;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.bean.UsuarioBean;
import bucketdev.laboratorio.login.view.pane.LoginGridPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import lombok.Getter;

public class LoginAnchorView extends AnchorPane {

	private @Getter LoginGridPane gpLogin;

	public LoginAnchorView() {
		gpLogin = new LoginGridPane();
		
		init();
	}

	public void init() {
		ImageView imgWall = BDev.creaImagen("login.imgwall");
		imgWall.setEffect(new BoxBlur(10, 10, 2));
		StackPane stack = new StackPane(imgWall, gpLogin);

		getChildren().add(stack);

		AnchorPane.setTopAnchor(stack, 0.0);
		AnchorPane.setBottomAnchor(stack, 0.0);
		AnchorPane.setLeftAnchor(stack, 0.0);
		AnchorPane.setRightAnchor(stack, 0.0);
	}
	
	public UsuarioBean getValues() {
		return gpLogin.getValues();
	}

}
