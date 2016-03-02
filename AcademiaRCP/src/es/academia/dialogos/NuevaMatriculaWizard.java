package es.academia.dialogos;

import org.eclipse.jface.wizard.Wizard;

import es.academia.modelo.Alumno;
import es.academia.modelo.Curso;

public class NuevaMatriculaWizard extends Wizard {
	
	private Curso curso;
	private Alumno alumno;
	

	public NuevaMatriculaWizard() {
		setWindowTitle("Nueva Matrícula");
	}

	@Override
	public void addPages() {
		SeleccionarCursoPage sp = new SeleccionarCursoPage();
		addPage(sp);
		NuevaMatriculaPage mp = new NuevaMatriculaPage();
		mp.setAlumno(alumno);
		addPage(mp);
		
	}

	@Override
	public boolean performFinish() {
		return false;
	}
	
	public void setCurso(Curso curso){
		this.curso = curso;
	}
	
	public Curso getCurso(){
		return this.curso ;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
	

}
