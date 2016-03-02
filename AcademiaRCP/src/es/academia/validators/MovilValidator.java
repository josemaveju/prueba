package es.academia.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

public class MovilValidator implements IValidator {

	private static final Log log = LogFactory.getLog(MovilValidator.class);
	
	private int tamTexto;

	
	public MovilValidator ( ){
		log.debug("Creando el objeto");
		System.err.println("Entrando en Movil Validator");
		tamTexto = 9;
	}

	@Override
	public IStatus validate(Object value) {
		// TODO Auto-generated method stub
		log.debug("Entrando en el metodo Validate");
		System.err.println("Entrando en el metodo Validate");
		String valor;
		// Comprueba que el tamaño es el correcto (9)
		
		 if (value instanceof String){
			 valor = (String) value;
			 if (valor.trim().length()!=0)
			 
				 if ( valor.trim().length()!= tamTexto){  // El tamaño debe ser 9
	 				 log.debug("Error de tamaño.");
	 				 System.err.println("Error de tamaño.");
					 return ValidationStatus.error("El tamaño debe ser "+tamTexto); 
				 }
				 else if (!(valor.startsWith("6")||valor.startsWith("7"))){   // Empieza por 6 o 7			 
					 log.debug("No empieza por 6 o 7");
					 System.err.println("No empieza por 6 o 7");
					 return ValidationStatus.error("Debe empezar por 6 o 7"); 
					 
				 } else if (!comprobarNumero(value.toString().trim()))     // Es numérico
					 return ValidationStatus.error("Debe ser numérico."); 		 
		 }
		 else{
			 System.err.println ("No es una instancia de String. " + value.getClass());
			 return ValidationStatus.error("Debe ser una cadena de Texto"); 
		 }

		 return ValidationStatus.OK_STATUS;


	}
	
	private boolean comprobarNumero(String value){
		 try{
			 Double.parseDouble(value);
		 }
	 	catch (NumberFormatException nf){
	 		log.debug("El valor no es numérivo");
	 		return false; 
	 	}
	 	catch (Exception e){
	 		log.debug("Error al convertir en numero: "+e.getStackTrace());
	 		return false; 
	 	}
		 return true;
		
	}

}
