package bucketdev.laboratorio.instalaciones.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.InstalacionBean;
import bucketdev.laboratorio.bean.ModuloBean;
import bucketdev.laboratorio.event.TableViewEvent;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.instalaciones.event.InstalacionEvent;
import bucketdev.laboratorio.instalaciones.view.InstalacionBorderView;
import bucketdev.laboratorio.instalaciones.view.InstalacionTableView;
import bucketdev.laboratorio.instalaciones.viewmodel.InstalacionViewModel;
import bucketdev.laboratorio.modulo.controller.ModuloController;
import bucketdev.laboratorio.modulo.event.ModuloEvent;
import bucketdev.laboratorio.type.BDevTipoBotonesMensaje;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.mensaje.MensajePopUp;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.util.converter.NumberStringConverter;

public class InstalacionController extends ModuloController {

	private InstalacionBorderView view;
	private InstalacionViewModel viewModel;

	public InstalacionController(ModuloBean _moduloBean) {
		view = new InstalacionBorderView(_moduloBean);
		viewModel = new InstalacionViewModel();

		atarEventos();
	}

	public void atarEventos() {
		view.addEventHandler(ModuloEvent.AGREGAR, new EventHandler<ModuloEvent>() {

			@Override
			public void handle(ModuloEvent event) {
				view.mostrarMensajeProducto();
			}
		});

		view.addEventHandler(ModuloEvent.EDITAR, new EventHandler<ModuloEvent>() {

			@Override
			public void handle(ModuloEvent event) {
				InstalacionBean instalacionBean = view.getSeleccionado();
				if (instalacionBean == null) {
					BDevMain.mostrarMensaje(BDev.getMensaje("instalaciones.seleccionar.editar"));
				} else {
					view.mostrarMensajeEditarProducto(instalacionBean);
				}
			}
		});

		view.addEventHandler(ModuloEvent.ELIMINAR, new EventHandler<ModuloEvent>() {

			@Override
			public void handle(ModuloEvent event) {
				InstalacionBean instalacionBean = view.getSeleccionado();
				if (instalacionBean == null) {
					BDevMain.mostrarMensaje(BDev.getMensaje("instalaciones.seleccionar.eliminar"));
				} else {
					MensajePopUp mensaje = new MensajePopUp("¿Desea eliminar la instalación?",
							new Label(BDev.getMensaje("instalaciones.eliminar.confirmacion")),
							BDevTipoBotonesMensaje.ACEPTAR_CANCELAR, BDevTipoMensaje.ALERTA) {

						@Override
						public void guardar() {
						}

						@Override
						public void aceptar() {
							try {
								viewModel.eliminar(instalacionBean);
								BDevMain.mostrarMensaje(BDev.getMensaje("instalaciones.actualizar.correcto"),
										BDevTipoMensaje.CORRECTO);
								view.cargar();
								this.close();
							} catch (BDevException bde) {
								BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
							}
						}
					};
					mensaje.show();
				}
			}
		});

		view.addEventHandler(InstalacionEvent.ACTUALIZAR_INSTALACION, new EventHandler<InstalacionEvent>() {

			@Override
			public void handle(InstalacionEvent event) {
				try {
					viewModel.agregarOEditar(event.getInstalacionBean());
					BDevMain.mostrarMensaje(BDev.getMensaje("instalaciones.actualizar.correcto"), BDevTipoMensaje.CORRECTO);
				} catch (BDevException bde) {
					BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
				}
			}
		});
		
		view.addEventHandler(InstalacionEvent.RENOVAR, new EventHandler<InstalacionEvent>() {

			@Override
			public void handle(InstalacionEvent event) {
				InstalacionBean instalacionBean = view.getSeleccionado();
				if (instalacionBean == null) {
					BDevMain.mostrarMensaje(BDev.getMensaje("instalaciones.seleccionar.renovar"));
				} else {
					view.mostrarMensajeRenovarInstalacion(instalacionBean);
				}
			}
		});

		view.addEventHandler(InstalacionEvent.RENOVAR_INSTALACION, new EventHandler<InstalacionEvent>() {

			@Override
			public void handle(InstalacionEvent event) {
				try {
					viewModel.renovar(event.getInstalacionBean());
					BDevMain.mostrarMensaje(BDev.getMensaje("instalaciones.actualizar.correcto"), BDevTipoMensaje.CORRECTO);
				} catch (BDevException bde) {
					BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
				}
			}
		});
		
		view.addEventHandler(InstalacionEvent.FILTRAR_INSTALACION, new EventHandler<InstalacionEvent>() {

			@Override
			public void handle(InstalacionEvent event) {
				if (event.getFiltroBean().isPorCaducar()) {
					event.getFiltroBean().setDiasCaducidad(Integer.valueOf(view.getNfDiasCaducidad().getTextValue()));
				}
				view.setFiltroBean(event.getFiltroBean());
			}
		});
		
		view.addEventHandler(TableViewEvent.CARGADO, new EventHandler<TableViewEvent>() {

			@Override
			public void handle(TableViewEvent event) {
				String numDiasCaducidad = view.getNfDiasCaducidad().getTextValue().isEmpty()
						? BDev.getPropiedad("configuracion.caducidad") : view.getNfDiasCaducidad().getTextValue();
				InstalacionTableView tableView = ((InstalacionTableView) event.getTableView());
				for (InstalacionBean instalacionBean : tableView.getItems()) {
					view.getNfDiasCaducidad().textProperty().bindBidirectional(instalacionBean.diasCaducidadProperty(),
							new NumberStringConverter());
				}
				view.getNfDiasCaducidad().setText(numDiasCaducidad);
			}
		});
		
		view.addEventHandler(InstalacionEvent.EXPORTAR_EXCEL, new EventHandler<InstalacionEvent>() {

			@Override
			public void handle(InstalacionEvent event) {
				Workbook wb = null;
				FileOutputStream out = null;
				try {
					wb = new XSSFWorkbook();

					Sheet sheet = wb.createSheet("Reporte");

					PrintSetup printSetup = sheet.getPrintSetup();
					printSetup.setLandscape(true);
					sheet.setFitToPage(true);
					sheet.setHorizontallyCenter(true);

					Row headerRow = sheet.createRow(1);
					headerRow.setHeightInPoints(40);
					
					Font titleFont = wb.createFont();
					titleFont.setColor(IndexedColors.WHITE.getIndex());
					
					CellStyle styleHeader = wb.createCellStyle();
					styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
					styleHeader.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
					styleHeader.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
					styleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
					styleHeader.setFont(titleFont);
					
					CellStyle styleText = wb.createCellStyle();
					styleText.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
					
					ObservableList<TableColumn<InstalacionBean, ?>> listaColumnas = view.getTableView().getColumns();
					List<InstalacionBean> listaInstalaciones = view.getTableView().getItems();

					int[] arrColumnWidth = new int[view.getTableView().getColumns().size()];
					//Crea los encabezados
					for (int columna = 0; columna < view.getTableView().getColumns().size(); columna++) {
						arrColumnWidth[columna] = listaColumnas.get(columna).getText().length();

						Cell headerCell = headerRow.createCell(1 + columna);
						headerCell.setCellValue(listaColumnas.get(columna).getText());
						headerCell.setCellStyle(styleHeader);
					}
					//Crea los registros
					for (int registro = 0; registro < listaInstalaciones.size(); registro++) {
						Row row = sheet.createRow(2 + registro);
						
						for (int columna = 0; columna < view.getTableView().getColumns().size(); columna++) {
							String value = getValueColumn(listaColumnas.get(columna), listaInstalaciones.get(registro));
							arrColumnWidth[columna] = arrColumnWidth[columna] > value.length() ? arrColumnWidth[columna] : value.length();
							
							Cell cell = row.createCell(1 + columna);
							cell.setCellValue(value);
							cell.setCellStyle(styleText);
						}
					}
					
					for (int columna = 0; columna < view.getTableView().getColumns().size(); columna++) {
						sheet.setColumnWidth(1 + columna, arrColumnWidth[columna] * 256);
					}
					
					String file = "C:\\bdevlaboratorio\\Reporte.xlsx";
					out = new FileOutputStream(file);
					wb.write(out);
					BDevMain.mostrarMensaje(BDev.getMensaje("instalaciones.exportar.correcto"), BDevTipoMensaje.CORRECTO);
				} catch (Exception  e) {
					BDevMain.mostrarMensaje(BDev.getMensaje("instalaciones.exportar.error"), BDevTipoMensaje.ERROR);
					BDev.getLogger().error(BDev.getMensaje("instalaciones.exportar.error"), e);
				} finally {
					try {
						if(out != null)
							out.close();
						if(wb != null)
							wb.close();
					} catch (IOException e) {
						String strMensaje = BDev.getMensaje("genericos.administrador");
						BDevMain.mostrarMensaje(strMensaje, BDevTipoMensaje.ERROR);
						BDev.getLogger().error(strMensaje, e);
					}
				}

			}
		});
	}
	
	private String getValueColumn(TableColumn<InstalacionBean, ?> _columna, InstalacionBean _instalacionBean)
			throws Exception {
		String strColumnaId = _columna.getId();
		Class<?> entity = InstalacionBean.class;
		String strMethod = String.format("get%s", Character.toUpperCase(strColumnaId.charAt(0)) + strColumnaId.substring(1));
		Method method = entity.getDeclaredMethod(strMethod, new Class[] {});
		if(strMethod.equals("getDiasCaducidad")) {
			Calendar calendarCaducidad = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

			calendarCaducidad.setTime(_instalacionBean.getFechaCaducidad());
			calendarCaducidad.add(Calendar.DATE, _instalacionBean.getCaducidad());
			return format.format(calendarCaducidad.getTime());
		} else {
			return String.valueOf(method.invoke(_instalacionBean));
			
		}
	}

	@Override
	public Node getView() {
		return view;
	}

	@Override
	public void cargar() {
		view.cargar();
	}

}
