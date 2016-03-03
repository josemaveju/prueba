package es.academia.dialogos;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;

import es.academia.modelo.Alumno;
import es.academia.modelo.AlumnoHome;
import es.academia.modelo.Profesor;
import es.academia.modelo.ProfesorHome;
import es.academia.utils.ACALog;
import es.academia.utils.BuscarLista;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;

public class BuscarDlg extends TitleAreaDialog {
	private static final Logger log = ACALog.getLogger(BuscarDlg.class);
	
//	public static final String BUSCAR_ALUMO = "alumno";
//	public static final String BUSCAR_PROFESOR = "profesor";
	
	private DataBindingContext m_bindingContext;
	private Text txNombre;
	private Text txApellidos;
	private Text txCodigo;
	private Text txNIF;
	
	private String tipoBusqueda;
	private List encontrados;

	
	
	/**
	 * @wbp.nonvisual location=85,289
	 */
	private final Alumno alumno = new Alumno();
	private final Profesor profesor  = new Profesor();

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public BuscarDlg(Shell parentShell) {
		super(parentShell);
		setHelpAvailable(false);
	}
	
	public void setTipoBusqueda(String busqueda){
		this.tipoBusqueda = busqueda;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitleImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/find_alumno_72.png"));
		setMessage("Rellena los campos por los que quieres buscar y pulsa OK");

		if (tipoBusqueda.equalsIgnoreCase(BuscarLista.BUSCAR_ALUMO))
			setTitle("Buscar un alumno");
		else if (tipoBusqueda.equalsIgnoreCase(BuscarLista.BUSCAR_PROFESOR))
			setTitle("Buscar un profesor");
		
		
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Label label = new Label(container, SWT.NONE);
		label.setText("Nombre");
		label.setBounds(10, 56, 69, 15);
		
		txNombre = new Text(container, SWT.BORDER);
		txNombre.setBounds(10, 72, 151, 20);
		
		Label label_1 = new Label(container, SWT.NONE);
		label_1.setText("Apellidos");
		label_1.setBounds(187, 56, 69, 15);
		
		txApellidos = new Text(container, SWT.BORDER);
		txApellidos.setBounds(187, 72, 343, 20);
		
		Label label_2 = new Label(container, SWT.NONE);
		label_2.setText("C\u00F3digo");
		label_2.setBounds(10, 10, 39, 15);
		
		txCodigo = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		txCodigo.setEditable(true);
		txCodigo.setBounds(10, 26, 55, 20);
		
		Label label_3 = new Label(container, SWT.NONE);
		label_3.setText("Nif");
		label_3.setBounds(10, 105, 69, 15);
		
		txNIF = new Text(container, SWT.BORDER);
		txNIF.setBounds(10, 122, 105, 20);

		return area;
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
//		m_bindingContext = initDataBindings();
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(563, 316);
	}
	
	protected void buttonPressed(int buttonId)  {

		if (buttonId==0){
			
			buscarAlumnos();
			close();
		}
		if (buttonId==1)
			close();
		
	}
	
	private void buscarAlumnos() {

		setEncontrados((List)BuscarLista.buscarListaAvanzada(tipoBusqueda, txNombre.getText(), 
				        txApellidos.getText(), txNIF.getText(), txCodigo.getText()));

		/*		if (tipoBusqueda.equalsIgnoreCase(BUSCAR_ALUMO)){
			Alumno alumno = new Alumno();
			alumno.setNombre(txNombre.getText());
			alumno.setApellidos(txApellidos.getText());
			if (!txCodigo.getText().equals(""))
				alumno.setIdAlumno(Integer.valueOf(txCodigo.getText()).intValue());
			alumno.setNif(txNIF.getText());
			AlumnoHome ah = new AlumnoHome();
			List<Alumno> alumnos = ah.findByExample(alumno);
			setEncontrados((List)alumnos);
		}
		else if (tipoBusqueda.equalsIgnoreCase(BUSCAR_PROFESOR)){
			Profesor profesor = new Profesor();
			profesor.setNombre(txNombre.getText());
			profesor.setApellidos(txApellidos.getText());
			if (!txCodigo.getText().equals(""))
				profesor.setIdProfesor(Integer.valueOf(txCodigo.getText()).intValue());
			profesor.setNif(txNIF.getText());
			ProfesorHome ah = new ProfesorHome();
			List<Profesor> profesores = ah.findByExample(profesor);
			setEncontrados((List)profesores);
			
		}
*/			

/*		log.debug("Alumnos: " + alumnos);
		Iterator it = alumnos.iterator();
		while (it.hasNext()){
			Alumno mat = (Alumno) it.next();
			log.debug("Matricula: "+mat.getIdAlumno());
			log.debug("Datos: Curso: "+mat.getNombre() +" " +mat.getApellidos());
		}
*/		
	}

	public List getEncontrados() {
		return encontrados;
	}

	public void setEncontrados(List encontrados) {
		this.encontrados = encontrados;
	}
	

/*	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTxNombreObserveWidget = WidgetProperties.text(SWT.Modify).observe(txNombre);
		IObservableValue nombreAlumnoObserveValue = BeanProperties.value("nombre").observe(alumno);
		bindingContext.bindValue(observeTextTxNombreObserveWidget, nombreAlumnoObserveValue, null, null);
		//
		IObservableValue observeTextTxApellidosObserveWidget = WidgetProperties.text(SWT.Modify).observe(txApellidos);
		IObservableValue apellidosAlumnoObserveValue = BeanProperties.value("apellidos").observe(alumno);
		bindingContext.bindValue(observeTextTxApellidosObserveWidget, apellidosAlumnoObserveValue, null, null);
		//
		IObservableValue observeTextTxCodigoObserveWidget = WidgetProperties.text(SWT.Modify).observe(txCodigo);
		IObservableValue idAlumnoAlumnoObserveValue = BeanProperties.value("idAlumno").observe(alumno);
		bindingContext.bindValue(observeTextTxCodigoObserveWidget, idAlumnoAlumnoObserveValue, null, null);
		//
		IObservableValue observeTextTxNIFObserveWidget = WidgetProperties.text(SWT.Modify).observe(txNIF);
		IObservableValue nifAlumnoObserveValue = BeanProperties.value("nif").observe(alumno);
		bindingContext.bindValue(observeTextTxNIFObserveWidget, nifAlumnoObserveValue, null, null);
		//
		return bindingContext;
	}
*/
}
