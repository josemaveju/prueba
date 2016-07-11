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
import es.academia.modelo.Aula;
import es.academia.modelo.AulaHome;
import es.academia.modelo.ConocimientoHome;
import es.academia.modelo.CursoHome;
import es.academia.modelo.Materia;
import es.academia.modelo.MateriaHome;
import es.academia.modelo.Matricula;
import es.academia.modelo.Profesor;
import es.academia.modelo.ProfesorHome;
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
import org.eclipse.jface.viewers.StructuredViewer;
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

import org.eclipse.swt.widgets.Combo;

import es.academia.modelo.Curso;
import org.eclipse.wb.swt.TableViewerColumnSorter;



public class DetalleCurso extends Dialog implements IConstantes{
	private Binding impMesBinding;
	private Binding impHoraBinding;
	private Binding impMatriculaBinding;
	private Binding profesorBinding;
	private Binding ffinBinding;
	private Binding descripcionBinding;
	private Binding finicioBinding;
	private static final Logger log = ACALog.getLogger(DetalleCurso.class);
	
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
	private Text txDescripcion;
	private Text txAgno;
	/**
	 * @wbp.nonvisual location=91,521
	 */
//	private Alumno alumno = new Alumno();
//	private AlumnoHome ah = new AlumnoHome();
	private int claveCurso = 0; 
	private DateTime txFInicio;
	private Text txMaxAlumnos;
	private Text txHorasSemana;
//	private CCombo txConocioCombo;
//	private ComboViewer txConocio;
//    private ControlDecoration cdTelMovil;
    private Table tbMatriculas;
    private TableViewer tablaMatriculas;
    private TableViewer tablaRecibos;
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

	private Button btnNoActivo;
	private Text txImpMatricula;
	private Text txImpHora;
	private Text txImpMes;
	/**
	 * @wbp.nonvisual location=184,511
	 */
	private Curso curso = new Curso();
	private CursoHome ch = new CursoHome();
	private DateTime txFFin;
	private CCombo txAula;
	private CCombo txMateria;
	private CCombo txProfesor;
	private ComboViewer txProfesorViewer;
	private StructuredViewer txMateriaViewer;
	private ComboViewer txAulaViewer;
	

	public void setTipoOperacion(String tipoOperacion){
		this.tipoOperacion = tipoOperacion;
	}
	
