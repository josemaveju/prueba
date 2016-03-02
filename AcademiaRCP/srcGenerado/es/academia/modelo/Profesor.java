package es.academia.modelo;

// Generated 21-jul-2014 23:03:01 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

import es.academia.utils.ACALog;

/**
 * Profesor generated by hbm2java
 */
public class Profesor extends AbstractModelObject implements java.io.Serializable {

	private static final Logger log = ACALog.getLogger(Profesor.class);
	
	private Integer idProfesor;
	private String nombre;
	private String apellidos;
	private String nif;
	private Date FNacimiento;
	private String direccion;
	private String provincia;
	private String poblacion;
	private Integer cp;
	private String telFijo;
	private String telMovil;
	private String email;
	private String titulacion;
	private String numCuenta;
	private String numCtaContable;
	private BigDecimal impFijo;
	private BigDecimal porcRecibo;
	private Date FAlta;
	private Date FBaja;
	private String estado;

	public Profesor() {
	}

	public Profesor(String nombre, String apellidos, String nif,
			Date FNacimiento, String direccion, String provincia,
			String poblacion, Integer cp, String telFijo, String telMovil,
			String email, String titulacion, String numCuenta,
			String numCtaContable, BigDecimal impFijo, BigDecimal porcRecibo,
			Date FAlta, Date FBaja, String estado) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nif = nif;
		this.FNacimiento = FNacimiento;
		this.direccion = direccion;
		this.provincia = provincia;
		this.poblacion = poblacion;
		this.cp = cp;
		this.telFijo = telFijo;
		this.telMovil = telMovil;
		this.email = email;
		this.titulacion = titulacion;
		this.numCuenta = numCuenta;
		this.numCtaContable = numCtaContable;
		this.impFijo = impFijo;
		this.porcRecibo = porcRecibo;
		this.FAlta = FAlta;
		this.FBaja = FBaja;
		this.estado = estado;
	}

	public Integer getIdProfesor() {
		return this.idProfesor;
	}

	public void setIdProfesor(Integer idProfesor) {
		firePropertyChange("idProfesor", this.idProfesor, idProfesor);
		this.idProfesor = idProfesor;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		firePropertyChange("nombre", this.nombre, nombre);
		this.nombre = nombre;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		firePropertyChange("apellidos", this.apellidos, apellidos);
		this.apellidos = apellidos;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		firePropertyChange("nif", this.nif, nif);
		this.nif = nif;
	}

	public Date getFNacimiento() {
		return this.FNacimiento;
	}

	public void setFNacimiento(Date FNacimiento) {
		firePropertyChange("FNacimiento", this.FNacimiento, FNacimiento);
		this.FNacimiento = FNacimiento;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		firePropertyChange("direccion", this.direccion, direccion);
		this.direccion = direccion;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		firePropertyChange("provincia", this.provincia, provincia);
		this.provincia = provincia;
	}

	public String getPoblacion() {
		return this.poblacion;
	}

	public void setPoblacion(String poblacion) {
		firePropertyChange("poblacion", this.poblacion, poblacion);
		this.poblacion = poblacion;
	}

	public Integer getCp() {
		return this.cp;
	}

	public void setCp(Integer cp) {
		firePropertyChange("cp", this.cp, cp);
		this.cp = cp;
	}

	public String getTelFijo() {
		return this.telFijo;
	}

	public void setTelFijo(String telFijo) {
		firePropertyChange("telFijo", this.telFijo, telFijo);
		this.telFijo = telFijo;
	}

	public String getTelMovil() {
		return this.telMovil;
	}

	public void setTelMovil(String telMovil) {
		firePropertyChange("telMovil", this.telMovil, telMovil);
		this.telMovil = telMovil;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		firePropertyChange("email", this.email, email);
		this.email = email;
	}

	public String getTitulacion() {
		return this.titulacion;
	}

	public void setTitulacion(String titulacion) {
		firePropertyChange("titulacion", this.titulacion, titulacion);
		this.titulacion = titulacion;
	}

	public String getNumCuenta() {
		return this.numCuenta;
	}

	public void setNumCuenta(String numCuenta) {
		firePropertyChange("numCuenta", this.numCuenta, numCuenta);
		this.numCuenta = numCuenta;
	}

	public String getNumCtaContable() {
		return this.numCtaContable;
	}

	public void setNumCtaContable(String numCtaContable) {
		firePropertyChange("numCtaContable", this.numCtaContable, numCtaContable);
		this.numCtaContable = numCtaContable;
	}

	public BigDecimal getImpFijo() {
		log.debug("getImporteFijo");
		return this.impFijo;
	}

	public void setImpFijo(BigDecimal impFijo) {
		log.debug("setImporteFijo");
		firePropertyChange("impFijo", this.impFijo, impFijo);
		this.impFijo = impFijo;
	}

	public BigDecimal getPorcRecibo() {
		log.debug("getPorcRecibo");
		return this.porcRecibo;
	}

	public void setPorcRecibo(BigDecimal porcRecibo) {
		log.debug("setPorcRecibo");
		firePropertyChange("porcRecibo", this.porcRecibo, porcRecibo);
		this.porcRecibo = porcRecibo;
	}

	public Date getFAlta() {
		return this.FAlta;
	}

	public void setFAlta(Date FAlta) {
		firePropertyChange("FAlta", this.FAlta, FAlta);
		this.FAlta = FAlta;
	}

	public Date getFBaja() {
		return this.FBaja;
	}

	public void setFBaja(Date FBaja) {
		firePropertyChange("FBaja", this.FBaja, FBaja);
		this.FBaja = FBaja;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		firePropertyChange("estado", this.estado, estado);
		this.estado = estado;
	}

}
