package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.service.EmailService;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private Environment env; //aca ponemos el API key, recordar q environment es de spring O TAMBIEN PUEDO DEFINIRLA EN PROPERTIES
    @Value("${sendgrid.email.sender}") //directo el nombre
    private String emailSender;
    @Value("${sendgrid.email.enabled}")
    private Boolean enabled;//esto lo cambio de la property, si la dejo false no envia nada

    @Override
    public void sendWelcomeEmailTo(String to) {
        if(!enabled){
            return;
        }
        String apiKey = env.getProperty("EMAIL_API_KEY");

        Email fromEmail = new Email(emailSender);
        Email toEmail = new Email(to);
        Content content = new Content(
                "text/plain",
                "Bienvenido/a a Alkemy Disney"
        );
        String subject = "Alkemy API Disney";
        Mail mail = new Mail(fromEmail,subject,toEmail,content);
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try{
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

        }catch (IOException ex){
            System.out.println("Error trying to send the email");
        }
    }

}
