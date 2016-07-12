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

import es.academia.modelo.Serierecibo;
import es.academia.modelo.SeriereciboHome;
import es.academia.utils.GestorErrores;
import es.academia.utils.IConstantes;
import es.academia.utils.Utilidades;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;

public class DetalleSerieRecibo extends Dialog implements IConstantes{
	private DataBindingContext m_bindingContext;
	private Text txNumSerie;
	private Text txDescSerie;
	private Text txSigRecibo;
	private Text txSigFactura;
	
	/**
	 * @wbp.nonvisual location=38,179
	 */
	private  Serierecibo serierecibo ;
	private  int claveSerie;


	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public DetalleSerieRecibo(Shell parentShell) {
		super(parentShell);
	}

	   protected void configureShell(Shell newShell) {
		      super.configureShell(newShell);
		      newShell.setText("Mantenimiento de series recibo");
		   }	
	   
	
	public void setClaveSerie(int clave){
		this.claveSerie = clave;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setBounds(16, 13, 61, 15);
		lblNewLabel.setText("Num. Serie:");
		
		txNumSerie = new Text(container, SWT.BORDER);
		txNumSerie.setEditable(false);
		txNumSerie.setBounds(93, 10, 76, 21);
		
		Label lblDescirpcin = new Label(container, SWT.NONE);
		lblDescirpcin.setText("Descripci\u00F3n:");
		lblDescirpcin.setBounds(11, 43, 66, 15);
		
		txDescSerie = new Text(container, SWT.BORDER);
		txDescSerie.setBounds(93, 40, 277, 21);
		Utilidades.addControlesTexto(txDescSerie,false,8);
		
		Label lblSigRecibo = new Label(container, SWT.NONE);
		lblSigRecibo.setText("Sig. Recibo:");
		lblSigRecibo.setBounds(16, 70, 61, 15);
		
		Label lblSigFactura = new Label(container, SWT.NONE);
		lblSigFactura.setText("Sig. Factura:");
		lblSigFactura.setBounds(14, 97, 63, 15);
		
		txSigRecibo = new Text(container, SWT.BORDER);
		txSigRecibo.setBounds(93, 67, 93, 21);
		Utilidades.addControlesTexto(txSigRecibo,false,10);

		txSigFactura = new Text(container, SWT.BORDER);
		txSigFactura.setBounds(93, 94, 93, 21);
		Utilidades.addControlesTexto(txSigFactura,false,10);
		setBean();
		return container;
	}

	private void setBean() {
		// TODO Auto-generated method stub
		if (claveSerie<0)
			serierecibo= new Serierecibo();
		else{
			SeriereciboHome srh = new SeriereciboHome();
			serierecibo = srh.findById(claveSerie);
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
				SeriereciboHome srh = new SeriereciboHome();
				srh.persist(serierecibo);
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
		
		if (txDescSerie.getText().equals("")){
			errores = errores +"El campo Descripción está vacío";
		}
		
		if (txSigRecibo.getText().equals("")){
			errores = errores+"\n" +"El campo Siguiente recibo está vacío";
		}

		if (txSigRecibo.getText().equals("0")){
			errores = errores+"\n" +"El campo Siguiente recibo debe ser mayor que 0";
		}
		if (txSigFactura.getText().equals("")){
			errores = errores+"\n" +"El campo Siguiente factura está vacío";
		}

		if (txSigFactura.getText().equals("0")){
			errores = errores+"\n" +"El campo Siguiente factura debe ser mayor que 0";
		}
	
		try {
			Integer sigRecibo = Integer.parseInt(txSigRecibo.getText());
			if (sigRecibo <0)
				errores = errores+"\n" +"El campo Siguiente recibo debe ser mayor que 0";
		}
		catch(NumberFormatException e){
			errores = errores +"\n"+"El campo siguiente recibo debe ser numérico";
		}
		try {
			Integer sigFactura = Integer.parseInt(txSigFactura.getText());
			if (sigFactura <0)
				errores = errores+"\n" +"El campo Siguiente factura debe ser mayor que 0";

		}
		catch(NumberFormatException e){
			errores = errores +"\n"+"El campo siguiente factura debe ser numérico";
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
		IObservableValue observeTextTxNumSerieObserveWidget = WidgetProperties.text(SWT.Modify).observe(txNumSerie);
		IObservableValue idSerieSeriereciboObserveValue = BeanProperties.value("idSerie").observe(serierecibo);
		bindingContext.bindValue(observeTextTxNumSerieObserveWidget, idSerieSeriereciboObserveValue, null, null);
		//
		IObservableValue observeTextTxDescSerieObserveWidget = WidgetProperties.text(SWT.Modify).observe(txDescSerie);
		IObservableValue descSerieSeriereciboObserveValue = BeanProperties.value("descSerie").observe(serierecibo);
		bindingContext.bindValue(observeTextTxDescSerieObserveWidget, descSerieSeriereciboObserveValue, null, null);
		//
		IObservableValue observeTextTxSigReciboObserveWidget = WidgetProperties.text(SWT.Modify).observe(txSigRecibo);
		IObservableValue sigReciboSeriereciboObserveValue = BeanProperties.value("sigRecibo").observe(serierecibo);
		bindingContext.bindValue(observeTextTxSigReciboObserveWidget, sigReciboSeriereciboObserveValue, null, null);
		//
		IObservableValue observeTextTxSigFacturaObserveWidget = WidgetProperties.text(SWT.Modify).observe(txSigFactura);
		IObservableValue sigFacturaSeriereciboObserveValue = BeanProperties.value("sigFactura").observe(serierecibo);
		bindingContext.bindValue(observeTextTxSigFacturaObserveWidget, sigFacturaSeriereciboObserveValue, null, null);
		//
		return bindingContext;
	}
	
}