	public DetalleCurso(Shell parentShell) {
		super(parentShell);
//		setHelpAvailable(false);
		setShellStyle(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}
	
	public void setClaveCurso(int claveCurso){
		this.claveCurso = claveCurso;
	}
	
	public void setCurso(Curso curso){
		this.curso = curso;
	}

	public int getClaveCurso(){
		return this.claveCurso;
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
		//TODO Revisar las listas. Debe estar referidas a alumnos, no a cursos.
		
//		setTitleImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/graduated_72.png"));
		Composite area = (Composite) super.createDialogArea(parent);
		area.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		area.setLayout(null);
		Composite container = new Composite(area, SWT.BORDER);
		container.setBounds(0, 2, 801, 472);
		container.setLayout(null);
		
		Composite opciones = new Composite(container, SWT.BORDER);
		opciones.setBackground(SWTResourceManager.getColor(248, 248, 255));
		opciones.setBounds(0, 90, 169, 373);
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
		lbMatriculas.setText("Alumnos matriculados");
		
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
		
		Composite datosFijos = new Composite(container, SWT.BORDER);
		datosFijos.setBackground(SWTResourceManager.getColor(248, 248, 255));
		datosFijos.setBounds(0, 0, 792, 84);
		
		Label lblCdigo = new Label(datosFijos, SWT.NONE);
		lblCdigo.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblCdigo.setBounds(126, 0, 39, 15);
		lblCdigo.setText("C\u00F3digo");
		
		txCodigo = new Text(datosFijos, SWT.BORDER | SWT.READ_ONLY);
		txCodigo.setBounds(126, 16, 55, 20);
		
		txDescripcion = new Text(datosFijos, SWT.BORDER);
		txDescripcion.setBounds(217, 16, 420, 20);
		Utilidades.addControlesTexto(txDescripcion,false,70);
		
		ControlDecoration cdApellidos = new ControlDecoration(txDescripcion, SWT.LEFT | SWT.TOP);
		cdApellidos.setShowOnlyOnFocus(true);
		cdApellidos.setDescriptionText("Apellidos del alumno");
		
		Label lblApellidos = new Label(datosFijos, SWT.NONE);
		lblApellidos.setText("Descripci\u00F3n");
		lblApellidos.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblApellidos.setBounds(217, 0, 69, 15);
		
		Label lblNif = new Label(datosFijos, SWT.NONE);
		lblNif.setText("A\u00F1o del curso");
		lblNif.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblNif.setBounds(217, 38, 69, 15);
		
		txAgno = new Text(datosFijos, SWT.BORDER);
		txAgno.setBounds(217, 55, 69, 20);
		Utilidades.addControlesTexto(txAgno,false,12);
		
		Label lblFechaAlta = new Label(datosFijos, SWT.NONE);
		lblFechaAlta.setText("Fecha inicio");
		lblFechaAlta.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblFechaAlta.setBounds(308, 35, 69, 15);
		
		txFInicio = new DateTime(datosFijos, SWT.BORDER | SWT.DROP_DOWN);
		txFInicio.setBounds(308, 52, 112, 21);
		
		CLabel lblNewLabel = new CLabel(datosFijos, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblNewLabel.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/libros_48.png"));
		lblNewLabel.setBounds(10, 5, 78, 70);
		lblNewLabel.setText("");
		
		btnNoActivo = new Button(datosFijos, SWT.CHECK);
		btnNoActivo.setBounds(734, 18, 13, 16);
		
		Label lblNewLabel_1 = new Label(datosFijos, SWT.NONE);
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblNewLabel_1.setBounds(667, 19, 61, 15);
		lblNewLabel_1.setText("No activo");
		
		Label lblFechaFin = new Label(datosFijos, SWT.NONE);
		lblFechaFin.setText("Fecha fin");
		lblFechaFin.setBackground(SWTResourceManager.getColor(248, 248, 255));
		lblFechaFin.setBounds(448, 35, 69, 15);
		
		txFFin = new DateTime(datosFijos, SWT.BORDER | SWT.DROP_DOWN);
		txFFin.setBounds(448, 52, 112, 21);
		
		central = new Composite(container, SWT.NONE);
		central.setLocation(175, 90);
		central.setSize(617, 373);
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
		grpDomicilio.setText("Datos del curso");
		grpDomicilio.setBounds(10, 34, 593, 123);
		
		Label lblDireccin = new Label(grpDomicilio, SWT.NONE);
		lblDireccin.setText("Aula");
		lblDireccin.setBounds(350, 63, 69, 15);
		
		Button btnMasAula = new Button(grpDomicilio, SWT.NONE);
		btnMasAula.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Integer claveAula;
				claveAula = new Integer(-1);

				DetalleAula dlg = new DetalleAula(getShell());
				dlg.setClaveAula(claveAula.intValue());
			
				dlg.open();
				txAulaViewer.refresh();
				
			}
		});
		btnMasAula.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/mas.gif"));
		btnMasAula.setBounds(562, 77, 21, 21);
		
		txAula = new CCombo(grpDomicilio, SWT.BORDER | SWT.READ_ONLY);
		txAula.setVisibleItemCount(10);
		txAula.setEditable(false);
		txAula.setBounds(350, 78, 206, 20);
	// Rellenar el Combo
		txAulaViewer = new ComboViewer(txAula); 
		try {
			txAulaViewer.setContentProvider(new ArrayContentProvider().getInstance());
			txAulaViewer.setLabelProvider(new LabelProvider() {

		        public String getText(Object element) {
		            if (element instanceof Aula) {
		            		return ((Aula)element).getDescAula();
		            }
		            return super.getText(element);
		        }
		    });
			
			AulaHome ch = new AulaHome();
			txAulaViewer.setInput(ch.listarTodos());
			
		}catch (RuntimeException re){
			re.printStackTrace();
			GestorErrores.mensajeTexto("Error cargando lista de aulas.\n\n" + 
		                    re.getMessage()+"\n" + re.getCause(), NIVEL_ERROR);
		}
		
