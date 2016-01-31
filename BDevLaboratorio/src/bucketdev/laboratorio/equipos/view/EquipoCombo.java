package bucketdev.laboratorio.equipos.view;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.equipos.viewmodel.EquipoViewModel;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.GenericoCombo;

public class EquipoCombo extends GenericoCombo<EquipoBean> {
	
	private EquipoViewModel viewModel;

	public EquipoCombo() {
		super(false, "Seleccionar Equipo...");
		
		viewModel = new EquipoViewModel();
		
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
			BDev.getLogger().error(BDev.getMensaje("equipos.consulta.error"), e);
			BDevMain.mostrarMensaje(BDev.getMensaje("equipos.consulta.error"), BDevTipoMensaje.ERROR);
		}
		return getItems().size();
	}

	/*
	 * @see cayp.generico.combo.ComboBoxGenerico#setValueById(int)
	 */
	@Override
	public void setValueById(int id) {
		for(EquipoBean equipoBean : getItems()){
			if(equipoBean.getId() == id){
				setValue(equipoBean);
			}
		}
	}

}
