package es.academia.validators;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class CodPostalValidator implements IValidator {
	
	private int tamTexto;
	
	public CodPostalValidator (){
		tamTexto = 5;
	}

	@Override
	public IStatus validate(Object value) {
		// TODO Auto-generated method stub

		// Comprueba que el tamaño es el correcto (5)
		 if (value instanceof String){
			 if ( ((String)value).length()> tamTexto ){
 				 System.out.println("Error de tamaño.");
				 return ValidationStatus.error("El tamaño del dato ("
						 +((String)value).length()+") supera el máximo: "+tamTexto); 
			 }
			 else if ( ((String)value).length()< tamTexto ){
	 				 System.out.println("Error de tamaño.");
					 return ValidationStatus.error("El tamaño del dato debe ser : "+tamTexto); 
				 }
			 else {
				  String s = String.valueOf(value);
			      if (!s.matches("\\d*")) {
  				    return ValidationStatus.error("Debe ser un dato numérico"); 
			      }  
			        return ValidationStatus.ok();

			 }
		 }
		 else
			 return ValidationStatus.error("El valor debe ser una cadena de texto");
		 
	}

}
