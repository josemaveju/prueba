package es.academia.validators;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.conversion.Converter;

import es.academia.modelo.AlumnoHome;
import es.academia.modelo.Conocimiento;
import es.academia.modelo.ConocimientoHome;
import es.academia.utils.ACALog;

public class BooleanToStringConverter extends Converter {

	private static final Logger log = ACALog.getLogger(BooleanToStringConverter.class);
	
	public BooleanToStringConverter() {
		this(Boolean.class,String.class);
		log.debug("Constructor: " );

	}

	public BooleanToStringConverter(Object fromType, Object toType) {
		super(fromType, toType);

	}


	public Object convert(Object fromObject) {

		log.debug("Converter. Entrada: " + fromObject+ " "+ fromObject.getClass());
		String sino = "";
		Boolean entrada = (Boolean)fromObject;
		
		if (entrada.booleanValue())
			sino = "S";
		else
			sino = "N";
		
		log.debug("Devolvemos: " + sino);
		return sino;
	}

}
