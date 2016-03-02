package es.academia.validators;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.databinding.conversion.Converter;

import es.academia.modelo.Conocimiento;
import es.academia.modelo.ConocimientoHome;

public class ConocimientoToIntegerConverter extends Converter {

	public ConocimientoToIntegerConverter() {
	
		this(String.class, Integer.class);
		System.err.println("ConocimientoToInteger: Constructor");
		// TODO Auto-generated constructor stub
	}

	public ConocimientoToIntegerConverter(Object fromType, Object toType) {
		super(fromType, toType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object convert(Object fromObject) {
		// TODO Auto-generated method stub
		System.err.println("ConocimientoToInteger: Converter: " + fromObject+ " "+ fromObject.getClass());
		Conocimiento cono = new Conocimiento();
		cono.setDescripcion((String) fromObject);
		ConocimientoHome ch = new ConocimientoHome();
		List<Conocimiento> cono2 = ch.findByExample(cono);
		Integer salida=null;
		
		if (!cono2.isEmpty())
			salida = ((Conocimiento) cono2.iterator().next()).getIdConocimiento();  // si hay más de uno, nos quedamos con el primero.		
		else 
			salida = new Integer(0);	
		
		System.err.println("ConocimientoToInteger: Converter. Devolvemos: " +  salida);	
		return salida;
	}

}
