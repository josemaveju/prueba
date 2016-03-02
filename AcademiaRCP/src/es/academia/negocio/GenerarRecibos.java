package es.academia.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenerarRecibos {
	
	private boolean generarPendientes;
	private Date mensualidad;
	private boolean todosCursos;
	private List<Integer> cursos;
	private String concepto;
	
	public static final String CONCEPTODEFECTO = "Cobro mensualidad";
	

/*
 * Constructor por defecto.
 * Llama al constructor con parámetros para que genere todos los recibos.
 */
	public GenerarRecibos(){
		this(true, null, true, null, CONCEPTODEFECTO);
	}

	
/* Constructor.
 * 
 * Parámetros: 
 * - Indicador de si hay que generar todos o no.
 * - Indicador de la mensualidad que hay que generar (Puede ser nulo)
 * - Indicador de si hay que generar todos los cursos
 * - Lista de los cursos que hay que generar (Puede ser nulo)
 * - Concepto por defecto para los recibos. 
 * 
 */
	public GenerarRecibos (boolean generarPendientes, Date mensualidad, 
			               boolean todosCursos, List<Integer> cursos, String concepto){
		
		this.generarPendientes = generarPendientes;

		if (mensualidad == null)
			this.mensualidad = null;
		else
			this.mensualidad = mensualidad;
		
		this.todosCursos = todosCursos;

		if (cursos == null)
			this.cursos = new ArrayList();
		else
			this.cursos = cursos;
		
		if (concepto != null)
			this.concepto = concepto;
		else
			this.concepto = "";
	}

	
/*
 * Setter y Getters de los atributos privados
 */

	public boolean isGenerarPendientes() {
		return generarPendientes;
	}


	public void setGenerarPendientes(boolean generarPendientes) {
		this.generarPendientes = generarPendientes;
	}


	public Date getMensualidad() {
		return mensualidad;
	}


	public void setMensualidad(Date mensualidad) {
		this.mensualidad = mensualidad;
	}


	public boolean isTodosCursos() {
		return todosCursos;
	}


	public void setTodosCursos(boolean todosCursos) {
		this.todosCursos = todosCursos;
	}


	public List<Integer> getCursos() {
		return cursos;
	}


	public void setCursos(List<Integer> cursos) {
		this.cursos = cursos;
	}


	public String getConcepto() {
		return concepto;
	}


	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	

}
