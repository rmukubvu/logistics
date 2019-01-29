package za.charurama.logistics.messaging;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import za.charurama.logistics.models.RoutingMessage;

public class TwilioClientSingleton {
    private static TwilioClientSingleton instance;
    private final String ACCOUNT_SID = "AC4cfb2d24bb0aab19249af1ad33cecdea";
    private static final String AUTH_TOKEN = "a5e460406248767cd0d8c32e6ea9560b";

    private TwilioClientSingleton(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static synchronized TwilioClientSingleton getInstance(){
        if(instance == null){
            instance = new TwilioClientSingleton();
        }
        return instance;
    }

    public String send(RoutingMessage routingMessage, boolean isWhatsapp){

        String from = "+" + routingMessage.getSourceMsisdn();
        String to = "+" + routingMessage.getDestinationMsisdn();

        if(isWhatsapp){
            from = "whatsapp:+441618507453";
            to = "whatsapp:+" + routingMessage.getDestinationMsisdn();
        }

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(to),
                new com.twilio.type.PhoneNumber(from),
                routingMessage.getMessage())
                .create();
        return message.getSid();
    }
}
