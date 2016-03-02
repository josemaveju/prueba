package es.academia;

import org.apache.log4j.Logger;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import es.academia.modelo.HibernateUtil;
import es.academia.utils.GestorErrores;
import es.academia.utils.IConstantes;

/**
 * This workbench advisor creates the window advisor, and specifies
 * the perspective id for the initial window.
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor implements IConstantes{


	
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

	public String getInitialWindowPerspectiveId() {
		return Perspective.ID;
	} 

	// Se llama después de la inicialización pero antes de que ninguna ventana se abra.
	public void postStartup (){
          // preStart
		
		if ( ! conexionDB()){
			GestorErrores.mensajeTexto("Error conectando a base de datos.", NIVEL_ERROR);
			PlatformUI.getWorkbench().close() ;
		}
	}

	
	private boolean conexionDB(){

		boolean resultado = true;
		
		// HibernateUtil tiene una sección estática, así que basta 
		// con instanciar el objeto para que arranque la Base de datos. 
		try{
			HibernateUtil hu = new HibernateUtil();
		}catch (ExceptionInInitializerError e){
			resultado = false;
		}
		
		return resultado;
		
	}


}
