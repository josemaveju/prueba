package es.academia.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.osgi.service.prefs.Preferences;

import es.academia.modelo.Alumno;

public class EmailSenderService implements IConstantes{
	private static final Logger log = ACALog.getLogger(EmailSenderService.class);
	
	private final Properties properties = new Properties();
	
	private String password ;

	private Session session;

	private void init() {
       Preferences preferences = ConfigurationScope.INSTANCE.getNode("es.academiaRCP");
       Preferences sub1 = preferences.node("mailPreferences");
 
		properties.put("mail.smtp.host", sub1.get(HOST_PREF, ""));
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port",sub1.get(PUERTO_PREF, ""));
		properties.put("mail.smtp.mail.sender",sub1.get(REMITENTE_PREF, ""));
		properties.put("mail.smtp.user", sub1.get(USUARIO_PREF, ""));
		properties.put("mail.smtp.auth", "true");
		password = sub1.get(CONTRASEÑA_PREF, "");
		

		session = Session.getDefaultInstance(properties);
	}

	public void sendEmail(String direccion, String asunto, String mensaje) throws MessagingException{

		init();
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress((String)properties.get("mail.smtp.mail.sender")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(direccion));
			message.setSubject(asunto);
			message.setText(mensaje);
			Transport t = session.getTransport("smtp");
			t.connect((String)properties.get("mail.smtp.user"), password);
			t.sendMessage(message, message.getAllRecipients());
			t.close();
		}catch (MessagingException me){
			log.error("Error enviando e-mail: "+me.getMessage());
			throw me;
		}
		
	}

}
