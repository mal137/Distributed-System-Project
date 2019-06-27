package com.vs.sensor;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import static com.vs.sensor.CustomRandomMethod.randomBoolean;
import static com.vs.sensor.CustomRandomMethod.randomInt;
import static com.vs.sensor.DefaultConstants.*;

/**
 * This class handles the SensorData and sends them to the Smarthome Server
 */
class Sensor extends Thread{

    private String m_ipAddress;
    private int m_udp_port;
    private int m_tcp_port;
    private DatagramSocket m_clientSocket; // conectionless socket for client
    private InetAddress m_ipAddressForSending;
    private byte[] m_sendData;
    private ArrayList<SensorData> m_sdArray;

    /**
     * Default constructor
     */
    Sensor() {
        this.m_sdArray = new ArrayList();
        this.m_ipAddress = IP_ADDRESS;
        this.m_udp_port = UDP_PORT;
        this.m_tcp_port = TCP_PORT;
    }

    /**
     * Constructor initializes ip and port
     * @param ipAddress IP Address of the smarthome
     * @param udp_port Port for the smarthome
     * @param tcp_port Port for Webserver
     */
    Sensor(String ipAddress, int udp_port, int tcp_port) {
        this.m_sdArray = new ArrayList();
        this.m_ipAddress = ipAddress;
        this.m_udp_port = udp_port;
        this.m_tcp_port = tcp_port;
    }

    /**
     * Run Method is starting a RandomSensorData Generator and simultaneously UDP DatagrammPacket
     */
    @Override
    public void run() {
        try {
            //Starting both threads
            RandomSensorData randSD = new RandomSensorData(m_sdArray);
            TCPClient tcpClient = new TCPClient(m_sdArray, m_ipAddress, m_tcp_port);
            randSD.start();
            tcpClient.start();

            initializeSensorArray();
            m_clientSocket = new DatagramSocket();
            m_ipAddressForSending = InetAddress.getByName(m_ipAddress);
            m_sendData = new byte[256];
            //byte arrays for information
            byte[] m_receiveData = new byte[256];

            menu();

            while(true){
                Thread.sleep(2000);
                sendMessage();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends Message to Server
     * @throws IOException Throws whenever it is null
     */
    private void sendMessage() throws IOException {
        String allSensorInfo = new String();

        for (SensorData sdArr : m_sdArray) {
            m_sendData = sdArr.toString().getBytes();
            DatagramPacket m_sendPacket = new DatagramPacket(m_sendData, m_sendData.length, m_ipAddressForSending, m_udp_port);
            m_clientSocket.send(m_sendPacket);

            //For debugging mode
            /*m_receivePacket = new DatagramPacket(m_receiveData, m_receiveData.length);//Buffer, Buffer-length
            m_clientSocket.receive(m_receivePacket);
            String modifiedSentence = new String(m_receivePacket.getData());
            System.out.println("FROM SERVER:" + modifiedSentence);//Bekommt keine direkten Infos vom Server, sondern nur das was geschickt wurde
            */
        }
        //As a confirmation
        System.out.println(allSensorInfo);
    }

    /**
     * Print a welcome message
     */
    private void menu() {
        System.out.println("Automated print of Sensor");
        System.out.println("----------BEGIN----------");
        System.out.println();
    }

    /**
     * Initializes the Sensor Array in order to operate with the sensor data
     */
    private void initializeSensorArray() {
        for(int i = 0;i < 4; i++)
            m_sdArray.add(new SensorData(randomInt(0, 100), randomInt(0, 100), randomInt(1000, 100000), randomBoolean()));
    }
}
