package bucketdev.laboratorio.view;

import javafx.scene.control.ComboBox;

public abstract class GenericoCombo<A> extends ComboBox<A> {

	public GenericoCombo() {
		this(true, "Seleccionar...");
	}
	public GenericoCombo(boolean _loadOnRender) {
		this(_loadOnRender, "Seleccionar...");
	}
	
	public GenericoCombo(String _prompt) {
		this(true, _prompt);
	}
	
	public GenericoCombo(boolean _loadOnRender, String _prompt) {
		setPromptText(_prompt);
		
		if(_loadOnRender){
			cargar();
		}
	}
	
	public abstract int cargar();
	
	public abstract void setValueById(int id);



}
