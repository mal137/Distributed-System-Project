package com.vs.hersteller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.vs.hersteller.DefaultConstants.*;
import static com.vs.hersteller.MqttHandler.*;

public class SendWeatherDataToSmarthome extends MqttCallBack {

    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SendWeatherDataToSmarthome.class);

    /** The global CLI parameters that have been parsed in Main. */
    //private CliParameters cliParameters;
    /**
     * The broker URL.
     */
    private String broker;

    GsonBuilder gsonBuilder;
    Gson gson;

    /**
     * Default constructor that initializes
     * various class attributes.
     */
    public SendWeatherDataToSmarthome() {

        // Create the broker string from command line arguments.
        broker = BROKER;
        gsonBuilder = new GsonBuilder();
        gson = new Gson();

    }

    /**
     * Runs the MQTT client and publishes a message.
     */
    public void run() {

        // Create some MQTT connection options.
        MqttConnectOptions mqttConnectOpts = new MqttConnectOptions();
        mqttConnectOpts.setCleanSession(true);
        mqttConnectOpts.setUserName(USERNAME);
        mqttConnectOpts.setPassword(PASSWORD.toCharArray());
        mqttConnectOpts.setConnectionTimeout(30);
        mqttConnectOpts.setKeepAliveInterval(60);

        try {

            MqttClient client = new MqttClient(broker, MqttClient.generateClientId());
            client.setCallback(new MqttCallBack());

            for(String w : WEATHERDATA_ARRAY) {
                //Construct object for sending
                //This prevent later difficulties with object mapping
                // Connect to the MQTT broker using the connection options.
                client.connect(mqttConnectOpts);
                LOGGER.info("Connected to MQTT broker: " + client.getServerURI());

                // Create the message and set a quality-of-service parameter.

                MqttMessage message = new MqttMessage(w.getBytes());
                message.setQos(QOS_LEVEL_2);

                // Publish the message.
                //Thread.sleep(6000);
                client.publish(TOPIC_WEATHER, message);
                LOGGER.info("Published message: " + message);
            }

        } catch (MqttException e) {
            LOGGER.error("An error occurred: " + e.getMessage());
        } /*catch (InterruptedException e ){
            LOGGER.error("An error occurred: " + e.getMessage());
        }*/
    }
}
