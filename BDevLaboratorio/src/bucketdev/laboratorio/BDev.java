package bucketdev.laboratorio;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bucketdev.laboratorio.bean.SesionBean;
import bucketdev.laboratorio.bean.UsuarioBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import lombok.Getter;
import lombok.Setter;

public class BDev {

	public static final Rectangle2D PANTALLA = Screen.getPrimary().getVisualBounds();
	private @Getter @Setter static SesionBean sesionBean;

	public static String getPropiedad(String key) {
		InputStream inputStream = null;
		try {
			Properties properties = new Properties();
			inputStream = new FileInputStream("/Users/rodrigo/git/BDevLaboratorio/config/laboratorio.properties");
			properties.load(inputStream);
			String propiedad = properties.getProperty(key);

			if (propiedad == null) {
				String mensajeUsuario = "La propiedad " + key + ", no existe";
				getLogger().warn(mensajeUsuario);
				propiedad = "";
			}

			return propiedad;
		} catch (Exception e) {
			String mensajeUsuario = "Ocurrió un error al obtener la propiedad ";
			getLogger().error(mensajeUsuario, e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				String mensajeUsuario = "Ocurrió un error al cerrar el stream de propiedades";
				getLogger().fatal(mensajeUsuario, e);
			}
		}
		return "";
	}

	public static String getMensaje(String key) {
		InputStream inputStream = null;
		try {
			Properties properties = new Properties();
			inputStream = new FileInputStream("/Users/rodrigo/git/BDevLaboratorio/config/mensajes.properties");
			properties.load(inputStream);
			String mensaje = properties.getProperty(key);

			if (mensaje == null) {
				String mensajeUsuario = "El mensaje " + key + ", no existe";
				getLogger().warn(mensajeUsuario);
				mensaje = "";
			}

			return mensaje;
		} catch (Exception e) {
			String mensajeUsuario = "Ocurrió un error al obtener el mensaje";
			getLogger().error(mensajeUsuario, e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				String mensajeUsuario = "Ocurrió un error al cerrar el stream de mensajes";
				getLogger().fatal(mensajeUsuario, e);
			}
		}
		return "";
	}

	public static Logger getLogger() {
		return LogManager.getLogger("bdev.genericos");
	}

	public static ImageView creaImagen(String key) {
		ImageView img = null;
		try {
			img = new ImageView(new Image(BDev.class.getResourceAsStream(BDev.getPropiedad(key))));
		} catch (Exception e) {
			String mensajeUsuario = "Ocurrió un error al crear la imagen";
			getLogger().warn(mensajeUsuario, e);
			img = new ImageView(new Image(BDev.class.getResourceAsStream(BDev.getPropiedad("img.default"))));
		}
		return img;
	}

	public static void showTooltip(Control control, String tooltipText) {
		try {
			Point2D p = control.localToScene(0.0, 0.0);

			final Tooltip customTooltip = new Tooltip();
			customTooltip.setText(tooltipText);

			control.setTooltip(customTooltip);
			customTooltip.setAutoHide(true);

			customTooltip.show(control.getScene().getWindow(),
					p.getX() + control.getScene().getX() + control.getScene().getWindow().getX() + control.getWidth(),
					p.getY() + control.getScene().getY() + control.getScene().getWindow().getY() + control.getHeight());
			Timer timerRemover = new Timer(true);
			TimerTask taskRemover = new TimerTask() {

				@Override
				public void run() {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							customTooltip.hide();
							control.setTooltip(null);
						}
					});
				}
			};
			timerRemover.schedule(taskRemover, 3000);
		} catch (Exception e) {
			String mensajeUsuario = "El tooltip no pudo ser creado";
			getLogger().error(mensajeUsuario, e);
		}

	}

	public static Connection getConnection() throws BDevException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionUrl = new StringBuilder(String.format("jdbc:mysql://%s", BDev.getPropiedad("conexion.serverName")))
					.append(String.format(":%s", BDev.getPropiedad("conexion.puerto")))
					.append(String.format("/%s", BDev.getPropiedad("conexion.db")))
					.append(String.format("?user=%s", BDev.getPropiedad("conexion.usuario")))
					.append(String.format("&password=%s", BDev.getPropiedad("conexion.contrasena")))
					.toString();
			return DriverManager.getConnection(connectionUrl);
		} catch (Exception e) {
			throw new BDevException("genericos.conexion", BDevTipoMensaje.ERROR);
		}
	}

	public static String obtenerNombreUsuario(UsuarioBean usuarioBean) {
		StringBuilder nombre = new StringBuilder();

		nombre.append(usuarioBean.getNombre());
		if (usuarioBean.getApellidoPaterno() != null && !usuarioBean.getApellidoPaterno().trim().isEmpty()) {
			nombre.append(" ").append(usuarioBean.getApellidoPaterno());
		}
		if (usuarioBean.getApellidoMaterno() != null && !usuarioBean.getApellidoMaterno().trim().isEmpty()) {
			nombre.append(" ").append(usuarioBean.getApellidoMaterno());
		}

		return nombre.toString();
	}

}
