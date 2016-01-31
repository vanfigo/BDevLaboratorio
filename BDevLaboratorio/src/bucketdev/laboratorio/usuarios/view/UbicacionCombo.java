package bucketdev.laboratorio.usuarios.view;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.UbicacionBean;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.ubicaciones.viewmodel.UbicacionViewModel;
import bucketdev.laboratorio.view.GenericoCombo;

public class UbicacionCombo extends GenericoCombo<UbicacionBean> {
	
	private UbicacionViewModel viewModel;

	public UbicacionCombo() {
		super(false, "Seleccionar Ubicación...");
		
		viewModel = new UbicacionViewModel();
		
		cargar();
	}

	/*
	 * @see cayp.generico.combo.ComboBoxGenerico#cargar()
	 */
	@Override
	public int cargar()  {
		try {
			getItems().clear();
			getItems().addAll(viewModel.consulta());
		} catch (BDevException bde) {
			BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("ubicaciones.consulta.error"), e);
			BDevMain.mostrarMensaje(BDev.getMensaje("ubicaciones.consulta.error"), BDevTipoMensaje.ERROR);
		}
		return getItems().size();
	}

	/*
	 * @see cayp.generico.combo.ComboBoxGenerico#setValueById(int)
	 */
	@Override
	public void setValueById(int id) {
		for(UbicacionBean ubicacionBean : getItems()){
			if(ubicacionBean.getId() == id){
				setValue(ubicacionBean);
			}
		}
	}

}
