package es.academia.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Mensajes implements IConstantes {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(FICHERO_MENSAJES);

	private Mensajes() {}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return RESOURCE_BUNDLE.getString(ESCRITORIO_ERROR_INEXISTENTE);
		}
	}
}
