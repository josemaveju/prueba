package es.academia.dialogos;

import org.eclipse.jface.wizard.Wizard;

import es.academia.modelo.Alumno;
import es.academia.modelo.Curso;
import es.academia.negocio.NegocioException;
import es.academia.utils.GestorErrores;
import es.academia.utils.IConstantes;

public class NuevaMatriculaWizard extends Wizard  implements IConstantes{
	
	private Curso curso;
	private Alumno alumno;
	
	protected SeleccionarCursoPage sp;
	protected NuevaMatriculaPage mp;
	protected SalidaRecibosPage rp;

	public NuevaMatriculaWizard() {
		setWindowTitle("Nueva Matrícula");
	}

	@Override
	public void addPages() {
		sp = new SeleccionarCursoPage();
		addPage(sp);
		mp = new NuevaMatriculaPage();
		mp.setAlumno(alumno);
		addPage(mp);
		rp = new SalidaRecibosPage();
		addPage(rp);
		
		
	}

	@Override
	public boolean canFinish() {

//		GestorErrores.mensajeTexto("canFinish", NIVEL_ERROR);
		return mp.isPageComplete();
	}
	
	public boolean performFinish() {

//		GestorErrores.mensajeTexto("performFinish", NIVEL_ERROR);
		try {
			mp.matAl.matricular(mp.matricula,rp.listaRecibos);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			GestorErrores.mensajeTexto("Error matriculando al alumno: " + e.getMessage(), NIVEL_ERROR);
			e.printStackTrace();
			return false;
		}
		 
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
