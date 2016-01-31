package bucketdev.laboratorio.menu.view.pane;

import java.util.Timer;
import java.util.TimerTask;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.menu.view.MensajeEstatusBean;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Duration;

/**
 * @author rodrigo.loyola
 * @since 08/04/2015
 * @revision 08/04/2015
 * @version 0.0.1
 * 
 *          Clase que sirve para mostrar los mensajes de la aplicación hacia el
 *          usuario
 */
public class MenuMensajeHBox extends HBox {
	
	/**
	 * @property <Timer> timer objeto que controla el tiempo que es visible el
	 *           mensaje al usuario
	 */
	private Timer timer;
	/**
	 * @property <TimerTask> tareaOcultar tarea a ejecutarse cuando se cumpla el
	 *           tiempo de mostrado del mensaje, oculta el mensaje
	 */
	private TimerTask tareaOcultar;
	/**
	 * @property <HBox> cajaMensaje caja que contiene al ícono de notificación y
	 *           al mensaje
	 */
	private HBox cajaMensaje;
	/**
	 * @property <ImageView> icono imagen representativa del tipo de alerta
	 */
	private ImageView icono;
	/**
	 * @property <ImageView> icono imagen para cerrar la alerta
	 */
	private ImageView iconoCerrar;
	/**
	 * @property <Label> mensaje etiqueta para mostrar el mensaje
	 */
	private Label mensaje;
	/**
	 * @property <MensajeEstatusBean> MensajeEstatusBean con el mensaje a mostrar actual
	 */
	private MensajeEstatusBean mensajeEstatusBean;
	/**
	 * @property <Animation> mostrar animación para mostrar el mensaje
	 */
	private Animation mostrar;
	/**
	 * @property <Animation> ocultar animación para ocultar el mensaje
	 */
	private Animation ocultar;
	/**
	 * @property List<String> pilaMensajes lista observable que contiene los mensajes a mostrar
	 */
	private ObservableList<MensajeEstatusBean> pilaMensajes = FXCollections.observableArrayList();
	/**
	 * @property boolean animado indica si la barra de estatus está actualmente
	 *           mostrando un mensaje
	 */
	private boolean animado;
	/**
	 * @cfg <long> SPACING espaciado entre los elementos de la alerta
	 */
	private final long SPACING = 10;
	/**
	 * @cfg <double> DURACION lapso de la animación de mostrado/ocultado
	 */
	private final double DURACION = 500;

	public MenuMensajeHBox() {
		setSpacing(SPACING);
		setAlignment(Pos.CENTER_LEFT);
		getStyleClass().add("statusbar");
		getStyleClass().add("");

		init();

		inicializarAnimaciones();
		
		atarEventos();
	}

	private void init() {
		timer = new Timer("ocultar", true);

		icono = new ImageView();
		mensaje = new Label();
		iconoCerrar = BDev.creaImagen("boton.img.cerrar");
		iconoCerrar.setCursor(Cursor.HAND);
		
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);


		cajaMensaje = new HBox();
		HBox.setHgrow(cajaMensaje, Priority.ALWAYS);
		cajaMensaje.getChildren().addAll(icono, mensaje, spacer, iconoCerrar);
		cajaMensaje.setSpacing(SPACING);
		cajaMensaje.setAlignment(Pos.CENTER);

		getChildren().add(cajaMensaje);
	}

	private void inicializarAnimaciones() {

		mostrar = new Transition() {
			{
				setCycleDuration(Duration.millis(DURACION));
			}

			@Override
			protected void interpolate(double _intervalo) {
				cajaMensaje.setTranslateY((getHeight() * _intervalo) - getHeight());
				cajaMensaje.setOpacity(1 * _intervalo);
			}
		};

		ocultar = new Transition() {
			{
				setCycleDuration(Duration.millis(DURACION));
			}

			@Override
			protected void interpolate(double _intervalo) {
				cajaMensaje.setTranslateY(_intervalo * getHeight());
				cajaMensaje.setOpacity(1 - _intervalo);
			}
		};

		mostrar.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(tareaOcultar != null){
					tareaOcultar.cancel();
				}
				tareaOcultar = new TimerTask() {

					@Override
					public void run() {
						if(!mensajeEstatusBean.isPermanecer()){
							ocultar.play();
						}
					}
				};
				timer.schedule(tareaOcultar, mensajeEstatusBean.getTipoMensaje().getDuracion());
			}
		});
		
		ocultar.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(getPilaMensajes().isEmpty()){
					setAnimado(false);
					getStyleClass().set(1, "");
				} else {
					obtenerMensaje();
					mostrar.play();
				}
			}
		});

	}
	
	private void atarEventos(){
		getPilaMensajes().addListener(new ListChangeListener<MensajeEstatusBean>() {

			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends MensajeEstatusBean> _change) {
				while(_change.next()){
					if(_change.wasAdded()){
						if(!isAnimado()){
							obtenerMensaje();
							setAnimado(true);
							mostrar.play();
						}
					}
				}
			}
		});
		
		iconoCerrar.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				ocultar.play();
			}
		});
	}
	private void obtenerMensaje(){
		mensajeEstatusBean = getPilaMensajes().remove(0);
		
		mensaje.setText(mensajeEstatusBean.getMensaje());
		iconoCerrar.setVisible(mensajeEstatusBean.isPermanecer());
		
		getStyleClass().set(1, mensajeEstatusBean.getTipoMensaje().getClase());
		
		cajaMensaje.getChildren().set(0, mensajeEstatusBean.getIcono());
	}

	public void mostrarMensaje(String _mensaje, BDevTipoMensaje _tipoMensaje, boolean permanecer) {
		MensajeEstatusBean MensajeEstatusBean = new MensajeEstatusBean(_mensaje, _tipoMensaje, permanecer);
		
		getPilaMensajes().add(MensajeEstatusBean);
	}

	public ObservableList<MensajeEstatusBean> getPilaMensajes() {
		return pilaMensajes;
	}

	public boolean isAnimado() {
		return animado;
	}

	public void setAnimado(boolean animado) {
		this.animado = animado;
	}
}
