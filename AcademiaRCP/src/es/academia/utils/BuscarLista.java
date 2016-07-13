package es.academia.utils;

import java.util.List;

import es.academia.modelo.Alumno;
import es.academia.modelo.AlumnoHome;
import es.academia.modelo.Curso;
import es.academia.modelo.CursoHome;
import es.academia.modelo.Materia;
import es.academia.modelo.MateriaHome;
import es.academia.modelo.Profesor;
import es.academia.modelo.ProfesorHome;

public  class BuscarLista {
	public static final String BUSCAR_ALUMNO = "alumno";
	public static final String BUSCAR_PROFESOR = "profesor";
	public static final String BUSCAR_CURSO = "curso";
	public static final String BUSCAR_MATERIA = "materia";
	
	public static List buscarListaAvanzada(String tipoBusqueda,String criterio1, 
			                               String criterio2, String criterio3, String criterio4){
		
		if (tipoBusqueda.equalsIgnoreCase(BUSCAR_ALUMNO)){
			Alumno alumno = new Alumno();
			alumno.setNombre(criterio1);
			alumno.setApellidos(criterio2);
			if (!criterio4.equals(""))
				alumno.setIdAlumno(Integer.valueOf(criterio4).intValue());
			if (!criterio3.equals(""))
				alumno.setNif(criterio3);
			
			AlumnoHome ah = new AlumnoHome();
			List<Alumno> alumnos = ah.findByExample(alumno);
			return (List<Alumno>) alumnos;
		}
		else if (tipoBusqueda.equalsIgnoreCase(BUSCAR_PROFESOR))
			{
			Profesor profesor = new Profesor();
			profesor.setNombre(criterio1);
			profesor.setApellidos(criterio2);
			if (!criterio4.equals(""))
				profesor.setIdProfesor(Integer.valueOf(criterio4).intValue());
			if (!criterio3.equals(""))
				profesor.setNif(criterio3);
			
			ProfesorHome ah = new ProfesorHome();
			List<Profesor> profesores = ah.findByExample(profesor);
			return (List<Profesor>)profesores;
		}
		else if (tipoBusqueda.equalsIgnoreCase(BUSCAR_CURSO))
		{
			Curso curso = new Curso();
			curso.setDescCurso(criterio1);

			if (!criterio2.equals(""))
				curso.setMateria((Materia)BuscarLista.buscarListaAvanzada(BUSCAR_MATERIA, criterio2, "", "", "").get(0));
			
			if (!criterio4.equals(""))
				curso.setIdCurso(Integer.valueOf(criterio4).intValue());
			
			if (!criterio3.equals(""))
				curso.setProfesor((Profesor)BuscarLista.buscarListaAvanzada(BUSCAR_PROFESOR, criterio3, "", "", "").get(0));
			
			CursoHome ah = new CursoHome();
			List<Curso> cursos = ah.findByExample(curso);
			return (List<Curso>)cursos;
		}
		else if (tipoBusqueda.equalsIgnoreCase(BUSCAR_MATERIA))
		{
			Materia materia = new Materia();
			materia.setDescMateria(criterio1);
	
			if (!criterio4.equals(""))
				materia.setIdMateria(Integer.valueOf(criterio4).intValue());
			
			MateriaHome ah = new MateriaHome();
			List<Materia> materias = ah.findByExample(materia);
			return (List<Materia>)materias;
		}
		else
			return null;

   }
	
    public static List buscarNombreApellido(String tipoBusqueda,String texto){

    	List listaTotal=buscarListaAvanzada(tipoBusqueda,texto,"","","");
    	listaTotal.addAll(buscarListaAvanzada(tipoBusqueda,"",texto,"",""));
    	
    	return listaTotal;
    }
	
 
}
