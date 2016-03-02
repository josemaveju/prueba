package es.academia;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import es.academia.vistas.VistaAlumnos;

public class Perspective implements IPerspectiveFactory {

	/**
	 * The ID of the perspective as specified in the extension.
	 */
	public static final String ID = "AcademiaRCP.perspective";

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		
//		layout.addStandaloneView(NavigationView.ID,  false, IPageLayout.LEFT, 0.25f, editorArea);
		IFolderLayout folder = layout.createFolder("messages", IPageLayout.TOP, 1f, editorArea);
		folder.addPlaceholder(VistaAlumnos.ID + ":*");
		folder.addView(VistaAlumnos.ID);
		
//		layout.getViewLayout(NavigationView.ID).setCloseable(false);
	}
}
