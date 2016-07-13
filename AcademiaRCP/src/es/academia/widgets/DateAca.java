package es.academia.widgets;

import java.text.SimpleDateFormat;
import java.util.Calendar; 
import java.util.Date;

public class DateAca extends Date {
	
	private String formato ="dd/MM/yyyy";

	public void setSeparador(String formato){
		this.formato = formato;
	}
	
	public String toString(){
		
		SimpleDateFormat formateador = new SimpleDateFormat(formato);
		return formateador.format(this);
	}

}
