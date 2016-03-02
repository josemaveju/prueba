package es.academia.acciones;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import es.academia.ICommandIds;
import es.academia.dialogos.DetalleAlumno;
import es.academia.modelo.Alumno;
import es.academia.utils.GestorErrores;
import es.academia.utils.IConstantes;


public class NuevoAlumnoAction extends Action {
	
	private final IWorkbenchWindow window;
	private int instanceNum = 0;

	
	public NuevoAlumnoAction(IWorkbenchWindow window, String label) {
		this.window = window;

        setText(label);
        // The id is used to refer to the action in a menu or toolbar
		setId(ICommandIds.CMD_OPEN);
        // Associate the action with a pre-defined command, to allow key bindings.
		setActionDefinitionId(ICommandIds.CMD_OPEN);
		setImageDescriptor(academiarcp.Activator.getImageDescriptor("/icons/Add_alumno_24.png"));
	}
	
	public void run() {
		if(window != null) {	
			
			try {
				Alumno alumno = new Alumno();
				DetalleAlumno dlg = new DetalleAlumno(window.getShell());
				dlg.setAlumno(alumno);
				dlg.seleccionarPanel(IConstantes.PANELGENERAL);
				dlg.setTipoOperacion(IConstantes.TIPOOPERALTA);
				dlg.open();

			} catch (Exception e) {
				GestorErrores.mensajeTexto("Error abriendo pantalla de nuevo alumno:" + e.getMessage(), IConstantes.NIVEL_ERROR);
			}
		}
	}
}
