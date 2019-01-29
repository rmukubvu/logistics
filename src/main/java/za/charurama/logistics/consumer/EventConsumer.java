package za.charurama.logistics.consumer;


public class EventConsumer {

    /*@Autowired
    private MessagingLogsRepository messagingLogsRepository;

    @Autowired
    private EmailService sendGridEmailService;

    @Autowired
    private SmsService clickatellSmsService;

    @Autowired
    private SmsResponseRepository smsResponseRepository;

    @Value("${email.originator}")
    private String from;

    private ObjectMapper mapper = new ObjectMapper();
    private DisruptorManager disruptorManager = new DisruptorManager();

    @RabbitListener(queues="location_q")
    public void receive(String message) {
        try {
            VehicleLocation vehicleLocation = mapper.readValue(message,VehicleLocation.class);
            //process all received messages
            disruptorManager.publish(vehicleLocation);
            //doSend(vehicleLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doSend(VehicleLocation vehicleLocation){

        String mapsUrl = String.format("http://www.google.com/maps/place/%f,%f", vehicleLocation.getLatitude(), vehicleLocation.getLongitude());
        String message = String.format("%s\n\n%s\n%s", mapsUrl, "ZC20181132 -INNSCOR-CONSOL", "OUT OF CUSTOMS");
        String subject = "ZC20181132 -INNSCOR-CONSOL";
        //mock data
        Executors.newSingleThreadExecutor().execute(() -> {
            smsNotification("27780956607", message);
            sendWhatsappNotification("27780956607", message);
            sendEmailNotification("rmukubvu@gmail.com", subject, message);
        });
    }

    private void sendEmailNotification(String destination,String subject,String message) {
        String response = sendGridEmailService.sendText(from, destination, subject, message);
        logMessages(new MessagingLog("12345", destination, message, MessagingTypes.EMAIL, response));
    }

    private void sendWhatsappNotification(String destination,String message){
        RoutingMessage routingMessage = new RoutingMessage("441618507453", destination, message, true);
        String response = TwilioClientSingleton.getInstance().send(routingMessage, true);
        logMessages(new MessagingLog("12345", destination, message, MessagingTypes.WHATSAPP, response));
    }

    private void smsNotification(String destination,String message){
        SmsResponse response = clickatellSmsService.sendSms(destination,message);
        String responseFromServiceProvider;
        if (response.getError() == null){
            responseFromServiceProvider = response.getMessages().get(0).getApiMessageId();
        }else {
            responseFromServiceProvider = response.getErrorDescription().toString();
        }
        //save response
        smsResponseRepository.save(response);
        //save logs
        logMessages(new MessagingLog("12345", destination, message, MessagingTypes.SMS, responseFromServiceProvider));
    }

    private void logMessages(MessagingLog messagingLogs){
        messagingLogsRepository.save(messagingLogs);
    }*/
}