		txMateria = new CCombo(grpDomicilio, SWT.BORDER | SWT.READ_ONLY);
		txMateria.setVisibleItemCount(10);
		txMateria.setEditable(false);
		txMateria.setBounds(10, 78, 307, 20);
	  // Rellenar el Combo
		txMateriaViewer = new ComboViewer(txMateria);
		try {
			txMateriaViewer.setContentProvider(new ArrayContentProvider().getInstance());
			txMateriaViewer.setLabelProvider(new LabelProvider() {

		        public String getText(Object element) {
		            if (element instanceof Materia) {
		            		return ((Materia)element).getDescMateria();
		            }
		            return super.getText(element);
		        }
		    });
			
			MateriaHome ch = new MateriaHome();
			txMateriaViewer.setInput(ch.listarTodos());
			
		}catch (RuntimeException re){
			re.printStackTrace();
			GestorErrores.mensajeTexto("Error cargando lista de materias.\n\n" + 
		                    re.getMessage()+"\n" + re.getCause(), NIVEL_ERROR);
		}
		
		Label lblMateria = new Label(grpDomicilio, SWT.NONE);
		lblMateria.setText("Materia");
		lblMateria.setBounds(10, 63, 69, 15);
		
		Button btnMasMateria = new Button(grpDomicilio, SWT.NONE);
		btnMasMateria.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/mas.gif"));
		btnMasMateria.setBounds(323, 77, 21, 21);
		
		txProfesor = new CCombo(grpDomicilio, SWT.BORDER | SWT.READ_ONLY);
		txProfesor.setVisibleItemCount(10);
		txProfesor.setEditable(false);
		txProfesor.setBounds(10, 37, 546, 20);

