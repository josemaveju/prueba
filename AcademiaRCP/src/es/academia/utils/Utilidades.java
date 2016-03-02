package es.academia.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

public class Utilidades {

	public static void addControlesTexto(Text control, boolean numerico, int maxLetras){
		control.setTextLimit(maxLetras);
		
		if (numerico){
			control.addVerifyListener(new VerifyListener() {  
				  
			    @Override  
			    public void verifyText(final VerifyEvent event) {  
			        switch (event.keyCode) {  
			            case SWT.BS:           // Backspace  
			            case SWT.DEL:          // Delete  
			            case SWT.HOME:         // Home  
			            case SWT.END:          // End  
			            case SWT.ARROW_LEFT:   // Left arrow  
			            case SWT.ARROW_RIGHT:  // Right arrow  
			                return;  
			        }  
			  
			        if (!Character.isDigit(event.character)) {  
			            event.doit = false;  // disallow the action  
			        }  
			    }  
			});  
	   }
	}
	
    public static String calcularEdad(String fecha){
    		System.err.println("Fecha: " + fecha);
		    Date fechaNac=null;
	        try {
	            /**Se puede cambiar la mascara por el formato de la fecha
	            que se quiera recibir, por ejemplo año mes día "yyyy-MM-dd"
	            en este caso es día mes año*/
	            fechaNac = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
	        } catch (Exception ex) {
	            System.out.println("Error:"+ex);
	        }
	        Calendar fechaNacimiento = Calendar.getInstance();
	        //Se crea un objeto con la fecha actual
	        Calendar fechaActual = Calendar.getInstance();
	        //Se asigna la fecha recibida a la fecha de nacimiento.
	        fechaNacimiento.setTime(fechaNac);
	        //Se restan la fecha actual y la fecha de nacimiento
	        int año = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
	        int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
	        int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);
	        //Se ajusta el año dependiendo el mes y el día
	        if(mes<0 || (mes==0 && dia<0)){
	            año--;
	        }
	        //Regresa la edad en base a la fecha de nacimiento
	        return ""+año;
     }
    
    public static void traceToLog(Exception e, Logger log){
    	StackTraceElement[] st = e.getStackTrace();
    	
    	int elementos = st.length;
    	
    	for (int i=0;i< elementos;i++){
    		log.debug(st[i]);
    	}
    	
    }
			
}
