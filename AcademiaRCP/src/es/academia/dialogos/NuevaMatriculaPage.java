package es.academia.dialogos;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.DateTime;

import es.academia.modelo.Recibo;
import es.academia.modelo.Matricula;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.core.databinding.beans.BeanProperties;

import es.academia.modelo.Alumno;
import es.academia.modelo.Curso;
import es.academia.negocio.MatricularAlumno;
import es.academia.negocio.NegocioException;
import es.academia.utils.ACALog;
import es.academia.utils.GestorErrores;
import es.academia.utils.IConstantes;

import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;

public class NuevaMatriculaPage extends WizardPage implements IConstantes{
	private DataBindingContext m_bindingContext;
	private Text txNombreAlumno;
	private Text txNifAlumno;
	protected Text txDescCurso;
	private Text txImpMatricula;
	private Text txImpHora;
	private Text txHorasMes;
	private Text txImpMes;
	private Text txDescuento;
	/**
	 * @wbp.nonvisual location=92,469
	 */
	protected Matricula matricula;
	private Label lblFechaDesde;
	private Label lblFechaHasta;
	private DateTime txFechaInicio;
	private DateTime txFechafin;
	/**
	 * @wbp.nonvisual location=155,469
	 */
	private Alumno alumno ;
	/**
	 * @wbp.nonvisual location=201,469
	 */
	private Curso curso;
	private Label lblNombre;
	
	protected MatricularAlumno matAl;
	
	private static final Logger log = ACALog.getLogger(NuevaMatriculaPage.class);	

	/**
	 * Create the wizard.
	 */
	public NuevaMatriculaPage() {
		super("wizardPage");
		setTitle("Nueva Matrícula");
		setDescription("Rellena los datos de la matrícula");
	}
	

	protected Alumno getAlumno() {
		return alumno;
	}

	protected void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	protected Curso getCurso() {
		return curso;
	}

	protected void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	protected void setMatricula() throws NegocioException{
		matAl = new MatricularAlumno();
		matricula = matAl.obtenerDatosDefecto(getCurso().getIdCurso(),alumno.getIdAlumno());
		initDataBindings();
		
	}



	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(null);
		
