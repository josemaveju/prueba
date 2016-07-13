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

import es.academia.modelo.MateriaHome;
import es.academia.modelo.Materia;
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

public class DetalleMateria extends Dialog implements IConstantes{
	private DataBindingContext m_bindingContext;
	private Text txIdMateria;
	private Text txDescMateria;
	
	/**
	 * @wbp.nonvisual location=38,179
	 */
	private  Materia materia ;
	private  int claveMateria;


	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public DetalleMateria(Shell parentShell) {
		super(parentShell);
	
	}

   protected void configureShell(Shell newShell) {
	      super.configureShell(newShell);
	      newShell.setText("Mantenimiento de materias");
	   }	
   
	public void setClaveMateria(int clave){
		this.claveMateria = clave;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		
		Group grpMaterias = new Group(container, SWT.NONE);
		grpMaterias.setText("Materias");
		grpMaterias.setBounds(10, 13, 403, 117);
		
		Label lblIdMateria = new Label(grpMaterias, SWT.NONE);
		lblIdMateria.setLocation(10, 35);
		lblIdMateria.setSize(61, 15);
		lblIdMateria.setText("Id. Materia");
		
		txIdMateria = new Text(grpMaterias, SWT.BORDER);
		txIdMateria.setEnabled(false);
		txIdMateria.setLocation(76, 31);
		txIdMateria.setSize(76, 21);
		txIdMateria.setEditable(false);
		
		Label lblDescirpcin = new Label(grpMaterias, SWT.NONE);
		lblDescirpcin.setLocation(10, 73);
		lblDescirpcin.setSize(66, 15);
		lblDescirpcin.setText("Descripci\u00F3n:");
		
		txDescMateria = new Text(grpMaterias, SWT.BORDER);
		txDescMateria.setLocation(77, 70);
		txDescMateria.setSize(301, 21);
		Utilidades.addControlesTexto(txDescMateria,false,8);
		grpMaterias.setTabList(new Control[]{txIdMateria, txDescMateria});
		
		setBean();
		return container;
	}

	private void setBean() {
		// TODO Auto-generated method stub
		if (claveMateria<0)
			materia= new Materia();
		else{
			MateriaHome srh = new MateriaHome();
			materia = srh.findById(claveMateria);
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
				MateriaHome srh = new MateriaHome();
				srh.persist(materia);
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
		
		if (txDescMateria.getText().equals("")){
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
		IObservableValue observeTextTxNumSerieObserveWidget = WidgetProperties.text(SWT.Modify).observe(txIdMateria);
		IObservableValue idSerieSeriereciboObserveValue = BeanProperties.value("idMateria").observe(materia);
		bindingContext.bindValue(observeTextTxNumSerieObserveWidget, idSerieSeriereciboObserveValue, null, null);
		//
		IObservableValue observeTextTxDescSerieObserveWidget = WidgetProperties.text(SWT.Modify).observe(txDescMateria);
		IObservableValue descSerieSeriereciboObserveValue = BeanProperties.value("descMateria").observe(materia);
		bindingContext.bindValue(observeTextTxDescSerieObserveWidget, descSerieSeriereciboObserveValue, null, null);
		//
		return bindingContext;
	}
}
