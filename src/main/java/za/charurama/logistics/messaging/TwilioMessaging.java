package za.charurama.logistics.messaging;


import za.charurama.logistics.models.RoutingMessage;

public class TwilioMessaging {
    private TwilioClientSingleton routing;

    public TwilioMessaging(){
        routing = TwilioClientSingleton.getInstance();
    }

    public String send(RoutingMessage model){
        return routing.send(model,true);
    }
}
