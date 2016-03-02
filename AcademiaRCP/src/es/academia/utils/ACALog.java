package es.academia.utils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import es.academia.utils.IConstantes;


public class ACALog extends Logger implements IConstantes {

	private static int diasLogs = 7;
	private static Level nivel = Level.OFF;
    private static File directorioLogs;

	static {
		try {
            // Establecemos la ruta dinamicamente
            final StringBuffer rutaLogs = new StringBuffer();
            rutaLogs.append(Informacion.getRutaEjecucion());

            rutaLogs.append(LOG_DIRECTORIO);
            directorioLogs = new File(rutaLogs.toString());

			if(directorioLogs!=null && !directorioLogs.exists()){
                  directorioLogs.mkdir();
            }

            rutaLogs.append('/');
            
            final StringBuffer rutaLogsEU = new StringBuffer();
            rutaLogsEU.append(rutaLogs);
            rutaLogsEU.append(LOG_FICHERO);
            System.setProperty("log.file",rutaLogsEU.toString());

                        // Recogemos la configuración de los dias
			diasLogs = Integer.parseInt(ELOG_DIAS);
		} catch (Exception e){
                  e.printStackTrace();
                }
  		try {
			// Borramos los ficheros
			if(directorioLogs!=null && directorioLogs.isDirectory()){
				final File[] ficheros = directorioLogs.listFiles();

				if(ficheros!=null){
					for(int i=0;i<ficheros.length;i++){
						final File fichero = ficheros[i];

						// Le sumamos a la fecha los dias que pueden durar los logs
						final Calendar calendario = Calendar.getInstance();
						calendario.setTime(new Date(fichero.lastModified()));
						calendario.add(Calendar.DAY_OF_YEAR, diasLogs);
						final Date fechaFichero = calendario.getTime();
						final Date fechaActual = new Date(System.currentTimeMillis());

						// Si se ha pasado de fecha lo borramos
						if(fechaActual.compareTo(fechaFichero)>0){
							fichero.delete();
						}
					}
				}
			}
		} catch (Exception e){
                  e.printStackTrace();
                }
	}

	protected ACALog(String nombre) {
		super(nombre);
	}

	public static Logger getLogger(Class clase){
		final Logger log = Logger.getLogger(clase);
		// Intentamos recoger el nivel de fichero
		try {
			nivel = Level.toLevel(LOG_NIVEL);
		} catch (Exception e){
                  e.printStackTrace();
                }
		// Establecemos el nivel
		log.setLevel(nivel);
		return log;
	}
 	
	private static void createLogger(){
	}	
}
