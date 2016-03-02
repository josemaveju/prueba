package es.academia.validators;

import java.math.BigDecimal;
import org.apache.log4j.Logger;
import org.eclipse.core.databinding.conversion.Converter;

import es.academia.utils.ACALog;
import es.academia.utils.Utilidades;

public class StringToBigDecimalConverter extends Converter {

	private static final Logger log = ACALog.getLogger(StringToBigDecimalConverter.class);
	
	public StringToBigDecimalConverter() {
		this(String.class,BigDecimal.class);
		log.debug("Después de llamar al constructor por defecto" );
	}

	public StringToBigDecimalConverter(Object fromType, Object toType) {
		super(String.class, BigDecimal.class);
		log.debug("Después de llamar al constructor con parámetros. De: " + fromType.getClass() + " a: "+toType.getClass());
	}


	public Object convert(Object fromObject) {

		log.debug("Entrada en método converter: " + fromObject+ " "+ fromObject.getClass());
		String entrada = (String) fromObject;

		BigDecimal salida= null;
		
		if ( entrada == null || entrada.equals(""))
			salida = new BigDecimal(0);
		else{
			try{
				double db = Double.valueOf(entrada).doubleValue();
				salida = new BigDecimal(db);
			}catch (Exception p){
				salida = null;
				log.error("Error convirtiendo a BigDecimal: " +p.getMessage());
				Utilidades.traceToLog(p,log);
			}			
			log.debug("Devolvemos: " + salida.toString());
		}
		log.debug("Saliendo de la conversión. Convertido a BigDecimal "+ salida.toPlainString());
		log.debug("Clase de salida: "+ salida.getClass());
		return salida;
	}

}
