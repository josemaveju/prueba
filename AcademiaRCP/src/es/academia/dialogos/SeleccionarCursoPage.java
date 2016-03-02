package es.academia.dialogos;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;

import es.academia.modelo.Curso;
import es.academia.modelo.CursoHome;
import es.academia.negocio.NegocioException;
import es.academia.utils.ACALog;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SeleccionarCursoPage extends WizardPage {
	private static final Logger log = ACALog.getLogger(SeleccionarCursoPage.class);
	private Table table;
	/**
	 * @wbp.nonvisual location=81,469
	 */
	private  Curso curso;
	private TableViewer tableViewer;
	private CursoHome cursoHome = new CursoHome();
	private boolean enableNext;

	/**
	 * Create the wizard.
	 */
	public SeleccionarCursoPage() {
		super("wizardPage");
		setTitle("Nueva Matrícula");
		setDescription("Seleccionar el curso en el que quieres matricular al alumno");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		
		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				log.debug ("Entrando en WidgetSelected");
				getWizard().getContainer().updateButtons();
				try {
					seleccionarCurso();
				} catch (NegocioException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		table.setHeaderVisible(true);
		table.setBounds(10, 10, 637, 313);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnCdigo = tableViewerColumn.getColumn();
		tblclmnCdigo.setWidth(64);
		tblclmnCdigo.setText("C\u00F3digo");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNombreCurso = tableViewerColumn_1.getColumn();
		tblclmnNombreCurso.setWidth(237);
		tblclmnNombreCurso.setText("Nombre del curso");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnFInicio = tableViewerColumn_2.getColumn();
		tblclmnFInicio.setWidth(72);
		tblclmnFInicio.setText("F. inicio");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnFFin = tableViewerColumn_3.getColumn();
		tblclmnFFin.setWidth(75);
		tblclmnFFin.setText("F. Fin");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnProfesor = tableViewerColumn_4.getColumn();
		tblclmnProfesor.setWidth(184);
		tblclmnProfesor.setText("Profesor");
		
		cargarListaCursos();
		setPageComplete(false);
	}
	
	protected void seleccionarCurso() throws NegocioException {
		// TODO Auto-generated method stub

		int idxSelect= table.getSelectionIndex();
		if (idxSelect == -1)
			curso=null;
    	else{
			TableItem select = table.getItem(idxSelect);
			Integer claveCurso = new Integer(select.getText(0));
			CursoHome clh = new CursoHome();
			curso = clh.findById(claveCurso);
    	}
		
		((NuevaMatriculaWizard)getWizard()).setCurso(curso);
		((NuevaMatriculaPage)getNextPage()).setCurso(curso);
		((NuevaMatriculaPage)getNextPage()).setMatricula();
		((NuevaMatriculaPage)getNextPage()).initDataBindings();
	}

	private void cargarListaCursos() {
		// TODO Auto-generated method stub
		ViewerSupport.bind(tableViewer, 
		                   new WritableList(cursoHome.listarTodos(), Curso.class), 
		                   BeanProperties.values(Curso.class, new String[] {
				                          "idCurso", "descCurso","FInicio","FFin",
				                          "idProfesor"}));	
	}
	

	public boolean canFlipToNextPage (){

		   if (table.getSelectionIndex()!= -1){
			   enableNext=true; 
			   return true;
		   }
		   else{
			  enableNext=false;
		   	 return false;
		   }
		}
	
	public boolean canFinish(){
		return false;
	}

	public boolean isPageComplete() {
		IWizardPage[] pages = getWizard().getPages();
//		        hard coding for this case . But this is bad..
		if(enableNext && pages[1].isPageComplete()) {
		return true;
		}
		return false;
		}

}
