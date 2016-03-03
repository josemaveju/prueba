package es.academia.utils;

import java.util.List;

import es.academia.modelo.Alumno;
import es.academia.modelo.AlumnoHome;
import es.academia.modelo.Profesor;
import es.academia.modelo.ProfesorHome;

public  class BuscarLista {
	public static final String BUSCAR_ALUMO = "alumno";
	public static final String BUSCAR_PROFESOR = "profesor";
	
	public static List buscarListaAvanzada(String tipoBusqueda,String nombre, String apellidos, String nif, String codigo){
		if (tipoBusqueda.equalsIgnoreCase(BUSCAR_ALUMO)){
			Alumno alumno = new Alumno();
			alumno.setNombre(nombre);
			alumno.setApellidos(apellidos);
			if (!codigo.equals(""))
				alumno.setIdAlumno(Integer.valueOf(codigo).intValue());
			alumno.setNif(nif);
			AlumnoHome ah = new AlumnoHome();
			List<Alumno> alumnos = ah.findByExample(alumno);
			return (List<Alumno>) alumnos;
		}
		else //if (tipoBusqueda.equalsIgnoreCase(BUSCAR_PROFESOR))
			{
			Profesor profesor = new Profesor();
			profesor.setNombre(nombre);
			profesor.setApellidos(apellidos);
			if (!codigo.equals(""))
				profesor.setIdProfesor(Integer.valueOf(codigo).intValue());
			profesor.setNif(nif);
			ProfesorHome ah = new ProfesorHome();
			List<Profesor> profesores = ah.findByExample(profesor);
			return (List<Profesor>)profesores;
			
		}

	}
	
    public static List buscarNombreApellido(String tipoBusqueda,String texto){

    	List listaTotal=buscarListaAvanzada(tipoBusqueda,texto,"","","");
    	listaTotal.addAll(buscarListaAvanzada(tipoBusqueda,"",texto,"",""));
    	
    	return listaTotal;
    }
	

}
