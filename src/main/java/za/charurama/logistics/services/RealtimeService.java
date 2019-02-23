package za.charurama.logistics.services;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import za.charurama.logistics.constants.GoogleMaps;

public class RealtimeService {
    private IMqttClient client;

    public RealtimeService(IMqttClient client){
        this.client = client;
        try {
            connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        client.connect(options);
    }

    public void sendMessage(String message) throws MqttException {
        byte[] payload = message.getBytes();
        MqttMessage mqttMessage = new MqttMessage(payload);
        client.publish(GoogleMaps.SYSTEM_UPDATES_TOPIC,mqttMessage);
    }

    public void locationUpdates(String message) throws MqttException {
        byte[] payload = message.getBytes();
        MqttMessage mqttMessage = new MqttMessage(payload);
        client.publish(GoogleMaps.LOCATION_UPDATES_TOPIC,mqttMessage);
    }
}
