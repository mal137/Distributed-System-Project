package com.vs.smarthome;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import static com.vs.smarthome.DefaultConstants.*;

public class MqttSubscriber {

    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MqttSubscriber.class);

    //Data with shared access from Webserver
    private ArrayList<WeatherData> m_weatherDataArr;

    public MqttSubscriber(ArrayList<WeatherData> weatherDataArr){
        this.m_weatherDataArr = weatherDataArr;
    }

    public void receive() {

        // Create some MQTT connection options.
        MqttConnectOptions mqttConnectOpts = new MqttConnectOptions();
        mqttConnectOpts.setCleanSession(true);
        //mqttConnectOpts.setUserName(USERNAME);
        //mqttConnectOpts.setPassword(PASSWORD.toCharArray());
        mqttConnectOpts.setConnectionTimeout(30);
        mqttConnectOpts.setKeepAliveInterval(60);

        try {
            MqttClient client = new MqttClient(BROKER, MqttClient.generateClientId());
            client.setCallback(new MqttCallBack());

            // Connect to the MQTT broker.
            client.connect(mqttConnectOpts);
            LOGGER.info("Connected to MQTT broker: " + client.getServerURI());

            // Subscribe to a topic.
            client.subscribe(TOPIC_WEATHER);
            LOGGER.info("Subscribed to topic: " + client.getTopic(TOPIC_WEATHER));
        } catch (MqttException e) {
            LOGGER.error("An error occurred: " + e.getMessage());
        }
    }
}
