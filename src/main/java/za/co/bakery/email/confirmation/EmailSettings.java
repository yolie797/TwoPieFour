package za.co.bakery.email.confirmation;

import java.io.File;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class EmailSettings {
    
    private final String senderEmail;
    private final String senderPassword;

    public EmailSettings(String senderEmail, String senderPassword) {
        this.senderEmail = senderEmail;
        this.senderPassword = senderPassword;
    }
    
    public EmailSettings() {
        this("topieforbakery2@gmail.com","jmswyppsiiadpexj");
    }

    public boolean sendEmail(String recipientEmail, String subject, String body) throws MessagingException {
        boolean sentEmail = false;
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

       Transport transport = session.getTransport();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());

            System.out.println("Email sent successfully!");
            sentEmail=true;
        
        return sentEmail;
    }
    public boolean sendEmail(String recipientEmail, String subject, Multipart body) throws MessagingException {
        boolean sentEmail = false;
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

       Transport transport = session.getTransport();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setContent(body);

            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());

            System.out.println("Email sent successfully!");
            sentEmail=true;
        
        return sentEmail;
    }

    public boolean sendRegistrationEmail(String userName, String recipientEmail, String confirmationCode) throws MessagingException {
        String subject = "Welcome to ToPieFor Online Bakery - Account Registration Confirmation";
        StringBuilder bodyBuilder = new StringBuilder();

        bodyBuilder.append("Dear ").append(userName).append(",\n\nThank you for joining ToPieFor Online Bakery ! Your account registration is nearly complete.")
                .append("\n\nUse this code to activate your account: ")
                .append(confirmationCode).append("\n\n")
                .append("If you have any questions or need assistance, please contact our support team at 0783051442.\n\n")
                .append("Welcome aboard\n\nToPieFor Bakery Team");
        String body = bodyBuilder.toString();

        return sendEmail(recipientEmail, subject, body);
    }
    
    public boolean sendInvoiceEmail(String userName, String recipientEmail, File pdfFile) throws MessagingException {
        String subject = "ToPieFor Online Bakery - INVOICE";
        BodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        
        messageBodyPart.setText("Dear " + userName + ",\n\nThank you for purchase on our product(s)! Please find invoice attachment.\n\nKind regards\n\nToPieFor Bakery Team");
        multipart.addBodyPart(messageBodyPart);
        messageBodyPart = new MimeBodyPart();
        
        messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(pdfFile.getAbsolutePath())));
        messageBodyPart.setFileName("invoice.pdf");
        multipart.addBodyPart(messageBodyPart);
        
        return sendEmail(recipientEmail, subject, multipart);
    }

    public void sendPasswordRecoveryEmail(String recipientEmail, String confirmationCode) throws MessagingException {
        String subject = " ToPieFor Password Recovery System";
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("Click the following link to reset your password:\n")
                .append("http://localhost:8080/ToPieFor/customer/passwordrecovery.jsp")
                .append("\n\nUse this code to activate your account")
                .append(confirmationCode)
                .append("\n\nIf you have any questions or need assistance, please contact our support team at 0783051442.\n\n")
                .append("ToPieFor Bakery Team");

        String body = bodyBuilder.toString();

        sendEmail(recipientEmail, subject, body);
    }
}

