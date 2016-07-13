package es.academia.dialogos;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.Binding;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.CLabel;

import es.academia.utils.ACALog;
import es.academia.utils.GestorErrores;
import es.academia.utils.IConstantes;
import es.academia.utils.Utilidades;
import es.academia.widgets.CLabelAca;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DateTime;

import es.academia.modelo.Alumno;
import es.academia.modelo.AlumnoHome;
import es.academia.modelo.ConocimientoHome;
import es.academia.modelo.Matricula;
import es.academia.modelo.Recibo;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.runtime.IStatus;

import es.academia.validators.FormatFechaConverter;
import es.academia.validators.MovilValidator;
import es.academia.validators.NoVacioValidator;
import es.academia.validators.TelFijoValidator;
import es.academia.validators.EMailValidator;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ObservableSetContentProvider;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Canvas;

import es.academia.validators.BooleanToStringConverter;
import es.academia.validators.StringToBooleanConverter;



public class DetalleAlumno extends Dialog implements IConstantes{
	private static final Logger log = ACALog.getLogger(DetalleAlumno.class);
	
	private Binding eMailBinding;
	private Binding telMovilBinding;
	private Binding telFijoBinding;
    private Composite central;
    private Composite datosGenerales;
    private Composite datosCursos;
    private Composite datosRecibos;
    private StackLayout centralLayout;
    
	private DataBindingContext m_bindingContext;
	private Text txCodigo;
	private Text txNombre;
	private Text txApellidos;
	private Text txNIF;
	private Text txTelFijo;
	private Text txTelMovil;
	/**
	 * @wbp.nonvisual location=121,551
	 */
	private Alumno alumno = new Alumno();
	private AlumnoHome ah = new AlumnoHome();
	private int claveAlumno = 0; 
	private Label lblNombre;
	private DateTime txFechaAlta;
	private Text txDireccion;
	private Text txLocalidad;
	private Text txProvincia;
	private Text txCPostal;
	private Text txemail;
	private Text txEdad;
	private Text txNotas;
	private Text txNombreTutor;
	private Text txApellidosTutor;
	private Text txNifTutor;
	private Text txTelefonoTutor;
	private CCombo txConocioCombo;
	private ComboViewer txConocio;
	private DateTime txFechaNacimiento;
	private Label lblNif_1;
    private ControlDecoration cdTelMovil;
    private Table tbMatriculas;
    private TableViewer tablaMatriculas;
    private TableViewer tablaRecibos;
    private Text txTelMovil2;
    private ViewerFilter filtroActivas;
    private Table tbRecibos;
	private ViewerFilter filtroRecActivo;
	private String opcionPanel;
	private String tipoOperacion;

	private Binding nombreBinding;

	private Binding apellidosBinding;

	private Binding direccionBinding;

	private Binding poblacionBinding;

	private Binding provinciaBinding;

	private Binding conocimientoBinding;
	private Button btnNoActivo;
	

	public void setTipoOperacion(String tipoOperacion){
		this.tipoOperacion = tipoOperacion;
	}
	
	public DetalleAlumno(Shell parentShell) {
		super(parentShell);
//		setHelpAvailable(false);
		setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}
	
	public void setClaveAlumno(int claveAlumno){
		this.claveAlumno = claveAlumno;
	}
	
	public void setAlumno(Alumno alumno){
		this.alumno = alumno;
	}

	public int getClaveAlumno(){
		return this.claveAlumno;
	}
	
	public void seleccionarPanel(String opcion){
		this.opcionPanel = opcion;
	}
	
