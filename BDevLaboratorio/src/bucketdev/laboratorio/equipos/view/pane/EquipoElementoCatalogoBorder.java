package bucketdev.laboratorio.equipos.view.pane;

import bucketdev.laboratorio.BDev;
import bucketdev.laboratorio.BDevMain;
import bucketdev.laboratorio.bean.ElementoCatalogoBean;
import bucketdev.laboratorio.bean.EquipoBean;
import bucketdev.laboratorio.busqueda.view.pane.ElementoCatalogoBorder;
import bucketdev.laboratorio.equipos.viewmodel.EquipoViewModel;
import bucketdev.laboratorio.exception.BDevException;
import bucketdev.laboratorio.type.BDevTipoMensaje;
import javafx.collections.FXCollections;

public class EquipoElementoCatalogoBorder extends ElementoCatalogoBorder<EquipoBean> {
	
	private EquipoViewModel viewModel;

	public EquipoElementoCatalogoBorder(String _filtro) {
		super(_filtro);
	}

	/*
	 * @see cayp.generico.control.busqueda.facade.ControlBusquedaPaneFacade#filtrarElementos()
	 */
	@Override
	public void filtrarElementos() {
		getElementosFiltrados().clear();
		for (ElementoCatalogoBean elemento : getListaElementos()) {
			if (isEquals(elemento)) {
				getElementosFiltrados().add(elemento);
			}
		}
	}

	/*
	 * @see cayp.generico.control.busqueda.facade.ControlBusquedaPaneFacade#getElementoOriginal(cayp.utils.bean.ElementoCatalogoBean)
	 */
	@Override
	public EquipoBean getBeanSeleccionado(ElementoCatalogoBean _elemento) {
		for(EquipoBean equipo : getListaElementosOriginales()){
			if(_elemento.getId().equals(equipo.getClave())){
				return equipo;
			}
		}
		return null;
	}

	/*
	 * @see cayp.generico.control.busqueda.facade.ControlBusquedaPaneFacade#isEquals(cayp.utils.bean.ElementoCatalogoBean)
	 */
	@Override
	public boolean isEquals(ElementoCatalogoBean _elemento) {
		String filterString = getTxtBuscar().getText().trim();
		if (filterString == null || filterString.isEmpty()) {
			return true;
		}

		String lowerCaseFilterString = filterString.toLowerCase();

		if (_elemento.getValor().toLowerCase().indexOf(lowerCaseFilterString) >= 0
				|| _elemento.getId().toLowerCase().indexOf(lowerCaseFilterString) >= 0) {
			return true;
		}

		return false;
	}

	/*
	 * @see cayp.generico.control.busqueda.facade.ControlBusquedaPaneFacade#obtenerElementos()
	 */
	@Override
	public void obtenerElementos() {
		try {
			setListaElementosOriginales(getViewModel().consulta());
				for (EquipoBean equipo : getListaElementosOriginales()) {
					ElementoCatalogoBean elemento = new ElementoCatalogoBean();
					
					elemento.setId(equipo.getClave());
					elemento.setValor(equipo.getNombre());
					
					getListaElementos().add(elemento);
				}

			setElementosFiltrados(FXCollections.observableArrayList(getListaElementos()));
		} catch (BDevException bde) {
			BDevMain.mostrarMensaje(BDev.getMensaje(bde.getCodigoError()), bde.getTipoMensaje());
		} catch (Exception e) {
			BDev.getLogger().error(BDev.getMensaje("equipos.consulta.error"), e);
			BDevMain.mostrarMensaje(BDev.getMensaje("equipos.consulta.error"), BDevTipoMensaje.ERROR);
		}
	}

	public EquipoViewModel getViewModel() {
		if(viewModel == null) {
			viewModel = new EquipoViewModel();
		}
		return viewModel;
	}

}
