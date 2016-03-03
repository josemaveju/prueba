package es.academia;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import es.academia.acciones.NuevoAlumnoAction;
import es.academia.acciones.OpenRecibosWizard;
import es.academia.acciones.OpenViewAction;
import es.academia.utils.IConstantes;
import es.academia.vistas.VistaAlumnos;
import es.academia.vistas.VistaCursos;
import es.academia.vistas.VistaProfesores;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of the
 * actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    // Actions - important to allocate these only in makeActions, and then use them
    // in the fill methods.  This ensures that the actions aren't recreated
    // when fillActionBars is called with FILL_PROXY.
    private IWorkbenchAction exitAction;
    private IWorkbenchAction aboutAction;
    private IWorkbenchAction newWindowAction;
    private OpenViewAction gestionAlumnos;
    private NuevoAlumnoAction nuevoAlumno;
    private OpenViewAction gestionProfesores;
    private OpenViewAction gestionCursos;
    private OpenViewAction gestionRecibos;
    private OpenViewAction gestionFacturas;
    private OpenViewAction gestionContabilidad;
    private OpenViewAction cobrarRecibo;
    private OpenViewAction gestionMaterias;
    
    private OpenRecibosWizard generarRecibos;
   
    private Action messagePopupAction;
    private IWorkbenchAction preferencias;

    

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }
    
    protected void makeActions(final IWorkbenchWindow window) {
        // Creates the actions and registers them.
        // Registering is needed to ensure that key bindings work.
        // The corresponding commands keybindings are defined in the plugin.xml file.
        // Registering also provides automatic disposal of the actions when
        // the window is closed.

        exitAction = ActionFactory.QUIT.create(window);
        register(exitAction);
        
        aboutAction = ActionFactory.ABOUT.create(window);
        register(aboutAction);
        
        preferencias = ActionFactory.PREFERENCES.create(window);
        register (preferencias);
        
//        newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
//        register(newWindowAction);
        
        gestionAlumnos = new OpenViewAction(window, "&Alumnos", VistaAlumnos.ID, "/icons/elementary_school_24.png");
        register(gestionAlumnos);
        
        nuevoAlumno = new NuevoAlumnoAction(window, "&Nuevo alumno");
        register(nuevoAlumno);
        
        gestionProfesores = new OpenViewAction(window, "&Profesores", VistaProfesores.ID, "/icons/graduated2_24.png");
        register(gestionProfesores);
        
        gestionCursos = new OpenViewAction(window, "&Cursos", VistaCursos.ID, "/icons/cursos.png");
        register(gestionCursos);
        
        gestionRecibos = new OpenViewAction(window, "&Recibos", VistaCursos.ID, "/icons/recibos_24.png");
        register(gestionRecibos);
       
        gestionFacturas = new OpenViewAction(window, "&Facturas emitidas", VistaCursos.ID, "/icons/factura_24.png");
        register(gestionFacturas);
        
        gestionContabilidad = new OpenViewAction(window, "&Gestión contable", VistaCursos.ID, "/icons/dinero_grafico_24.png");
        register(gestionContabilidad);
        
        gestionMaterias = new OpenViewAction(window, "&Materias", VistaCursos.ID, "/icons/libros_24.png");
        register(gestionMaterias);
 
        cobrarRecibo = new OpenViewAction(window, "&Materias", VistaCursos.ID, "/icons/payment-icon_16.png");
        register(cobrarRecibo);
        
        generarRecibos = new OpenRecibosWizard (window);
        register(generarRecibos);
    
    }
    
    protected void fillMenuBar(IMenuManager menuBar) {
        MenuManager fileMenu = new MenuManager("Gestión &Académica", IConstantes.MENU_ACADEMICO);
        MenuManager econMenu = new MenuManager("Gestión &Económica", IConstantes.MENU_ECONOMICO);
        MenuManager inforMenu = new MenuManager("&Informes", IConstantes.MENU_INFORMES);
       
        MenuManager utilMenu = new MenuManager("&Utilidades", IConstantes.MENU_UTILIDADES);
        MenuManager helpMenu = new MenuManager("A&yuda", IWorkbenchActionConstants.M_HELP);
        
        menuBar.add(fileMenu);
        menuBar.add(econMenu);
        menuBar.add(inforMenu);
        menuBar.add(utilMenu);
        // Add a group marker indicating where action set menus will appear.
 //       menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        menuBar.add(helpMenu);
        
        // Academico
        fileMenu.add(gestionAlumnos);
        fileMenu.add(nuevoAlumno);
        fileMenu.add(new Separator());
        fileMenu.add(gestionProfesores);
        fileMenu.add(new Separator());
        fileMenu.add(gestionCursos);
        fileMenu.add(new Separator());
        fileMenu.add(gestionMaterias);
        fileMenu.add(new Separator());
        fileMenu.add(exitAction);

        //Económico
        econMenu.add(gestionRecibos);
        econMenu.add(generarRecibos);
        econMenu.add(new Separator());      
        econMenu.add(gestionFacturas);
        econMenu.add(new Separator());      
        econMenu.add(gestionContabilidad);
     
     
        utilMenu.add(preferencias);
        // Help
        helpMenu.add(aboutAction);
    }
    
    protected void fillCoolBar(ICoolBarManager coolBar) {
        IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
        IToolBarManager toolbar2 = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
        coolBar.add(new ToolBarContributionItem(toolbar, "academia"));   
        toolbar.add(gestionAlumnos);
        toolbar.add(nuevoAlumno);
        toolbar.add(new Separator());
        toolbar.add(gestionProfesores);
        toolbar.add(new Separator());
        toolbar.add(gestionCursos);
        toolbar.add(new Separator());
        toolbar.add(gestionMaterias);
        toolbar.add(new Separator());
        coolBar.add(new ToolBarContributionItem(toolbar2, "economico"));          
        toolbar2.add(new Separator());

        toolbar2.add(gestionRecibos);
        toolbar2.add(generarRecibos);
        toolbar2.add(gestionFacturas);
        toolbar2.add(gestionContabilidad);
       
//        toolbar.add(messagePopupAction);
    }
}
