package es.academia.propiedades;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferencePageContainer;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.ScaleFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;

import academiarcp.Activator;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import es.academia.utils.IConstantes;

//public class ConfigAcademiaPreferences extends PreferencePage implements
public class ConfigAcademiaPreferences extends PreferencePage implements 
		IWorkbenchPreferencePage, IConstantes {
	private Text txDenomSoc;
	private Text txNIF;
	private Text txDirSoc;
	private Text txLocalidadSoc;
	private Text txCPosSoc;
	private Text txProvinciaSoc;
	private Text txNombreCom;
	private Text txCPosCom;
	private Text txProvinciaCom;
	private Text txLocalidadCom;
	private Text txDirCom;
	private Text txTelFijo;
	private Text txTelMovil;
	private Text txEMail;
	private Text txWeb;
	


	/**
	 * Create the preference page.
	 */
	public ConfigAcademiaPreferences() {
		setDescription("Configurar los datos de la academia");
		setTitle("Datos del centro");
	}

	/**
	 * Create contents of the preference page.
	 * @param parent
	 */
	public Control createContents(Composite parent)  {
		parent.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new FormLayout());
		
		
		Group grpDomicilioSocial = new Group(container, SWT.NONE);
		FormData fd_grpDomicilioSocial = new FormData();
		fd_grpDomicilioSocial.bottom = new FormAttachment(0, 152);
		fd_grpDomicilioSocial.right = new FormAttachment(0, 538);
		fd_grpDomicilioSocial.top = new FormAttachment(0, 37);
		fd_grpDomicilioSocial.left = new FormAttachment(0, 10);
		grpDomicilioSocial.setLayoutData(fd_grpDomicilioSocial);
		grpDomicilioSocial.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD | SWT.ITALIC));
		grpDomicilioSocial.setText("Domicilio social");
		GridLayout gl_grpDomicilioSocial = new GridLayout(4, false);
		gl_grpDomicilioSocial.marginTop = 8;
		grpDomicilioSocial.setLayout(gl_grpDomicilioSocial);
		
		Label lblDireccin = new Label(grpDomicilioSocial, SWT.NONE);
		lblDireccin.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDireccin.setText("Direcci\u00F3n:");
		
		txDirSoc = new Text(grpDomicilioSocial, SWT.BORDER);
		txDirSoc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Label lblLocalidad = new Label(grpDomicilioSocial, SWT.NONE);
		lblLocalidad.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLocalidad.setText("Localidad:");
		
		txLocalidadSoc = new Text(grpDomicilioSocial, SWT.BORDER);
		txLocalidadSoc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		Label lblCPostal = new Label(grpDomicilioSocial, SWT.NONE);
		lblCPostal.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCPostal.setText("C. Postal:");
		
		txCPosSoc = new Text(grpDomicilioSocial, SWT.BORDER);
		txCPosSoc.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Label lblProvincia = new Label(grpDomicilioSocial, SWT.NONE);
		lblProvincia.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblProvincia.setText("Provincia:");
		
		txProvinciaSoc = new Text(grpDomicilioSocial, SWT.BORDER);
		txProvinciaSoc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	

		Group grpDomicilioComercial = new Group(container, SWT.NONE);
		FormData fd_grpDomicilioComercial = new FormData();
		fd_grpDomicilioComercial.bottom = new FormAttachment(0, 381);
		fd_grpDomicilioComercial.right = new FormAttachment(0, 538);
		fd_grpDomicilioComercial.top = new FormAttachment(0, 191);
		fd_grpDomicilioComercial.left = new FormAttachment(0, 10);
		grpDomicilioComercial.setLayoutData(fd_grpDomicilioComercial);
		grpDomicilioComercial.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD | SWT.ITALIC));
		grpDomicilioComercial.setText("Domicilio comercial");
		GridLayout gl_grpDomicilioComercial = new GridLayout(4, false);
		gl_grpDomicilioComercial.marginTop = 8;
		grpDomicilioComercial.setLayout(gl_grpDomicilioComercial);
		
		Label label = new Label(grpDomicilioComercial, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("Direcci\u00F3n:");
		
		txDirCom = new Text(grpDomicilioComercial, SWT.BORDER);
		txDirCom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Label label_3 = new Label(grpDomicilioComercial, SWT.NONE);
		label_3.setText("Localidad:");
		label_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txLocalidadCom = new Text(grpDomicilioComercial, SWT.BORDER);
		txLocalidadCom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Label label_1 = new Label(grpDomicilioComercial, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("C. Postal:");
		
		txCPosCom = new Text(grpDomicilioComercial, SWT.BORDER);
		txCPosCom.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Label label_2 = new Label(grpDomicilioComercial, SWT.NONE);
		label_2.setText("Provincia:");
		label_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txProvinciaCom = new Text(grpDomicilioComercial, SWT.BORDER);
		txProvinciaCom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTelFijo = new Label(grpDomicilioComercial, SWT.NONE);
		lblTelFijo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTelFijo.setText("Tel. fijo:");
		
		txTelFijo = new Text(grpDomicilioComercial, SWT.BORDER);
		GridData gd_txTelFijo = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txTelFijo.widthHint = 115;
		txTelFijo.setLayoutData(gd_txTelFijo);
		
		Label lblTelMvil = new Label(grpDomicilioComercial, SWT.NONE);
		lblTelMvil.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTelMvil.setText("Tel. M\u00F3vil:");
		
		txTelMovil = new Text(grpDomicilioComercial, SWT.BORDER);
		txTelMovil.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblEmail = new Label(grpDomicilioComercial, SWT.NONE);
		lblEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEmail.setText("e-Mail:");
		
		txEMail = new Text(grpDomicilioComercial, SWT.BORDER);
		txEMail.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Label lblPginaWeb = new Label(grpDomicilioComercial, SWT.NONE);
		lblPginaWeb.setText("P\u00E1gina web:");
		lblPginaWeb.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		txWeb = new Text(grpDomicilioComercial, SWT.BORDER);
		txWeb.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Label lblDenomSocial = new Label(container, SWT.NONE);
		FormData fd_lblDenomSocial = new FormData();
		fd_lblDenomSocial.right = new FormAttachment(0, 91);
		fd_lblDenomSocial.top = new FormAttachment(0, 10);
		fd_lblDenomSocial.left = new FormAttachment(0, 10);
		lblDenomSocial.setLayoutData(fd_lblDenomSocial);
		lblDenomSocial.setText("Denom. social");
		
		txDenomSoc = new Text(container, SWT.BORDER);
		FormData fd_txDenomSoc = new FormData();
		fd_txDenomSoc.right = new FormAttachment(0, 391);
		fd_txDenomSoc.top = new FormAttachment(0, 7);
		fd_txDenomSoc.left = new FormAttachment(0, 97);
		txDenomSoc.setLayoutData(fd_txDenomSoc);
		
		Label lblNifcif = new Label(container, SWT.NONE);
		FormData fd_lblNifcif = new FormData();
		fd_lblNifcif.right = new FormAttachment(0, 445);
		fd_lblNifcif.top = new FormAttachment(0, 10);
		fd_lblNifcif.left = new FormAttachment(0, 397);
		lblNifcif.setLayoutData(fd_lblNifcif);
		lblNifcif.setText("NIF/CIF");
		
		txNIF = new Text(container, SWT.BORDER);
		FormData fd_txNIF = new FormData();
		fd_txNIF.right = new FormAttachment(0, 538);
		fd_txNIF.top = new FormAttachment(0, 7);
		fd_txNIF.left = new FormAttachment(0, 451);
		txNIF.setLayoutData(fd_txNIF);
		
		Label lblNombreComercial = new Label(container, SWT.NONE);
		FormData fd_lblNombreComercial = new FormData();
		fd_lblNombreComercial.top = new FormAttachment(0, 167);
		fd_lblNombreComercial.left = new FormAttachment(0, 10);
		lblNombreComercial.setLayoutData(fd_lblNombreComercial);
		lblNombreComercial.setText("Nombre comercial");
		
		txNombreCom = new Text(container, SWT.BORDER);
		FormData fd_txNombreCom = new FormData();
		fd_txNombreCom.right = new FormAttachment(0, 538);
		fd_txNombreCom.top = new FormAttachment(0, 164);
		fd_txNombreCom.left = new FormAttachment(0, 115);
		txNombreCom.setLayoutData(fd_txNombreCom);

		restaurarPreferencias();
		return container;
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub

	    setDescription("Configurar los datos de la academia");
	}
/*	private Text txDenomSoc;
	private Text txNIF;
	private Text txDirSoc;
	private Text txLocalidadSoc;
	private Text txCPosSoc;
	private Text txProvinciaSoc;
	private Text txNombreCom;
	private Text txCPosCom;
	private Text txProvinciaCom;
	private Text txLocalidadCom;
	private Text txDirCom;
	private Text txTelFijo;
	private Text txTelMovil;
	private Text txEMail;
	private Text txWeb;
*/
	protected void restaurarPreferencias(){
        Preferences preferences = ConfigurationScope.INSTANCE.getNode("es.academiaRCP");
            Preferences sub1 = preferences.node("generalPreferences");
            txDenomSoc.setText(sub1.get(DENOMINACION_SOCIAL_PREF, ""));
            txNIF.setText(sub1.get(NIF_PREF, ""));
            txDirSoc.setText(sub1.get(DIRECCION_SOCIAL_PREF, ""));
            txLocalidadSoc.setText(sub1.get(LOCALIDAD_SOCIAL_PREF, ""));
            txCPosSoc.setText(sub1.get(C_POSTAL_SOCIAL_PREF, ""));
            txProvinciaSoc.setText(sub1.get(PROVINCIA_SOCIAL_PREF, ""));
            txNombreCom.setText(sub1.get(NOMBRE_COMERCIAL_PREF, ""));
            txCPosCom.setText(sub1.get(CPOSTAL_COMERCIAL_PREF, ""));
            txProvinciaCom.setText(sub1.get(PROVINCIA_COMERCIAL_PREF, ""));
            txLocalidadCom.setText(sub1.get(LOCALIDAD_COMERCIAL_PREF, ""));
            txDirCom.setText(sub1.get(DIRECCION_COMERCIAL_PREF, ""));
            txTelFijo.setText(sub1.get(TEL_FIJO_PREF, ""));
            txTelMovil.setText(sub1.get(TEL_MOVIL_PREF, ""));
            txEMail.setText(sub1.get(EMAIL_PREF, ""));
            txWeb.setText(sub1.get(WEB_PREF, ""));
	}
	
	protected void performApply(){
        Preferences preferences = ConfigurationScope.INSTANCE.getNode("es.academiaRCP");
            Preferences sub1 = preferences.node("generalPreferences");
            sub1.put(DENOMINACION_SOCIAL_PREF,txDenomSoc.getText());
            sub1.put(NIF_PREF,txNIF.getText());
            sub1.put(DIRECCION_SOCIAL_PREF,txDirSoc.getText());
            sub1.put(LOCALIDAD_SOCIAL_PREF,txLocalidadSoc.getText());
            sub1.put(C_POSTAL_SOCIAL_PREF,txCPosSoc.getText());
            sub1.put(PROVINCIA_SOCIAL_PREF,txProvinciaSoc.getText());
            sub1.put(NOMBRE_COMERCIAL_PREF,txNombreCom.getText());
            sub1.put(CPOSTAL_COMERCIAL_PREF,txCPosCom.getText());
            sub1.put(PROVINCIA_COMERCIAL_PREF,txProvinciaCom.getText());
            sub1.put(LOCALIDAD_COMERCIAL_PREF,txLocalidadCom.getText());
            sub1.put(DIRECCION_COMERCIAL_PREF,txDirCom.getText());
            sub1.put(TEL_FIJO_PREF,txTelFijo.getText());
            sub1.put(TEL_MOVIL_PREF,txTelMovil.getText());
            sub1.put(EMAIL_PREF,txEMail.getText());
            sub1.put(WEB_PREF,txWeb.getText());
            
            try {
                // forces the application to save the preferences
                preferences.flush();
              } catch (BackingStoreException e2) {
                e2.printStackTrace();
              }

		
	}


}
