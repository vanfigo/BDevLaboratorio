package bucketdev.laboratorio.view.mensaje;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.type.BDevTipoBotonesMensaje;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.facade.MensajePopUpFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import lombok.Getter;

public abstract class MensajePopUp extends Stage implements MensajePopUpFacade {

	protected Button btnAceptar;
	protected Button btnCancelar;
	protected Button btnGuardar;
	private BorderPane contenedor;
	private @Getter Node nodeCentro;
	private HBox cajaBottom;

	private EventHandler<KeyEvent> presionarTecla;

	private BDevTipoBotonesMensaje tiposBotonesMensaje;
	private BDevTipoMensaje tipoMensaje;
	private double width;

	public MensajePopUp() {
		this("", new Pane());
	}

	public MensajePopUp(String _titulo, Node _contenido) {
		this(_titulo, _contenido, BDevTipoBotonesMensaje.CANCELAR);
	}

	public MensajePopUp(String _titulo, Node _contenido, Window _owner) {
		this(_titulo, _contenido, BDevTipoBotonesMensaje.CANCELAR, BDevTipoMensaje.INFORMACION);
	}

	public MensajePopUp(String _titulo, Node _contenido, BDevTipoBotonesMensaje _tiposBotones, Window _owner) {
		this(_titulo, _contenido, _tiposBotones, BDevTipoMensaje.INFORMACION);
	}

	public MensajePopUp(String _titulo, Node _contenido, BDevTipoBotonesMensaje _tiposBotones) {
		this(_titulo, _contenido, _tiposBotones, BDevTipoMensaje.INFORMACION);
	}

	public MensajePopUp(String _titulo, Node _contenido, BDevTipoBotonesMensaje _tiposBotones,
			BDevTipoMensaje _tipoMensaje) {
		this(_titulo, _contenido, _tiposBotones, _tipoMensaje, BDevMain.getPrimaryStage().getScene().getWindow());
	}

	public MensajePopUp(String _titulo, Node _contenido, BDevTipoBotonesMensaje _tiposBotones,
			BDevTipoMensaje _tipoMensaje, Window _owner) {
		this(_titulo, _contenido, _tiposBotones, _tipoMensaje, _owner, 450);
	}
	
	
	public MensajePopUp(String _titulo, Node _contenido, BDevTipoBotonesMensaje _tiposBotones,
			BDevTipoMensaje _tipoMensaje, Window _owner, double _width) {
		initModality(Modality.WINDOW_MODAL);
		initOwner(_owner);
		setResizable(false);
		initStyle(StageStyle.TRANSPARENT);

		setTitle(_titulo);
		nodeCentro = _contenido;
		tiposBotonesMensaje = _tiposBotones;
		tipoMensaje = _tipoMensaje;
		width = _width;

		contenedor = new BorderPane();
		btnCancelar = new Button("Cerrar");
		btnAceptar = new Button("Aceptar");
		btnGuardar = new Button("Guardar");

		init();

		atarEventos();
	}
	public void init() {
		btnCancelar.getStyleClass().add("danger");
		btnCancelar.setGraphic(BDev.creaImagen("boton.img.cerrar"));
		btnAceptar.getStyleClass().add("success");
		btnAceptar.setGraphic(BDev.creaImagen("boton.img.aceptar"));
		btnGuardar.getStyleClass().add("primary");
		btnGuardar.setGraphic(BDev.creaImagen("boton.img.guardar"));
	
		contenedor.setTop(buildTop());
		contenedor.setCenter(buildCenter());
		contenedor.setBottom(buildBottom());
		contenedor.getStyleClass().add("mensaje-container");
		contenedor.setPadding(new Insets(10L));
		contenedor.setMaxWidth(width);
		contenedor.setPrefWidth(width);
		contenedor.setMinWidth(width);

		setScene(new Scene(contenedor));
		getScene().setFill(Color.TRANSPARENT);
		getScene().getStylesheets()
				.add(MensajePopUp.class.getResource(BDev.getPropiedad("genericos.rutaCSS")).toExternalForm());

	}

	protected Pane buildTop() {
		HBox cajaTop = new HBox(15);
		cajaTop.setPadding(new Insets(10));
		cajaTop.setAlignment(Pos.CENTER_LEFT);
		cajaTop.getStyleClass().addAll("mensaje-titulo", tipoMensaje.getClase());
		ImageView iconoMensaje = new ImageView(new Image(getClass().getResourceAsStream(tipoMensaje.getIconoGrande())));

		Label lbTitulo = new Label(getTitle());
		lbTitulo.setWrapText(true);

		cajaTop.getChildren().addAll(iconoMensaje, lbTitulo);

		return cajaTop;
	}

	protected Pane buildCenter() {
		VBox cajaCentro = new VBox(15);
		cajaCentro.setPadding(new Insets(10));
		cajaCentro.getStyleClass().addAll("mensaje-descripcion");
		cajaCentro.setAlignment(Pos.CENTER);

		cajaCentro.getChildren().add(nodeCentro);

		return cajaCentro;
	}

	protected Pane buildBottom() {
		cajaBottom = new HBox(50);
		cajaBottom.getStyleClass().add("mensaje-botones");
		cajaBottom.setPadding(new Insets(10));
		cajaBottom.setAlignment(Pos.CENTER);

		switch (tiposBotonesMensaje) {
		case CANCELAR:
			cajaBottom.getChildren().add(btnCancelar);
			break;
		case ACEPTAR_CANCELAR:
			cajaBottom.getChildren().addAll(btnAceptar, btnCancelar);
			break;
		case GUARDAR_CANCELAR:
			cajaBottom.getChildren().addAll(btnGuardar, btnCancelar);
			break;
		default:
			break;
		}

		return cajaBottom;
	}

	@Override
	public void cancelar() {
		this.close();
	}

	protected void atarEventos() {
		btnCancelar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				cancelar();
			}
		});

		btnAceptar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				aceptar();
			}
		});

		btnGuardar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				guardar();
			}
		});

		this.addEventFilter(KeyEvent.KEY_RELEASED, getPresionarTecla());
	}

	public EventHandler<KeyEvent> getPresionarTecla() {
		if (presionarTecla == null) {
			presionarTecla = new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					if (event.getCode().equals(KeyCode.ENTER)) {
						switch (tiposBotonesMensaje) {
						case CANCELAR:
							cancelar();
							break;
						case ACEPTAR_CANCELAR:
							aceptar();
							break;
						case GUARDAR_CANCELAR:
							guardar();
							break;
						default:
							break;
						}
					} else if (event.getCode().equals(KeyCode.ESCAPE)) {
						cancelar();
					}
				}
			};
		}
		return presionarTecla;
	}

	public void setPresionarTecla(EventHandler<KeyEvent> presionarTecla) {
		this.presionarTecla = presionarTecla;
	}

}
