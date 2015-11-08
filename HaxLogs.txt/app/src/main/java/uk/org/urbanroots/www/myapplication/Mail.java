package uk.org.urbanroots.www.myapplication;

import java.util.Date;
import java.util.Properties;
import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import android.content.SharedPreferences;
import android.app.Activity;
import android.widget.TextView;
import android.widget.EditText;

 // Background mail sender class
public class Mail extends javax.mail.Authenticator {
    private String _user;
    private String _pass;
// Mail receivers to be edited
    private String[] _to={"receipientEmail","Receipient2Email"};
    private String _from;

    private String _port;
    private String _sport;

    private String _host;

    private String _subject;
    private String _body;

    private boolean _auth;

    private boolean _debuggable;

    private Multipart _multipart;


    public Mail() {
        _host = "smtp.gmail.com"; // default smtp server
        _port = "465"; // default smtp port
        _sport = "465"; // default socketfactory port
        _user = ""; // username to be changed further down
        _pass = ""; // password to be changed further down
        _from = "Sender"; // email sent from
        _subject = "New member"; // email subject
        _body = ""; // email body to be changed further down

        _debuggable = false; // debug mode on or off - default off
        _auth = true; // smtp authentication - default on

        _multipart = new MimeMultipart();

        // There is something wrong with MailCap, javamail can not find a handler for the multipart/mixed part, so this bit needs to be added.
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
    }

    // Create new mail instance passing the data we need.
    public Mail(String user, String pass,String name,String mobile,String email,String postcode,String interests,String availability) {
        this();
        _body ="New member name : "+name+"\n"+"Mobile number : "+mobile +"\n"+"Email : "+email + "\n"+"Postcode : "+postcode+"\n"+"Interests : "+interests +"\n"+"Avalability : "+availability;
        _user = user;
        _pass = pass;
    }
//ToDo
    public Mail(String user,String pass,String subject,String message,String date)
    {
        this();
        _body=date+"    "+message;
        _user = user;
        _pass = pass;
        _subject=subject;
    }

// Not needed
    public boolean send(String s) throws  Exception{
        Properties props = _setProperties();

        //    if(!_user.equals("") && !_pass.equals("") && _to.length > 0 && !_from.equals("") && !_subject.equals("") && !_body.equals("")) {
        Session session = Session.getInstance(props, this);

        final MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(_from));
        InternetAddress[] addressTo = new InternetAddress[_to.length];
        for (int i = 0; i < _to.length; i++) {
            addressTo[i] = new InternetAddress(_to[i]);
        }
        msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);
        msg.setSubject(_subject);
        msg.setSentDate(new Date());
// setup message body
        BodyPart messageBodyPart = new MimeBodyPart();
        MimeBodyPart imagePart = new MimeBodyPart();

        imagePart.attachFile(s);
       // messageBodyPart.attachFile("resources/"+s);
       // messageBodyPart.setText(_body);
        _multipart.addBodyPart(imagePart);

// Put parts in message
        msg.setContent(_multipart);

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
// send email
                    Transport.send(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


        return true;
    /*    } else {
            return false;
        }
        */
    }

    // method to send the mail
    public boolean send() throws Exception {
        Properties props = _setProperties();

        //    if(!_user.equals("") && !_pass.equals("") && _to.length > 0 && !_from.equals("") && !_subject.equals("") && !_body.equals("")) {
        Session session = Session.getInstance(props, this);

        final MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(_from));
        InternetAddress[] addressTo = new InternetAddress[_to.length];
        for (int i = 0; i < _to.length; i++) {
            addressTo[i] = new InternetAddress(_to[i]);
        }
        msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);
        msg.setSubject(_subject);
        msg.setSentDate(new Date());
// setup message body
        BodyPart messageBodyPart = new MimeBodyPart();
      //  messageBodyPart.attachFile("resources/teapot.jpg");
        messageBodyPart.setText(_body);
        _multipart.addBodyPart(messageBodyPart);

// Put parts in message
        msg.setContent(_multipart);

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
// send email
                    Transport.send(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


        return true;
    /*    } else {
            return false;
        }
        */
    }

    public void addAttachment(String filename) throws Exception {
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);

        _multipart.addBodyPart(messageBodyPart);
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(_user, _pass);
    }

    private Properties _setProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.host", _host);

        if(_debuggable) {
            props.put("mail.debug", "true");
        }

        if(_auth) {
            props.put("mail.smtp.auth", "true");
        }

        props.put("mail.smtp.port", _port);
        props.put("mail.smtp.socketFactory.port", _sport);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        return props;
    }

    // the getters and setters
    public String getBody() {
        return _body;
    }

    public void setBody(String _body) {
        this._body = _body;
    }

    // more of the getters and setters …..
}
