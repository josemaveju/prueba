package es.academia.negocio;

import java.math.BigDecimal;

 

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import es.academia.modelo.Alumno;
import es.academia.modelo.AlumnoHome;
import es.academia.modelo.Curso;
import es.academia.modelo.CursoHome;
import es.academia.modelo.HibernateUtil;
import es.academia.modelo.Matricula;
import es.academia.modelo.MatriculaHome;
import es.academia.modelo.Recibo;
import es.academia.modelo.ReciboHome;
import es.academia.modelo.Serierecibo;
import es.academia.modelo.SeriereciboHome;
import es.academia.utils.ACALog;
import es.academia.utils.IConstantes;
import es.academia.widgets.DateAca;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MatricularAlumno {
//	private Matricula matricula;
	private static final Logger log = ACALog.getLogger(MatricularAlumno.class);
	
	public MatricularAlumno(){
	
//		this.matricula = matricula;
	}
	
	public static Matricula obtenerDatosDefecto(Integer idCurso, Integer idAlumno) throws NegocioException{
		log.debug ("Entrando en obtenerDatosDefecto");
		
		Matricula mat = new Matricula();

		// Obtener los datos del curso
		CursoHome ch = new CursoHome();
		Curso curso = ch.findById(idCurso);
		
		if (curso==null){
			throw new NegocioException(NegocioException.ERROR_CREANDO_MATRICULA,
					   "Error al crear la matricula. Curso no existe","MATRICULAR_ALUMNO");
		}

		// Obtener los datos del alumno
		AlumnoHome alh = new AlumnoHome();
		Alumno alumno = alh.findById(idAlumno);
		
		if (alumno==null){
			throw new NegocioException(NegocioException.ERROR_CREANDO_MATRICULA,
					   "Error al crear la matricula. Alumno no existe","MATRICULAR_ALUMNO");
		}

		mat.setAlumno(alumno);
		mat.setCurso(curso);
		mat.setFDesde(curso.getFInicio());
		mat.setFHasta(curso.getFFin());
		mat.setFecUltReciboGen(null);
		mat.setPmatricula(curso.getImpMatricula());
		mat.setImpHora(curso.getImpHora());
		mat.setImpMes(curso.getImpMes());
		mat.setHorasDefecto(curso.getHorasSemana());
		mat.setDescuento(new BigDecimal(0));

		return mat;
	}
	
	
	public void matricular(Matricula matricula, List<Recibo> recibos)  throws NegocioException{

		ReciboHome rh = new ReciboHome();
		Recibo rec;
		int siguienteRecibo ;

		// Validamos que no sean null 
	
		if (matricula == null)
			throw new NegocioException(NegocioException.ERROR_CREANDO_MATRICULA,
					   "Error al crear la matricula. Matricula no debe ser nula","MATRICULAR_ALUMNO");
		
		
		Session sesion= HibernateUtil.getSessionFactory().getCurrentSession();	
		
		try {
			sesion.beginTransaction();
			
			// Validaciones del recibo.
			validarMatricula(matricula);

			// Insertar en la tabla Matricula
			MatriculaHome mh = new MatriculaHome();
			mh.persist(matricula,sesion);
			
			// Insertar los recibos.
			SeriereciboHome srh = new SeriereciboHome();
			Serierecibo sr = srh.obtenerSerieDefecto(sesion);
			siguienteRecibo = sr.getSigRecibo();
			
			Iterator it = recibos.iterator();
			while (it.hasNext()){	
				rec = (Recibo)it.next();
				rec.setIdMatricula(matricula.getIdMatricula());
				rec.setMatricula(matricula);
				rec.setNumRecibo(new Integer(siguienteRecibo++));
				rh.persist(rec,sesion);
			}

			sr.setSigRecibo(siguienteRecibo);
			srh.persist(sr,sesion);

			// Si todo ha ido bien hacemos COMMIT de todo.
			sesion.getTransaction().commit();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			sesion.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
		
		
		
	}
	
	private void insertarRecibos(List<Recibo> recibos,Matricula matricula, Session sesion) {
		
	}

	private void validarMatricula(Matricula matricula)  throws NegocioException{
		// TODO Auto-generated method stub
		
	}

	private ArrayList<Recibo> generarRecibos(Matricula matricula, Session sesion) throws NegocioException{
	
		ArrayList<Recibo> listaRecibos = new ArrayList<Recibo>();
		
		Calendar desde = GregorianCalendar.getInstance();
		Calendar hasta = GregorianCalendar.getInstance();
		Calendar hastaFinMes = GregorianCalendar.getInstance();
		Calendar cl = GregorianCalendar.getInstance();
		Calendar finMesCl= Calendar.getInstance();
		Calendar iniMesCl = Calendar.getInstance();
		
		int siguienteRecibo=1;

		try {
			// Valores fijos para todos los recibos
		
			// Generar todos los recibos
			desde.setTime(matricula.getFDesde());
			hasta.setTime(matricula.getFHasta());
			hastaFinMes.setTime(matricula.getFHasta());
			hastaFinMes.set(Calendar.DAY_OF_MONTH,hastaFinMes.getMaximum(Calendar.DAY_OF_MONTH));
			cl.setTime(desde.getTime());
			
		//	ReciboHome rh = new ReciboHome();
			
			log.debug("Antes de empezar el bucle:");
			log.debug("desde: " + desde.get(Calendar.YEAR)+"/"+(desde.get(Calendar.MONTH)+1)+"/"+desde.get(Calendar.DAY_OF_MONTH));
			log.debug("hasta: " + hasta.get(Calendar.YEAR)+"/"+(hasta.get(Calendar.MONTH)+1)+"/"+hasta.get(Calendar.DAY_OF_MONTH));
			log.debug("hastaFinMes: " + hastaFinMes.get(Calendar.YEAR)+"/"+(hastaFinMes.get(Calendar.MONTH)+1)+"/"+hastaFinMes.get(Calendar.DAY_OF_MONTH));
			log.debug("cl: " + cl.get(Calendar.YEAR)+"/"+(cl.get(Calendar.MONTH)+1)+"/"+cl.get(Calendar.DAY_OF_MONTH));
			
			while (cl.compareTo(hastaFinMes)<=0){
				finMesCl.setTime(cl.getTime());
				iniMesCl.setTime(cl.getTime());
				iniMesCl.set(Calendar.DAY_OF_MONTH, cl.getActualMinimum(Calendar.DAY_OF_MONTH));
				finMesCl.set(Calendar.DAY_OF_MONTH, cl.getActualMaximum(Calendar.DAY_OF_MONTH));
				log.debug("dentro del bucle:");
				log.debug("cl: " + cl.get(Calendar.YEAR)+"/"+(cl.get(Calendar.MONTH)+1)+"/"+cl.get(Calendar.DAY_OF_MONTH));
				log.debug("iniMesCl: " + iniMesCl.get(Calendar.YEAR)+"/"+(iniMesCl.get(Calendar.MONTH)+1)+"/"+iniMesCl.get(Calendar.DAY_OF_MONTH));
				log.debug("finMesCl: " + finMesCl.get(Calendar.YEAR)+"/"+(finMesCl.get(Calendar.MONTH)+1)+"/"+finMesCl.get(Calendar.DAY_OF_MONTH));
				Recibo rec = new Recibo();
				//rec.setIdMatricula(matricula.getIdMatricula());
				rec.setIdMatricula(1);
				rec.setFGeneracion(null);
				
				if (matricula.getImpMes() == null)
					rec.setImpMes(new BigDecimal(0));
				else 
					rec.setImpMes(matricula.getImpMes());

				if (matricula.getHorasDefecto() == null)
					rec.setNumHoras(new Integer(0));
				else 
					rec.setNumHoras(matricula.getHorasDefecto());
				
				if (matricula.getImpHora() == null)
					rec.setPrecHora(new BigDecimal(0));
				else 
					rec.setPrecHora(matricula.getImpHora());

				if (rec.getNumHoras()== null ||rec.getPrecHora()==null)
					rec.setImpHoras(new BigDecimal(0));
				else
					rec.setImpHoras(new BigDecimal(rec.getNumHoras().doubleValue()*rec.getPrecHora().doubleValue()));
				
/*				log.debug("Número de horas matricula: " + matricula.getHorasDefecto().intValue());
				log.debug("Número de horas recibo: " + rec.getNumHoras().intValue());
				log.debug("Precio horas matricula: " + matricula.getImpHora().doubleValue());
				log.debug("Precio horas recibo: " + rec.getPrecHora().doubleValue());
				log.debug("Importe horas recibo: " + rec.getImpHoras().doubleValue());
*/				
				if (matricula.getDescuento() ==null)
					rec.setDescuento(new BigDecimal(0));
				else 
					rec.setDescuento(matricula.getDescuento());
				
				double importeTotal=0.0;
				double importeMes= rec.getImpMes().doubleValue();
				double importeHoras = rec.getImpHoras().doubleValue();
				double descuento = rec.getDescuento().doubleValue();
				
				importeTotal = (importeMes + importeHoras)- ((importeMes+importeHoras)*descuento) / 100;
				rec.setImpTotal(new BigDecimal(importeTotal));
				
				rec.setPagado("N");
				rec.setIndExportado("N");
				rec.setEstadoRecibo(Recibo.ESTADOPENDIENTE);
				
				// Obtener la serie de los recibos en curso 
				
				SeriereciboHome srh = new SeriereciboHome();
				Serierecibo sr = srh.obtenerSerieDefecto(sesion);
			//	siguienteRecibo = sr.getSigRecibo();
			//	sr.setSigRecibo(siguienteRecibo+1);
			//	srh.persist(sr,sesion);
				rec.setIdSerie(sr);
				
				if (finMesCl.compareTo(hasta)>0)
					finMesCl.setTime(hasta.getTime());
				
				String concepto =Recibo.CONCEPTOFIJO + IConstantes.MESES[cl.get(Calendar.MONTH)] 
						          + " de " + cl.get(Calendar.YEAR);
				rec.setConcepto(concepto);
				rec.setFDesde(iniMesCl.getTime());
				rec.setFHasta(finMesCl.getTime());
				
			//	rec.setNumRecibo(new Integer(siguienteRecibo));
			//	rh.persist(rec,sesion);
				listaRecibos.add(rec);
				
				cl.add(Calendar.MONTH, 1);
		}
		}catch (Exception e){
			e.printStackTrace();
			throw new NegocioException(NegocioException.ERROR_CREANDO_MATRICULA,
					   "Error al crear la matricula. Generando recibos por defecto.\n" +e.getMessage()
					   ,"MATRICULAR_ALUMNO");
		}
		return listaRecibos;
		
	}

	public List<Recibo> obtenerRecibos(Matricula matricula) throws Exception {
		Session sesion= HibernateUtil.getSessionFactory().getCurrentSession();
		List <Recibo> listaRecibos =null;
		try {
			sesion.beginTransaction();
			listaRecibos = generarRecibos(matricula,sesion);
			// Si todo ha ido bien hacemos COMMIT de todo.
			sesion.getTransaction().commit();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			sesion.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
		return listaRecibos;
		
	}
	

}
