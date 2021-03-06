package com.pallol.novela.util;

import com.pallol.novela.dto.EspecificacionEmail;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class ContactoEmail {
    
    private EspecificacionEmail cotizacionServicioEmail;

    public ContactoEmail(EspecificacionEmail cotizacionServicioEmail) {
        this.cotizacionServicioEmail = cotizacionServicioEmail;
    }

    public boolean enviaEmail() {
        boolean comprobacion = false;
        // Recipient's email ID needs to be mentioned.
        String to = cotizacionServicioEmail.getEmailDestino();

        // Sender's email ID needs to be mentioned
        String from = cotizacionServicioEmail.getEmailOrigen();

        final String usuario = cotizacionServicioEmail.getEmailOrigen();//change accordingly
        final String passwd = cotizacionServicioEmail.getPasswd();//change accordingly

        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.auth", "true");  // If you need to authenticate
        propiedades.put("mail.transport.protocol", "smtp");
        propiedades.put("mail.smtp.port", "587"); //TLS Port
        propiedades.put("mail.smtp.user", cotizacionServicioEmail.getEmailOrigen());
        propiedades.put("mail.smtp.clave", cotizacionServicioEmail.getPasswd());    //La clave de la cuenta


        Session session = Session.getInstance(propiedades,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, passwd);
            }
        });

        try {
            Transport transport = session.getTransport("smtp");
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from, cotizacionServicioEmail.getNombreRemitente() != null && "".equals(cotizacionServicioEmail.getNombreRemitente()) == false ? cotizacionServicioEmail.getNombreRemitente() : cotizacionServicioEmail.getEmailOrigen()));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(from));

            // Set Subject: header field
            message.setSubject(cotizacionServicioEmail.getAsunto());

            Multipart htmlMultipart = new MimeMultipart("related");

            // Part two is attachment
            MimeBodyPart htmlMimePart = new MimeBodyPart();

            String mensajeHTML = cotizacionServicioEmail.getMensajeHTML()
                    + (cotizacionServicioEmail.getUrlImagenCuerpoCorreo() != null ? "<br/><img src=\"cid:nanomotrix_cuerpo\"><br/>" : "")
                    + (cotizacionServicioEmail.getUrlImagenFirma() != null ? "<br/><img width=\"582\" height=\"229\" src=\"cid:nanomotrix_firma\"><br/>" : "")
                    + (cotizacionServicioEmail.getPieDePagina() != null ? cotizacionServicioEmail.getPieDePagina() : "");
            htmlMimePart.setContent(mensajeHTML, "text/html; charset=utf-8");
            htmlMultipart.addBodyPart(htmlMimePart);
//

//
            MimeBodyPart htmlAndTextBodyPart = new MimeBodyPart();
            htmlAndTextBodyPart.setContent(htmlMultipart);
//
            Multipart multipart = new MimeMultipart("mixed");
            multipart.addBodyPart(htmlAndTextBodyPart);
//

            message.setContent(multipart);

            // Send message
            transport.connect("smtp.gmail.com", cotizacionServicioEmail.getEmailOrigen(), cotizacionServicioEmail.getPasswd());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
//            Transport.send(message);
            System.out.println("Sent message successfully....");
            comprobacion = true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LanzadorDeEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comprobacion;
    }
}
