package es.academia.validators;

import org.eclipse.core.databinding.conversion.Converter;

import es.academia.modelo.Conocimiento;
import es.academia.modelo.ConocimientoHome;

public class IntegerToConocimientoConverter extends Converter {

	public IntegerToConocimientoConverter() {
		this(Integer.class,String.class);
		System.err.println("IntegerToConocimiento: Constructor: " );
		// TODO Auto-generated constructor stub
	}

	public IntegerToConocimientoConverter(Object fromType, Object toType) {
		super(fromType, toType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object convert(Object fromObject) {
		// TODO Auto-generated method stub
		System.err.println("IntegerToConocimiento: Converter. Entrada: " + fromObject+ " "+ fromObject.getClass());
		Integer idCono = (Integer) fromObject;
		ConocimientoHome ch = new ConocimientoHome();
		
		Conocimiento cono = ch.findById(idCono);
		System.err.println("IntegerToConocimiento: Converter. Devolvemos: " + cono.getDescripcion());
		return cono.getDescripcion();
	}

}
