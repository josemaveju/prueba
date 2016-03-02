package es.academia.dialogos;

import javax.mail.MessagingException;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Group;

import es.academia.modelo.Alumno;
import es.academia.utils.EmailSenderService;
import es.academia.utils.GestorErrores;
import es.academia.utils.IConstantes;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.osgi.service.prefs.Preferences;

public class SendMail extends Dialog implements IConstantes{
	private DataBindingContext m_bindingContext;
	private Text txNombre;
	private Text txDirCorreo;
	private Text txAsunto;
	private StyledText txMensaje;
	/**
	 * @wbp.nonvisual location=35,449
	 */
	private  Alumno alumno =null;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SendMail(Shell parentShell) {
		super(parentShell);
	}
	
	public void setAlumno(Alumno alumno){
		this.alumno = alumno;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		
		Label lblEnviandoCorreoPara = new Label(container, SWT.NONE);
		lblEnviandoCorreoPara.setBounds(10, 16, 98, 15);
		lblEnviandoCorreoPara.setText("Enviando correo a:");
		
		txNombre = new Text(container, SWT.BORDER);
		txNombre.setEditable(false);
		txNombre.setBounds(121, 10, 592, 21);
		
		Label lblDireccionDeCorreo = new Label(container, SWT.NONE);
		lblDireccionDeCorreo.setBounds(67, 45, 41, 15);
		lblDireccionDeCorreo.setAlignment(SWT.RIGHT);
		lblDireccionDeCorreo.setText("e-mail:");
		
		txDirCorreo = new Text(container, SWT.BORDER);
		txDirCorreo.setEditable(false);
		txDirCorreo.setBounds(121, 39, 592, 21);
		
		Label lblAsunto = new Label(container, SWT.NONE);
		lblAsunto.setBounds(67, 76, 41, 15);
		lblAsunto.setText("Asunto:");
		
		txAsunto = new Text(container, SWT.BORDER);
		txAsunto.setBounds(121, 73, 592, 21);
		
		txMensaje = new StyledText(container, SWT.BORDER);
        Preferences preferences = ConfigurationScope.INSTANCE.getNode("es.academiaRCP");
        Preferences sub1 = preferences.node("mailPreferences");
 
 		txMensaje.setText(sub1.get(FIRMA_PREF, ""));
		txMensaje.setBounds(10, 109, 703, 286);

		return container;
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
			
			EmailSenderService ems = new EmailSenderService();
			try {
				ems.sendEmail("josemaveju@gmail.com", txAsunto.getText(), txMensaje.getText());
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				GestorErrores.mensajeTexto("Error enviando Mensaje: " +e.getMessage(), NIVEL_ERROR);
				e.printStackTrace();
			}
			close();
		}
		if (buttonId==1)
			close();
		
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(729, 485);
	}
	
	
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTxNombreObserveWidget = WidgetProperties.text(SWT.Modify).observe(txNombre);
		IObservableValue nombreAlumnoObserveValue = BeanProperties.value("nombreCompleto").observe(alumno);
		bindingContext.bindValue(observeTextTxNombreObserveWidget, nombreAlumnoObserveValue, null, null);
		//
		IObservableValue observeTextTxDirCorreoObserveWidget = WidgetProperties.text(SWT.Modify).observe(txDirCorreo);
		IObservableValue eMailAlumnoObserveValue = BeanProperties.value("EMail").observe(alumno);
		bindingContext.bindValue(observeTextTxDirCorreoObserveWidget, eMailAlumnoObserveValue, null, null);
		//
		return bindingContext;
	}
}
