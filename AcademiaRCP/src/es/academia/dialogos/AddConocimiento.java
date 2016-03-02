package es.academia.dialogos;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;

import es.academia.modelo.Conocimiento;
import es.academia.modelo.ConocimientoHome;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AddConocimiento extends Dialog {
	private DataBindingContext m_bindingContext;
	private Text txDescripcion;
	/**
	 * @wbp.nonvisual location=49,129
	 */
	private final Conocimiento conocimiento = new Conocimiento();
	private Label lblDescripcin;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public AddConocimiento(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FormLayout());
		
		lblDescripcin = new Label(container, SWT.HORIZONTAL);
		FormData fd_lblDescripcin = new FormData();
		fd_lblDescripcin.top = new FormAttachment(0, 10);
		fd_lblDescripcin.left = new FormAttachment(0, 10);
		lblDescripcin.setLayoutData(fd_lblDescripcin);
		lblDescripcin.setText("Descripci\u00F3n");
		
		txDescripcion = new Text(container, SWT.BORDER);
		FormData fd_txDescripcion = new FormData();
		fd_txDescripcion.right = new FormAttachment(0, 434);
		fd_txDescripcion.top = new FormAttachment(lblDescripcin, 6);
		fd_txDescripcion.left = new FormAttachment(0, 10);
		txDescripcion.setLayoutData(fd_txDescripcion);

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		m_bindingContext = initDataBindings();
	}

	protected void buttonPressed(int buttonId)  {
		if (buttonId==0){
			System.err.println("boton presionado:" + buttonId);
			ConocimientoHome ch = new ConocimientoHome();
			ch.persist(conocimiento);
		}
		close();
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 164);
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTxDescripcionObserveWidget = WidgetProperties.text(SWT.FocusOut).observe(txDescripcion);
		IObservableValue descripcionConocimientoObserveValue = PojoProperties.value("descripcion").observe(conocimiento);
		bindingContext.bindValue(observeTextTxDescripcionObserveWidget, descripcionConocimientoObserveValue, null, null);
		//
		return bindingContext;
	}
   
	protected void configureShell(Shell shell) {
	      super.configureShell(shell);
	      shell.setText("Nuevo lugar de conocimiento");
	 }

}
