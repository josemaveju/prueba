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
import org.eclipse.swt.widgets.Group;

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
	 * @wbp.nonvisual location=85,299
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
		setTitle("B\u00FAsqueda avanzada");
		setTitleImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/find_alumno_72.png"));
		setMessage("Rellena los campos por los que quieres buscar y pulsa OK\r\nPuedes rellenar varios a la vez");

		if (tipoBusqueda.equalsIgnoreCase(BuscarLista.BUSCAR_ALUMO))
			setTitle("Buscar un alumno");
		else if (tipoBusqueda.equalsIgnoreCase(BuscarLista.BUSCAR_PROFESOR))
			setTitle("Buscar un profesor");
		
		
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Group grpDatos = new Group(container, SWT.NONE);
		grpDatos.setText("Datos");
		grpDatos.setBounds(10, 10, 491, 154);
		
		Label label = new Label(grpDatos, SWT.NONE);
		label.setLocation(12, 62);
		label.setSize(52, 15);
		label.setText("Nombre");
		
		txNombre = new Text(grpDatos, SWT.BORDER);
		txNombre.setLocation(82, 58);
		txNombre.setSize(151, 20);
		
		Label label_1 = new Label(grpDatos, SWT.NONE);
		label_1.setLocation(12, 94);
		label_1.setSize(51, 15);
		label_1.setText("Apellidos");
		
		txApellidos = new Text(grpDatos, SWT.BORDER);
		txApellidos.setLocation(82, 91);
		txApellidos.setSize(343, 20);
		
		Label label_3 = new Label(grpDatos, SWT.NONE);
		label_3.setLocation(12, 128);
		label_3.setSize(51, 15);
		label_3.setText("Nif");
		
		txNIF = new Text(grpDatos, SWT.BORDER);
		txNIF.setLocation(82, 124);
		txNIF.setSize(105, 20);
		
		Label label_2 = new Label(grpDatos, SWT.NONE);
		label_2.setLocation(12, 27);
		label_2.setSize(39, 15);
		label_2.setText("C\u00F3digo");
		
		txCodigo = new Text(grpDatos, SWT.BORDER | SWT.READ_ONLY);
		txCodigo.setLocation(82, 25);
		txCodigo.setSize(55, 20);
		txCodigo.setEditable(true);
		grpDatos.setTabList(new Control[]{txCodigo, txNombre, txApellidos, txNIF});

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
		return new Point(517, 318);
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
}
