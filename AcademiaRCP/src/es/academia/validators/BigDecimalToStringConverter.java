package es.academia.validators;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.conversion.Converter;

import es.academia.utils.ACALog;
import es.academia.utils.Utilidades;

public class BigDecimalToStringConverter extends Converter {

	private static final Logger log = ACALog.getLogger(BigDecimalToStringConverter.class);
	
	public BigDecimalToStringConverter() {

		this(BigDecimal.class,String.class);
		log.debug("Después de llamar al constructor por defecto" );
	}

	public BigDecimalToStringConverter(Object fromType, Object toType) {
		super(BigDecimal.class, String.class);
		log.debug("Después de llamar al constructor con parámetros. De: " + fromType.getClass() + " a: "+toType.getClass());
	}


	public Object convert(Object fromObject) {

		log.debug("Entrada en método converter: " + fromObject+ " "+ fromObject.getClass());
		String salida = "";
		BigDecimal entrada = (BigDecimal)fromObject;
		
		if (entrada == null){
			salida = "";
		}
		else{
			log.debug("Convert. Entrada.toString(): "+ entrada.toString());
			log.debug("Convert. Entrada.toPlainString(): "+ entrada.toPlainString());
			
			try{
				salida = entrada.toPlainString();
				
			}
			catch (Exception e){
				salida = "";
				log.error("Error convirtiendo a BigDecimal: " +e.getMessage());
				Utilidades.traceToLog(e,log);		
			}
		}
		log.debug("Saliendo de la conversión. Convertido a String "+ salida);
		log.debug("Clase de salida: "+ salida.getClass());
		return salida;
	}

}
