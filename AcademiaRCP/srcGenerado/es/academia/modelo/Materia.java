package es.academia.modelo;

// Generated 21-jul-2014 23:03:01 by Hibernate Tools 3.4.0.CR1

/**
 * Materia generated by hbm2java
 */
public class Materia implements java.io.Serializable {

	private Integer idMateria;
	private String descMateria;

	public Materia() {
	}

	public Materia(String descMateria) {
		this.descMateria = descMateria;
	}

	public Integer getIdMateria() {
		return this.idMateria;
	}

	public void setIdMateria(Integer idMateria) {
		this.idMateria = idMateria;
	}

	public String getDescMateria() {
		return this.descMateria;
	}

	public void setDescMateria(String descMateria) {
		this.descMateria = descMateria;
	} 

	public boolean equals(Object materia){
		if (materia != null && materia instanceof Materia)
			if (((Materia)materia).getIdMateria() != null &&
					((Materia)materia).getIdMateria().intValue()== this.idMateria.intValue())
				return true;
			else
				return false;
		else
			return false;
	}
	
	
}
