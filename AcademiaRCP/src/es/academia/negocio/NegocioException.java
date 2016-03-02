package es.academia.negocio;


public class NegocioException extends Exception {

	  private final String code; // Código de error  (tipo)
	  private final String opError; // Operación que provocó el error
	  public static final String NO_ERROR = "OK"; // No se ha producido error

	  public static final String MSG_DEFAULT_NEGOCIO_ERROR = "Se ha producido un error al ejecutar la operación"; //Error generico de negocio
	  public static final String NEGOCIO_ERROR_COMP = "NegocioCompError"; // Error en la ejecución de un servicio (en Negocio) NUEVO
	  
// Lista de códigos de error.
	  public static final String ERROR_CREANDO_MATRICULA="ERROR_CREANDO_MATRICULA";
	  
	  
	  public NegocioException(String negocioErrorCode, String opError) {
	    super(MSG_DEFAULT_NEGOCIO_ERROR + " (" + negocioErrorCode + "): " + opError);
	    this.code = NegocioException.NEGOCIO_ERROR_COMP;
	    this.opError = opError;
	  }

	  public NegocioException(String code, String msg, String opError) {
	    super(msg);
	    this.code = code;
	    this.opError = opError;
	  }

	  public String getCode() {
	    return code;
	  }

	  public String getOpError() {
	    return opError;
	  }

}
