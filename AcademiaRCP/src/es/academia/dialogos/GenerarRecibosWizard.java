package es.academia.dialogos;

import org.eclipse.jface.wizard.Wizard;

import es.academia.negocio.DatosGenRecibos;

public class GenerarRecibosWizard extends Wizard {

	protected DatosGenRecibos  dtgr = new DatosGenRecibos();
	protected ListaRecibosPage listaRecibos = new ListaRecibosPage();
	protected GenerarRecibosPage generarRecibos = new GenerarRecibosPage();
	protected SelectRecibosPage selectRecibos = new SelectRecibosPage();
	
	public GenerarRecibosWizard() {
		setWindowTitle("New Wizard");
	}

	@Override
	public void addPages() {
		addPage(selectRecibos);
		addPage(listaRecibos);
		addPage(generarRecibos);
	}

	@Override
	public boolean performFinish() {
		return false;
	}

}
