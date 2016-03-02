package es.academia.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import es.academia.modelo.AlumnoHome;

public class EMailValidator implements IValidator {

	private static final Log log = LogFactory.getLog(EMailValidator.class);
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private int tamTexto;
	
	public EMailValidator (){
		super();
		log.debug("Creando el objeto");

	}


	public IStatus validate(Object value) {
		// TODO Auto-generated method stub
		log.debug("Entrando en el metodo Validate");

		// Comprueba que el tamaño es el correcto (5)
		 if (!isEmailValido((String)value)){
			 log.debug("El eMail No es correcto");
			 System.err.println("e-mail incorrecto");
			 return ValidationStatus.error("e-mail Incorrecto"); 
			 
		 }
		 else
			 return ValidationStatus.OK_STATUS;
	}
	

    /**
     * Validate given email with regular expression.
     *
     * @param email
     *            email for validation
     * @return true valid email, otherwise false
     */
    public static boolean isEmailValido(String email) {
 
        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
 
        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
 
    }	

}