   // Rellenar el Combo
		txProfesorViewer = new ComboViewer(txProfesor);
		try {
			txProfesorViewer.setContentProvider(new ArrayContentProvider().getInstance());
			txProfesorViewer.setLabelProvider(new LabelProvider() {

		        public String getText(Object element) {
		            if (element instanceof Profesor) {
		            	String nombre =((Profesor)element).getNombre();
		            	String apellidos = ((Profesor)element).getApellidos();
		            	if (apellidos == null)
		            		apellidos = "";
		            	
		            	return nombre + " " + apellidos;
		            }
		            return super.getText(element);
		        }
		    });
			
			ProfesorHome ch = new ProfesorHome();
			txProfesorViewer.setInput(ch.listarTodos());
			
		}catch (RuntimeException re){
			re.printStackTrace();
			GestorErrores.mensajeTexto("Error cargando lista de profesores.\n\n" + 
		                    re.getMessage()+"\n" + re.getCause(), NIVEL_ERROR);
		}

		
		Label lblProfesor = new Label(grpDomicilio, SWT.NONE);
		lblProfesor.setText("Profesor");
		lblProfesor.setBounds(10, 22, 69, 15);
		
		
		Group grpDatosEconomicos = new Group(datosGenerales, SWT.NONE);
		grpDatosEconomicos.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD | SWT.ITALIC));
		grpDatosEconomicos.setText("Datos Econ\u00F3micos");
		grpDatosEconomicos.setBounds(10, 185, 593, 144);
		
		Label lblMasAlumnos = new Label(grpDatosEconomicos, SWT.NONE);
		lblMasAlumnos.setLocation(10, 27);
		lblMasAlumnos.setSize(102, 15);
		lblMasAlumnos.setText("Capacidad m\u00E1xima");
		
		txMaxAlumnos = new Text(grpDatosEconomicos, SWT.BORDER | SWT.RIGHT);
		txMaxAlumnos.setLocation(10, 42);
		txMaxAlumnos.setSize(102, 20);
		Utilidades.addControlesTexto(txMaxAlumnos,false,30);
		
		Label lblHorasSemana = new Label(grpDatosEconomicos, SWT.NONE);
		lblHorasSemana.setLocation(150, 27);
		lblHorasSemana.setSize(75, 15);
		lblHorasSemana.setText("Horas/semana");
		
		txHorasSemana = new Text(grpDatosEconomicos, SWT.BORDER | SWT.RIGHT);
		txHorasSemana.setLocation(148, 42);
		txHorasSemana.setSize(83, 20);
		Utilidades.addControlesTexto(txHorasSemana,false,5);
		
		txImpMatricula = new Text(grpDatosEconomicos, SWT.BORDER | SWT.RIGHT);
		txImpMatricula.setBounds(10, 95, 89, 20);
		
		Label lblImpMatricula = new Label(grpDatosEconomicos, SWT.NONE);
		lblImpMatricula.setText("Importe matr\u00EDcula");
		lblImpMatricula.setBounds(10, 80, 84, 15);
		
		txImpHora = new Text(grpDatosEconomicos, SWT.BORDER | SWT.RIGHT);
		txImpHora.setBounds(148, 95, 89, 20);
		
		Label LblImporteHora = new Label(grpDatosEconomicos, SWT.NONE);
		LblImporteHora.setText("Importe por hora");
		LblImporteHora.setBounds(148, 81, 84, 15);
		
		txImpMes = new Text(grpDatosEconomicos, SWT.BORDER | SWT.RIGHT);
		txImpMes.setBounds(263, 95, 89, 20);
		
		Label LblImporteMes = new Label(grpDatosEconomicos, SWT.NONE);
		LblImporteMes.setText("Importe mensual");
		LblImporteMes.setBounds(263, 81, 84, 15);
		
		datosCursos = new Composite(central, SWT.BORDER);
		
		CLabel lblMatrculasDelAlumno = new CLabel(datosCursos, SWT.NONE);
		lblMatrculasDelAlumno.setText("Alumnos matriculados en el curso");
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
		tbMatriculas.setBounds(10, 78, 593, 281);
		
		TableViewerColumn tvMatCodigo = new TableViewerColumn(tablaMatriculas, SWT.NONE);
		new TableViewerColumnSorter(tvMatCodigo) {
			protected Object getValue(Object o) {
				// TODO remove this method, if your EditingSupport returns value
				return ((Matricula)o).getIdMatricula();
			}
		};
		
		
		
		
		TableColumn tblclmnCdigo = tvMatCodigo.getColumn();
		tblclmnCdigo.setAlignment(SWT.RIGHT);
		tblclmnCdigo.setWidth(50);
		tblclmnCdigo.setText("C\u00F3digo");
		
		TableViewerColumn tvMatDescripcion = new TableViewerColumn(tablaMatriculas, SWT.NONE);
		TableColumn tblclmnNombre = tvMatDescripcion.getColumn();
		tblclmnNombre.setWidth(200);
		tblclmnNombre.setText("Alumno");
		
		TableViewerColumn tvMatImporte = new TableViewerColumn(tablaMatriculas, SWT.NONE);
		TableColumn tblclmnTelefono = tvMatImporte.getColumn();
		tblclmnTelefono.setWidth(90);
		tblclmnTelefono.setText("Fijo");
		
		TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(tablaMatriculas, SWT.NONE);
		TableColumn tblclmnMvil = tableViewerColumn_9.getColumn();
		tblclmnMvil.setWidth(90);
		tblclmnMvil.setText("M\u00F3vil");
		
		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(tablaMatriculas, SWT.NONE);
		TableColumn tblclmnFDesde_1 = tableViewerColumn_7.getColumn();
		tblclmnFDesde_1.setWidth(79);
		tblclmnFDesde_1.setText("F. Desde");
		
		TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(tablaMatriculas, SWT.NONE);
		TableColumn tblclmnFHasta_1 = tableViewerColumn_8.getColumn();
		tblclmnFHasta_1.setWidth(79);
		tblclmnFHasta_1.setText("F. Hasta");

		
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
		tltmRadioItem.setToolTipText("Ver todos los alumnos matriculados");
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
//				abrirNuevoCurso();
			}
		});
		tltmNuevoCurso.setToolTipText("Matricular al alumno en un curso");
		tltmNuevoCurso.setText("Nueva matricula");
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
		tbRecibos.setBounds(10, 78, 593, 281);
		
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
		
		if (claveCurso != 0)
			setBean (this.claveCurso);
		
	//	centralLayout.topControl = datosGenerales;
	//	central.layout();
		if (opcionPanel == null){
			opcionPanel = PANELGENERAL;
		}
		mostrarPanel (opcionPanel);
		
		return area;
	}
