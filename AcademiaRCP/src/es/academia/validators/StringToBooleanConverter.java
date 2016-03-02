package es.academia.validators;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.conversion.Converter;

import es.academia.modelo.AlumnoHome;
import es.academia.modelo.Conocimiento;
import es.academia.modelo.ConocimientoHome;
import es.academia.utils.ACALog;

public class StringToBooleanConverter extends Converter {

	private static final Logger log = ACALog.getLogger(StringToBooleanConverter.class);
	
	public StringToBooleanConverter() {
		this(String.class,Boolean.class);
		log.debug("Constructor: " );

	}

	public StringToBooleanConverter(Object fromType, Object toType) {
		super(fromType, toType);

	}


	public Object convert(Object fromObject) {

		log.debug("Converter. Entrada: " + fromObject+ " "+ fromObject.getClass());
		String sino = (String) fromObject;
		Boolean salida = new Boolean(false);
		
		if (sino.equalsIgnoreCase("S"))
			salida = Boolean.valueOf(true);
		else
			salida = Boolean.valueOf(false);
		
		log.debug("Devolvemos: " + salida.toString());
		return salida;
	}

}
