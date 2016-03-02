package es.academia.modelo;

// Generated 21-jul-2014 23:03:01 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

public class MatriculaCurso extends AbstractModelObject implements java.io.Serializable {

	private Integer idCurso;
	private String descCurso;
	private Integer horasSemana;
	private String annoCurso;
	private Date FInicio;
	private Date FFin;

	private Integer idMatricula;
	private int idAlumno;
	private Date FDesde;
	private Date FHasta;
	private Date fecUltReciboGen;
	private BigDecimal pmatricula;
	private BigDecimal impMes;
	private BigDecimal impHora;

	public MatriculaCurso() {
	}

	public MatriculaCurso(String descCurso, 
			String annoCurso, Date FInicio, Date FFin,
			BigDecimal pmatricula, BigDecimal impMes,
			BigDecimal impHora, Date fecUltReciboGen, Date FDesde, 
			Date FHasta, int idAlumno, Integer idMatricula) {

		this.descCurso = descCurso;
		this.annoCurso = annoCurso;
		this.FInicio = FInicio;
		this.FFin = FFin;
		this.impMes = impMes;
		this.impHora = impHora;
		this.idAlumno = idAlumno;
		this.FDesde = FDesde;
		this.FHasta = FHasta;
		this.fecUltReciboGen = fecUltReciboGen;
		this.pmatricula = pmatricula;
		this.impHora = impHora;
		
	}

	public Integer getIdCurso() {
		return this.idCurso;
	}

	public void setIdCurso(Integer idCurso) {
		firePropertyChange("conocimiento", this.idCurso, idCurso);
		this.idCurso = idCurso;
	}

	public String getDescCurso() {
		return this.descCurso;
	}

	public void setDescCurso(String descCurso) {
		firePropertyChange("conocimiento", this.descCurso, descCurso);
		this.descCurso = descCurso;
	}

	public Integer getHorasSemana() {
		return this.horasSemana;
	}

	public void setHorasSemana(Integer horasSemana) {
		firePropertyChange("conocimiento", this.horasSemana, horasSemana);
		this.horasSemana = horasSemana;
	}

	public String getAnnoCurso() {
		return this.annoCurso;
	}

	public void setAnnoCurso(String annoCurso) {
		firePropertyChange("conocimiento", this.annoCurso, annoCurso);
		this.annoCurso = annoCurso;
	}

	public Date getFInicio() {
		return this.FInicio;
	}

	public void setFInicio(Date FInicio) {
		firePropertyChange("conocimiento", this.FInicio, FInicio);
		this.FInicio = FInicio;
	}

	public Date getFFin() {
		return this.FFin;
	}

	public void setFFin(Date FFin) {
		firePropertyChange("conocimiento", this.FFin, FFin);
		this.FFin = FFin;
	}

	public Integer getIdMatricula() {
		return this.idMatricula;
	}

	public void setIdMatricula(Integer idMatricula) {
		firePropertyChange("conocimiento", this.idMatricula, idMatricula);
		this.idMatricula = idMatricula;
	}

	public int getIdAlumno() {
		return this.idAlumno;
	}

	public void setIdAlumno(int idAlumno) {
		firePropertyChange("conocimiento", this.idAlumno, idAlumno);
		this.idAlumno = idAlumno;
	}
	public Date getFDesde() {
		return this.FDesde;
	}

	public void setFDesde(Date FDesde) {
		firePropertyChange("conocimiento", this.FDesde, FDesde);
		this.FDesde = FDesde;
	}

	public Date getFHasta() {
		return this.FHasta;
	}

	public void setFHasta(Date FHasta) {
		firePropertyChange("conocimiento", this.FHasta, FHasta);
		this.FHasta = FHasta;
	}

	public Date getFecUltReciboGen() {
		return this.fecUltReciboGen;
	}

	public void setFecUltReciboGen(Date fecUltReciboGen) {
		firePropertyChange("conocimiento", this.fecUltReciboGen, fecUltReciboGen);
		this.fecUltReciboGen = fecUltReciboGen;
	}

	public BigDecimal getPmatricula() {
		return this.pmatricula;
	}

	public void setPmatricula(BigDecimal pmatricula) {
		firePropertyChange("conocimiento", this.pmatricula, pmatricula);
		this.pmatricula = pmatricula;
	}

	public BigDecimal getImpMes() {
		return this.impMes;
	}

	public void setImpMes(BigDecimal impMes) {
		firePropertyChange("conocimiento", this.impMes, impMes);
		this.impMes = impMes;
	}

	public BigDecimal getImpHora() {
		return this.impHora;
	}

	public void setImpHora(BigDecimal impHora) {
		firePropertyChange("conocimiento", this.impHora, impHora);
		this.impHora = impHora;
	}

}
