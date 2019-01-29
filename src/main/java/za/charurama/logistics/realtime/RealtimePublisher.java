package za.charurama.logistics.realtime;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import za.charurama.logistics.models.VehicleLocation;

public class RealtimePublisher {
    private PubNub pubNub;
    private PNStatus _status;

    public RealtimePublisher() {
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setPublishKey("pub-c-26c98eaa-87d4-4c69-80aa-085bd2372633");
        pnConfiguration.setSubscribeKey("sub-c-dc4b053a-9a41-11e3-b726-02ee2ddab7fe");
        pubNub = new PubNub(pnConfiguration);
        init();
    }

    public void publishLocation(VehicleLocation vehicleLocation, String channel) {
        JsonObject messageJsonObject = new JsonObject();
        messageJsonObject.addProperty("vehicleId", vehicleLocation.getVehicleId());
        messageJsonObject.addProperty("latitude", vehicleLocation.getLatitude());
        messageJsonObject.addProperty("longitude", vehicleLocation.getLongitude());

        pubNub.publish().channel(channel).message(messageJsonObject).async(new PNCallback<PNPublishResult>() {
            @Override
            public void onResponse(PNPublishResult result, PNStatus status) {
                // Check whether request successfully completed or not.
                if (!status.isError()) {

                    // Message successfully published to specified channel.
                }
                // Request processing failed.
                else {

                    // Handle message publish error. Check 'category' property to find out possible issue
                    // because of which request did fail.
                    //
                    // Request can be resent using: [status retry];
                    //log the failures and do a follow up
                }
            }
        });
    }

    private void init() {
        pubNub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {

                if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
                    // This event happens when radio / connectivity is lost
                } else if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {

                    // Connect event. You can do stuff like publish, and know you'll get it.
                    // Or just use the connected event to confirm you are subscribed for
                    // UI / internal notifications, etc

                } else if (status.getCategory() == PNStatusCategory.PNReconnectedCategory) {

                    // Happens as part of our regular operation. This event happens when
                    // radio / connectivity is lost, then regained.
                } else if (status.getCategory() == PNStatusCategory.PNDecryptionErrorCategory) {

                    // Handle messsage decryption error. Probably client configured to
                    // encrypt messages and on live data feed it received plain text.
                }
            }


            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                // Handle new message stored in message.message
                if (message.getChannel() != null) {
                    // Message has been received on channel group stored in
                    // message.getChannel()
                } else {
                    // Message has been received on channel stored in
                    // message.getSubscription()
                }

                JsonElement receivedMessageObject = message.getMessage();
                System.out.println("Received message content: " + receivedMessageObject.toString());
                // extract desired parts of the payload, using Gson
                String msg = message.getMessage().getAsJsonObject().get("vehicleId").getAsString();
                System.out.println("msg content: " + msg);


            /*
                log the following items with your favorite logger
                    - message.getMessage()
                    - message.getSubscription()
                    - message.getTimetoken()
            */
            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        });
    }
}
