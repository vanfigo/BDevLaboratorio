package bucketdev.laboratorio.instalaciones.view.column;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.InstalacionBean;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class InstalacionFechaCaducidadColumn extends TableColumn<InstalacionBean, Integer> {

	private final long DIAS_MILISEGUNDOS = 1000 * 60 * 60 * 24;

	public InstalacionFechaCaducidadColumn() {
		this("");
	}

	public InstalacionFechaCaducidadColumn(String _header) {
		setText(_header);
		setCellFactory(new Callback<TableColumn<InstalacionBean, Integer>, TableCell<InstalacionBean, Integer>>() {

			@Override
			public TableCell<InstalacionBean, Integer> call(TableColumn<InstalacionBean, Integer> param) {
				return new TableCell<InstalacionBean, Integer>() {
					/*
					 * @see javafx.scene.control.Cell#updateItem(java.lang.
					 * Object, boolean)
					 */
					@Override
					protected void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);
						if (!empty && item != null) {
							try {
								InstalacionBean instalacionBean = getTableView().getItems().get(getIndex());
								Calendar calendarCaducidad = Calendar.getInstance();
								Calendar calendar = Calendar.getInstance();
								SimpleDateFormat format = new SimpleDateFormat("dd 'de' MMMMM, yyyy");

								calendarCaducidad.setTime(instalacionBean.getFechaCaducidad());
								calendarCaducidad.add(Calendar.DATE, instalacionBean.getCaducidad());

								long diasRestantes = Math.floorDiv(
										calendarCaducidad.getTimeInMillis() - calendar.getTimeInMillis(),
										DIAS_MILISEGUNDOS) + 1;
								boolean isPorCaducar = diasRestantes <= item;

								if (getStyleClass().size() > 4) {
									getStyleClass().remove(4, getStyleClass().size());
								}
								getStyleClass().add(isPorCaducar ? "danger" : "success");
								setAlignment(Pos.BASELINE_LEFT);
								setText(format.format(calendarCaducidad.getTime()));
								setGraphic(isPorCaducar ? BDev.creaImagen("status.img.caducado")
										: BDev.creaImagen("status.img.vigente"));
							} catch (Exception e) {
								setGraphic(null);
								setText(null);
								BDev.getLogger().error(BDev.getMensaje("instalaciones.formato.caducidad.error"), e);
								BDevMain.mostrarMensaje(BDev.getMensaje("instalaciones.formato.caducidad.error"),
										BDevTipoMensaje.ERROR);
							}
						} else {
							if (getStyleClass().size() > 4) {
								getStyleClass().remove(4, getStyleClass().size());
							}
							setGraphic(null);
							setText(null);
						}
					}
				};
			}
		});
	}

}
