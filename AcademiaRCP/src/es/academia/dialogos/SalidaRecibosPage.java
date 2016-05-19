package es.academia.dialogos;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Button;

import es.academia.modelo.Recibo;
import es.academia.utils.ACALog;
import es.academia.utils.GestorErrores;
import es.academia.utils.IConstantes;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SalidaRecibosPage extends WizardPage  implements IConstantes{
	private static final Logger log = ACALog.getLogger(SalidaRecibosPage.class);
	private Table table;
	
	protected List<Recibo> listaRecibos = new ArrayList<Recibo>();

	private TableViewer tableViewer;
	private Text txtCurso;

	/**
	 * Create the wizard.
	 */
	public SalidaRecibosPage() {
		super("wizardPage");
		setTitle("Lista de recibos generados");
		setDescription("Comprueba que son correctos los recibos que se generarán");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new FormLayout());
		
		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		FormData fd_table = new FormData();
		fd_table.top = new FormAttachment(0, 45);
		fd_table.left = new FormAttachment(0, 10);
		fd_table.bottom = new FormAttachment(0, 318);
		fd_table.right = new FormAttachment(0, 648);
		table.setLayoutData(fd_table);
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnFDesde = tableViewerColumn_1.getColumn();
		tblclmnFDesde.setWidth(70);
		tblclmnFDesde.setText("F. Desde");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnFHasta = tableViewerColumn_2.getColumn();
		tblclmnFHasta.setWidth(70);
		tblclmnFHasta.setText("F. Hasta");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnConcepto = tableViewerColumn_3.getColumn();
		tblclmnConcepto.setWidth(299);
		tblclmnConcepto.setText("Concepto");
		
		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnTotalRecibo = tableViewerColumn_7.getColumn();
		tblclmnTotalRecibo.setAlignment(SWT.RIGHT);
		tblclmnTotalRecibo.setWidth(70);
		tblclmnTotalRecibo.setText("Importe");
		
		Button btnEliminar = new Button(container, SWT.NONE);
		btnEliminar.setText("Eliminar");
		FormData fd_btnEliminar = new FormData();
		fd_btnEliminar.top = new FormAttachment(table, 6);
		btnEliminar.setLayoutData(fd_btnEliminar);
		
		Button btnEditar = new Button(container, SWT.NONE);
		fd_btnEliminar.left = new FormAttachment(btnEditar, 6);
		FormData fd_btnEditar = new FormData();
		fd_btnEditar.top = new FormAttachment(table, 6);
		fd_btnEditar.left = new FormAttachment(0, 10);
		btnEditar.setLayoutData(fd_btnEditar);
		btnEditar.setText("Editar");
		
		Button btnAadir = new Button(container, SWT.NONE);
		FormData fd_btnAadir = new FormData();
		fd_btnAadir.top = new FormAttachment(table, 6);
		fd_btnAadir.left = new FormAttachment(btnEliminar, 6);
		btnAadir.setLayoutData(fd_btnAadir);
		btnAadir.setText("A\u00F1adir");
		
		Label lblListadoDeRecibos = new Label(container, SWT.NONE);
		FormData fd_lblListadoDeRecibos = new FormData();
		fd_lblListadoDeRecibos.top = new FormAttachment(0, 10);
		fd_lblListadoDeRecibos.left = new FormAttachment(0, 10);
		lblListadoDeRecibos.setLayoutData(fd_lblListadoDeRecibos);
		lblListadoDeRecibos.setText("Listado de recibos del curso");
		
		txtCurso = new Text(container, SWT.BORDER);
		txtCurso.setEditable(false);
		FormData fd_txtCurso = new FormData();
		fd_txtCurso.right = new FormAttachment(table, 0, SWT.RIGHT);
		fd_txtCurso.top = new FormAttachment(0, 4);
		fd_txtCurso.left = new FormAttachment(lblListadoDeRecibos, 6);
		txtCurso.setLayoutData(fd_txtCurso);
		txtCurso.setText(((NuevaMatriculaPage)getPreviousPage()).txDescCurso.getText());

		initDataBinding();
	}
	
	protected void cargarListaRecibos() {
		// TODO Auto-generated method stub
		try {
			NuevaMatriculaPage mp = (NuevaMatriculaPage)getPreviousPage();
			listaRecibos = mp.matAl.obtenerRecibos(mp.matricula);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			GestorErrores.mensajeTexto("Error matriculando al alumno: " + e.getMessage(), NIVEL_ERROR);
			e.printStackTrace();

		}
		initDataBinding();
	}

	public void setListaRecibos(List listaRecibos){
		this.listaRecibos = listaRecibos;
	}
	
	private void initDataBinding() {
		// TODO Auto-generated method stub
		ViewerSupport.bind(tableViewer, 
		                   new WritableList(listaRecibos, Recibo.class), 
		                   BeanProperties.values(Recibo.class, new String[] {
				                           "FDesde","FHasta","concepto","impTotal"}));	
	}
}
