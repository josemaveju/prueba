package es.academia.modelo;

// Generated 21-jul-2014 23:03:01 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import es.academia.utils.ACALog;

/**
 * Curso generated by hbm2java
 */ 
public class Curso extends AbstractModelObject implements java.io.Serializable {
	private static final Logger log = ACALog.getLogger(Curso.class);

	private Integer idCurso;
	private String descCurso;
//	private int idMateria;
//	private int idProfesor;
//	private int idAula;
	private Integer horasSemana;
	private String annoCurso;
	private Date FInicio;
	private Date FFin;
	private Integer maxAlumnos;
	private BigDecimal impMatricula;
	private BigDecimal impMes;
	private BigDecimal impHora;
	private Materia materia;
	private Aula aula;
	private Profesor profesor;
	private Set<Matricula> matriculas = new HashSet<Matricula>();  
	private List<Recibo> recibos=new ArrayList<Recibo>();
	
	public Curso() {
	}
/*
	public Curso(int idMateria, int idProfesor, int idAula) {
		this.idMateria = idMateria;
		this.idProfesor = idProfesor;
		this.idAula = idAula;
	}
*/
	public Curso(String descCurso,  Profesor profesor, 
			Integer horasSemana, String annoCurso, Date FInicio, Date FFin,
			Integer maxAlumnos, BigDecimal impMatricula, BigDecimal impMes,
			BigDecimal impHora) {
		this.descCurso = descCurso;
		
		this.profesor = profesor;
		
		this.horasSemana = horasSemana;
		this.annoCurso = annoCurso;
		this.FInicio = FInicio;
		this.FFin = FFin;
		this.maxAlumnos = maxAlumnos;
		this.impMatricula = impMatricula;
		this.impMes = impMes;
		this.impHora = impHora;
//		this.setRecibos(null);
	}

	public Integer getIdCurso() {
		return this.idCurso;
	}

	public void setIdCurso(Integer idCurso) {
		firePropertyChange("idCurso", this.idCurso, idCurso);
		this.idCurso = idCurso;
	}

	public String getDescCurso() {
		return this.descCurso;
	}

	public void setDescCurso(String descCurso) {
		firePropertyChange("descCurso", this.descCurso, descCurso);
		this.descCurso = descCurso;
	}
/*
	public int getIdMateria() {
		return this.idMateria;
	}

	public void setIdMateria(int idMateria) {
		firePropertyChange("idMateria", this.idMateria, idMateria);
		this.idMateria = idMateria;
	}

	public int getIdProfesor() {
		return this.idProfesor;
	}

	public void setIdProfesor(int idProfesor) {
		firePropertyChange("idProfesor", this.idProfesor, idProfesor);
		this.idProfesor = idProfesor;
	}

	public int getIdAula() {
		return this.idAula;
	}

	public void setIdAula(int idAula) {
		firePropertyChange("idAula", this.idAula, idAula);
		this.idAula = idAula;
	}
*/
	public Integer getHorasSemana() {
		return this.horasSemana;
	}

	public void setHorasSemana(Integer horasSemana) {
		firePropertyChange("horasSemana", this.horasSemana, horasSemana);
		this.horasSemana = horasSemana;
	}

	public String getAnnoCurso() {
		return this.annoCurso;
	}

	public void setAnnoCurso(String annoCurso) {
		firePropertyChange("annoCurso", this.annoCurso, annoCurso);
		this.annoCurso = annoCurso;
	}

	public Date getFInicio() {
		return this.FInicio;
	}

	public void setFInicio(Date FInicio) {
		firePropertyChange("annoCurso", this.annoCurso, annoCurso);
		this.FInicio = FInicio;
	}

	public Date getFFin() {
		return this.FFin;
	}

	public void setFFin(Date FFin) {
		firePropertyChange("FFin", this.FFin, FFin);
		this.FFin = FFin;
	}

	public Integer getMaxAlumnos() {
		return this.maxAlumnos;
	}

	public void setMaxAlumnos(Integer maxAlumnos) {
		firePropertyChange("maxAlumnos", this.maxAlumnos, maxAlumnos);
		this.maxAlumnos = maxAlumnos;
	}

	public BigDecimal getImpMatricula() {
		return this.impMatricula;
	}

	public void setImpMatricula(BigDecimal impMatricula) {
		firePropertyChange("impMatricula", this.impMatricula, impMatricula);
		this.impMatricula = impMatricula;
	}

	public BigDecimal getImpMes() {
		return this.impMes;
	}

	public void setImpMes(BigDecimal impMes) {
		firePropertyChange("impMes", this.impMes, impMes);
		this.impMes = impMes;
	}

	public BigDecimal getImpHora() {
		return this.impHora;
	}

	public void setImpHora(BigDecimal impHora) {
		firePropertyChange("impHora", this.impHora, impHora);
		this.impHora = impHora;
	}
	
	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		firePropertyChange("materia", this.materia, materia);
		this.materia = materia;
	}

	public Aula getAula() {
		return aula;
	}

	public void setAula(Aula aula) {
		firePropertyChange("aula", this.aula, aula);
		this.aula = aula;
	}

	public Profesor getProfesor() {
		return profesor;
	}
	
	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	
	public Set<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(Set<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

    public List<Recibo> getRecibos() {
		CursoHome ch = new CursoHome();
		return ch.listaRecibos(this);
	}
	public void setRecibos(List<Recibo> recibos) {
		this.recibos = recibos;
	}
	public void addMatricula(Matricula matricula) 
    { 
        this.matriculas.add(matricula); 
    } 
	

}
