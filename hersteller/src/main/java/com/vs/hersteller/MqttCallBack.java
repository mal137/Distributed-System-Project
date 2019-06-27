package com.vs.hersteller;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.vs.hersteller.MqttHandler.*;


public class MqttCallBack implements MqttCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttCallBack.class);

    @Override
    public void connectionLost(Throwable throwable) {
        LOGGER.error("Connection to MQTT broker lost!");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        SENSORDATA_ARRAY.add(new String(mqttMessage.getPayload()));
        LOGGER.info("Message received: " + new String(mqttMessage.getPayload()));
        LOGGER.info("SENSORDATA_ARRAY size: " + SENSORDATA_ARRAY.size());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken mqttDeliveryToken) {
        try {
            LOGGER.info("Delivery completed: " + mqttDeliveryToken.getMessage());
        } catch (MqttException e) {
            LOGGER.error("Failed to get delivery token message: " + e.getMessage());
        }
    }
}
