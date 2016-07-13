package es.academia.dialogos;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import es.academia.modelo.Aula;
import es.academia.modelo.AulaHome;
import es.academia.modelo.Serierecibo;
import es.academia.modelo.SeriereciboHome;
import es.academia.utils.GestorErrores;
import es.academia.utils.IConstantes;
import es.academia.utils.Utilidades;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.swt.widgets.Group;
 
public class DetalleAula extends Dialog implements IConstantes{
	private DataBindingContext m_bindingContext;
	private Text txNumAula;
	private Text txDescAula;
	
	/**
	 * @wbp.nonvisual location=38,179
	 */
	private  Aula aula ;
	private  int claveAula;


	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public DetalleAula(Shell parentShell) {
		super(parentShell);
	}

	protected void configureShell(Shell newShell) {
      super.configureShell(newShell);
      newShell.setText("Mantenimiento de aulas");
	}	
	
	public void setClaveAula(int clave){
		this.claveAula = clave;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		
		Group grpAulas = new Group(container, SWT.NONE);
		grpAulas.setText("Aulas");
		grpAulas.setBounds(10, 13, 403, 117);
		
		Label lblNewLabel = new Label(grpAulas, SWT.NONE);
		lblNewLabel.setLocation(10, 35);
		lblNewLabel.setSize(61, 15);
		lblNewLabel.setText("Num. aula:");
		
		txNumAula = new Text(grpAulas, SWT.BORDER);
		txNumAula.setEnabled(false);
		txNumAula.setLocation(76, 31);
		txNumAula.setSize(76, 21);
		txNumAula.setEditable(false);
		
		Label lblDescirpcin = new Label(grpAulas, SWT.NONE);
		lblDescirpcin.setLocation(10, 73);
		lblDescirpcin.setSize(66, 15);
		lblDescirpcin.setText("Descripci\u00F3n:");
		
		txDescAula = new Text(grpAulas, SWT.BORDER);
		txDescAula.setLocation(77, 70);
		txDescAula.setSize(301, 21);
		Utilidades.addControlesTexto(txDescAula,false,8);
		grpAulas.setTabList(new Control[]{txNumAula, txDescAula});
		
		setBean();
		return container;
	}

	private void setBean() {
		// TODO Auto-generated method stub
		if (claveAula<0)
			aula= new Aula();
		else{
			AulaHome srh = new AulaHome();
			aula = srh.findById(claveAula);
		}
	}


	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		m_bindingContext = initDataBindings();
	}
	
	protected void buttonPressed(int buttonId)  {

		if (buttonId==0){
			String errores = comprobarDatos();
			if (errores.equalsIgnoreCase("")){
//				GestorErrores.mensajeTexto("Todo correcto", NIVEL_INFO);
				AulaHome srh = new AulaHome();
				srh.persist(aula);
				close();
			}
			else {
				GestorErrores.mensajeTexto("Existen errores:\n"+errores, NIVEL_ERROR);
			}
		}
		if (buttonId==1)
			close();
		
	}
	

	private String comprobarDatos() {
		// TODO Auto-generated method stub
		String errores="";
		
		if (txDescAula.getText().equals("")){
			errores = errores +"El campo Descripción está vacío";
		}
						
		return errores;
	}


	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(429, 210);
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTxNumSerieObserveWidget = WidgetProperties.text(SWT.Modify).observe(txNumAula);
		IObservableValue idSerieSeriereciboObserveValue = BeanProperties.value("idAula").observe(aula);
		bindingContext.bindValue(observeTextTxNumSerieObserveWidget, idSerieSeriereciboObserveValue, null, null);
		//
		IObservableValue observeTextTxDescSerieObserveWidget = WidgetProperties.text(SWT.Modify).observe(txDescAula);
		IObservableValue descSerieSeriereciboObserveValue = BeanProperties.value("descAula").observe(aula);
		bindingContext.bindValue(observeTextTxDescSerieObserveWidget, descSerieSeriereciboObserveValue, null, null);
		//
		return bindingContext;
	}
}
