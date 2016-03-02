package es.academia.utils;

import java.util.ArrayList;
import java.util.Hashtable;

import org.eclipse.core.runtime.Platform;

// Esta clase NO PUEDE TENER LOGS porque la clase de los logs la utiliza para cargar sus propiedades y si esta tiene logs
// no tenemos disponibles dichas propiedades
public class Informacion implements IConstantes {

	private static String estado;
    private static String rutaEjecucion;
    private static int anchoPantalla;
    private static int altoPantalla;
    private static String tipoInstalacion;
        
        
        // Obtenemos la ruta de configuracion
        static {
          if (Platform.getInstanceLocation() != null) {
            final StringBuffer ruta = new StringBuffer();
            ruta.append(Platform.getInstanceLocation().getURL().getFile().substring(1));
            if (ruta.charAt(ruta.length()-1) != '/')
              ruta.append('/');
            Informacion.setRutaEjecucion(ruta.toString());
          }
        }
        
        public static String getEstado() {
			return estado;
		}

		public static void setEstado(String estado) {
			Informacion.estado = estado;
		}

		public static String getRutaEjecucion() {
			return rutaEjecucion;
		}

		public static void setRutaEjecucion(String rutaEjecucion) {
			Informacion.rutaEjecucion = rutaEjecucion;
		}

		public static int getAnchoPantalla() {
			return anchoPantalla;
		}

		public static void setAnchoPantalla(int anchoPantalla) {
			Informacion.anchoPantalla = anchoPantalla;
		}

		public static int getAltoPantalla() {
			return altoPantalla;
		}

		public static void setAltoPantalla(int altoPantalla) {
			Informacion.altoPantalla = altoPantalla;
		}

		public static String getTipoInstalacion() {
			return tipoInstalacion;
		}

		public static void setTipoInstalacion(String tipoInstalacion) {
			Informacion.tipoInstalacion = tipoInstalacion;
		}
                
}