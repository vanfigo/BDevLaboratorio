package bucketdev.laboratorio.busqueda.facade;

import bucketdev.laboratorio.bean.ElementoCatalogoBean;

public interface ElementoCatalogoFacade<T> {

	public void obtenerElementos();

	public void filtrarElementos();

	public boolean isEquals(ElementoCatalogoBean _elemento);
	
	public T getBeanSeleccionado(ElementoCatalogoBean _elemento);
	
	public void solicitarCancelar();
	
	public void solicitarSeleccionar();

}