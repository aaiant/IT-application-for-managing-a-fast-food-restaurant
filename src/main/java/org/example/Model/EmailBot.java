package org.example.Model;

import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.Properties;

public class EmailBot {
    public static class EmailResult {
        private final boolean succes;
        private final String mesaj;

        public EmailResult(boolean succes, String mesaj) {
            this.succes = succes;
            this.mesaj = mesaj;
        }

        public boolean isSuccess() { return succes; }
        public String getMessage() { return mesaj; }
    }

    public static EmailResult verificaSiTrimiteEmail(String expeditor, String parolaExpeditor, String destinatar, String subiect, String corpEmail) {
        if (!verificareAdresaEmail(destinatar)) {
            return new EmailResult(false, "Adresa de email destinatar invalidă: " + destinatar);
        }

        if (!verificareDomeniu(destinatar)) {
            return new EmailResult(false, "Domeniul adresei de email nu există sau nu are configurate înregistrări MX: " + destinatar);
        }

        Properties props = getProprietati(expeditor);
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(expeditor, parolaExpeditor);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(expeditor));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatar));
            message.setSubject(subiect);
            message.setText(corpEmail);

            Transport.send(message);
            return new EmailResult(true, "E-mail trimis cu succes către " + destinatar);
        } catch (MessagingException e) {
            return new EmailResult(false, "Eroare la trimiterea email-ului: " + e.getMessage());
        }
    }

    public static void trimiteEmail(String expeditor, String parolaExpeditor, String destinatar, String subiect, String corpEmail) {
        Properties props = getProprietati(expeditor);
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(expeditor, parolaExpeditor);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(expeditor));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatar));
            message.setSubject(subiect);
            message.setText(corpEmail);

            Transport.send(message);

            System.out.println("Email trimis cu succes de la " + expeditor + " către " + destinatar);

        } catch (MessagingException e) {
            throw new RuntimeException("Eroare la trimiterea email-ului: " + e.getMessage());
        }
    }

    public static boolean verificareAdresaEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public static boolean verificareDomeniu(String email) {
        String domeniu = email.substring(email.indexOf('@') + 1);
        try {
            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext dirContext = new InitialDirContext(env);
            Attributes attributes = dirContext.getAttributes(domeniu, new String[] { "MX" });
            Attribute attribute = attributes.get("MX");
            return (attribute != null && attribute.size() > 0);
        } catch (NamingException e) {
            return false;
        }
    }

    private static Properties getProprietati(String expeditor) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        if (expeditor.endsWith("@gmail.com")) {
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
        } else if (expeditor.endsWith("@yahoo.com")) {
            props.put("mail.smtp.host", "smtp.mail.yahoo.com");
            props.put("mail.smtp.port", "587");
        } else if (expeditor.endsWith("@outlook.com") || expeditor.endsWith("@hotmail.com") || expeditor.endsWith("@365.univ-ovidius.ro")) {
            props.put("mail.smtp.host", "smtp.office365.com");
            props.put("mail.smtp.port", "587");
        } else {
            throw new IllegalArgumentException("Adresa expeditorului necunoscută sau nesuportată: " + expeditor);
        }
        return props;
    }
}