package es.academia.validators;

import java.text.Format;
import java.text.ParseException;
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
import es.academia.utils.GestorErrores;

public class ParseFechaConverter extends Converter {

	Logger log = ACALog.getLogger(ParseFechaConverter.class);
	
	public ParseFechaConverter() {
	
		this(String.class, Date.class);
		log.debug("Entrando en el Constructor");
		
		// TODO Auto-generated constructor stub
	}

	public ParseFechaConverter(Object fromType, Object toType) {
		super(fromType, toType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object convert(Object fromObject) {
		// TODO Auto-generated method stub
		log.debug("Converter: " + fromObject+ " "+ fromObject.getClass());
		SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");

		Date salida = null;
		try {
			salida = dtf.parse((String)fromObject);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.debug("Error parseando la fecha: " + e);
			e.printStackTrace();
			GestorErrores.mensajeTexto("Error parseando la fecha", GestorErrores.NIVEL_ERROR);
		}
		
		log.debug("Converter. Devolvemos: " +  salida);	
		
		return salida;
	}

}
