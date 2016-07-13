package es.academia.vistas;

import java.util.Date;
import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.part.ViewPart;

import es.academia.modelo.Profesor;
import es.academia.modelo.ProfesorHome;





import java.util.Iterator;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

import es.academia.dialogos.BuscarDlg;
import es.academia.dialogos.DetalleAlumno;
import es.academia.dialogos.DetalleProfesor;
import es.academia.dialogos.SendMail;
import es.academia.modelo.Alumno;
import es.academia.modelo.AlumnoHome;
import es.academia.utils.BuscarLista;
import es.academia.utils.GestorErrores;
import es.academia.utils.IConstantes;
import es.academia.widgets.CLabelAca;

import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.wb.swt.TableViewerColumnSorter;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class VistaProfesores extends ViewPart implements IConstantes{

	public static final String ID = "es.academia.vistas.VistaProfesores"; //$NON-NLS-1$

	private Table table;
	private ProfesorHome profesorHome= new ProfesorHome();
	private TableViewer tableViewer;
	private ViewerFilter filtroActivas;
	private List<Profesor> profesores;

	private ViewerFilter filtroBuscar;
	private Text txFiltroRapido;

	public VistaProfesores() {
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
					ExpandItem xpndtmProfesores = new ExpandItem(expandBar, SWT.NONE);
					xpndtmProfesores.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/graduated2_24.png"));
					xpndtmProfesores.setExpanded(true);
					xpndtmProfesores.setText("Profesores");
					{
						Composite composite = new Composite(expandBar, SWT.NONE);
						xpndtmProfesores.setControl(composite);
						composite.setLayout(new RowLayout(SWT.VERTICAL));
						{
							CLabelAca lblNuevoProfesor = new CLabelAca(composite, SWT.NONE);
							lblNuevoProfesor.addMouseListener(new MouseAdapter() {
//  Alta de un profesor								
								public void mouseUp(MouseEvent e) {
									Profesor profesor = new Profesor();
									DetalleProfesor dlg = new DetalleProfesor(getSite().getShell());
									dlg.setProfesor(profesor);
									dlg.seleccionarPanel(PANELGENERAL);
									dlg.setTipoOperacion(TIPOOPERALTA);
									dlg.open();
									cargarListaProfesores();

								}
							});
							lblNuevoProfesor.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/graduated2_add_16.png"));
							lblNuevoProfesor.setText("Nuevo profesor");
						}
						{
							CLabelAca lblDarDeBaja = new CLabelAca(composite, SWT.NONE);
							lblDarDeBaja.addMouseListener(new MouseAdapter() {
//  Baja de un profesor								

								public void mouseUp(MouseEvent e) {
									bajaProfesor();
								}
							});
							lblDarDeBaja.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/graduated2_borrar_16.png"));
							lblDarDeBaja.setText("Dar de baja");
						}
						{
							CLabelAca lblDetalle = new CLabelAca(composite, SWT.NONE);
							lblDetalle.addMouseListener(new MouseAdapter() {
//  Detalle de un profesor								
								public void mouseUp(MouseEvent e) {
									abrirDetalle(PANELGENERAL);
								}
							});
							lblDetalle.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/matricula.png"));
							lblDetalle.setText("Detalle");
						}
						{
							CLabelAca lblBuscarProfesor = new CLabelAca(composite, SWT.NONE);
							lblBuscarProfesor.addMouseListener(new MouseAdapter() {
// Buscar un profesor								
								public void mouseUp(MouseEvent e) {
									 abrirBuscar();
								}
							});
							lblBuscarProfesor.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/buscar_16.png"));
							lblBuscarProfesor.setText("Buscar profesor");
						}
						{
							CLabelAca lblEnviarEmail = new CLabelAca(composite, SWT.NONE);
							lblEnviarEmail.addMouseListener(new MouseAdapter() {
//  Enviar e-mail								
								public void mouseUp(MouseEvent e) {
									int idxSelect;
									if ((idxSelect = table.getSelectionIndex())== -1){
										// Mensaje de error
										GestorErrores.mensajeTexto("No hay nada seleccionado", NIVEL_INFO);
										
								       }
									else{
										TableItem select = table.getItem(idxSelect);
										Integer claveProfesor = new Integer(select.getText(0));
										ProfesorHome alh = new ProfesorHome();
										Profesor profesor = alh.findById(claveProfesor);
										
										SendMail dlg = new SendMail(getSite().getShell());
//**********   **						dlg.setAlumno(profesor);
										dlg.open();
									}

								}
							});
							lblEnviarEmail.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/mail.gif"));
							lblEnviarEmail.setText("Enviar eMail");
						}
					}
					xpndtmProfesores.setHeight(xpndtmProfesores.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
				}
				{
					ExpandItem xpndtmCursos = new ExpandItem(expandBar, SWT.NONE);
					xpndtmCursos.setExpanded(true);
					xpndtmCursos.setText("Cursos");
					{
						Composite composite = new Composite(expandBar, SWT.NONE);
						xpndtmCursos.setControl(composite);
						composite.setLayout(new RowLayout(SWT.VERTICAL));
						{
							CLabelAca lblVerCursos = new CLabelAca(composite, SWT.NONE);
							lblVerCursos.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseUp(MouseEvent e) {
									abrirDetalle(PANELCURSOS);
								}
							});
							lblVerCursos.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/libros_16.png"));
							lblVerCursos.setText("Ver cursos");
						}
						{
							CLabelAca lblMatricular = new CLabelAca(composite, SWT.NONE);
							lblMatricular.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/matriculas.png"));
							lblMatricular.setText("Matricular");
						}
					}
					xpndtmCursos.setHeight(xpndtmCursos.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
				}
				{
					ExpandItem xpndtmRecibos = new ExpandItem(expandBar, SWT.NONE);
					xpndtmRecibos.setExpanded(true);
					xpndtmRecibos.setText("Recibos");
					{
						Composite composite = new Composite(expandBar, SWT.NONE);
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
							lblVerRecibos.setText("Ver facturas");
						}
						{
							CLabelAca lblGenerarRecibos = new CLabelAca(composite, SWT.NONE);
							lblGenerarRecibos.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/Dinero_16.png"));
							lblGenerarRecibos.setText("Generar factura");
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
					Composite composite_1 = new Composite(composite, SWT.NONE);
					composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
					composite_1.setLayoutData(BorderLayout.NORTH);
					composite_1.setLayout(new FormLayout());
					{
						CLabel lblNewLabel = new CLabel(composite_1, SWT.NONE);
						lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
						FormData fd_lblNewLabel = new FormData();
						fd_lblNewLabel.top = new FormAttachment(0, 3);
						fd_lblNewLabel.left = new FormAttachment(0, 8);
						lblNewLabel.setLayoutData(fd_lblNewLabel);
						lblNewLabel.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/graduated2_72.png"));
						lblNewLabel.setText("");
					}
					{
						Composite composite_2 = new Composite(composite_1, SWT.NONE);
						composite_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
						FormData fd_composite_2 = new FormData();
						fd_composite_2.top = new FormAttachment(0, 3);
						fd_composite_2.left = new FormAttachment(0, 89);
						composite_2.setLayoutData(fd_composite_2);
						RowLayout rl_composite_2 = new RowLayout(SWT.VERTICAL);
						rl_composite_2.marginLeft = 15;
						rl_composite_2.spacing = 8;
						rl_composite_2.marginTop = 5;
						composite_2.setLayout(rl_composite_2);
						{
							CLabel lblListaDeProfesores = new CLabel(composite_2, SWT.NONE);
							lblListaDeProfesores.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
							lblListaDeProfesores.setLeftMargin(0);
							lblListaDeProfesores.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD | SWT.ITALIC));
							lblListaDeProfesores.setImage(null);
							lblListaDeProfesores.setText("Lista de profesores");
						}
						{
							Composite composite_3 = new Composite(composite_2, SWT.NONE);
							composite_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
							composite_3.setLayout(new RowLayout(SWT.HORIZONTAL));
							{
								Button btnMostrarTodos = new Button(composite_3, SWT.RADIO);
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
								Button btnMostrarSloLos = new Button(composite_3, SWT.RADIO);
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
						Composite composite_2 = new Composite(composite_1, SWT.NONE);
						FormData fd_composite_2 = new FormData();
						fd_composite_2.right = new FormAttachment(100, -5);
						fd_composite_2.bottom = new FormAttachment(0, 81);
						fd_composite_2.top = new FormAttachment(0);
						fd_composite_2.left = new FormAttachment(100, -217);
						composite_2.setLayoutData(fd_composite_2);
						composite_2.setLayout(null);
						composite_2.setBackgroundMode(SWT.INHERIT_FORCE);
						composite_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
						{
							txFiltroRapido = new Text(composite_2, SWT.BORDER | SWT.SEARCH);
							txFiltroRapido.setBounds(33, 47, 146, 21);
						}
						{
							Button button = new Button(composite_2, SWT.NONE);
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
				}
				{
					Composite composite_1 = new Composite(composite, SWT.NONE);
					composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
					composite_1.setLayoutData(BorderLayout.SOUTH);
					RowLayout rl_composite_1 = new RowLayout(SWT.HORIZONTAL);
					rl_composite_1.marginBottom = 11;
					rl_composite_1.marginTop = 11;
					composite_1.setLayout(rl_composite_1);
				}
				{
					tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
					table = tableViewer.getTable();
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseDoubleClick(MouseEvent e) {
							abrirDetalle(PANELGENERAL);
						}
					});
					table.setHeaderVisible(true);
					table.setLayoutData(BorderLayout.CENTER);
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						new TableViewerColumnSorter(tableViewerColumn) {
							@Override
							protected Object getValue(Object o) {
								
								return new Integer(((Profesor) o).getIdProfesor());
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
								
								return ((Profesor)o).getNombre();
							}
						};

						TableColumn tblclmnNombre = tableViewerColumn.getColumn();
						tblclmnNombre.setWidth(130);
						tblclmnNombre.setText("Nombre");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						new TableViewerColumnSorter(tableViewerColumn) {
							@Override
							protected Object getValue(Object o) {
					
								return ((Profesor)o).getApellidos();
							}
						};
						TableColumn tblclmnApellidos = tableViewerColumn.getColumn();
						tblclmnApellidos.setWidth(175);
						tblclmnApellidos.setText("Apellidos");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						new TableViewerColumnSorter(tableViewerColumn) {
							@Override
							protected Object getValue(Object o) {
								
								return new Integer(((Profesor) o).getTelMovil());
							}
						};
						TableColumn tblclmnTelMovil = tableViewerColumn.getColumn();
						tblclmnTelMovil.setWidth(80);
						tblclmnTelMovil.setText("Tel m\u00F3vil");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						new TableViewerColumnSorter(tableViewerColumn) {
							@Override
							protected Object getValue(Object o) {
								
								return new Integer(((Profesor) o).getTelFijo());
							}
						};
						TableColumn tblclmnTelFijo = tableViewerColumn.getColumn();
						tblclmnTelFijo.setWidth(80);
						tblclmnTelFijo.setText("Tel. fijo");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						new TableViewerColumnSorter(tableViewerColumn) {
							@Override
							protected Object getValue(Object o) {
							
								return ((Profesor)o).getEmail();
							}
						};
						TableColumn tblclmnEmail = tableViewerColumn.getColumn();
						tblclmnEmail.setWidth(220);
						tblclmnEmail.setText("e-mail");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						new TableViewerColumnSorter(tableViewerColumn) {
							@Override
							protected Object getValue(Object o) {
								
								return ((Profesor)o).getDireccion();
							}
						};
						TableColumn tblclmnDireccion = tableViewerColumn.getColumn();
						tblclmnDireccion.setWidth(220);
						tblclmnDireccion.setText("Direcci\u00F3n");
					}
					sashForm.setWeights(new int[] {140, 550});
					
					    filtroActivas = new ViewerFilter(){
						public boolean select (Viewer visor, Object objetoPadre, Object objeto){
							
							if (((Profesor)objeto).getFBaja() != null )
								return false;
							else
								return true;
						}
					};
					
					//table.setColumnOrder(new int[] {1});
				}
			}
		}

		createActions();
		initializeToolBar();
		initializeMenu();
		cargarListaProfesores();
	}

	protected void bajaProfesor() {

		int idxSelect;
		if ((idxSelect = table.getSelectionIndex())== -1){
			// Mensaje de error
			GestorErrores.mensajeTexto("No hay nada seleccionado", NIVEL_INFO);
			
	       }
		else{
			MessageBox messageBox = new MessageBox(getSite().getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
	        messageBox.setMessage("¿Estás seguro de que quieres dar de baja este profesor?");
	        messageBox.setText("Baja de profesor");
	        int response = messageBox.open();
	        if (response == SWT.YES){
				TableItem select = table.getItem(idxSelect);
				int claveProfesor = new Integer(select.getText(0)).intValue();
				try {
					Profesor pr = profesorHome.findById(claveProfesor);
					pr.setFBaja(new Date());
					profesorHome.persist(pr);
					cargarListaProfesores();
				}catch (Exception e){
					GestorErrores.mensajeTexto("Error dando de baja al alumno:\n\n"+e.getMessage(), IConstantes.NIVEL_ERROR);
					e.printStackTrace();
				}
	        }
		}
	}

	private void cargarListaProfesores() {

		ViewerSupport.bind(tableViewer, 
		                   new WritableList(profesorHome.listarTodos(), Profesor.class), 
		                   BeanProperties.values(Profesor.class, new String[] {
				                          "idProfesor", "nombre","apellidos","telMovil",
				                          "telFijo","email","direccion"}));	
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
			Integer claveProfesor = new Integer(select.getText(0));
			DetalleProfesor dlg = new DetalleProfesor(getSite().getShell());
			dlg.setClaveProfesor(claveProfesor.intValue());

			dlg.seleccionarPanel(opcion);
			dlg.setTipoOperacion(TIPOOPERMODIFICACION);
			dlg.open();
			cargarListaProfesores();
		}

	}

	
	private void abrirBuscar(){
				
		profesores = null;
		BuscarDlg dlg = new BuscarDlg(getSite().getShell());
		dlg.setTipoBusqueda(BuscarLista.BUSCAR_PROFESOR);
		
		dlg.open();
		
		profesores = dlg.getEncontrados();
		
		hacerFiltro();
		
	}
	
	private void filtroRapido(){
		
		profesores = null;
		profesores = BuscarLista.buscarNombreApellido(BuscarLista.BUSCAR_PROFESOR, txFiltroRapido.getText());
		
		hacerFiltro();
	}
	
	private void hacerFiltro(){
		if (profesores != null){
		    filtroBuscar = new ViewerFilter(){
			public boolean select (Viewer visor, Object objetoPadre, Object objeto){
				int codigo = ((Profesor)objeto).getIdProfesor();
				Iterator it = profesores.iterator();
				boolean encontrado = false;
				
				while (it.hasNext()){
					Profesor mat = (Profesor) it.next();
					if (mat.getIdProfesor()== codigo){
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
