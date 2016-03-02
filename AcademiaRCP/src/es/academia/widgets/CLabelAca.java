package es.academia.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchPage;

public class CLabelAca extends CLabel {

	public CLabelAca(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
		this.addMouseTrackListener(new MouseTrackAdapter() {
			Cursor cursorMano ;
			Cursor cursorFlecha;
			public void mouseEnter(MouseEvent e) {
				((CLabel)e.getSource()).setForeground(e.display.getSystemColor(SWT.COLOR_BLUE));
				cursorMano = new Cursor(e.display, SWT.CURSOR_HAND);
				((CLabel)e.getSource()).setCursor(cursorMano);
				
			}
			public void mouseExit(MouseEvent e) {
				cursorFlecha = new Cursor(e.display, SWT.CURSOR_ARROW);
				((CLabel)e.getSource()).setForeground(e.display.getSystemColor(SWT.COLOR_BLACK));
				((CLabel)e.getSource()).setCursor(cursorFlecha);
			}
			public void dispose(){
				cursorMano.dispose();
				cursorFlecha.dispose();
			}
		});
/*		this.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				int instanceNum;
				((Widget)e.getSource())..getActivePage().showView(viewId, Integer.toString(instanceNum++), IWorkbenchPage.VIEW_ACTIVATE);
				
			}
		});
*/
	}

}




	

