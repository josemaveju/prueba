package es.academia.dialogos;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import es.academia.modelo.ReciboHome;
import es.academia.negocio.NegocioException;
import es.academia.utils.ACALog;
import es.academia.utils.GestorErrores;
import es.academia.utils.IConstantes;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SelectRecibosPage extends WizardPage {
	private static final Logger log = ACALog.getLogger(ReciboHome.class);
	
	private Text txCurso;
	private Text txConcepto;
	private DateTime txMes;
	private DateTime txFechaEmision;
	private DateTime txFechaFin;
	private Button rbTodos;
	private Button rbCurso;
	private Integer idCurso= null;
	private Button btncurso;
    
	private Status statusFechas = new Status(IStatus.OK, "not_used", 0, "", null);
	
	/**
	 * Create the wizard.
	 */
	public SelectRecibosPage() {
		super("SelectRecibosPage");
		setTitle("Generación masiva de recibos");
		setDescription("Selección de recibos");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		
		Group grpFechas = new Group(container, SWT.NONE);
		grpFechas.setText("Fechas");
		grpFechas.setBounds(10, 10, 562, 91);
		
		Label lblMesQueSe = new Label(grpFechas, SWT.NONE);
		lblMesQueSe.setBounds(10, 23, 111, 19);
		lblMesQueSe.setText("Mes que se generar\u00E1:");
		
		txMes = new DateTime(grpFechas, SWT.BORDER | SWT.SHORT);
		txMes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setFechasDefecto();
			}
		});
		txMes.setBounds(127, 17, 124, 19);
		
		Label lblFechaDeEmisin = new Label(grpFechas, SWT.NONE);
		lblFechaDeEmisin.setBounds(10, 56, 111, 13);
		lblFechaDeEmisin.setText("Fecha de emisi\u00F3n:");
		
		txFechaEmision = new DateTime(grpFechas, SWT.BORDER | SWT.DROP_DOWN);
		txFechaEmision.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				validarFechas();
			}
		});
		txFechaEmision.setBounds(127, 50, 124, 19);
		
		Label lblFechaDeFin = new Label(grpFechas, SWT.NONE);
		lblFechaDeFin.setText("Fecha de fin:");
		lblFechaDeFin.setBounds(314, 56, 75, 13);
		
		txFechaFin = new DateTime(grpFechas, SWT.BORDER | SWT.DROP_DOWN);
		txFechaFin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				validarFechas();
			}
		});

		txFechaFin.setBounds(395, 50, 124, 19);
		
		Group grpSeleccinDeRecibos = new Group(container, SWT.NONE);
		grpSeleccinDeRecibos.setText("Selecci\u00F3n de recibos");
		grpSeleccinDeRecibos.setBounds(10, 110, 562, 80);
		
		rbTodos = new Button(grpSeleccinDeRecibos, SWT.RADIO);
		rbTodos.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btncurso.setEnabled(false);
				getWizard().getContainer().updateButtons();
			}
		});
		rbTodos.setSelection(true);
		rbTodos.setBounds(10, 24, 168, 16);
		rbTodos.setText("Todos los recibos pendientes");
		
		rbCurso = new Button(grpSeleccinDeRecibos, SWT.RADIO);
		rbCurso.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btncurso.setEnabled(true);
				getWizard().getContainer().updateButtons();
			}
		});
		rbCurso.setBounds(10, 54, 180, 16);
		rbCurso.setText("S\u00F3lo los recibos de este curso");
		
		txCurso = new Text(grpSeleccinDeRecibos, SWT.BORDER | SWT.READ_ONLY);
		txCurso.setBounds(196, 51, 296, 19);
		
		btncurso = new Button(grpSeleccinDeRecibos, SWT.NONE);
		btncurso.setEnabled(false);
		btncurso.setBounds(495, 51, 24, 19);
		btncurso.setText("...");
		
		Group grpConcepto = new Group(container, SWT.NONE);
		grpConcepto.setText("Concepto");
		grpConcepto.setBounds(10, 196, 562, 145);
		
		txConcepto = new Text(grpConcepto, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		txConcepto.setBounds(10, 21, 542, 114);
	}
	
	
	protected void validarFechas() {
		// TODO Auto-generated method stub
		Calendar clDesde = new GregorianCalendar();
		clDesde.set(txFechaEmision.getYear(), txFechaEmision.getMonth(), txFechaEmision.getDay());
		log.debug("clDesde: "+clDesde.get(Calendar.DATE)+"/"+clDesde.get(Calendar.MONTH)+"/"+clDesde.get(Calendar.YEAR));
		
		Calendar clHasta = new GregorianCalendar();
		clHasta.set(txFechaFin.getYear(), txFechaFin.getMonth(), txFechaFin.getDay());
		
		log.debug("clHasta: "+clHasta.get(Calendar.DATE)+"/"+clHasta.get(Calendar.MONTH)+"/"+clHasta.get(Calendar.YEAR));		
		
		if (clDesde.after(clHasta)){
			 statusFechas = new Status(IStatus.ERROR, "not_used", 0, "La fecha de fin debe ser mayor que la fecha de emisión", null);	
			 setErrorMessage(statusFechas.getMessage());
		}
		else{
			 statusFechas = new Status(IStatus.OK, "not_used", 0, "", null);	
			 setErrorMessage(null);
			 //setDescription("Selección de recibos");
		}
		 
		
		 getWizard().getContainer().updateButtons();
	}
	
	public boolean canFlipToNextPage(){
		if (statusFechas.getSeverity() != Status.OK){
			return false;
		}

		if (rbCurso.getSelection() && idCurso == null)
			return false;
		
		
		return true;
		
		
	}

	protected void setFechasDefecto() {
		// TODO Auto-generated method stub
	
		Calendar cl = new GregorianCalendar();
		cl.set(txMes.getYear(), txMes.getMonth(), txMes.getDay());
		
		txFechaFin.setDate(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.getActualMaximum(Calendar.DAY_OF_MONTH));
		txFechaEmision.setDate(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH), cl.getActualMinimum(Calendar.DAY_OF_MONTH));
	}

	public IWizardPage getNextPage(){
		   saveDataToModel();		
		   ((GenerarRecibosWizard)getWizard()).dtgr.obtenerListaRecibos();
		   
			return ((GenerarRecibosWizard)getWizard()).listaRecibos;
		       
	}

	private void saveDataToModel() {
		// TODO Auto-generated method stub
		((GenerarRecibosWizard)getWizard()).dtgr.setConcepto(txConcepto.getText());
		Calendar cl = new GregorianCalendar (txFechaEmision.getYear(),txFechaEmision.getMonth(),txFechaEmision.getDay());
		((GenerarRecibosWizard)getWizard()).dtgr.setFechaEmision(new Date(cl.getTimeInMillis()));
		
		cl.set(txFechaFin.getYear(), txFechaFin.getMonth(), txFechaFin.getDay());
		((GenerarRecibosWizard)getWizard()).dtgr.setFechaFin(new Date(cl.getTimeInMillis()));
		
		cl.set(txMes.getYear(), txMes.getMonth(), 1);
		((GenerarRecibosWizard)getWizard()).dtgr.setFechaFin(new Date(cl.getTimeInMillis()));
		
		((GenerarRecibosWizard)getWizard()).dtgr.setTodos(rbTodos.getSelection());
		((GenerarRecibosWizard)getWizard()).dtgr.setUnCurso(rbCurso.getSelection());
		
		((GenerarRecibosWizard)getWizard()).dtgr.setIdCurso(idCurso);
		
	}
}