		Group grpAlumno = new Group(container, SWT.NONE);
		grpAlumno.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD | SWT.ITALIC));
		grpAlumno.setText("Alumno");
		grpAlumno.setBounds(10, 10, 642, 56);
		
		lblNombre = new Label(grpAlumno, SWT.NONE);
		lblNombre.setText("Nombre:");
		lblNombre.setBounds(10, 25, 46, 15);
		
		txNombreAlumno = new Text(grpAlumno, SWT.BORDER);
		txNombreAlumno.setEditable(false);
		txNombreAlumno.setBounds(62, 22, 383, 20);
		
		Label label_1 = new Label(grpAlumno, SWT.NONE);
		label_1.setText("NIF: ");
		label_1.setBounds(481, 25, 30, 15);
		
		txNifAlumno = new Text(grpAlumno, SWT.BORDER);
		txNifAlumno.setEditable(false);
		txNifAlumno.setBounds(517, 22, 115, 20);
		
		Group grpCurso = new Group(container, SWT.NONE);
		grpCurso.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD | SWT.ITALIC));
		grpCurso.setText("Curso");
		grpCurso.setBounds(10, 84, 642, 56);
		
		Label lblDescripcin = new Label(grpCurso, SWT.NONE);
		lblDescripcin.setText("Descripci\u00F3n:");
		lblDescripcin.setBounds(10, 25, 73, 15);
		
		txDescCurso = new Text(grpCurso, SWT.BORDER);
		txDescCurso.setEditable(false);
		txDescCurso.setBounds(89, 22, 543, 20);
		
		Group grpFechas = new Group(container, SWT.NONE);
		grpFechas.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD | SWT.ITALIC));
		grpFechas.setText("Fechas");
		grpFechas.setBounds(10, 159, 168, 147);
		
		lblFechaDesde = new Label(grpFechas, SWT.NONE);
		lblFechaDesde.setBounds(10, 32, 85, 15);
		lblFechaDesde.setText("Fecha inicio:");
		
		txFechaInicio = new DateTime(grpFechas, SWT.BORDER);
		txFechaInicio.setBounds(10, 47, 138, 22);
		
		lblFechaHasta = new Label(grpFechas, SWT.NONE);
		lblFechaHasta.setText("Fecha fin:");
		lblFechaHasta.setBounds(10, 87, 85, 15);
		
		txFechafin = new DateTime(grpFechas, SWT.BORDER);
		txFechafin.setBounds(10, 104, 138, 22);
		
		Group grpImportes = new Group(container, SWT.NONE);
		grpImportes.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD | SWT.ITALIC));
		grpImportes.setText("Importes");
		grpImportes.setBounds(184, 159, 468, 147);
		
		Label lblImporteMatrcula = new Label(grpImportes, SWT.NONE);
		lblImporteMatrcula.setText("Importe matr\u00EDcula");
		lblImporteMatrcula.setBounds(10, 32, 105, 15);
		
		txImpMatricula = new Text(grpImportes, SWT.BORDER);
		txImpMatricula.setBounds(10, 49, 115, 20);
		
		Label lblImporteHora = new Label(grpImportes, SWT.NONE);
		lblImporteHora.setText("Importe hora");
		lblImporteHora.setBounds(160, 32, 105, 15);
		
		txImpHora = new Text(grpImportes, SWT.BORDER);
		txImpHora.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				getWizard().getContainer().updateButtons();
			}
		});
		txImpHora.setBounds(160, 49, 132, 20);
		
		Label lblHorasAlMes = new Label(grpImportes, SWT.NONE);
		lblHorasAlMes.setText("Horas al mes");
		lblHorasAlMes.setBounds(331, 32, 105, 15);
		
		txHorasMes = new Text(grpImportes, SWT.BORDER);
		txHorasMes.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				getWizard().getContainer().updateButtons();
			}
		});
		
		txHorasMes.setBounds(331, 49, 115, 20);
		
		Label lblImporteFijoAl = new Label(grpImportes, SWT.NONE);
		lblImporteFijoAl.setText("Importe fijo al mes");
		lblImporteFijoAl.setBounds(160, 87, 105, 15);
		
		txImpMes = new Text(grpImportes, SWT.BORDER);
		txImpMes.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				getWizard().getContainer().updateButtons();
			}
		});
		
		txImpMes.setBounds(160, 104, 132, 20);
		
		Label lblDescuento = new Label(grpImportes, SWT.NONE);
		lblDescuento.setText("Descuento (%)");
		lblDescuento.setBounds(331, 87, 105, 15);
		
		txDescuento = new Text(grpImportes, SWT.BORDER);
		txDescuento.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				getWizard().getContainer().updateButtons();
			}
		});
		
		txDescuento.setBounds(331, 104, 115, 20);
		m_bindingContext = initDataBindings();
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeSelectionTxFechaInicioObserveWidget = WidgetProperties.selection().observe(txFechaInicio);
		IObservableValue fDesdeMatriculaObserveValue = BeanProperties.value("FDesde").observe(matricula);
		bindingContext.bindValue(observeSelectionTxFechaInicioObserveWidget, fDesdeMatriculaObserveValue, null, null);
		//
		IObservableValue observeSelectionTxFechafinObserveWidget = WidgetProperties.selection().observe(txFechafin);
		IObservableValue fHastaMatriculaObserveValue = BeanProperties.value("FHasta").observe(matricula);
		bindingContext.bindValue(observeSelectionTxFechafinObserveWidget, fHastaMatriculaObserveValue, null, null);
		//
		IObservableValue observeTextTxImpMatriculaObserveWidget = WidgetProperties.text(SWT.Modify).observe(txImpMatricula);
		IObservableValue pmatriculaMatriculaObserveValue = BeanProperties.value("pmatricula").observe(matricula);
		bindingContext.bindValue(observeTextTxImpMatriculaObserveWidget, pmatriculaMatriculaObserveValue, null, null);
		//
		IObservableValue observeTextTxImpHoraObserveWidget = WidgetProperties.text(SWT.Modify).observe(txImpHora);
		IObservableValue impHoraMatriculaObserveValue = BeanProperties.value("impHora").observe(matricula);
		bindingContext.bindValue(observeTextTxImpHoraObserveWidget, impHoraMatriculaObserveValue, null, null);
		//
		IObservableValue observeTextTxHorasMesObserveWidget = WidgetProperties.text(SWT.Modify).observe(txHorasMes);
		IObservableValue horasDefectoMatriculaObserveValue = BeanProperties.value("horasDefecto").observe(matricula);
		bindingContext.bindValue(observeTextTxHorasMesObserveWidget, horasDefectoMatriculaObserveValue, null, null);
		//
		IObservableValue observeTextTxImpMesObserveWidget = WidgetProperties.text(SWT.Modify).observe(txImpMes);
		IObservableValue impMesMatriculaObserveValue = BeanProperties.value("impMes").observe(matricula);
		bindingContext.bindValue(observeTextTxImpMesObserveWidget, impMesMatriculaObserveValue, null, null);
		//
		IObservableValue observeTextTxDescuentoObserveWidget = WidgetProperties.text(SWT.Modify).observe(txDescuento);
		IObservableValue descuentoMatriculaObserveValue = BeanProperties.value("descuento").observe(matricula);
		bindingContext.bindValue(observeTextTxDescuentoObserveWidget, descuentoMatriculaObserveValue, null, null);
		//
		IObservableValue observeTextTxNombreAlumnoObserveWidget = WidgetProperties.text(SWT.Modify).observe(txNombreAlumno);
		IObservableValue nombreCompletoAlumnoObserveValue = BeanProperties.value("nombreCompleto").observe(alumno);
		bindingContext.bindValue(observeTextTxNombreAlumnoObserveWidget, nombreCompletoAlumnoObserveValue, null, null);
		//
		IObservableValue observeTextTxNifAlumnoObserveWidget = WidgetProperties.text(SWT.Modify).observe(txNifAlumno);
		IObservableValue nifAlumnoObserveValue = BeanProperties.value("nif").observe(alumno);
		bindingContext.bindValue(observeTextTxNifAlumnoObserveWidget, nifAlumnoObserveValue, null, null);
		//
		IObservableValue observeTextTxDescCursoObserveWidget = WidgetProperties.text(SWT.Modify).observe(txDescCurso);
		IObservableValue descCursoCursoObserveValue = BeanProperties.value("descCurso").observe(curso);
		bindingContext.bindValue(observeTextTxDescCursoObserveWidget, descCursoCursoObserveValue, null, null);
		//
		return bindingContext;
	}

	
	public boolean canFlipToNextPage (){

		return datosCorrectos();
	}
	
	public boolean canFinish(){
		return false;
	}

	private boolean datosCorrectos(){
//		GestorErrores.mensajeTexto("Entro en datosCorrectos", NIVEL_ERROR);
		log.debug("Entrando en datosCorrectos");
		log.debug("Datos:");
		log.debug("txImpHora: "+ txImpHora.getText());
		log.debug("txHorasMes: "+ txHorasMes.getText());

		if ( (txImpHora.getText()).equals("") && !(txHorasMes.getText()).equals("") ){
			return false;
		}
		if ( !(txImpHora.getText()).equals("") && (txHorasMes.getText()).equals("") )
			return false;
		
		if ((txImpHora.getText()).equals("") && (txHorasMes.getText()).equals(""))
			return false;
		
		return true;		

	}
	
	public WizardPage getNextPage(){
		SalidaRecibosPage rp = ((NuevaMatriculaWizard)getWizard()).rp;
		rp.cargarListaRecibos();
		return rp;
	}
	
}
