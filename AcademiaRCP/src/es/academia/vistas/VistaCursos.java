package es.academia.vistas;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
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

import es.academia.widgets.CLabelAca;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.wb.swt.TableViewerColumnSorter;
import org.eclipse.jface.viewers.Viewer;

public class VistaCursos extends ViewPart {

	public static final String ID = "es.academia.vistas.vistaAlumnos"; //$NON-NLS-1$
	private Table table;

	public VistaCursos() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		{
			SashForm sashForm = new SashForm(parent, SWT.NONE);
//			sashForm.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
//			sashForm.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			{
				ExpandBar expandBar = new ExpandBar(sashForm, SWT.BORDER);
//				expandBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
//				expandBar.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
				{
					ExpandItem xpndtmAlumnos = new ExpandItem(expandBar, SWT.NONE);
					xpndtmAlumnos.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/alumno grande.png"));
					xpndtmAlumnos.setExpanded(true);
					xpndtmAlumnos.setText("Alumnos");
					{
						Composite composite = new Composite(expandBar, SWT.NONE);
						xpndtmAlumnos.setControl(composite);
						composite.setLayout(new RowLayout(SWT.VERTICAL));
						{
							CLabelAca lblNuevoAlumno = new CLabelAca(composite, SWT.NONE);
							lblNuevoAlumno.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/1405030026_Add-Male-User.png"));
							lblNuevoAlumno.setText("Nuevo alumno");
						}
						{
							CLabelAca lblDarDeBaja = new CLabelAca(composite, SWT.NONE);
							lblDarDeBaja.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/1405030018_Remove-Male-User.png"));
							lblDarDeBaja.setText("Dar de baja");
						}
						{
							CLabelAca lblDetalle = new CLabelAca(composite, SWT.NONE);
							lblDetalle.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/1405029846_cv.png"));
							lblDetalle.setText("Detalle");
						}
						{
							CLabelAca lblBuscarAlumno = new CLabelAca(composite, SWT.NONE);
							lblBuscarAlumno.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/1405029607_folder-search.png"));
							lblBuscarAlumno.setText("Buscar alumno");
						}
						{
							CLabelAca lblEnviarEmail = new CLabelAca(composite, SWT.NONE);
							lblEnviarEmail.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/sample3.gif"));
							lblEnviarEmail.setText("Enviar eMail");
						}
					}
					xpndtmAlumnos.setHeight(xpndtmAlumnos.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
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
							lblVerCursos.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/cursos.png"));
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
							lblVerRecibos.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/recibos.png"));
							lblVerRecibos.setText("Ver recibos");
						}
						{
							CLabelAca lblGenerarRecibos = new CLabelAca(composite, SWT.NONE);
							lblGenerarRecibos.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/1405029794_06.png"));
							lblGenerarRecibos.setText("Generar recibos");
						}
					}
					xpndtmRecibos.setHeight(xpndtmRecibos.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
				}
			}
			{
				Composite composite = new Composite(sashForm, SWT.NONE);
				composite.setLayout(new BorderLayout(0, 0));
				{
					Composite composite_1 = new Composite(composite, SWT.NONE);
					composite_1.setLayoutData(BorderLayout.NORTH);
					composite_1.setLayout(new RowLayout(SWT.VERTICAL));
					{
						CLabel lblListaDeAlumnos = new CLabel(composite_1, SWT.NONE);
						lblListaDeAlumnos.setLeftMargin(20);
						lblListaDeAlumnos.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD | SWT.ITALIC));
						lblListaDeAlumnos.setImage(ResourceManager.getPluginImage("AcademiaRCP", "icons/alumno grande.png"));
						lblListaDeAlumnos.setText("Lista de alumnos");
					}
					{
						Composite composite_2 = new Composite(composite_1, SWT.NONE);
						RowLayout rl_composite_2 = new RowLayout(SWT.HORIZONTAL);
						rl_composite_2.marginLeft = 25;
						rl_composite_2.spacing = 8;
						rl_composite_2.marginTop = 15;
						composite_2.setLayout(rl_composite_2);
						{
							Button btnMostrarSloLos = new Button(composite_2, SWT.RADIO);
							btnMostrarSloLos.setText("Mostrar s\u00F3lo los activos");
						}
						{
							Button btnMostrarTodos = new Button(composite_2, SWT.RADIO);
							btnMostrarTodos.setText("Mostrar todos");
						}
					}
				}
				{
					Composite composite_1 = new Composite(composite, SWT.NONE);
					composite_1.setLayoutData(BorderLayout.SOUTH);
					RowLayout rl_composite_1 = new RowLayout(SWT.HORIZONTAL);
					composite_1.setLayout(rl_composite_1);
					{
						Button btnAceptar = new Button(composite_1, SWT.NONE);
						btnAceptar.setText("Aceptar");
					}
					{
						Button btnCancelar = new Button(composite_1, SWT.NONE);
						btnCancelar.setText("Cancelar");
					}
				}
				{
					TableViewer tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
					table = tableViewer.getTable();
					table.setHeaderVisible(true);
					table.setLayoutData(BorderLayout.CENTER);
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						TableColumn tblclmnCdigo = tableViewerColumn.getColumn();
						tblclmnCdigo.setWidth(60);
						tblclmnCdigo.setText("C\u00F3digo");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						TableColumn tblclmnNombre = tableViewerColumn.getColumn();
						tblclmnNombre.setWidth(130);
						tblclmnNombre.setText("Nombre");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						TableColumn tblclmnApellidos = tableViewerColumn.getColumn();
						tblclmnApellidos.setWidth(164);
						tblclmnApellidos.setText("Apellidos");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						TableColumn tblclmnTelMovil = tableViewerColumn.getColumn();
						tblclmnTelMovil.setWidth(80);
						tblclmnTelMovil.setText("Tel m\u00F3vil");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						TableColumn tblclmnTelFijo = tableViewerColumn.getColumn();
						tblclmnTelFijo.setWidth(80);
						tblclmnTelFijo.setText("Tel. fijo");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						TableColumn tblclmnEmail = tableViewerColumn.getColumn();
						tblclmnEmail.setWidth(220);
						tblclmnEmail.setText("e-mail");
					}
					{
						TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
						TableColumn tblclmnDireccion = tableViewerColumn.getColumn();
						tblclmnDireccion.setWidth(100);
						tblclmnDireccion.setText("Direcci\u00F3n");
					}
				}
			}
			sashForm.setWeights(new int[] {141, 450});
		}

		createActions();
		initializeToolBar();
		initializeMenu();
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

}
