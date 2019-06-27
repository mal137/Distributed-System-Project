package com.vs.hersteller;

import static com.vs.hersteller.DefaultConstants.*;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CopyOnWriteArrayList;


public class MqttHandler {
    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MqttCallBack.class);
    /**
     * The broker URL.
     */
    private String m_broker;
    private MqttClient m_client;
    private MqttConnectOptions m_connOpt;
    private final String m_serverId;
    public static boolean EXECUTED_REST_REQUEST = false;

    //Static Array so all three servers can access
    public static CopyOnWriteArrayList<String> WEATHERDATA_ARRAY;
    public static CopyOnWriteArrayList<String> SENSORDATA_ARRAY;

    /**
     * Default constructor
     */
    public MqttHandler(String serverId) {
        this.WEATHERDATA_ARRAY = new CopyOnWriteArrayList<>();
        this.SENSORDATA_ARRAY = new CopyOnWriteArrayList<>();
        this.m_broker = BROKER;
        this.m_serverId = serverId;
        m_connOpt = new MqttConnectOptions();
    }

    /**
     * Runs the MQTT client.
     */
    public void run() {
        m_connOpt.setCleanSession(true);
        //m_connOpt.setUserName(USERNAME);
        //m_connOpt.setPassword(PASSWORD.toCharArray());
        m_connOpt.setConnectionTimeout(30);
        m_connOpt.setKeepAliveInterval(60);

        try {
            m_client = new MqttClient(BROKER, MqttClient.generateClientId());
            m_client.setCallback(new MqttCallBack());

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        connect();
    }

    //Connecting to the Broker and subscribing to the topics
    private void connect() {
        try {
            m_client.connect(m_connOpt);
            m_client.subscribe(TOPIC_SENSOR, QOS_LEVEL_2);
            //System.out.println(m_serverId + " connected to " + BROKER);
            LOGGER.info(m_serverId + " connected to " + BROKER);

            for (int i = 0; i < WEATHERDATA_ARRAY.size(); i++) {
                //Ensure that all other components have started already
                // Create the message and set a quality-of-service parameter.
                MqttMessage message = new MqttMessage(WEATHERDATA_ARRAY.get(i).getBytes());
                message.setQos(QOS_LEVEL_2);

                //Publish the message.
                m_client.publish(TOPIC_WEATHER, message);
                LOGGER.info("Published message: " + message);
            }
        } catch (
                MqttException e) {
            LOGGER.error("Error is: " + e.getMessage());
        }

    }

    //Disconnecting for ms miliseconds, then reconnect
    public void disconnectFromServer(int ms) {

        try {
            m_client.disconnect();
            //System.out.println(m_serverId + " disconnected from " + BROKER);
            LOGGER.info(m_serverId + " disconnected from " + BROKER);
            synchronized (this) {
                this.wait(ms);
            }
            connect();
        } catch (MqttException ex) {
            LOGGER.error(ex.getMessage());
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
