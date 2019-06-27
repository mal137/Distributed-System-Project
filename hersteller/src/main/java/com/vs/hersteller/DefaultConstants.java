package com.vs.hersteller;

public abstract class DefaultConstants {
    private DefaultConstants() {
    }

    /** Standard UDP Port */
    public static final int UDP_PORT = 1337;
    /** Standard TCP Port*/
    public static final int TCP_PORT = 27015;
    /** Standard HTTP Port*/
    public static final int HTTP_PORT = 8081;
    // Standard Broker IP
    public static final String BROKER = "tcp://hivemq.xyz.gg:1883";
    // Username Broker
    public static final String USERNAME = "ddbwrslu";
    // Password Broker
    public static final String PASSWORD = "3ESknp9CmKhP";
    // Standard QoS Level
    public static final int QOS_LEVEL_2 = 2;
    // Standard Topic
    public static final String TOPIC_SENSOR = "smarthome/sensordata";
    // Standard Topic
    public static final String TOPIC_WEATHER = "smarthome/weatherdata";
}
