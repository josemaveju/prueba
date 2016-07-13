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
import es.academia.modelo.Materia;
import es.academia.modelo.MateriaHome;
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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;

public class VistaMaterias extends ViewPart implements IConstantes{

	private static final Logger log = ACALog.getLogger(VistaMaterias.class);
	
	public static final String ID = "es.academia.vistas.vistaMaterias"; //$NON-NLS-1$
	private Table table;
	private MateriaHome materiaHome= new MateriaHome();
	private TableViewer tableViewer;
	private ViewerFilter filtroActivas;
	private List<Materia> materia;

	private ViewerFilter filtroBuscar;
	private CLabel lblNewLabel;
	private Text txFiltroRapido;


	public VistaMaterias() {
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
					xpndtmCursos.setText("Materias");
					{
						Composite composite = new Composite(expandBar, SWT.NONE);
						composite.setBackground(SWTResourceManager.getColor(176, 196, 222));
						xpndtmCursos.setControl(composite);
						composite.setLayout(new RowLayout(SWT.VERTICAL));
						{
							CLabelAca lblNuevaMateria = new CLabelAca(composite, SWT.NONE);
							lblNuevaMateria.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseUp(MouseEvent e) {
									Materia materia = new Materia();
//									DetalleAlumno dlg = new DetalleAlumno(getSite().getShell());
//									dlg.setMateria(materia);
//									dlg.seleccionarPanel(PANELGENERAL);
//									dlg.setTipoOperacion(TIPOOPERALTA);
//									dlg.open();

								}
							});
							lblNuevaMateria.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/Add_alumno_16.png"));
							lblNuevaMateria.setText("Nueva materia");
						}
						{
							CLabelAca lblDarDeBaja = new CLabelAca(composite, SWT.NONE);
							lblDarDeBaja.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseUp(MouseEvent e) {
									bajaAlumno();
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
					{
						Composite composite = new Composite(expandBar, SWT.NONE);
						composite.setBackground(SWTResourceManager.getColor(176, 196, 222));
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
				}
				{
					ExpandItem xpndtmRecibos = new ExpandItem(expandBar, SWT.NONE);
					xpndtmRecibos.setExpanded(true);
					xpndtmRecibos.setText("Cursos");
					{
						Composite composite = new Composite(expandBar, SWT.NONE);
						composite.setBackground(SWTResourceManager.getColor(176, 196, 222));
						xpndtmRecibos.setControl(composite);
						composite.setLayout(new RowLayout(SWT.VERTICAL));
						{
							CLabelAca lblVerCursos = new CLabelAca(composite, SWT.NONE);
							lblVerCursos.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseUp(MouseEvent e) {
									abrirDetalle(PANELRECIBOS);
								}
							});
							lblVerCursos.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/recibos.png"));
							lblVerCursos.setText("Ver cursos");
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
					Composite composite_Cabecera = new Composite(composite, SWT.NO_FOCUS);
					composite_Cabecera.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
					composite_Cabecera.setLayoutData(BorderLayout.NORTH);
					composite_Cabecera.setLayout(new RowLayout(SWT.HORIZONTAL));
					{
						lblNewLabel = new CLabel(composite_Cabecera, SWT.NONE);
						lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
						lblNewLabel.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/libros_48.png"));
						lblNewLabel.setText("");
					}
					{
						CLabel lblListaDeAlumnos = new CLabel(composite_Cabecera, SWT.NONE);
						lblListaDeAlumnos.setBackgroundMode(SWT.INHERIT_FORCE);
						lblListaDeAlumnos.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
						lblListaDeAlumnos.setLeftMargin(0);
						lblListaDeAlumnos.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD | SWT.ITALIC));
						lblListaDeAlumnos.setImage(null);
						lblListaDeAlumnos.setText("Mantenimiento de materias");
					}
				}
				{
					Composite composite_1 = new Composite(composite, SWT.NONE);
					composite_1.setLayoutData(BorderLayout.CENTER);
					composite_1.setLayout(new BorderLayout(2, 2));
					{
						Composite Barrafiltros = new Composite(composite_1, SWT.BORDER);
						Barrafiltros.setLayoutData(BorderLayout.NORTH);
						Barrafiltros.setBackground(SWTResourceManager.getColor(211, 211, 211));
						Barrafiltros.setLayout(null);
						{
							Composite compositeRadio = new Composite(Barrafiltros, SWT.NONE);
							compositeRadio.setBounds(10, 2, 249, 22);
							compositeRadio.setBackground(SWTResourceManager.getColor(211, 211, 211));
							compositeRadio.setLayout(new RowLayout(SWT.HORIZONTAL));
							{
								Button btnMostrarTodos = new Button(compositeRadio, SWT.RADIO);
								btnMostrarTodos.setBackground(SWTResourceManager.getColor(211, 211, 211));
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
								btnMostrarSloLos.setBackground(SWTResourceManager.getColor(211, 211, 211));
								btnMostrarSloLos.addSelectionListener(new SelectionAdapter() {
									@Override
									public void widgetSelected(SelectionEvent e) {
										tableViewer.addFilter(filtroActivas);
									}
								});
								btnMostrarSloLos.setText("S\u00F3lo activas");
							}
						}
						
						txFiltroRapido = new Text(Barrafiltros, SWT.BORDER | SWT.SEARCH);
						txFiltroRapido.setBounds(292, 2, 123, 21);
						
						Button button = new Button(Barrafiltros, SWT.NONE);
						button.setLocation(416, 2);
						button.setSize(21, 21);
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
					}
					tableViewer = new TableViewer(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
					table = tableViewer.getTable();
					table.setBackground(SWTResourceManager.getColor(240, 248, 255));
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseDoubleClick(MouseEvent e) {
							abrirDetalle(PANELGENERAL);
						}
					});
					table.setHeaderVisible(true);
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.LEFT);
						new TableViewerColumnSorter(tableViewerColumn) {
							@Override
							protected Object getValue(Object o) {
								// TODO remove this method, if your EditingSupport returns value
								return new Integer(((Materia) o).getIdMateria());
							}
						};
						
						TableColumn tblclmnCdigo = tableViewerColumn.getColumn();
						tblclmnCdigo.setWidth(85);
						tblclmnCdigo.setText("C\u00F3digo");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						new TableViewerColumnSorter(tableViewerColumn) {
							@Override
							protected Object getValue(Object o) {
								// TODO remove this method, if your EditingSupport returns value
								return ((Materia)o).getDescMateria();
							}
						};

						TableColumn tblclmnNombre = tableViewerColumn.getColumn();
						tblclmnNombre.setWidth(424);  
						tblclmnNombre.setText("Nombre");
					}
					{
						Composite BarraTotales = new Composite(composite_1, SWT.BORDER);
						BarraTotales.setLayoutData(BorderLayout.SOUTH);
						BarraTotales.setLayout(null);
						
						Label lblTotalCursos = new Label(BarraTotales, SWT.NONE);
						lblTotalCursos.setBounds(10, 3, 79, 21);
						lblTotalCursos.setText("Total materias:");
						
						Label lblCursos = new Label(BarraTotales, SWT.NONE);
						lblCursos.setBounds(95, 3, 55, 15);
					}
					sashForm.setWeights(new int[] {133, 459});
					{
					}
				}
				{
					
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
		}

		createActions();
		initializeToolBar();
		initializeMenu();
		cargarListaCursos();
	}

	protected void bajaAlumno() {
		// TODO Auto-generated method stub
		int idxSelect;
		if ((idxSelect = table.getSelectionIndex())== -1){
			// Mensaje de error
			GestorErrores.mensajeTexto("No hay nada seleccionado", NIVEL_INFO);
			
	       }
		else{
			MessageBox messageBox = new MessageBox(getSite().getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
	        messageBox.setMessage("¿Estás seguro de que quieres dar de baja esta materia?");
	        messageBox.setText("Baja de un curso");
	        int response = messageBox.open();
	        if (response == SWT.YES){
				TableItem select = table.getItem(idxSelect);
				int claveMateria = new Integer(select.getText(0)).intValue();
				try {
					Materia al = materiaHome.findById(claveMateria);
				//	al.setBaja("S");
					materiaHome.persist(al);
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
		                   new WritableList(materiaHome.listarTodos(), Materia.class), 
		                   BeanProperties.values(Materia.class, new String[] {
				                          "idMateria", "descMateria"}));	
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
			Integer claveAlumno = new Integer(select.getText(0));
			DetalleAlumno dlg = new DetalleAlumno(getSite().getShell());
			dlg.setClaveAlumno(claveAlumno.intValue());

			dlg.seleccionarPanel(opcion);
			dlg.setTipoOperacion(TIPOOPERMODIFICACION);
			dlg.open();
			cargarListaCursos();
		}

	}

	
	private void abrirBuscar(){
				
		materia = null;
		BuscarDlg dlg = new BuscarDlg(getSite().getShell());
		dlg.setTipoBusqueda(BuscarLista.BUSCAR_MATERIA);
		
		dlg.open();
		
		materia = dlg.getEncontrados();
		
		hacerFiltro();
	}
	
	private void filtroRapido(){
		
		materia = null;
		materia = BuscarLista.buscarNombreApellido(BuscarLista.BUSCAR_MATERIA, txFiltroRapido.getText());
		
		hacerFiltro();
	}
	
	private void hacerFiltro(){
		if (materia != null){
		    filtroBuscar = new ViewerFilter(){
			public boolean select (Viewer visor, Object objetoPadre, Object objeto){
				int codigo = ((Materia)objeto).getIdMateria();
				Iterator it = materia.iterator();
				boolean encontrado = false;
				
				while (it.hasNext()){
					Materia mat = (Materia) it.next();
					if (mat.getIdMateria()== codigo){
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
