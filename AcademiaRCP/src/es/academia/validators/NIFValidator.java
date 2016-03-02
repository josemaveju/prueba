package es.academia.validators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import es.academia.modelo.AlumnoHome;

public class NIFValidator implements IValidator {

	private static final Log log = LogFactory.getLog(NIFValidator.class);
	
	private int tamTexto;
	
	public NIFValidator (){
		log.debug("Creando el objeto");
		tamTexto = 9;
	}

	@Override
	public IStatus validate(Object value) {
		// TODO Auto-generated method stub
		log.debug("Entrando en el metodo Validate");

		// Comprueba que el tamaño es el correcto (5)
		 if (!isNifValido((String)value)){
			 log.debug("El NIF No es correcto");
			 System.err.println("NIF incorrecto");
			 return ValidationStatus.error("NIF Incorrecto"); 
			 
		 }
		 else
			 return ValidationStatus.OK_STATUS;
	}
	
	
	
    private boolean isNifValido(String nif) {

        boolean resultado = false;
        final String LETRAS_NIF = "TRWAGMYFPDXBNJZSQVHLCKE";

        try {
        String nif1=nif.toUpperCase();
        if (nif1.matches("[0-9]{8}[" + LETRAS_NIF + "]")) {
                int dni = Integer.parseInt(nif1.substring(0, 8));
                char letraCalculada = LETRAS_NIF.charAt(dni % 23);
                if (letraCalculada == nif1.charAt(8)) {
                        resultado = true;
                }
        }
        } catch (Exception e) {
            // Si ha habido alg�n error es porque hay alg�n parseo que tira bien.
            resultado = false;
        }

        return resultado;
}
	

}
