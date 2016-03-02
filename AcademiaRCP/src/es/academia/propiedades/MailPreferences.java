package es.academia.propiedades;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.custom.StyledText;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import academiarcp.Activator;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.wb.swt.SWTResourceManager;

import es.academia.utils.IConstantes;

public class MailPreferences extends PreferencePage implements
		IWorkbenchPreferencePage, IConstantes {
	private Text txDesde;
	private Text txHost;
	private Text txPuerto;
	private Text txUsuario;
	private Text txContraseña;
    private StyledText txFirma;
	/**
	 * Create the preference page.
	 */
	public MailPreferences() {
		setDescription("Configurar las opciones para enviar e-mails");
		setTitle("Configuraci\u00F3n del correo");
	}

	/**
	 * Create contents of the preference page.
	 * @param parent
	 */
	@Override
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.BORDER);
		FormLayout fl_container = new FormLayout();
		fl_container.spacing = 2;
		fl_container.marginBottom = 2;
		fl_container.marginTop = 2;
		fl_container.marginRight = 4;
		fl_container.marginLeft = 4;
		container.setLayout(fl_container);
		
		Label label = new Label(container, SWT.NONE);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(0, 8);
		fd_label.left = new FormAttachment(0, 5);
		label.setLayoutData(fd_label);
		label.setText("Remitente");
		
		txDesde = new Text(container, SWT.BORDER);
		FormData fd_txDesde = new FormData();
		fd_txDesde.right = new FormAttachment(100, 101, 0);
		fd_txDesde.top = new FormAttachment(0, 5);
		fd_txDesde.left = new FormAttachment(0, 70);
		txDesde.setLayoutData(fd_txDesde);
		
		Label label_1 = new Label(container, SWT.NONE);
		FormData fd_label_1 = new FormData();
		fd_label_1.top = new FormAttachment(0, 34);
		fd_label_1.left = new FormAttachment(0, 5);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("Host");
		
		txHost = new Text(container, SWT.BORDER);
		FormData fd_txHost = new FormData();
		fd_txHost.right = new FormAttachment(100, 101, 0);
		fd_txHost.top = new FormAttachment(0, 31);
		fd_txHost.left = new FormAttachment(0, 70);
		txHost.setLayoutData(fd_txHost);
		
		Label label_2 = new Label(container, SWT.NONE);
		FormData fd_label_2 = new FormData();
		fd_label_2.top = new FormAttachment(0, 60);
		fd_label_2.left = new FormAttachment(0, 5);
		label_2.setLayoutData(fd_label_2);
		label_2.setText("Puerto");
		
		txPuerto = new Text(container, SWT.BORDER);
		FormData fd_txPuerto = new FormData();
		fd_txPuerto.right = new FormAttachment(100, 101, 0);
		fd_txPuerto.top = new FormAttachment(0, 57);
		fd_txPuerto.left = new FormAttachment(0, 70);
		txPuerto.setLayoutData(fd_txPuerto);
		
		Label label_3 = new Label(container, SWT.NONE);
		FormData fd_label_3 = new FormData();
		fd_label_3.top = new FormAttachment(0, 94);
		fd_label_3.left = new FormAttachment(0, 5);
		label_3.setLayoutData(fd_label_3);
		label_3.setText("Usuario");
		
		txUsuario = new Text(container, SWT.BORDER);
		FormData fd_txUsuario = new FormData();
		fd_txUsuario.right = new FormAttachment(100, 101, 0);
		fd_txUsuario.top = new FormAttachment(0, 92);
		fd_txUsuario.left = new FormAttachment(0, 70);
		txUsuario.setLayoutData(fd_txUsuario);
		
		Label label_4 = new Label(container, SWT.NONE);
		FormData fd_label_4 = new FormData();
		fd_label_4.top = new FormAttachment(0, 120);
		fd_label_4.left = new FormAttachment(0, 5);
		label_4.setLayoutData(fd_label_4);
		label_4.setText("Contrase\u00F1a");
		
		txContraseña = new Text(container, SWT.BORDER | SWT.PASSWORD);
		FormData fd_txContraseña = new FormData();
		fd_txContraseña.right = new FormAttachment(100, 101, 0);
		fd_txContraseña.top = new FormAttachment(0, 118);
		fd_txContraseña.left = new FormAttachment(0, 70);
		txContraseña.setLayoutData(fd_txContraseña);
		
		Group group = new Group(container, SWT.NONE);
		group.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		group.setLayout(new FormLayout());
		FormData fd_group = new FormData();
		fd_group.bottom = new FormAttachment(100, 105, 0);
		fd_group.right = new FormAttachment(100, 101, 0);
		fd_group.top = new FormAttachment(0, 148);
		fd_group.left = new FormAttachment(0, 5);
		group.setLayoutData(fd_group);
		group.setText("Firma del correo");
		
		txFirma = new StyledText(group, SWT.BORDER);
		FormData fd_txFirma = new FormData();
		fd_txFirma.bottom = new FormAttachment(100, 103, 0);
		fd_txFirma.right = new FormAttachment(100, 101, 0);
		fd_txFirma.top = new FormAttachment(0, 5);
		fd_txFirma.left = new FormAttachment(0, 5);
		txFirma.setLayoutData(fd_txFirma);

		restaurarPreferencias();
		return container;
	}

	/**
	 * Initialize the preference page.
	 */
	public void init(IWorkbench workbench) {
		// Initialize the preference page
//		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}
	
	protected void restaurarPreferencias(){
        Preferences preferences = ConfigurationScope.INSTANCE.getNode("es.academiaRCP");
            Preferences sub1 = preferences.node("mailPreferences");
            txDesde.setText(sub1.get(REMITENTE_PREF, ""));
            txHost.setText(sub1.get(HOST_PREF, ""));
            txPuerto.setText(sub1.get(PUERTO_PREF, ""));
            txUsuario.setText(sub1.get(USUARIO_PREF, ""));
            txContraseña.setText(sub1.get(CONTRASEÑA_PREF, ""));
            txFirma.setText(sub1.get(FIRMA_PREF, ""));
	}
	
	protected void performApply(){
        Preferences preferences = ConfigurationScope.INSTANCE.getNode("es.academiaRCP");
            Preferences sub1 = preferences.node("mailPreferences");
            sub1.put(REMITENTE_PREF,txDesde.getText());
            sub1.put(HOST_PREF, txHost.getText());
            sub1.put(PUERTO_PREF,txPuerto.getText());
            sub1.put(USUARIO_PREF, txUsuario.getText());
            sub1.put(CONTRASEÑA_PREF, txContraseña.getText());
            sub1.put(FIRMA_PREF,txFirma.getText());
           
            try {
                // forces the application to save the preferences
                preferences.flush();
              } catch (BackingStoreException e2) {
                e2.printStackTrace();
              }

		
	}

}
