package es.academia.dialogos;


import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class GenerarRecibosPage extends WizardPage {
	private Table table;
	private Text txImporteTotal;

	/**
	 * Create the wizard.
	 */
	public GenerarRecibosPage() {
		super("GenerarRecibosPage");
		setTitle("Generación masiva de recibos");
		setDescription("Generar y emitir recibos");
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
		
		Label lblTotalImporte = new Label(container, SWT.NONE);
		lblTotalImporte.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.BOLD));
		lblTotalImporte.setBounds(366, 328, 107, 13);
		lblTotalImporte.setText("Total importe.......:");
		
		txImporteTotal = new Text(container, SWT.BORDER | SWT.READ_ONLY | SWT.RIGHT);
		txImporteTotal.setEditable(false);
		txImporteTotal.setBounds(474, 322, 98, 19);
		
		Button btnGenerarYEmitir = new Button(container, SWT.RADIO);
		btnGenerarYEmitir.setSelection(true);
		btnGenerarYEmitir.setBounds(10, 326, 107, 16);
		btnGenerarYEmitir.setText("Generar y emitir");
		
		Button btnSloGenerar = new Button(container, SWT.RADIO);
		btnSloGenerar.setBounds(123, 326, 83, 16);
		btnSloGenerar.setText("S\u00F3lo generar");
	}
}
