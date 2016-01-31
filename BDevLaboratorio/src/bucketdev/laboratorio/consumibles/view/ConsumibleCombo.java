package bucketdev.laboratorio.consumibles.view;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.ConsumibleBean;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.consumibles.viewmodel.ConsumibleViewModel;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import bucketdev.laboratorio.view.GenericoCombo;

public class ConsumibleCombo extends GenericoCombo<ConsumibleBean> {
	
	private ConsumibleViewModel viewModel;
	private EquipoBean equipoBean;

	public ConsumibleCombo() {
		this(null);
	}
	
	public ConsumibleCombo(EquipoBean _equipoBean) {
		super(false, "Seleccionar Consumible...");
		
		equipoBean = _equipoBean;
		viewModel = new ConsumibleViewModel();
		
		cargar();
	}

	/*
	 * @see cayp.generico.combo.ComboBoxGenerico#cargar()
	 */
	@Override
	public int cargar()  {
		try {
			getItems().clear();
			getItems().addAll(viewModel.consulta(equipoBean));
		} catch (BDevException bde) {
			BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("consumibles.consulta.error"), e);
			BDevMain.mostrarMensaje(BDev.getMensaje("consumibles.consulta.error"), BDevTipoMensaje.ERROR);
		}
		return getItems().size();
	}
	
	public void setEquipoBean(EquipoBean _equipoBean) {
		equipoBean = _equipoBean;
		cargar();
	}

	/*
	 * @see cayp.generico.combo.ComboBoxGenerico#setValueById(int)
	 */
	@Override
	public void setValueById(int id) {
		for(ConsumibleBean consumibleBean : getItems()){
			if(consumibleBean.getId() == id){
				setValue(consumibleBean);
			}
		}
	}

}
