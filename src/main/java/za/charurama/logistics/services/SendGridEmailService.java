package za.charurama.logistics.services;


import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import za.charurama.logistics.contracts.EmailService;

import java.io.IOException;

@Service
public class SendGridEmailService implements EmailService {

    @Value("${spring.sendgrid.api-key}")
    private String sendGridAPIKey;

    @Override
    public String sendText(String from, String to, String subject, String body) {
        Response response = sendEmail(from, to, subject, new Content("text/plain", body));
        return response.getBody() ;
    }

    @Override
    public String sendHTML(String from, String to, String subject, String body) {
        Response response = sendEmail(from, to, subject, new Content("text/html", body));
        return String.format("Http Code: %d \n%s",response.getStatusCode(),response.getBody());
    }

    private Response sendEmail(String from, String to, String subject, Content content) {
        SendGrid sendGridClient = new SendGrid(sendGridAPIKey);
        Mail mail = new Mail(new Email(from), subject, new Email(to), content);
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = sendGridClient.api(request);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return response;
    }

}
