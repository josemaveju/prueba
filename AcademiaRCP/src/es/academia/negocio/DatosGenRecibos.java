package es.academia.negocio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import es.academia.modelo.Recibo;
import es.academia.modelo.ReciboHome;
import es.academia.modelo.Serierecibo;
import es.academia.modelo.SeriereciboHome;
import es.academia.utils.ACALog;
import es.academia.widgets.DateAca;

import org.apache.log4j.Logger;
import org.hibernate.Session;

public class DatosGenRecibos {

	private static final Logger log = ACALog.getLogger(MatricularAlumno.class);
	
	Date mesGeneracion;
	Date fechaEmision;
	Date fechaFin;
	boolean todos;
	boolean unCurso;
	Integer idCurso;
	String concepto;
	List <Recibo> listaRecibos;
	

	
	public Date getMesGeneracion() {
		return mesGeneracion;
	}
	public void setMesGeneracion(Date mesGeneracion) {
		this.mesGeneracion = mesGeneracion;
	}
	
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public boolean isTodos() {
		return todos;
	}
	public void setTodos(boolean todos) {
		this.todos = todos;
	}
	
	public boolean isUnCurso() {
		return unCurso;
	}
	public void setUnCurso(boolean unCurso) {
		this.unCurso = unCurso;
	}
	
	public Integer getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}
	
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	
	public List<Recibo> getListaRecibos() {
		return listaRecibos;
	}
	public void setListaRecibos(ArrayList<Recibo> listaRecibos) {
		this.listaRecibos = listaRecibos;
	}

	
	/*
	 * Se encarga de eliminar un recibo de la lista de recibos
	 */
	public void removeRecibo(Integer recibo){
		Iterator it = listaRecibos.iterator();
		
		while (it.hasNext()){
			Recibo rec = (Recibo)it.next();
			if (rec.getIdRecibo().intValue() == recibo.intValue())
				it.remove();
		}
	}
	
	public void obtenerListaRecibos(){
		ReciboHome rch = new ReciboHome();
		
		listaRecibos = rch.findListaRecibos(mesGeneracion, todos, unCurso, idCurso);
	}

	public void generarRecibos() throws NegocioException{
		// 	Método para generar los recibos seleccionados.
		ReciboHome rch = new ReciboHome();
		Iterator it = listaRecibos.iterator();
		
		Session sesion = rch.obtenerSesion();
		
		rch.abrirTransaccion(sesion);
		while (it.hasNext()){
			
			Serierecibo sr = obtenerSiguienteRecibo();
			int sigRecibo = sr.getSigRecibo();
			Recibo rec = (Recibo) it.next();
 
			// Asignamos valor a todos los campos variables	
			rec.setEstadoRecibo(Recibo.ESTADOGENERADO);
			rec.setConcepto(this.concepto);
			rec.setFDesde(this.fechaEmision);
			rec.setFHasta(this.fechaFin);

			//rec.setFDesde(this.fechaEmision);
			//rec.setFHasta(this.fechaFin);
			Date hoy = new Date();
			hoy.setTime(System.currentTimeMillis());
			rec.setFGeneracion(hoy);
			
			rec.setNumRecibo(Integer.valueOf(sigRecibo++));
			rec.setIdSerie(sr);
			// Datos contables
			rec.setNumHoras(rec.getMatricula().getHorasDefecto());
			rec.setImpMes(rec.getMatricula().getImpMes());
			rec.setPrecHora(rec.getMatricula().getImpMes());
			rec.setDescuento(rec.getMatricula().getDescuento());
			// Calculo de los importes
			double impMes = rec.getImpMes().doubleValue();
			double descuento = rec.getDescuento().doubleValue();
			double precioHora = rec.getPrecHora().doubleValue();
			double numHoras = rec.getNumHoras().doubleValue();
			
			double impoHoras=precioHora * numHoras;
			double impoTotal = impoHoras + impMes;
			
			double impDescuento = impoTotal * descuento / 100;
			double impoTotDescuento = impoTotal - impDescuento;
			
			rec.setImpHoras(BigDecimal.valueOf(impoHoras));
			rec.setImpTotal(BigDecimal.valueOf(impoTotal));
			rec.setDescuento(BigDecimal.valueOf(impDescuento));
			rec.setImpoTotalDescuento(BigDecimal.valueOf(impoTotDescuento));

			log.debug("Recibo generado: " + rec.getIdRecibo());
			log.debug("Importe: " +rec.getImpTotal());
			log.debug("Importe descuento: " +rec.getImpoDescuento());
			log.debug("Importe: " +rec.getImpoTotalDescuento());
			log.debug("----------------------------------");
			try{
				rch.actSinCommit(rec, sesion);
			}
			catch (RuntimeException re){
				rch.hacerRollback(sesion);
				throw new NegocioException("Error generando recibos ", re.getMessage());
			}
		}

		// si no ha habido error hacemos COMMIT;
		rch.hacerCommit(sesion);
		
	}
	
	private Serierecibo obtenerSiguienteRecibo() {
		// TODO Auto-generated method stub
		Serierecibo sr = new Serierecibo();
		SeriereciboHome srh = new SeriereciboHome();
		
		sr.setDefecto("S");
		
		Serierecibo sr2 = (Serierecibo) srh.findByExample(sr).get(0);
		return sr2;
	}
	
	 
	public void emitirRecibos(){
		
		// Método para emitir los recibos seleccionados
	}
	
}
