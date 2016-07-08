package es.academia.vistas;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.ExpandBar;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

import es.academia.dialogos.BuscarDlg;
import es.academia.dialogos.DetalleAlumno;
import es.academia.dialogos.DetalleCurso;
import es.academia.dialogos.SendMail;
import es.academia.modelo.Alumno;
import es.academia.modelo.AlumnoHome;
import es.academia.modelo.Curso;
import es.academia.modelo.CursoHome;
import es.academia.utils.ACALog;
import es.academia.utils.BuscarLista;
import es.academia.utils.GestorErrores;
import es.academia.utils.IConstantes;
import es.academia.widgets.CLabelAca;

import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.wb.swt.TableViewerColumnSorter;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.custom.StyledText;

public class VistaCursos extends ViewPart implements IConstantes{

	private static final Logger log = ACALog.getLogger(BuscarDlg.class);
	
	public static final String ID = "es.academia.vistas.vistaCursos"; //$NON-NLS-1$
	private Table table;
	private CursoHome cursoHome= new CursoHome();
	private TableViewer tableViewer;
	private ViewerFilter filtroActivas;
	private List<Alumno> alumnos;

	private ViewerFilter filtroBuscar;
	private CLabel lblNewLabel;
	private Text txFiltroRapido;
	public VistaCursos() {
		setTitleImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/Add_alumno_16.png"));
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		{
			SashForm sashForm = new SashForm(parent, SWT.NONE);
			sashForm.setSashWidth(2);
//			sashForm.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
//			sashForm.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			{
				ExpandBar expandBar = new ExpandBar(sashForm, SWT.BORDER);
				expandBar.setBackground(SWTResourceManager.getColor(176, 196, 222));
//				expandBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
//				expandBar.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
				{
					ExpandItem xpndtmCursos = new ExpandItem(expandBar, SWT.NONE);
					xpndtmCursos.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/elementary_school_24.png"));
					xpndtmCursos.setExpanded(true);
					xpndtmCursos.setText("Cursos");
					{
						Composite composite = new Composite(expandBar, SWT.NONE);
						composite.setBackground(SWTResourceManager.getColor(176, 196, 222));
						xpndtmCursos.setControl(composite);
						composite.setLayout(new RowLayout(SWT.VERTICAL));
						{
							CLabelAca lblNuevoCurso = new CLabelAca(composite, SWT.NONE);
							lblNuevoCurso.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseUp(MouseEvent e) {
									Curso curso = new Curso();
									DetalleCurso dlg = new DetalleCurso(getSite().getShell());
									dlg.setCurso(curso);
									dlg.seleccionarPanel(PANELGENERAL);
									dlg.setTipoOperacion(TIPOOPERALTA);
									dlg.open();
									cargarListaCursos();

								}
							});
							lblNuevoCurso.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/Add_alumno_16.png"));
							lblNuevoCurso.setText("Nuevo curso");
						}
						{
							CLabelAca lblDarDeBaja = new CLabelAca(composite, SWT.NONE);
							lblDarDeBaja.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseUp(MouseEvent e) {
									bajaCurso();
								}
							});
							lblDarDeBaja.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/Del_alumno_16.png"));
							lblDarDeBaja.setText("Dar de baja");
						}
						{
							CLabelAca lblDetalle = new CLabelAca(composite, SWT.NONE);
							lblDetalle.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseUp(MouseEvent e) {
									abrirDetalle(PANELGENERAL);
								}
							});
							lblDetalle.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/matricula.png"));
							lblDetalle.setText("Detalle");
						}
						{
							CLabelAca lblBuscarAlumno = new CLabelAca(composite, SWT.NONE);
							lblBuscarAlumno.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseUp(MouseEvent e) {
									 abrirBuscar();
								}
							});
							lblBuscarAlumno.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/buscar_16.png"));
							lblBuscarAlumno.setText("Filtro avanzado");
						}
					}
					xpndtmCursos.setHeight(xpndtmCursos.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
				}
				{
					ExpandItem xpndtmMatriculas = new ExpandItem(expandBar, SWT.NONE);
					xpndtmMatriculas.setExpanded(true);
					xpndtmMatriculas.setText("Matriculas");
					{
						Composite composite = new Composite(expandBar, SWT.NONE);
						composite.setBackground(SWTResourceManager.getColor(176, 196, 222));
						xpndtmMatriculas.setControl(composite);
						composite.setLayout(new RowLayout(SWT.VERTICAL));
						{
							CLabelAca lblVerMatriculas = new CLabelAca(composite, SWT.NONE);
							lblVerMatriculas.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseUp(MouseEvent e) {
									abrirDetalle(PANELCURSOS);
								}
							});
							lblVerMatriculas.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/libros_16.png"));
							lblVerMatriculas.setText("Ver matriculas");
						}
						{
							CLabelAca lblMatricular = new CLabelAca(composite, SWT.NONE);
							lblMatricular.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/matriculas.png"));
							lblMatricular.setText("Matricular");
						}
					}
					xpndtmMatriculas.setHeight(xpndtmMatriculas.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
				}
				{
					ExpandItem xpndtmRecibos = new ExpandItem(expandBar, SWT.NONE);
					xpndtmRecibos.setExpanded(true);
					xpndtmRecibos.setText("Recibos");
					{
						Composite composite = new Composite(expandBar, SWT.NONE);
						composite.setBackground(SWTResourceManager.getColor(176, 196, 222));
						xpndtmRecibos.setControl(composite);
						composite.setLayout(new RowLayout(SWT.VERTICAL));
						{
							CLabelAca lblVerRecibos = new CLabelAca(composite, SWT.NONE);
							lblVerRecibos.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseUp(MouseEvent e) {
									abrirDetalle(PANELRECIBOS);
								}
							});
							lblVerRecibos.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/recibos.png"));
							lblVerRecibos.setText("Ver recibos");
						}
						{
							CLabelAca lblGenerarRecibos = new CLabelAca(composite, SWT.NONE);
							lblGenerarRecibos.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/Dinero_16.png"));
							lblGenerarRecibos.setText("Generar recibos");
						}
					}
					xpndtmRecibos.setHeight(xpndtmRecibos.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
				}
			}
			{
				Composite composite = new Composite(sashForm, SWT.BORDER);
				composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
				composite.setLayout(new BorderLayout(0, 0));
				{
					Composite composite_Cabecera = new Composite(composite, SWT.NONE);
					composite_Cabecera.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
					composite_Cabecera.setLayoutData(BorderLayout.NORTH);
					composite_Cabecera.setLayout(new FormLayout());
					{
						lblNewLabel = new CLabel(composite_Cabecera, SWT.NONE);
						lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
						FormData fd_lblNewLabel = new FormData();
						fd_lblNewLabel.top = new FormAttachment(0, 3);
						fd_lblNewLabel.left = new FormAttachment(0, 3);
						lblNewLabel.setLayoutData(fd_lblNewLabel);
						lblNewLabel.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/libros_48.png"));
						lblNewLabel.setText("");
					}
					{
						Composite composite_2 = new Composite(composite_Cabecera, SWT.NONE);
						composite_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
						FormData fd_composite_2 = new FormData();
						fd_composite_2.bottom = new FormAttachment(0, 81);
						fd_composite_2.top = new FormAttachment(0, 3);
						fd_composite_2.left = new FormAttachment(0, 84);
						composite_2.setLayoutData(fd_composite_2);
						RowLayout rl_composite_2 = new RowLayout(SWT.VERTICAL);
						rl_composite_2.marginLeft = 15;
						rl_composite_2.spacing = 8;
						rl_composite_2.marginTop = 5;
						composite_2.setLayout(rl_composite_2);
						{
							CLabel lblListaDeAlumnos = new CLabel(composite_2, SWT.NONE);
							lblListaDeAlumnos.setBackgroundMode(SWT.INHERIT_FORCE);
							lblListaDeAlumnos.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
							lblListaDeAlumnos.setLeftMargin(0);
							lblListaDeAlumnos.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD | SWT.ITALIC));
							lblListaDeAlumnos.setImage(null);
							lblListaDeAlumnos.setText("Lista de cursos");
						}
						{
							Composite compositeRadio = new Composite(composite_2, SWT.NONE);
							compositeRadio.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
							compositeRadio.setLayout(new RowLayout(SWT.HORIZONTAL));
							{
								Button btnMostrarTodos = new Button(compositeRadio, SWT.RADIO);
								btnMostrarTodos.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
								btnMostrarTodos.addSelectionListener(new SelectionAdapter() {
									@Override
									public void widgetSelected(SelectionEvent e) {
										tableViewer.resetFilters();
									}
								});
								btnMostrarTodos.setText("Mostrar todos");
							}
							{
								Button btnMostrarSloLos = new Button(compositeRadio, SWT.RADIO);
								btnMostrarSloLos.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
								btnMostrarSloLos.addSelectionListener(new SelectionAdapter() {
									@Override
									public void widgetSelected(SelectionEvent e) {
										tableViewer.addFilter(filtroActivas);
									}
								});
								btnMostrarSloLos.setText("Mostrar s\u00F3lo los activos");
							}
						}
					}
					{
						Composite composite_filtro = new Composite(composite_Cabecera, SWT.NONE);
						composite_filtro.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
						composite_filtro.setBackgroundMode(SWT.INHERIT_FORCE);
						composite_filtro.setLayout(null);
						FormData fd_composite_filtro = new FormData();
						fd_composite_filtro.left = new FormAttachment(100, -217);
						fd_composite_filtro.top = new FormAttachment(lblNewLabel, 0, SWT.TOP);
						fd_composite_filtro.right = new FormAttachment(100, -5);
						fd_composite_filtro.bottom = new FormAttachment(0, 81);
						composite_filtro.setLayoutData(fd_composite_filtro);
						
						txFiltroRapido = new Text(composite_filtro, SWT.BORDER | SWT.SEARCH);
						txFiltroRapido.setBounds(33, 47, 146, 21);
						
						Button button = new Button(composite_filtro, SWT.NONE);
						button.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseUp(MouseEvent e) {
							}
						});
						button.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								filtroRapido();
							}
						});
						button.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/search.gif"));
						button.setBounds(179, 46, 24, 22);
					}
				}
				{
					Composite composite_1 = new Composite(composite, SWT.NONE);
					composite_1.setLayoutData(BorderLayout.SOUTH);
					RowLayout rl_composite_1 = new RowLayout(SWT.HORIZONTAL);
					rl_composite_1.marginBottom = 11;
					rl_composite_1.marginTop = 11;
					composite_1.setLayout(rl_composite_1);
					{
					}
				}
				{
					tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
					table = tableViewer.getTable();
					table.setBackground(SWTResourceManager.getColor(240, 248, 255));
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseDoubleClick(MouseEvent e) {
							abrirDetalle(PANELGENERAL);
						}
					});
					table.setHeaderVisible(true);
					table.setLayoutData(BorderLayout.CENTER);
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.LEFT);
						new TableViewerColumnSorter(tableViewerColumn) {
							@Override
							protected Object getValue(Object o) {
								// TODO remove this method, if your EditingSupport returns value
								return new Integer(((Curso) o).getIdCurso());
							}
						};
						
						TableColumn tblclmnCdigo = tableViewerColumn.getColumn();
						tblclmnCdigo.setWidth(60);
						tblclmnCdigo.setText("C\u00F3digo");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						new TableViewerColumnSorter(tableViewerColumn) {
							@Override
							protected Object getValue(Object o) {
								// TODO remove this method, if your EditingSupport returns value
								return ((Curso)o).getDescCurso();
							}
						};

						TableColumn tblclmnNombre = tableViewerColumn.getColumn();
						tblclmnNombre.setWidth(200);  
						tblclmnNombre.setText("Nombre");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						new TableViewerColumnSorter(tableViewerColumn) {
							@Override
							protected Object getValue(Object o) {
								// TODO remove this method, if your EditingSupport returns value
								return ((Curso)o).getMateria().getDescMateria();
							}
						};
						TableColumn tblclmnMateria = tableViewerColumn.getColumn();
						tblclmnMateria.setWidth(150);
						tblclmnMateria.setText("Materia");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						new TableViewerColumnSorter(tableViewerColumn) {
							@Override
							protected Object getValue(Object o) {
								// TODO remove this method, if your EditingSupport returns value
								return ((Curso) o).getFInicio();
							}
						};
						TableColumn tblclmnFDeste = tableViewerColumn.getColumn();
						tblclmnFDeste.setWidth(80);
						tblclmnFDeste.setText("F. Desde");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						new TableViewerColumnSorter(tableViewerColumn) {
							@Override
							protected Object getValue(Object o) {
								// TODO remove this method, if your EditingSupport returns value
								return ((Curso) o).getFFin();
							}
						};
						TableColumn tblclmnFHasta = tableViewerColumn.getColumn();
						tblclmnFHasta.setWidth(80);
						tblclmnFHasta.setText("F. Hasta");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						new TableViewerColumnSorter(tableViewerColumn) {
							@Override
							protected Object getValue(Object o) {
								// TODO remove this method, if your EditingSupport returns value
								return ((Curso)o).getProfesor().getNombre();
							}
						};
						TableColumn tblclmnProfesor = tableViewerColumn.getColumn();
						tblclmnProfesor.setWidth(220);
						tblclmnProfesor.setText("Profesor");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						new TableViewerColumnSorter(tableViewerColumn) {
							@Override
							protected Object getValue(Object o) {
								// TODO remove this method, if your EditingSupport returns value
								return ((Alumno)o).getDireccion();
							}
						};
						TableColumn tblclmnDireccion = tableViewerColumn.getColumn();
						tblclmnDireccion.setWidth(50);
						tblclmnDireccion.setText("Check");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						TableColumn tblclmnBaja = tableViewerColumn.getColumn();
						tblclmnBaja.setResizable(false);
						tblclmnBaja.setWidth(0);
						tblclmnBaja.setText("Baja");
						
					}
					
					    filtroActivas = new ViewerFilter(){
						public boolean select (Viewer visor, Object objetoPadre, Object objeto){
							
/*							if (((Curso)objeto).getBaja().equals("S") )
								return false;
							else
*/								return true;
						}
					};
					
					//table.setColumnOrder(new int[] {1});
				}
			}
			sashForm.setWeights(new int[] {141, 690});
		}

		createActions();
		initializeToolBar();
		initializeMenu();
		cargarListaCursos();
	}

	protected void bajaCurso() {

		int idxSelect;
		if ((idxSelect = table.getSelectionIndex())== -1){
			// Mensaje de error
			GestorErrores.mensajeTexto("No hay nada seleccionado", NIVEL_INFO);
			
	       }
		else{
			MessageBox messageBox = new MessageBox(getSite().getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
	        messageBox.setMessage("¿Estás seguro de que quieres dar de baja este curso?");
	        messageBox.setText("Baja de un curso");
	        int response = messageBox.open();
	        if (response == SWT.YES){
				TableItem select = table.getItem(idxSelect);
				int claveCurso = new Integer(select.getText(0)).intValue();
				try {
					Curso al = cursoHome.findById(claveCurso);
//					al.setBaja("S");
					cursoHome.persist(al);
					cargarListaCursos();
				}catch (Exception e){
					GestorErrores.mensajeTexto("Error dando de baja el curso:\n\n"+e.getMessage(), IConstantes.NIVEL_ERROR);
					e.printStackTrace();
				}
				
	        }
		}
		

	}

	private void cargarListaCursos() {
		// TODO Auto-generated method stub
		ViewerSupport.bind(tableViewer, 
		                   new WritableList(cursoHome.listarTodos(), Curso.class), 
		                   BeanProperties.values(Curso.class, new String[] {
				                          "idCurso", "descCurso","materia.descMateria",
				                          "FInicio","FFin","profesor.nombre","maxAlumnos","aula.descAula"}));	
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars()
				.getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars()
				.getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}
	
	private void abrirDetalle( String opcion){
		int idxSelect;
		if ((idxSelect = table.getSelectionIndex())== -1){
			// Mensaje de error
			GestorErrores.mensajeTexto("No hay nada seleccionado", NIVEL_INFO);
			
	       }
		else{
			TableItem select = table.getItem(idxSelect);
			Integer claveCurso = new Integer(select.getText(0));
			DetalleCurso dlg = new DetalleCurso(getSite().getShell());
			dlg.setClaveCurso(claveCurso.intValue());

			dlg.seleccionarPanel(opcion);
			dlg.setTipoOperacion(TIPOOPERMODIFICACION);
			dlg.open();
			cargarListaCursos();
		}

	}

	
	private void abrirBuscar(){
				
		alumnos = null;
		BuscarDlg dlg = new BuscarDlg(getSite().getShell());
		dlg.setTipoBusqueda(BuscarLista.BUSCAR_ALUMO);
		
		dlg.open();
		
		alumnos = dlg.getEncontrados();
		
		hacerFiltro();
	}
	
	private void filtroRapido(){
		
		alumnos = null;
		alumnos = BuscarLista.buscarNombreApellido(BuscarLista.BUSCAR_ALUMO, txFiltroRapido.getText());
		
		hacerFiltro();
	}
	
	private void hacerFiltro(){
		if (alumnos != null){
		    filtroBuscar = new ViewerFilter(){
			public boolean select (Viewer visor, Object objetoPadre, Object objeto){
				int codigo = ((Alumno)objeto).getIdAlumno();
				Iterator it = alumnos.iterator();
				boolean encontrado = false;
				
				while (it.hasNext()){
					Alumno mat = (Alumno) it.next();
					if (mat.getIdAlumno()== codigo){
						encontrado=true;
						break;
					}
				}
		
				return encontrado;
				}
		    };
		
			tableViewer.addFilter(filtroBuscar);
		}
		
	}
	
}
