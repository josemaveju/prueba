package es.academia.propiedades;

import java.util.List;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

import es.academia.dialogos.DetalleAula;
import es.academia.dialogos.DetalleSerieRecibo;
import es.academia.modelo.Aula;
import es.academia.modelo.AulaHome;
import es.academia.modelo.Serierecibo;
import es.academia.modelo.SeriereciboHome;
import es.academia.utils.GestorErrores;
import es.academia.utils.IConstantes;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AulasPreferences extends PreferencePage implements
		IWorkbenchPreferencePage, IConstantes {
	private Table tablaAulas;
	private AulaHome srh = new AulaHome();
	private TableViewer tvAulas;
	private List<Aula> listaAulas;

	/**
	 * Create the preference page.
	 */
	public AulasPreferences() {
		setDescription("Configurar las aulas disponibles en la academia");
		setTitle("Configuración de aulas");
	}

	/**
	 * Create contents of the preference page.
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		noDefaultAndApplyButton();
		
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new FormLayout());
		
		Composite composite = new Composite(container, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(98);
		fd_composite.right = new FormAttachment(100);
		fd_composite.top = new FormAttachment(0);
		fd_composite.left = new FormAttachment(0);
		composite.setLayoutData(fd_composite);
		composite.setLayout(new FormLayout());
		
		Group group = new Group(composite, SWT.NONE);
		FormData fd_group = new FormData();
		fd_group.bottom = new FormAttachment(98);
		fd_group.right = new FormAttachment(100, 102, 0);
		fd_group.top = new FormAttachment(0, 10);
		fd_group.left = new FormAttachment(0, 10);
		group.setLayoutData(fd_group);
		group.setLayout(new FormLayout());
		
		tvAulas = new TableViewer(group, SWT.BORDER | SWT.FULL_SELECTION);
		tablaAulas = tvAulas.getTable();
		tablaAulas.setTouchEnabled(true);
		FormData fd_tablaAulas = new FormData();
		fd_tablaAulas.bottom = new FormAttachment(84);
		fd_tablaAulas.right = new FormAttachment(98);
		fd_tablaAulas.top = new FormAttachment(0, 7);
		fd_tablaAulas.left = new FormAttachment(0, 7);
		tablaAulas.setLayoutData(fd_tablaAulas);
		tablaAulas.setHeaderVisible(true);
		
		TableViewerColumn tcvNumeroAula = new TableViewerColumn(tvAulas, SWT.NONE);
		TableColumn tcNumeroAula = tcvNumeroAula.getColumn();
		tcNumeroAula.setWidth(61);
		tcNumeroAula.setText("Numero");
		
		TableViewerColumn tcvDescAula = new TableViewerColumn(tvAulas, SWT.NONE);
		TableColumn tcDescripcionAula = tcvDescAula.getColumn();
		tcDescripcionAula.setWidth(262);
		tcDescripcionAula.setText("Descripci\u00F3n");
		

		Button btnNuevoRec = new Button(group, SWT.NONE);
		btnNuevoRec.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				abrirDetalle("Nuevo");
			}
		});
		FormData fd_btnNuevoRec = new FormData();
		fd_btnNuevoRec.top = new FormAttachment(tablaAulas, 6);
		fd_btnNuevoRec.left = new FormAttachment(tablaAulas, 0, SWT.LEFT);
		fd_btnNuevoRec.right = new FormAttachment(0, 70);
		btnNuevoRec.setLayoutData(fd_btnNuevoRec);
		btnNuevoRec.setText("Nuevo");
		
		Button btnEditarRec = new Button(group, SWT.NONE);
		btnEditarRec.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				abrirDetalle("Editar");
			}
		});
		FormData fd_btnEditarRec = new FormData();
		fd_btnEditarRec.left = new FormAttachment(btnNuevoRec, 6);
		fd_btnEditarRec.top = new FormAttachment(tablaAulas, 6);
		btnEditarRec.setLayoutData(fd_btnEditarRec);
		btnEditarRec.setText("Editar");
		
		Button btnEliminarRec = new Button(group, SWT.NONE);
		btnEliminarRec.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				borrarAula();
			}
		});
		fd_btnEditarRec.right = new FormAttachment(btnEliminarRec, -6);
		FormData fd_btnEliminarRec = new FormData();
		fd_btnEliminarRec.right = new FormAttachment(0, 227);
		fd_btnEliminarRec.left = new FormAttachment(btnNuevoRec, 86);
		fd_btnEliminarRec.top = new FormAttachment(tablaAulas, 6);
		

		btnEliminarRec.setLayoutData(fd_btnEliminarRec);
		btnEliminarRec.setText("Eliminar");

		rellenarLista();
		return container;
	}

	private void obtenerLista(){
		listaAulas = srh.listarTodos();
	}
	
	private void rellenarLista() {
		// TODO Auto-generated method stub
		obtenerLista();
		ViewerSupport.bind(tvAulas, 
                new WritableList(listaAulas, Aula.class), 
                BeanProperties.values(Aula.class, new String[] {
		                          "idAula", "descAula"}));	

	}

	/**
	 * Initialize the preference page.
	 */
	public void init(IWorkbench workbench) {
		// Initialize the preference page
	}
	
	private void borrarAula(){
		int idxSelect= tablaAulas.getSelectionIndex();
 

		if (idxSelect == -1){
			// Mensaje de error
			GestorErrores.mensajeTexto("No hay nada seleccionado", NIVEL_INFO);
			return;
	       }
		else{
			MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_QUESTION
	                | SWT.YES | SWT.NO);
	        messageBox.setMessage("¿Estás seguro de que quieres borrar ese aula?");
	        messageBox.setText("Borrando el aula");
	        int response = messageBox.open();
	        if (response == SWT.YES){
	
				TableItem select = tablaAulas.getItem(idxSelect);
				int clave = new Integer(select.getText(0)).intValue();
				Aula sr = srh.findById(clave);
				try{			
					srh.delete(sr);
					rellenarLista();
				}
				catch (RuntimeException re){
					GestorErrores.mensajeTexto("error borrando la serie: "+ re.getMessage(), NIVEL_ERROR);
					re.printStackTrace();
				}
			}
	    }
		
	}
	
	private void abrirDetalle( String opcion){
		int idxSelect= tablaAulas.getSelectionIndex();
		if (opcion.equalsIgnoreCase("Editar"))
			if (idxSelect == -1){
				// Mensaje de error
				GestorErrores.mensajeTexto("No hay nada seleccionado", NIVEL_INFO);
				return;
		       }

		Integer claveAula;
		if (opcion.equalsIgnoreCase("Editar")){
				TableItem select = tablaAulas.getItem(idxSelect);
				claveAula = new Integer(select.getText(0));
		}
		else
			claveAula = new Integer(-1);

		DetalleAula dlg = new DetalleAula(getShell());
		dlg.setClaveAula(claveAula.intValue());
	
		dlg.open();
		rellenarLista();
	}

}
