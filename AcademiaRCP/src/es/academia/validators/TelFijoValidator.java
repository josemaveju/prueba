package es.academia.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

public class TelFijoValidator implements IValidator {

	private static final Log log = LogFactory.getLog(TelFijoValidator.class);
	
	private int tamTexto;

	
	public TelFijoValidator ( ){
		log.debug("Creando el objeto");
		tamTexto = 9;
	}

	@Override
	public IStatus validate(Object value) {
		// TODO Auto-generated method stub
		log.debug("TelFijoValidator: Entrando en el metodo Validate");
		System.err.println("TelFijoValidator: Entrando en el metodo Validate");
		String valor;
		// Comprueba que el tama�o es el correcto (9)
		
		 if (value instanceof String){
			 valor = (String) value;
			 if (valor.trim().length()!=0)
			 
				 if ( valor.trim().length()!= tamTexto){  // El tama�o debe ser 9
	 				 log.debug("TelFijoValidator: Error de tama�o.");
	 				 System.err.println("TelFijoValidator: Error de tama�o.");
					 return ValidationStatus.error("El tama�o debe ser "+tamTexto); 
				 }
				 else if (!(valor.startsWith("9"))){   // Empieza por 6 o 7			 
					 log.debug("TelFijoValidator: No empieza por 9");
					 System.err.println("TelFijoValidator: No empieza por 9");
					 return ValidationStatus.error("Debe empezar por 9"); 
					 
				 } else if (!comprobarNumero(value.toString().trim()))     // Es num�rico
					 return ValidationStatus.error("Debe ser num�rico."); 		 
		 }
		 else{
			 System.err.println ("TelFijoValidator: No es una instancia de Integer. " + value.getClass());
			 return ValidationStatus.error("Debe ser una cadena de Texto"); 
		 }

		 return ValidationStatus.OK_STATUS;


	}
	
	
	// Comprueba si el valor pasado es num�rico.
	
	private boolean comprobarNumero(String value){
		 try{
			 Double.parseDouble(value);
		 }
	 	catch (NumberFormatException nf){
	 		log.debug("El valor no es num�rivo");
	 		return false; 
	 	}
	 	catch (Exception e){
	 		log.debug("Error al convertir en numero: "+e.getStackTrace());
	 		return false; 
	 	}
		 return true;
		
	}

}


