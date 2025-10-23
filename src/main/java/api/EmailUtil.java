package api;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailUtil {

    public static void sendEmail(String toEmail, String subject, String messageText) throws MessagingException {
        final String fromEmail = "hmduy015@gmail.com";
        final String password = "lkqk jsfe jseh fpax";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(fromEmail, false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        msg.setSubject(subject);
        msg.setContent(messageText, "text/html; charset=utf-8");
        Transport.send(msg);
    }

    public static boolean isValidEmail(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }

    public static void sendResetLink(String toEmail, String token) {
        try {
            String resetLink = "http://localhost:8080/HRMSystem/recovery?token=" + token;
            String subject = "Password Reset Request";
            String content = "<p>Click <a href='" + resetLink + "'>here</a> to reset your password.</p>"
                    + "<p><b>Note:</b> This link will expire in 5 minutes.</p>";
            sendEmail(toEmail, subject, content);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
