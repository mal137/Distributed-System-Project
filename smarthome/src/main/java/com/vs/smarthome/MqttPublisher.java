package com.vs.smarthome;

import static com.vs.smarthome.DefaultConstants.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;


public class MqttPublisher {
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MqttPublisher.class);

    //Data with shared access from Webserver
    private ArrayList<String> m_sensorDataArr;

    //GSON Attribute from google library
    private GsonBuilder m_gsonBuilder;
    private Gson gson;

    public MqttPublisher(ArrayList<String> sensorArray){
        this.m_sensorDataArr = sensorArray;
        this.m_gsonBuilder = new GsonBuilder();
    }

    public void send() {
        //Needed for String Array to JSON
        //gson = m_gsonBuilder.create();
        gson = m_gsonBuilder.setPrettyPrinting().create(); //For beautify json format

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

            // Connect to the MQTT broker using the connection options.
            client.connect(mqttConnectOpts);
            LOGGER.info("Connected to MQTT broker: " + client.getServerURI());

            // Create the message and set a quality-of-service parameter.
            MqttMessage message = new MqttMessage(sensorArrToBytesArr());
            message.setQos(QOS_LEVEL_2);

            //Publish the message.
            client.publish(TOPIC_SENSOR, message);
            LOGGER.info("Published message: " + message);

            // Disconnect from the MQTT broker.
            client.disconnect();
            LOGGER.info("Disconnected from MQTT broker.");

        } catch (MqttException e) {
            LOGGER.error("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Create sendable format for MQTT
     * @return a sendable format
     */
    private byte[] sensorArrToBytesArr() {
        return gson.toJson(m_sensorDataArr).getBytes();
    }
}
