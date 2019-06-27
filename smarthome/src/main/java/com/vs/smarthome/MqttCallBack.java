package com.vs.smarthome;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.vs.smarthome.WebServer.weatherDataArr;


public class MqttCallBack implements MqttCallback {

    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MqttCallBack.class);

    @Override
    public void connectionLost(Throwable throwable) {
        LOGGER.error("Connection to MQTT broker lost!");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = new Gson();
        WeatherData wd = gson.fromJson(mqttMessage.toString(), WeatherData.class);
        weatherDataArr.add(wd);
        LOGGER.info("Message received: "+ new String(mqttMessage.getPayload()));
        LOGGER.info("weatherDataArr size: "+ weatherDataArr.size());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken mqttDeliveryToken) {
        try {
            LOGGER.info("Delivery completed: "+ mqttDeliveryToken.getMessage() );
        } catch (MqttException e) {
            LOGGER.error("Failed to get delivery token message: " + e.getMessage());
        }
    }
}

