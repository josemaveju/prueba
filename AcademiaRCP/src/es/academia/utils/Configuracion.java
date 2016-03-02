package es.academia.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.PropertyResourceBundle;

// Esta clase NO PUEDE TENER LOGS porque la clase de los logs la utiliza para cargar sus propiedades y si esta tiene logs
// no tenemos disponibles dichas propiedades
public class Configuracion implements IConstantes {

	private static final Hashtable configuracionInterna = new Hashtable();

	private static PropertyResourceBundle propiedades = null;

	static {
		
		try {
			   System.err.println("Inicializando propiedades. fichero: "+ FICHERO_PROPIEDADES);
               propiedades = new PropertyResourceBundle(new FileInputStream(FICHERO_PROPIEDADES));
			   System.err.println("IFichero inicializado: ");               
		} catch (Exception e) {
		   System.err.println("Error inicializando fichero: ");               
			e.printStackTrace();
		}		
		
		// Recogemos todos los valores del fichero y lo guardamos
		Enumeration claves = propiedades.getKeys();
		while(claves.hasMoreElements()){
			final String clave = claves.nextElement().toString();
			configuracionInterna.put(clave.toUpperCase(), propiedades.getString(clave));
		}		
		
	}

	private Configuracion() {}

	public static String getString(String key) {
		if(configuracionInterna.containsKey(key.toUpperCase()))
			return configuracionInterna.get(key.toUpperCase()).toString();
		else
            return "";
	}

	public static void setString(String key, String value){
		// Si ya existe el valor en la configuracion lo dejamos porque prevalece el del fichero
		if(!configuracionInterna.containsKey(key.toUpperCase()))
			configuracionInterna.put(key.toUpperCase(), value);
	}
	
	public static void updateString(String key, String value){
		configuracionInterna.put(key.toUpperCase(), value);
	}	
}