	private void mostrarPanel(String panel){
		if (panel.equalsIgnoreCase(PANELGENERAL)){
			centralLayout.topControl = datosGenerales;
		}
		else if (panel.equalsIgnoreCase(PANELCURSOS)){
			centralLayout.topControl = datosCursos;
			
		}else if (panel.equalsIgnoreCase(PANELRECIBOS)){
			centralLayout.topControl = datosRecibos;
		}
		central.layout();
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
//		setTitleImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/graduated_72.png"));
		Composite area = (Composite) super.createDialogArea(parent);
		area.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		area.setLayout(null);
		Composite container = new Composite(area, SWT.BORDER);
		container.setBounds(0, 2, 801, 489);
		container.setLayout(null);
		
		Composite opciones = new Composite(container, SWT.BORDER);
		opciones.setBackground(SWTResourceManager.getColor(248, 248, 255));
		opciones.setBounds(0, 90, 169, 395);
		opciones.setLayout(null);
		
		CLabelAca lbGenerales = new CLabelAca(opciones, SWT.NONE);
		lbGenerales.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				mostrarPanel(PANELGENERAL);
			}
		});
		lbGenerales.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/Add_alumno_16.png"));
		lbGenerales.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lbGenerales.setBounds(10, 47, 115, 21);
		lbGenerales.setText("Datos generales");
		
		CLabelAca lbMatriculas = new CLabelAca(opciones, SWT.NONE);
		lbMatriculas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				mostrarPanel(PANELCURSOS);

			}
		});
		lbMatriculas.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/libros_16.png"));
		lbMatriculas.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lbMatriculas.setBounds(10, 74, 145, 21);
		lbMatriculas.setText("Cursos matriculados");
		
		CLabelAca lbRecibos = new CLabelAca(opciones, SWT.NONE);
		lbRecibos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				mostrarPanel(PANELRECIBOS);
			}
		});
		lbRecibos.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/Dinero_16.png"));
		lbRecibos.setText("Recibos generados");
		lbRecibos.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lbRecibos.setBounds(10, 101, 145, 21);
		
		CLabel lblOpciones = new CLabel(opciones, SWT.NONE);
		lblOpciones.setAlignment(SWT.CENTER);
		lblOpciones.setFont(SWTResourceManager.getFont("Arial Black", 11, SWT.BOLD));
		lblOpciones.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblOpciones.setBackground(SWTResourceManager.getColor(70, 130, 180));
		lblOpciones.setBounds(0, 0, 165, 28);
		lblOpciones.setText("OPCIONES");
		
		CLabelAca lblcEnviarEmail = new CLabelAca(opciones, SWT.NONE);
		lblcEnviarEmail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				SendMail dlg = new SendMail(getShell());
				dlg.setAlumno(alumno);
				dlg.open();

			}
		});
		lblcEnviarEmail.setText("Enviar e-mail");
		lblcEnviarEmail.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/mail.gif"));
		lblcEnviarEmail.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblcEnviarEmail.setBounds(10, 128, 145, 21);
		
		Composite datosFijos = new Composite(container, SWT.BORDER);
		datosFijos.setBackground(SWTResourceManager.getColor(248, 248, 255));
		datosFijos.setBounds(0, 0, 792, 84);
		
		Label lblCdigo = new Label(datosFijos, SWT.NONE);
		lblCdigo.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblCdigo.setBounds(126, 0, 39, 15);
		lblCdigo.setText("C\u00F3digo");
		
		txCodigo = new Text(datosFijos, SWT.BORDER | SWT.READ_ONLY);
		txCodigo.setBounds(126, 16, 55, 20);
		
		lblNombre = new Label(datosFijos, SWT.NONE);
		lblNombre.setText("Nombre");
		lblNombre.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblNombre.setBounds(202, 0, 69, 15);
		
		txNombre = new Text(datosFijos, SWT.BORDER);
		txNombre.setBounds(202, 16, 151, 20);
		Utilidades.addControlesTexto(txNombre,false,40);
		
		ControlDecoration cdNombre = new ControlDecoration(txNombre, SWT.LEFT | SWT.TOP);
		cdNombre.setShowOnlyOnFocus(true);
		cdNombre.setDescriptionText("Nombre del alumno");
		
		txApellidos = new Text(datosFijos, SWT.BORDER);
		txApellidos.setBounds(379, 16, 258, 20);
		Utilidades.addControlesTexto(txApellidos,false,70);
		
		ControlDecoration cdApellidos = new ControlDecoration(txApellidos, SWT.LEFT | SWT.TOP);
		cdApellidos.setShowOnlyOnFocus(true);
		cdApellidos.setDescriptionText("Apellidos del alumno");
		
		Label lblApellidos = new Label(datosFijos, SWT.NONE);
		lblApellidos.setText("Apellidos");
		lblApellidos.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblApellidos.setBounds(379, 0, 69, 15);
		
		Label lblNif = new Label(datosFijos, SWT.NONE);
		lblNif.setText("Nif");
		lblNif.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblNif.setBounds(126, 40, 69, 15);
		
		txNIF = new Text(datosFijos, SWT.BORDER);
		txNIF.setBounds(126, 57, 105, 20);
		Utilidades.addControlesTexto(txNIF,false,12);
				
		Label telFijo = new Label(datosFijos, SWT.NONE);
		telFijo.setText("Telf. fijo");
		telFijo.setBackground(SWTResourceManager.getColor(248, 248, 255));
		telFijo.setBounds(257, 40, 69, 15);
		
		txTelFijo = new Text(datosFijos, SWT.BORDER);
		txTelFijo.setBounds(257, 57, 96, 20);
		Utilidades.addControlesTexto(txTelFijo,false,9);
		
		txTelMovil = new Text(datosFijos, SWT.BORDER);
		txTelMovil.setBounds(379, 57, 96, 20);
		Utilidades.addControlesTexto(txTelMovil,false,9);
		
		Label lblTelfMvil = new Label(datosFijos, SWT.NONE);
		lblTelfMvil.setText("Telf. m\u00F3vil");
		lblTelfMvil.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblTelfMvil.setBounds(379, 40, 69, 15);
		
		Label lblFechaAlta = new Label(datosFijos, SWT.NONE);
		lblFechaAlta.setText("Fecha alta");
		lblFechaAlta.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblFechaAlta.setBounds(635, 40, 69, 15);
		
		txFechaAlta = new DateTime(datosFijos, SWT.BORDER | SWT.DROP_DOWN);
		txFechaAlta.setBounds(635, 57, 112, 21);
		 
		Label lblTelfMovil2 = new Label(datosFijos, SWT.NONE);
		lblTelfMovil2.setText("Telf. m\u00F3vil 2");
		lblTelfMovil2.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblTelfMovil2.setBounds(505, 40, 69, 15);
		
		txTelMovil2 = new Text(datosFijos, SWT.BORDER);
		txTelMovil2.setBounds(505, 57, 96, 20);
		
		CLabel lblNewLabel = new CLabel(datosFijos, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblNewLabel.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/alumno_72.png"));
		lblNewLabel.setBounds(10, 5, 78, 70);
		lblNewLabel.setText("");
		
		btnNoActivo = new Button(datosFijos, SWT.CHECK);
		btnNoActivo.setBounds(734, 18, 13, 16);
		
		Label lblNewLabel_1 = new Label(datosFijos, SWT.NONE);
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblNewLabel_1.setBounds(667, 19, 61, 15);
		lblNewLabel_1.setText("No activo");
		
		central = new Composite(container, SWT.NONE);
		central.setLocation(175, 90);
		central.setSize(617, 395);
		centralLayout=new StackLayout();
		central.setLayout(centralLayout);
		
		datosGenerales = new Composite(central, SWT.BORDER);
		
		CLabel lblDatosGnerales = new CLabel(datosGenerales, SWT.NONE);
		lblDatosGnerales.setBottomMargin(0);
		lblDatosGnerales.setRightMargin(0);
		lblDatosGnerales.setLeftMargin(7);
		lblDatosGnerales.setText("Datos generales");
		lblDatosGnerales.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblDatosGnerales.setFont(SWTResourceManager.getFont("Cambria", 13, SWT.BOLD | SWT.ITALIC));
		lblDatosGnerales.setBackground(SWTResourceManager.getColor(70, 130, 180));
		lblDatosGnerales.setBounds(0, 0, 613, 28);
		
		Group grpDomicilio = new Group(datosGenerales, SWT.NONE);
		grpDomicilio.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD | SWT.ITALIC));
		grpDomicilio.setText("Domicilio");
		grpDomicilio.setBounds(10, 34, 593, 99);
		
		Label lblDireccin = new Label(grpDomicilio, SWT.NONE);
		lblDireccin.setText("Direcci\u00F3n");
		lblDireccin.setBounds(10, 18, 69, 15);
		
		txDireccion = new Text(grpDomicilio, SWT.BORDER);
		txDireccion.setBounds(10, 33, 573, 20);
		Utilidades.addControlesTexto(txDireccion,false,75);
		
		Label lblLocalidad = new Label(grpDomicilio, SWT.NONE);
		lblLocalidad.setText("Localidad");
		lblLocalidad.setBounds(10, 55, 52, 15);
		
		txLocalidad = new Text(grpDomicilio, SWT.BORDER);
		txLocalidad.setBounds(10, 70, 241, 20);
		Utilidades.addControlesTexto(txLocalidad,false,50);
		
		Label lblProvincia = new Label(grpDomicilio, SWT.NONE);
		lblProvincia.setText("Provincia");
		lblProvincia.setBounds(257, 55, 69, 15);
		
		txProvincia = new Text(grpDomicilio, SWT.BORDER);
		txProvincia.setBounds(257, 70, 218, 20);
		Utilidades.addControlesTexto(txProvincia,false,30);
		
		Label lblCPostal = new Label(grpDomicilio, SWT.NONE);
		lblCPostal.setText("C. Postal");
		lblCPostal.setBounds(481, 55, 75, 15);
		
		txCPostal = new Text(grpDomicilio, SWT.BORDER);
		txCPostal.setBounds(481, 70, 102, 20);
		Utilidades.addControlesTexto(txCPostal,false,5);
		
		Group grpOtrosDatos = new Group(datosGenerales, SWT.NONE);
		grpOtrosDatos.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD | SWT.ITALIC));
		grpOtrosDatos.setText("Otros Datos");
		grpOtrosDatos.setBounds(10, 139, 593, 166);
		
		Label lblEmail = new Label(grpOtrosDatos, SWT.NONE);
		lblEmail.setText("e-Mail");
		lblEmail.setBounds(10, 19, 69, 15);
		
		txemail = new Text(grpOtrosDatos, SWT.BORDER);
		txemail.setBounds(10, 34, 573, 20);
		Utilidades.addControlesTexto(txemail,false,100);
		
		txFechaNacimiento = new DateTime(grpOtrosDatos, SWT.BORDER | SWT.DROP_DOWN);
		txFechaNacimiento.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				txEdad.setText(Utilidades.calcularEdad(txFechaNacimiento.getDay()+"/"+(txFechaNacimiento.getMonth()+1)+"/"+txFechaNacimiento.getYear()));
			}
		});
		txFechaNacimiento.setBounds(10, 73, 112, 20);
		
		Label lbFechaNacimiento = new Label(grpOtrosDatos, SWT.NONE);
		lbFechaNacimiento.setText("F. Nacimiento");
		lbFechaNacimiento.setBounds(10, 57, 87, 15);
		
		Label lblEdad = new Label(grpOtrosDatos, SWT.NONE);
		lblEdad.setText("Edad");
		lblEdad.setBounds(153, 57, 39, 15);
		
		txEdad = new Text(grpOtrosDatos, SWT.BORDER | SWT.READ_ONLY);
		txEdad.setBounds(153, 73, 55, 20);
		
		Label lblcmoNosConoci = new Label(grpOtrosDatos, SWT.NONE);
		lblcmoNosConoci.setText("\u00BFC\u00F3mo nos conoci\u00F3?");
		lblcmoNosConoci.setBounds(242, 57, 143, 15);
		
		txConocioCombo = new CCombo(grpOtrosDatos, SWT.BORDER|SWT.READ_ONLY);
		txConocioCombo.setBounds(242, 73, 310, 20);
		
		txConocio = new ComboViewer(txConocioCombo);
		try {
			txConocio.setContentProvider(new ArrayContentProvider().getInstance());
			txConocio.setLabelProvider(new LabelProvider() {
		        @Override
		        public String getText(Object element) {
		            if (element instanceof Integer) {
		            	Integer current = (Integer) element;
		            	ConocimientoHome ch = new ConocimientoHome();
		                return ch.findById(current).getDescripcion();
		            }
		            return super.getText(element);
		        }
		    });
			
			ConocimientoHome ch = new ConocimientoHome();
			
			txConocio.setInput(ch.listarTodosInteger());
		}catch (RuntimeException re){
			GestorErrores.mensajeTexto("Error cargando lista de conocimiento.\n\n" + 
		                    re.getMessage()+"\n" + re.getCause(), NIVEL_ERROR);
		}
				
		txNotas = new Text(grpOtrosDatos, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		txNotas.setBounds(55, 100, 528, 57);
		Utilidades.addControlesTexto(txNotas,false,2048);
		
		Label lblNotas = new Label(grpOtrosDatos, SWT.NONE);
		lblNotas.setText("Notas");
		lblNotas.setBounds(10, 100, 39, 15);
		
		Button btnMasConocio = new Button(grpOtrosDatos, SWT.NONE);
		btnMasConocio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AddConocimiento ac = new AddConocimiento(getShell());
				ac.open();
			}
		});
		btnMasConocio.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/mas.gif"));
		btnMasConocio.setBounds(561, 73, 21, 21);
		
		Group grpDatosDelTutorr = new Group(datosGenerales, SWT.NONE);
		grpDatosDelTutorr.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD | SWT.ITALIC));
		grpDatosDelTutorr.setText("Datos del tutor");
		grpDatosDelTutorr.setBounds(10, 311, 593, 69);
		
		Label lblNombre_1 = new Label(grpDatosDelTutorr, SWT.NONE);
		lblNombre_1.setText("Nombre");
		lblNombre_1.setBounds(10, 18, 69, 15);
		
		txNombreTutor = new Text(grpDatosDelTutorr, SWT.BORDER);
		txNombreTutor.setBounds(10, 36, 140, 20);
		Utilidades.addControlesTexto(txNombreTutor,false,50);
		
		Label lblPellidos = new Label(grpDatosDelTutorr, SWT.NONE);
		lblPellidos.setText("Apellidos");
		lblPellidos.setBounds(156, 18, 69, 15);
		
		txApellidosTutor = new Text(grpDatosDelTutorr, SWT.BORDER);
		txApellidosTutor.setBounds(156, 36, 235, 20);
		Utilidades.addControlesTexto(txApellidosTutor,false,75);
		
		lblNif_1 = new Label(grpDatosDelTutorr, SWT.NONE);
		lblNif_1.setText("NIF");
		lblNif_1.setBounds(397, 18, 75, 15);
		
		txNifTutor = new Text(grpDatosDelTutorr, SWT.BORDER);
		txNifTutor.setBounds(397, 36, 90, 20);
		Utilidades.addControlesTexto(txNifTutor,false,12);
		
		txTelefonoTutor = new Text(grpDatosDelTutorr, SWT.BORDER);
		txTelefonoTutor.setBounds(493, 36, 90, 20);
		Utilidades.addControlesTexto(txTelefonoTutor,false,9);
		
		Label lblTelfono = new Label(grpDatosDelTutorr, SWT.NONE);
		lblTelfono.setText("Tel\u00E9fono");
		lblTelfono.setBounds(493, 18, 75, 15);
		
		datosCursos = new Composite(central, SWT.BORDER);
		
		CLabel lblMatrculasDelAlumno = new CLabel(datosCursos, SWT.NONE);
		lblMatrculasDelAlumno.setText("Matr\u00EDculas del alumno");
		lblMatrculasDelAlumno.setRightMargin(0);
		lblMatrculasDelAlumno.setLeftMargin(7);
		lblMatrculasDelAlumno.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblMatrculasDelAlumno.setFont(SWTResourceManager.getFont("Cambria", 13, SWT.BOLD | SWT.ITALIC));
		lblMatrculasDelAlumno.setBottomMargin(0);
		lblMatrculasDelAlumno.setBackground(SWTResourceManager.getColor(70, 130, 180));
		lblMatrculasDelAlumno.setBounds(0, 0, 613, 28);
		
		tablaMatriculas = new TableViewer(datosCursos, SWT.BORDER | SWT.FULL_SELECTION);
		tbMatriculas = tablaMatriculas.getTable();
		tbMatriculas.setHeaderVisible(true);
		tbMatriculas.setBounds(10, 78, 593, 309);
		
		TableViewerColumn tvMatCodigo = new TableViewerColumn(tablaMatriculas, SWT.NONE);
		TableColumn tblclmnCdigo = tvMatCodigo.getColumn();
		tblclmnCdigo.setAlignment(SWT.RIGHT);
		tblclmnCdigo.setWidth(50);
		tblclmnCdigo.setText("C\u00F3digo");
		
		TableViewerColumn tvMatDescripcion = new TableViewerColumn(tablaMatriculas, SWT.NONE);
		TableColumn tblclmnDescripcin = tvMatDescripcion.getColumn();
		tblclmnDescripcin.setWidth(186);
		tblclmnDescripcin.setText("Descripci\u00F3n");
		
		TableViewerColumn tvMatAgno = new TableViewerColumn(tablaMatriculas, SWT.NONE);
		TableColumn tblclmnAgno = tvMatAgno.getColumn();
		tblclmnAgno.setWidth(79);
		tblclmnAgno.setText("A\u00F1o");
		
		TableViewerColumn tvMatFInicio = new TableViewerColumn(tablaMatriculas, SWT.NONE);
		TableColumn tblclmnFInicio = tvMatFInicio.getColumn();
		tblclmnFInicio.setWidth(91);
		tblclmnFInicio.setText("F. Inicio");
		
		TableViewerColumn tvMatFFin = new TableViewerColumn(tablaMatriculas, SWT.NONE);
		TableColumn tblclmnFFin = tvMatFFin.getColumn();
		tblclmnFFin.setWidth(83);
		tblclmnFFin.setText("F. Fin");
		
		TableViewerColumn tvMatImporte = new TableViewerColumn(tablaMatriculas, SWT.NONE);
		TableColumn tblclmnImporteRecibo = tvMatImporte.getColumn();
		tblclmnImporteRecibo.setAlignment(SWT.RIGHT);
		tblclmnImporteRecibo.setWidth(91);
		tblclmnImporteRecibo.setText("Importe recibo");

		
		//Definición del filtro
	    filtroActivas = new ViewerFilter(){
		public boolean select (Viewer visor, Object objetoPadre, Object objeto){
			Matricula mat = (Matricula)objeto;
			Date fechaFin = mat.getFHasta();
			Date hoy = new Date();
			
			if ( fechaFin.before(hoy) )
				return false;
			else
				return true;
		}
	};
		
		
		CoolBar coolBar = new CoolBar(datosCursos, SWT.NONE);
		coolBar.setLocked(true);
		coolBar.setBounds(8, 34, 593, 38);
		
		CoolItem coolItem = new CoolItem(coolBar, SWT.NONE);
		coolItem.setSize(new Point(120, 50));
		
		ToolBar toolBar = new ToolBar(coolBar, SWT.FLAT);
		coolItem.setControl(toolBar);
		
		ToolItem tltmSloActivos = new ToolItem(toolBar, SWT.RADIO);
		tltmSloActivos.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tablaMatriculas.addFilter(filtroActivas);
			}
		});
		tltmSloActivos.setText("Activos");
		tltmSloActivos.setToolTipText("Ver s\u00F3lo los activos");
		tltmSloActivos.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/select_row_16.png"));
		
		ToolItem tltmRadioItem = new ToolItem(toolBar, SWT.RADIO);
		tltmRadioItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tablaMatriculas.resetFilters();
			}
		});
		tltmRadioItem.setText("todos");
		tltmRadioItem.setToolTipText("Ver todos las matr\u00EDculas");
		tltmRadioItem.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/select_all_16.png"));
		
		CoolItem coolItem_1 = new CoolItem(coolBar, SWT.NONE);
		coolItem_1.setSize(new Point(700, 50));
		
		ToolBar toolBar_1 = new ToolBar(coolBar, SWT.FLAT);
		coolItem_1.setControl(toolBar_1);
		
		ToolItem tltmVerDetalle = new ToolItem(toolBar_1, SWT.NONE);
		tltmVerDetalle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		tltmVerDetalle.setToolTipText("Ver detalle de la matricula seleccionada");
		tltmVerDetalle.setText("Ver detalle");
		tltmVerDetalle.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/factura_16.png"));
		
		ToolItem tltmNuevoCurso = new ToolItem(toolBar_1, SWT.NONE);
		tltmNuevoCurso.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				abrirNuevoCurso();
			}
		});
		tltmNuevoCurso.setToolTipText("Matricular al alumno en un curso");
		tltmNuevoCurso.setText("Nuevo curso");
		tltmNuevoCurso.setSelection(true);
		tltmNuevoCurso.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/mas.gif"));
		
		datosRecibos = new Composite(central, SWT.BORDER);
		
		CLabel lblRecibosDelAlumno = new CLabel(datosRecibos, SWT.NONE);
		lblRecibosDelAlumno.setText("Recibos del alumno");
		lblRecibosDelAlumno.setRightMargin(0);
		lblRecibosDelAlumno.setLeftMargin(7);
		lblRecibosDelAlumno.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblRecibosDelAlumno.setFont(SWTResourceManager.getFont("Cambria", 13, SWT.BOLD | SWT.ITALIC));
		lblRecibosDelAlumno.setBottomMargin(0);
		lblRecibosDelAlumno.setBackground(SWTResourceManager.getColor(70, 130, 180));
		lblRecibosDelAlumno.setBounds(0, 0, 613, 28);
		
		tablaRecibos = new TableViewer(datosRecibos, SWT.BORDER | SWT.FULL_SELECTION);
		tbRecibos = tablaRecibos.getTable();
		tbRecibos.setHeaderVisible(true);
		tbRecibos.setBounds(10, 78, 593, 309);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tablaRecibos, SWT.NONE);
		TableColumn tblclmnRecibo = tableViewerColumn.getColumn();
		tblclmnRecibo.setWidth(57);
		tblclmnRecibo.setText("Recibo");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tablaRecibos, SWT.NONE);
		TableColumn tblclmnSerie = tableViewerColumn_1.getColumn();
		tblclmnSerie.setWidth(65);
		tblclmnSerie.setText("Serie");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tablaRecibos, SWT.NONE);
		TableColumn tblclmnCurso = tableViewerColumn_2.getColumn();
		tblclmnCurso.setWidth(146);
		tblclmnCurso.setText("Curso");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tablaRecibos, SWT.NONE);
		TableColumn tblclmnFDesde = tableViewerColumn_4.getColumn();
		tblclmnFDesde.setAlignment(SWT.RIGHT);
		tblclmnFDesde.setWidth(76);
		tblclmnFDesde.setText("F. Desde");
		
		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(tablaRecibos, SWT.NONE);
		TableColumn tblclmnFHasta = tableViewerColumn_5.getColumn();
		tblclmnFHasta.setAlignment(SWT.RIGHT);
		tblclmnFHasta.setWidth(78);
		tblclmnFHasta.setText("F. Hasta");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tablaRecibos, SWT.NONE);
		TableColumn tblclmnImporte = tableViewerColumn_3.getColumn();
		tblclmnImporte.setAlignment(SWT.RIGHT);
		tblclmnImporte.setWidth(82);
		tblclmnImporte.setText("Importe");
		
		
		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(tablaRecibos, SWT.NONE);
		TableColumn tblclmnPagado = tableViewerColumn_6.getColumn();
		tblclmnPagado.setWidth(80);
		tblclmnPagado.setText("Pagado");
		
		//Definición del filtro
	    filtroRecActivo = new ViewerFilter(){
		public boolean select (Viewer visor, Object objetoPadre, Object objeto){
			Recibo rec = (Recibo)objeto;
			String pagado = rec.getPagado();
				
			if ( pagado.equalsIgnoreCase("N") )
				return true;
			else
				return false;
		}
	};
		
		
		CoolBar coolBar_1 = new CoolBar(datosRecibos, SWT.FLAT);
		coolBar_1.setBounds(9, 34, 590, 54);
		
		CoolItem coolItem_2 = new CoolItem(coolBar_1, SWT.NONE);
		coolItem_2.setSize(new Point(150, 45));
		
		ToolBar toolBar_2 = new ToolBar(coolBar_1, SWT.FLAT);
		coolItem_2.setControl(toolBar_2);
		
		ToolItem tltmSinPagar = new ToolItem(toolBar_2, SWT.RADIO);
		tltmSinPagar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tablaRecibos.addFilter(filtroRecActivo);
			}
		});
		tltmSinPagar.setToolTipText("Mostrar s\u00F3lo los no pagados");
		tltmSinPagar.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/money_delete_32.png"));
		
		ToolItem tltmTodos = new ToolItem(toolBar_2, SWT.RADIO);
		tltmTodos.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tablaRecibos.resetFilters();
			}
		});
		tltmTodos.setToolTipText("Mostrar todos");
		tltmTodos.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/money_add_32.png"));
		
		CoolItem coolItem_3 = new CoolItem(coolBar_1, SWT.NONE);
		coolItem_3.setSize(new Point(400, 45));
		
		ToolBar toolBar_3 = new ToolBar(coolBar_1, SWT.FLAT | SWT.RIGHT);
		coolItem_3.setControl(toolBar_3);
		
		ToolItem tltmDetalle = new ToolItem(toolBar_3, SWT.NONE);
		tltmDetalle.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/dinero_grafico_32.png"));
		tltmDetalle.setToolTipText("Ver detalle de recibo");
		
		ToolItem tltmNewItem = new ToolItem(toolBar_3, SWT.SEPARATOR);
		tltmNewItem.setText("New Item");
		
		ToolItem tltmCobrar = new ToolItem(toolBar_3, SWT.NONE);
		tltmCobrar.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/1407937023_cashbox.png"));
		tltmCobrar.setToolTipText("Marcar como cobrado");
		
		ToolItem tltmNoCobrado = new ToolItem(toolBar_3, SWT.NONE);
		tltmNoCobrado.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/cashbox_remove.png"));
		tltmNoCobrado.setToolTipText("Marcar como no cobrado");
