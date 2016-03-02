package es.academia.utils;

//import org.apache.log4j.Logger;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;


public class GestorErrores implements IConstantes {

	private final static Display display = PlatformUI.getWorkbench().getDisplay();

	
	public static void mensajeTexto(String mensaje, int nivel){
		final String _mensaje = mensaje;
		final int _nivel = nivel;

		display.syncExec(
				new Runnable() {
				    public void run(){
						final Shell shell = display.getActiveShell();
						String titulo = "";

						switch(_nivel){
							case NIVEL_ERROR:
								titulo = ERROR_TITULO_TEXTO;
								MessageDialog.openError(shell, titulo, _mensaje);

								break;
							case NIVEL_INFO:
								titulo = INFO_TITULO_TEXTO;
								MessageDialog.openWarning(shell, titulo, _mensaje);

								break;
						}
				    }
				  });
	}	

}