/*	
	protected void abrirNuevoCurso() {

		NuevaMatriculaWizard nmw = new NuevaMatriculaWizard();
		nmw.setAlumno(curso);
			
	    // Instantiates the wizard container with the wizard and opens it
	    WizardDialog dialog = new WizardDialog(getShell(), nmw);
	    dialog.create();
	    dialog.open();
		
	}
*/
	private void setBean(int claveCurso){
	//	CursoHome ch = new CursoHome();
		curso = ch.findById(claveCurso);

	//TODO Cargar las listas de cursos y recibos		
		// Enlazar con la lista de matriculas del curso	
		
		// Comprobamos si está cargada la lista de matriculas:
		
		
// Lista de matrículas
		ObservableSetContentProvider contentProviderM = new ObservableSetContentProvider();
		tablaMatriculas.setContentProvider(contentProviderM);
		tablaMatriculas.setLabelProvider( new MatriculaTableLabelProvider(
		     Properties.observeEach( contentProviderM.getKnownElements(), 
			    	BeanProperties.values(new String[]  {
			    			 "idMatricula", "alumno.nombre","alumno.telFijo","alumno.telMovil","FDesde","FHasta"} ) )
		));
		tablaMatriculas.setInput(BeansObservables.observeSet(curso,"matriculas", Matricula.class));

// Lista de recibos de una matricula		
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		tablaRecibos.setContentProvider(contentProvider);
		tablaRecibos.setLabelProvider( new ReciboTableLabelProvider(
		     Properties.observeEach( contentProvider.getKnownElements(), 
			    	BeanProperties.values(new String[]  {
                    "numRecibo", "descSerieRecibo","alulmno.nombre","FDesde","FHasta",
                    "impTotal","pagado"} ) )
		));
		tablaRecibos.setInput(BeansObservables.observeList(curso,PANELRECIBOS, Recibo.class));
		
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.OK_ID, "Aceptar",true);
	
		createButton(parent, IDialogConstants.CANCEL_ID,"Cancelar", false);
		m_bindingContext = initDataBindings();
		añadirDecorations();
	}

	
	protected void buttonPressed(int buttonId)  {

		if (buttonId==0){
			if (validadoresCorrectos()){
				ch.persist(curso);
				close();
			}
			else
				GestorErrores.mensajeTexto("Existen errores. \nNo se puede actualizar hasta que los errores se hayan solucionado.", NIVEL_ERROR);
		}
		if (buttonId==1)
			close();
		
	}

	
	private boolean validadoresCorrectos(){

		IStatus status1 = (IStatus) finicioBinding.getValidationStatus().getValue();
		IStatus status2 = (IStatus) ffinBinding.getValidationStatus().getValue();
		IStatus status3 = (IStatus) descripcionBinding.getValidationStatus().getValue();
		IStatus status4 = (IStatus) profesorBinding.getValidationStatus().getValue();
		IStatus status5 = (IStatus) impMatriculaBinding.getValidationStatus().getValue();
		IStatus status6 = (IStatus) impHoraBinding.getValidationStatus().getValue();
		IStatus status7 = (IStatus) impMesBinding.getValidationStatus().getValue();
		log.debug("Estados: "+"\n"+status1 + "\n"+ status2 +"\n"+ status3+"\n"+ status4+"\n"+ status5 );
		
		if (status1.matches(IStatus.ERROR) ||
			status2.matches(IStatus.ERROR) ||
			status3.matches(IStatus.ERROR) ||
			status4.matches(IStatus.ERROR) ||
			status5.matches(IStatus.ERROR) ||
			status6.matches(IStatus.ERROR) ||
			status7.matches(IStatus.ERROR) )
			return false;
		else
			return true;
	}
	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(807, 540);
	}
	
   protected void configureShell(Shell shell) {
	      super.configureShell(shell);
	      shell.setText("Mantenimiento de cursos");
	   }
	
	private void añadirDecorations(){
		ControlDecorationSupport.create(finicioBinding, SWT.TOP | SWT.LEFT); 
		ControlDecorationSupport.create(ffinBinding, SWT.TOP | SWT.LEFT); 
		ControlDecorationSupport.create(descripcionBinding, SWT.TOP | SWT.LEFT); 
		ControlDecorationSupport.create(profesorBinding, SWT.TOP | SWT.LEFT); 
		ControlDecorationSupport.create(impMatriculaBinding, SWT.TOP | SWT.LEFT); 
		ControlDecorationSupport.create(impHoraBinding, SWT.TOP | SWT.LEFT); 
		ControlDecorationSupport.create(impMesBinding, SWT.TOP | SWT.LEFT); 


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
				case 2:
					return recibo.getMatricula().getAlumno().getNombre()+" "+recibo.getMatricula().getAlumno().getApellidos();
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
 * 		1: "Nombre aLumno",
 * 		2: "NIF Alumno",
 * 		3: "Teléfono",
 * 		4: "FDesde",
 * 		5: "FHasta",
*/
		public MatriculaTableLabelProvider(IObservableMap[] iObservableMaps) {
			super(iObservableMaps);
		}

		@Override
		public String getColumnText(Object obj, int index) {
			Matricula matricula = (Matricula) obj;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			
			
			switch (index) {
				case 0:
					return Integer.toString(matricula.getIdMatricula());
				case 1:
					return matricula.getAlumno().getNombre()+" "+matricula.getAlumno().getApellidos();
							//TAConstants.DATETIME_FORMAT.format(recibo.getDate());
				case 2:
					return matricula.getAlumno().getTelFijo();
				case 3:
					return matricula.getAlumno().getTelMovil();				
				case 4:
					return sdf.format(matricula.getFDesde());				
				case 5:
					return sdf.format(matricula.getFHasta());				
				default:
					throw new IllegalArgumentException("Central Panel Mesage Table does not have column index:" + index);
			}
		}

	} 
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTxAgnoObserveWidget = WidgetProperties.text(SWT.Modify).observe(txAgno);
		IObservableValue annoCursoCursoObserveValue = BeanProperties.value("annoCurso").observe(curso);
		bindingContext.bindValue(observeTextTxAgnoObserveWidget, annoCursoCursoObserveValue, null, null);
		//
		IObservableValue observeSelectionTxFInicioObserveWidget = WidgetProperties.selection().observe(txFInicio);
		IObservableValue fInicioCursoObserveValue = BeanProperties.value("FInicio").observe(curso);
		UpdateValueStrategy strategy = new UpdateValueStrategy();
		strategy.setAfterConvertValidator(new NoVacioValidator());
		finicioBinding = bindingContext.bindValue(observeSelectionTxFInicioObserveWidget, fInicioCursoObserveValue, null, strategy);
		//
		IObservableValue observeTextTxCodigoObserveWidget = WidgetProperties.text(SWT.Modify).observe(txCodigo);
		IObservableValue idCursoCursoObserveValue = BeanProperties.value("idCurso").observe(curso);
		bindingContext.bindValue(observeTextTxCodigoObserveWidget, idCursoCursoObserveValue, null, null);
		//
		IObservableValue observeTextTxDescripcionObserveWidget = WidgetProperties.text(SWT.Modify).observe(txDescripcion);
		IObservableValue descCursoCursoObserveValue = BeanProperties.value("descCurso").observe(curso);
		UpdateValueStrategy strategy_1 = new UpdateValueStrategy();
		strategy_1.setAfterConvertValidator(new NoVacioValidator());
		descripcionBinding = bindingContext.bindValue(observeTextTxDescripcionObserveWidget, descCursoCursoObserveValue, null, strategy_1);
		//
		IObservableValue observeSelectionTxFFinObserveWidget = WidgetProperties.selection().observe(txFFin);
		IObservableValue fFinCursoObserveValue = BeanProperties.value("FFin").observe(curso);
		UpdateValueStrategy strategy_2 = new UpdateValueStrategy();
		strategy_2.setAfterConvertValidator(new NoVacioValidator());
		ffinBinding = bindingContext.bindValue(observeSelectionTxFFinObserveWidget, fFinCursoObserveValue, null, strategy_2);
		//
		IObservableValue observeSelectionTxAulaObserveWidget = ViewersObservables.observeSingleSelection(txAulaViewer);
		IObservableValue aulaCursoObserveValue = BeanProperties.value("aula").observe(curso);
		bindingContext.bindValue(observeSelectionTxAulaObserveWidget, aulaCursoObserveValue, null, null);
		//
		IObservableValue observeSelectionTxMateriaObserveWidget = ViewersObservables.observeSingleSelection(txMateriaViewer);
		IObservableValue materiaCursoObserveValue = BeanProperties.value("materia").observe(curso);
		bindingContext.bindValue(observeSelectionTxMateriaObserveWidget, materiaCursoObserveValue, null, null);
		//
		IObservableValue observeSelectionTxProfesorObserveWidget = ViewersObservables.observeSingleSelection(txProfesorViewer);
		IObservableValue profesorCursoObserveValue = BeanProperties.value("profesor").observe(curso);
		UpdateValueStrategy strategy_3 = new UpdateValueStrategy();
		strategy_3.setAfterConvertValidator(new NoVacioValidator());
		profesorBinding = bindingContext.bindValue(observeSelectionTxProfesorObserveWidget, profesorCursoObserveValue, null, strategy_3);
		//
		IObservableValue observeTextTxMaxAlumnosObserveWidget = WidgetProperties.text(SWT.Modify).observe(txMaxAlumnos);
		IObservableValue maxAlumnosCursoObserveValue = BeanProperties.value("maxAlumnos").observe(curso);
		bindingContext.bindValue(observeTextTxMaxAlumnosObserveWidget, maxAlumnosCursoObserveValue, null, null);
		//
		IObservableValue observeTextTxHorasSemanaObserveWidget = WidgetProperties.text(SWT.Modify).observe(txHorasSemana);
		IObservableValue horasSemanaCursoObserveValue = BeanProperties.value("horasSemana").observe(curso);
		bindingContext.bindValue(observeTextTxHorasSemanaObserveWidget, horasSemanaCursoObserveValue, null, null);
		//
		IObservableValue observeTextTxImpMatriculaObserveWidget = WidgetProperties.text(SWT.Modify).observe(txImpMatricula);
		IObservableValue impMatriculaCursoObserveValue = BeanProperties.value("impMatricula").observe(curso);
		UpdateValueStrategy strategy_4 = new UpdateValueStrategy();
		strategy_4.setAfterConvertValidator(new NoVacioValidator());
		impMatriculaBinding = bindingContext.bindValue(observeTextTxImpMatriculaObserveWidget, impMatriculaCursoObserveValue, null, strategy_4);
		//
		IObservableValue observeTextTxImpHoraObserveWidget = WidgetProperties.text(SWT.Modify).observe(txImpHora);
		IObservableValue impHoraCursoObserveValue = BeanProperties.value("impHora").observe(curso);
		UpdateValueStrategy strategy_5 = new UpdateValueStrategy();
		strategy_5.setAfterConvertValidator(new NoVacioValidator());
		impHoraBinding = bindingContext.bindValue(observeTextTxImpHoraObserveWidget, impHoraCursoObserveValue, null, strategy_5);
		//
		IObservableValue observeTextTxImpMesObserveWidget = WidgetProperties.text(SWT.Modify).observe(txImpMes);
		IObservableValue impMesCursoObserveValue = BeanProperties.value("impMes").observe(curso);
		UpdateValueStrategy strategy_6 = new UpdateValueStrategy();
		strategy_6.setAfterConvertValidator(new NoVacioValidator());
		impMesBinding = bindingContext.bindValue(observeTextTxImpMesObserveWidget, impMesCursoObserveValue, null, strategy_6);
		//
		return bindingContext;
	}
}
