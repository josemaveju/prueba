package es.academia.validators;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import es.academia.utils.ACALog;

public class NoVacioValidator implements IValidator {
	private static final Logger log = ACALog.getLogger(NoVacioValidator.class);	
	
	
	public NoVacioValidator (){

	}

	@Override
	public IStatus validate(Object value) {
		// TODO Auto-generated method stub

		// Comprueba que el tamaño es el correcto (5)
		 if (value instanceof String){
			 if ( ((String)value).length()==0 ){
	 				 log.debug("Error de tamaño.");
					 return ValidationStatus.error("El campo es obligatorio : "); 
				 }
			 else
		        return ValidationStatus.ok();
		 }
		 else
			 if (value == null)
				 return  ValidationStatus.error("El campo es obligatorio : ");
			 else
				 return ValidationStatus.ok();
	}

}
