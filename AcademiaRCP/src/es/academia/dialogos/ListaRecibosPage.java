package es.academia.dialogos;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class ListaRecibosPage extends WizardPage {
	private Table table;
	private Text txImporteTotal;

	/**
	 * Create the wizard.
	 */
	public ListaRecibosPage() {
		super("ListaRecibosPage");
		setTitle("Generación masiva de recibos");
		setDescription("Comprobar la lista de recibos");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		
		TableViewer tblRecibos = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tblRecibos.getTable();
		table.setHeaderVisible(true);
		table.setBounds(10, 10, 562, 302);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tblRecibos, SWT.NONE);
		TableColumn tblclmnCurso = tableViewerColumn.getColumn();
		tblclmnCurso.setWidth(213);
		tblclmnCurso.setText("Curso");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tblRecibos, SWT.NONE);
		TableColumn tblclmnAlumno = tableViewerColumn_1.getColumn();
		tblclmnAlumno.setWidth(244);
		tblclmnAlumno.setText("Alumno");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tblRecibos, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn_2.getColumn();
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Importe");
		
		Button btnEliminarRecibo = new Button(container, SWT.NONE);
		btnEliminarRecibo.setBounds(10, 318, 80, 23);
		btnEliminarRecibo.setText("Eliminar recibo");
		
		Button btnVerDetalle = new Button(container, SWT.NONE);
		btnVerDetalle.setText("Ver detalle");
		btnVerDetalle.setBounds(96, 318, 80, 23);
		
		Label lblTotalImporte = new Label(container, SWT.NONE);
		lblTotalImporte.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.BOLD));
		lblTotalImporte.setBounds(366, 328, 107, 13);
		lblTotalImporte.setText("Total importe.......:");
		
		txImporteTotal = new Text(container, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		txImporteTotal.setEditable(false);
		txImporteTotal.setBounds(474, 322, 98, 19);
	}
}
