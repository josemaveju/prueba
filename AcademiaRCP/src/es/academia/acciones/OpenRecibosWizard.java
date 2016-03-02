package es.academia.acciones;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import es.academia.ICommandIds;
import es.academia.dialogos.GenerarRecibosWizard;


public class OpenRecibosWizard extends Action {
	
	private final IWorkbenchWindow window;
	private int instanceNum = 0;

	
	public OpenRecibosWizard(IWorkbenchWindow window) {
		this.window = window;

        setText("Generar recibos");
        // The id is used to refer to the action in a menu or toolbar
		setId(ICommandIds.CMD_OPEN);
        // Associate the action with a pre-defined command, to allow key bindings.
		setActionDefinitionId(ICommandIds.CMD_OPEN);
		setImageDescriptor(academiarcp.Activator.getImageDescriptor("/icons/factura_24.png"));
	}
	
	public void run() {
		if(window != null) {	
			// TODO Auto-generated method stub
			GenerarRecibosWizard nmw = new GenerarRecibosWizard();
								
			// Instantiates the wizard container with the wizard and opens it
			WizardDialog dialog = new WizardDialog(window.getShell(), nmw);
			dialog.create();
			dialog.open();
		}
	}
}
