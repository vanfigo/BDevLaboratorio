package bucketdev.laboratorio.view;

import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class NumberField extends TextField {

	public NumberField() {
		this.setMinHeight(30);
		this.setPrefHeight(30);
		addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (!isInteger(event.getCharacter())) {
					if (event.getCharacter().equals(".")) {
						if ((getText().length() - getText().replace(".", "").length()) > 0) {
							event.consume();
						}
					} else if (event.getCharacter().equals("-")) {
						if ((getText().length() - getText().replace("-", "").length()) > 0) {
							event.consume();
						}
					} else {
						event.consume();
					}
				}
			}
		});
	}

	private boolean isInteger(String s) {
		Scanner sc = new Scanner(s.trim());
		if (!sc.hasNextInt(10)) {
			sc.close();
			return false;
		}
		sc.nextInt(10);
		boolean hasNext = !sc.hasNext();
		sc.close();
		return hasNext;
	}

	public String getTextValue() {
		if (!getText().matches("[-]?[0-9]*[\\.]?[0-9]*")) {
			return "";
		}
		return getText();
	}

	public String getTextValue(boolean _flForceValue) {
		if (_flForceValue) {
			return getText();
		} else {
			return getTextValue();
		}
	}

}
