package com.java.task.service;


import com.java.task.configuration.EmailNotifiactionConfiguration;
import com.java.task.models.User;
import com.java.task.utils.EncodeUtil;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImplementation implements EmailService {

    @Autowired
    EmailNotifiactionConfiguration emailNotifiactionConfiguration;

    @Autowired
    UserService userService;

    @Override
    public void sendText(User user, String token) throws Exception {

        Response response = sendmail(user,token);
    }

    private Response sendmail(User user ,String token) throws Exception {

        Mail mail = PersonalizeEmail(user,token);
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = emailNotifiactionConfiguration.getSendGrid().api(request);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("Error while sending mail");
        }
        return response;
    }

    private Mail PersonalizeEmail(User user,String token) throws Exception {

        Mail mail = new Mail();
        String encriptedEmail= EncodeUtil.base64(user.getEmailId());

        String link="http://localhost:8080/coursemanagement/validate?id="+encriptedEmail+"&token="+token;

        Email fromEmail = new Email();
        fromEmail.setName("sneha tiwari");
        fromEmail.setEmail("sssnehatiwari0@gmail.com");
        mail.setFrom(fromEmail);
        mail.setSubject("reset your password");

        Email replyTo = new Email();
        replyTo.setName("sneha tiwari");
        replyTo.setEmail("sssnehatiwari0@gmail.com");
        mail.setReplyTo(replyTo);

        Content content = new Content();

        content.setType("text/html");
        content.setValue(" to reset your password click on the given link "+link);
        mail.addContent(content);


        Personalization personalization = new Personalization();
        Email to = new Email();
        to.setName(user.getUserName());
        to.setEmail(user.getEmailId());
        personalization.addTo(to);

        mail.addPersonalization(personalization);

        return mail;
    }
}
