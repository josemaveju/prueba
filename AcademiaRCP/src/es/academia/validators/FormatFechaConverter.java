package es.academia.validators;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.DateFormatter;

import org.apache.log4j.Logger;
import org.eclipse.core.databinding.conversion.Converter;

import es.academia.modelo.Conocimiento;
import es.academia.modelo.ConocimientoHome;
import es.academia.utils.ACALog;

public class FormatFechaConverter extends Converter {

	Logger log = ACALog.getLogger(FormatFechaConverter.class);
	
	public FormatFechaConverter() {
	
		this(Date.class, String.class);
		log.debug("Entrando en el Constructor");
		
		// TODO Auto-generated constructor stub
	}

	public FormatFechaConverter(Object fromType, Object toType) {
		super(fromType, toType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object convert(Object fromObject) {
		// TODO Auto-generated method stub
		log.debug("Converter: " + fromObject+ " "+ fromObject.getClass());
		SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");

		String salida = dtf.format(((Date)fromObject));
		
		log.debug("Converter. Devolvemos: " +  salida);	
		
		return salida;
	}

}
