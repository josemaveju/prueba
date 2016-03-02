package es.academia.validators;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class TamTextoValidator implements IValidator {
	
	private int tamTexto;
	
	public TamTextoValidator (int tam){
		tamTexto = tam;
		System.out.println("Entrando en el constructor. tama�o: " + tam);
	}

	@Override
	public IStatus validate(Object value) {
		// TODO Auto-generated method stub
		System.out.println("Entrando en el validate. tama�o: ");
		
		 if (value instanceof String){
			 if ( ((String)value).length()> tamTexto ){
 				 System.out.println("Error de tama�o.");
				 return ValidationStatus.error("El tamalo del texto ("
						 +((String)value).length()+") supera el m�ximo: "+tamTexto); 
			 }
			 else
				 return Status.OK_STATUS;
		 }
		 else
			 return ValidationStatus.error("El valor debe ser una cadena de texto");
		 
	}

}