//		this.setTitle("Gestión de Alumnos");
//		this.setMessage("Detalle de un alumno");
/*		AlumnoHome ah = new AlumnoHome();
		alumno = ah.findById(1);
*/		
		if (claveAlumno != 0)
			setBean (this.claveAlumno);
		
	//	centralLayout.topControl = datosGenerales;
	//	central.layout();
		if (opcionPanel == null){
			opcionPanel = PANELGENERAL;
		}
		mostrarPanel (opcionPanel);
		
		return area;
	}
	
	protected void abrirNuevoCurso() {
		// TODO Auto-generated method stub
		NuevaMatriculaWizard nmw = new NuevaMatriculaWizard();
		nmw.setAlumno(alumno);
			
	    // Instantiates the wizard container with the wizard and opens it
	    WizardDialog dialog = new WizardDialog(getShell(), nmw);
	    dialog.create();
	    dialog.open();
		
	}

	private void setBean(int claveAlumno){
		log.debug("Entrando en setBean");
		//AlumnoHome ah = new AlumnoHome();
		alumno = ah.findById(claveAlumno);
		log.debug("Alumno instanciado");

		// Comprobamos si está cargada la lista de matriculas:
			
/*		Set matriculas = (Set)alumno.getMatriculas();
		log.debug("Matriculas: " + matriculas);
		Iterator it = matriculas.iterator();
		while (it.hasNext()){
			Matricula mat = (Matricula) it.next();
			log.debug("Matricula: "+mat.getIdMatricula());
			log.debug("Datos: Curso: "+mat.getCurso().getIdCurso()+" Alumno: "+mat.getAlumno().getIdAlumno());
		}
*/		
		// Enlazar con la lista de matriculas del alumno	
			
		ObservableSetContentProvider contentProviderM = new ObservableSetContentProvider();
		tablaMatriculas.setContentProvider(contentProviderM);
		tablaMatriculas.setLabelProvider( new MatriculaTableLabelProvider(
		     Properties.observeEach( contentProviderM.getKnownElements(), 
			    	BeanProperties.values(new String[]  {
			    			 "idMatricula", "nombreCurso","agnoCurso","FDesde","FHasta",
	                          "impMes"} ) )
		));
		tablaMatriculas.setInput(BeansObservables.observeSet(alumno,"matriculas", Matricula.class));

		
/*		ViewerSupport.bind(tablaMatriculas, 
                new WritableList(alumno.getMatriculas(), Matricula.class), 
                BeanProperties.values(Matricula.class, new String[] {
		                          "idMatricula", "nombreCurso","agnoCurso","FDesde","FHasta",
		                          "impMes"}));	
*/
		// Enlazar con la lista de recibos del alumno	
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		tablaRecibos.setContentProvider(contentProvider);
		tablaRecibos.setLabelProvider( new ReciboTableLabelProvider(
		     Properties.observeEach( contentProvider.getKnownElements(), 
			    	BeanProperties.values(new String[]  {
                    "numRecibo", "descSerieRecibo","nombreCurso","FDesde","FHasta",
                    "impTotal","pagado"} ) )
		));
		tablaRecibos.setInput(BeansObservables.observeList(alumno,PANELRECIBOS, Recibo.class));
		
/*		ViewerSupport.bind(tablaRecibos, 
                new WritableList(ah.getRecibos(alumno), Recibo.class), 
                BeanProperties.values(Recibo.class, new String[] {
		                          "numRecibo", "descSerieRecibo","nombreCurso","FDesde","FHasta",
		                          "impTotal","pagado"}));	
*/

	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, "Aceptar",true);
	
		createButton(parent, IDialogConstants.CANCEL_ID,
				"Cancelar", false);
		m_bindingContext = initDataBindings();
		añadirDecorations();
	}

	
	protected void buttonPressed(int buttonId)  {

		if (buttonId==0){
			if (validadoresCorrectos()){
				ah.persist(alumno);
				close();
			}
			else
				GestorErrores.mensajeTexto("Existen errores. \nNo se puede actualizar hasta que los errores se hayan solucionado.", NIVEL_ERROR);
		}
		if (buttonId==1)
			close();
		
	}

	
	private boolean validadoresCorrectos(){

		IStatus status1 = (IStatus) eMailBinding.getValidationStatus().getValue();
		IStatus status2 = (IStatus) telMovilBinding.getValidationStatus().getValue();
		IStatus status3 = (IStatus) telFijoBinding.getValidationStatus().getValue();
		IStatus status4 = (IStatus) nombreBinding.getValidationStatus().getValue();
		IStatus status5 = (IStatus) apellidosBinding.getValidationStatus().getValue();
		IStatus status6 = (IStatus) direccionBinding.getValidationStatus().getValue();
		IStatus status7 = (IStatus) provinciaBinding.getValidationStatus().getValue();
		IStatus status8 = (IStatus) poblacionBinding.getValidationStatus().getValue();
		log.debug("Estados: "+"\n"+status1 + "\n"+ status2 +"\n"+ status3+"\n"+ status4+"\n"+ status5 );
		
		if (status1.matches(IStatus.ERROR) ||
			status2.matches(IStatus.ERROR) ||
			status3.matches(IStatus.ERROR) ||
			status4.matches(IStatus.ERROR) ||
			status5.matches(IStatus.ERROR) ||
			status6.matches(IStatus.ERROR) ||
			status7.matches(IStatus.ERROR) ||
			status8.matches(IStatus.ERROR) )
			return false;
		else
			return true;
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(807, 571);
	}
	
   protected void configureShell(Shell shell) {
	      super.configureShell(shell);
	      shell.setText("Mantenimiento de alumnos");
	   }
	
	private void añadirDecorations(){
		ControlDecorationSupport.create(nombreBinding, SWT.TOP | SWT.LEFT); 
		ControlDecorationSupport.create(apellidosBinding, SWT.TOP | SWT.LEFT); 
		ControlDecorationSupport.create(telFijoBinding, SWT.TOP | SWT.LEFT); 
		ControlDecorationSupport.create(telMovilBinding, SWT.TOP | SWT.LEFT); 
		ControlDecorationSupport.create(eMailBinding, SWT.TOP | SWT.LEFT); 
		ControlDecorationSupport.create(direccionBinding, SWT.TOP | SWT.LEFT); 
		ControlDecorationSupport.create(provinciaBinding, SWT.TOP | SWT.LEFT); 
		ControlDecorationSupport.create(poblacionBinding, SWT.TOP | SWT.LEFT); 
//		ControlDecorationSupport.create(conocimientoBinding, SWT.TOP | SWT.LEFT); 

	}
	

	class ReciboTableLabelProvider extends ObservableMapLabelProvider implements ITableLabelProvider {

		public ReciboTableLabelProvider(IObservableMap[] iObservableMaps) {
			super(iObservableMaps);
		}

		@Override
		public String getColumnText(Object obj, int index) {
			Recibo recibo = (Recibo) obj;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			
			DecimalFormatSymbols dcfs = new DecimalFormatSymbols(new Locale("es","ES"));
			dcfs.setDecimalSeparator(',');
			dcfs.setGroupingSeparator('.'); 
			DecimalFormat dcmf= new DecimalFormat("###,##0.00",dcfs);
			
			switch (index) {
				case 0:
					return Integer.toString(recibo.getNumRecibo());
				case 1:
					return recibo.getDescSerieRecibo();
							//TAConstants.DATETIME_FORMAT.format(recibo.getDate());
				case 2:
					return recibo.getNombreCurso();
				case 3:
					return sdf.format(recibo.getFDesde());					
				case 4:
					return sdf.format(recibo.getFHasta());
				case 5:
					log.debug("valor que hay que formatear: " +recibo.getImpTotal().floatValue());
					return dcmf.format(recibo.getImpTotal().floatValue());
				case 6:
					return (recibo.getPagado()).equalsIgnoreCase("S")?"Sí":"No";
				default:
					throw new IllegalArgumentException("Central Panel Mesage Table does not have column index:" + index);
			}
		}

	} 
	
	class MatriculaTableLabelProvider extends ObservableMapLabelProvider implements ITableLabelProvider {

/*		0: "idMatricula", 
 * 		1: "nombreCurso",
 * 		2: "agnoCurso",
 * 		3: "FDesde",
 * 		4: "FHasta",
 *		5: "impMes"
*/
		public MatriculaTableLabelProvider(IObservableMap[] iObservableMaps) {
			super(iObservableMaps);
		}

		@Override
		public String getColumnText(Object obj, int index) {
			Matricula matricula = (Matricula) obj;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			
			DecimalFormatSymbols dcfs = new DecimalFormatSymbols(new Locale("es","ES"));
			dcfs.setDecimalSeparator(',');
			dcfs.setGroupingSeparator('.'); 
			DecimalFormat dcmf= new DecimalFormat("###,##0.00",dcfs);
			
			switch (index) {
				case 0:
					return Integer.toString(matricula.getIdMatricula());
				case 1:
					return matricula.getNombreCurso();
							//TAConstants.DATETIME_FORMAT.format(recibo.getDate());
				case 2:
					return matricula.getAgnoCurso();
				case 3:
					return sdf.format(matricula.getFDesde());					
				case 4:
					return sdf.format(matricula.getFHasta());
				case 5:
					log.debug("valor que hay que formatear: " +matricula.getImpMes().floatValue());
					return dcmf.format(matricula.getImpMes().floatValue());
				default:
					throw new IllegalArgumentException("Central Panel Mesage Table does not have column index:" + index);
			}
		}

	} 
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTxCodigoObserveWidget = WidgetProperties.text(SWT.FocusOut).observe(txCodigo);
		IObservableValue idAlumnoAlumnoObserveValue = BeanProperties.value("idAlumno").observe(alumno);
		bindingContext.bindValue(observeTextTxCodigoObserveWidget, idAlumnoAlumnoObserveValue, null, null);
		//
		IObservableValue observeTextTxNombreObserveWidget = WidgetProperties.text(SWT.FocusOut).observe(txNombre);
		IObservableValue nombreAlumnoObserveValue = BeanProperties.value("nombre").observe(alumno);
		UpdateValueStrategy strategy_obligatorio = new UpdateValueStrategy();
		strategy_obligatorio.setAfterConvertValidator(new NoVacioValidator());
		nombreBinding = bindingContext.bindValue(observeTextTxNombreObserveWidget, nombreAlumnoObserveValue, strategy_obligatorio, null);
		//
		IObservableValue observeTextTxApellidosObserveWidget = WidgetProperties.text(SWT.FocusOut).observe(txApellidos);
		IObservableValue apellidosAlumnoObserveValue = BeanProperties.value("apellidos").observe(alumno);
		apellidosBinding = bindingContext.bindValue(observeTextTxApellidosObserveWidget, apellidosAlumnoObserveValue, strategy_obligatorio, null);
		//
		IObservableValue observeTextTxTelFijoObserveWidget = WidgetProperties.text(SWT.FocusOut).observe(txTelFijo);
		IObservableValue telFijoAlumnoObserveValue = BeanProperties.value("telFijo").observe(alumno);
		UpdateValueStrategy strategy_1 = new UpdateValueStrategy();
		strategy_1.setAfterConvertValidator(new TelFijoValidator());
		telFijoBinding = bindingContext.bindValue(observeTextTxTelFijoObserveWidget, telFijoAlumnoObserveValue, strategy_1, null);
		//
		IObservableValue observeTextTxTelMovilObserveWidget = WidgetProperties.text(SWT.FocusOut).observe(txTelMovil);
		IObservableValue telMovilAlumnoObserveValue = BeanProperties.value("telMovil").observe(alumno);
		UpdateValueStrategy strategy = new UpdateValueStrategy();
		strategy.setAfterConvertValidator(new MovilValidator());
		telMovilBinding = bindingContext.bindValue(observeTextTxTelMovilObserveWidget, telMovilAlumnoObserveValue, strategy, null);
		//
		IObservableValue observeSelectionTxFechaAltaObserveWidget = WidgetProperties.selection().observe(txFechaAlta);
		IObservableValue fAltaAlumnoObserveValue = BeanProperties.value("FAlta").observe(alumno);
		bindingContext.bindValue(observeSelectionTxFechaAltaObserveWidget, fAltaAlumnoObserveValue, null, null);
		//
		IObservableValue observeTextTxNIFObserveWidget = WidgetProperties.text(SWT.FocusOut).observe(txNIF);
		IObservableValue nifAlumnoObserveValue = BeanProperties.value("nif").observe(alumno);
		bindingContext.bindValue(observeTextTxNIFObserveWidget, nifAlumnoObserveValue, null, null);
		//
		IObservableValue observeTextTxDireccionObserveWidget = WidgetProperties.text(SWT.FocusOut).observe(txDireccion);
		IObservableValue direccionAlumnoObserveValue = BeanProperties.value("direccion").observe(alumno);
		direccionBinding = bindingContext.bindValue(observeTextTxDireccionObserveWidget, direccionAlumnoObserveValue, strategy_obligatorio, null);
		//
		IObservableValue observeTextTxLocalidadObserveWidget = WidgetProperties.text(SWT.FocusOut).observe(txLocalidad);
		IObservableValue poblacionAlumnoObserveValue = BeanProperties.value("poblacion").observe(alumno);
		poblacionBinding = bindingContext.bindValue(observeTextTxLocalidadObserveWidget, poblacionAlumnoObserveValue, strategy_obligatorio, null);
		//
		IObservableValue observeTextTxProvinciaObserveWidget = WidgetProperties.text(SWT.FocusOut).observe(txProvincia);
		IObservableValue provinciaAlumnoObserveValue = BeanProperties.value("provincia").observe(alumno);
		provinciaBinding = bindingContext.bindValue(observeTextTxProvinciaObserveWidget, provinciaAlumnoObserveValue, strategy_obligatorio, null);
		//
		IObservableValue observeTextTxCPostalObserveWidget = WidgetProperties.text(SWT.FocusOut).observe(txCPostal);
		IObservableValue cpAlumnoObserveValue = BeanProperties.value("cp").observe(alumno);
		bindingContext.bindValue(observeTextTxCPostalObserveWidget, cpAlumnoObserveValue, null, null);
		//
		IObservableValue observeTextTxemailObserveWidget = WidgetProperties.text(SWT.FocusOut).observe(txemail);
		IObservableValue eMailAlumnoObserveValue = BeanProperties.value("EMail").observe(alumno);
		UpdateValueStrategy strategy_2 = new UpdateValueStrategy();
		strategy_2.setAfterConvertValidator(new EMailValidator());
		eMailBinding = bindingContext.bindValue(observeTextTxemailObserveWidget, eMailAlumnoObserveValue, strategy_2, null);
		//
		IObservableValue observeTextTxNotasObserveWidget = WidgetProperties.text(SWT.FocusOut).observe(txNotas);
		IObservableValue notasAlumnoObserveValue = BeanProperties.value("notas").observe(alumno);
		bindingContext.bindValue(observeTextTxNotasObserveWidget, notasAlumnoObserveValue, null, null);
		//
		IObservableValue observeSelectionTxFechaNacimientoObserveWidget = WidgetProperties.selection().observe(txFechaNacimiento);
		IObservableValue fNacimientoAlumnoObserveValue = BeanProperties.value("FNacimiento").observe(alumno);
		bindingContext.bindValue(observeSelectionTxFechaNacimientoObserveWidget, fNacimientoAlumnoObserveValue, null, null);
		//
		IObservableValue observeTextTxNombreTutorObserveWidget = WidgetProperties.text(SWT.Modify).observe(txNombreTutor);
		IObservableValue nombreTutorAlumnoObserveValue = BeanProperties.value("nombreTutor").observe(alumno);
		bindingContext.bindValue(observeTextTxNombreTutorObserveWidget, nombreTutorAlumnoObserveValue, null, null);
		//
		IObservableValue observeTextLblNif_1ObserveWidget = WidgetProperties.text().observe(lblNif_1);
		IObservableValue apellidosTutorAlumnoObserveValue = BeanProperties.value("apellidosTutor").observe(alumno);
		bindingContext.bindValue(observeTextLblNif_1ObserveWidget, apellidosTutorAlumnoObserveValue, null, null);
		//
		IObservableValue observeSingleSelectionTxConocio = ViewersObservables.observeSingleSelection(txConocio);
		IObservableValue conocimientoAlumnoObserveValue = BeanProperties.value("conocimiento").observe(alumno);
		conocimientoBinding = bindingContext.bindValue(observeSingleSelectionTxConocio, conocimientoAlumnoObserveValue, null, null);
		//
		IObservableValue observeTextTxEdadObserveWidget = WidgetProperties.text(SWT.Modify).observe(txEdad);
		IObservableValue edadAlumnoObserveValue = BeanProperties.value("edad").observe(alumno);
		bindingContext.bindValue(observeTextTxEdadObserveWidget, edadAlumnoObserveValue, null, null);
		//
		IObservableValue observeSelectionBtnNoActivoObserveWidget = WidgetProperties.selection().observe(btnNoActivo);
		IObservableValue bajaAlumnoObserveValue = BeanProperties.value("baja").observe(alumno);
		UpdateValueStrategy strategy_3 = new UpdateValueStrategy();
		strategy_3.setConverter(new BooleanToStringConverter());
		UpdateValueStrategy strategy_4 = new UpdateValueStrategy();
		strategy_4.setConverter(new StringToBooleanConverter());
		bindingContext.bindValue(observeSelectionBtnNoActivoObserveWidget, bajaAlumnoObserveValue, strategy_3, strategy_4);
		//
		return bindingContext;
	}
}
