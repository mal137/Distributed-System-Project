/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vs.smarthome;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mal.
 */
public class WebServer extends Thread {

    //private BufferedReader m_in;
    //private BufferedWriter m_out;
    private String m_line;
    private BufferedReader m_fromClient;
    private DataOutputStream m_toClient;
    private SensorDataHandler m_sdt;
    private int m_port;
    private int m_tcp_port;
    private int m_http_port;
    //**************************
    private ArrayList<String> m_lines;
    public static ArrayList<WeatherData> weatherDataArr;

    /**
     * Constructor of Webserver
     * @param port udp
     * @param tcpport tcp
     * @param httpport http
     */
    WebServer(int port, int tcpport, int httpport){
        this.m_lines = new ArrayList<>();
        this.weatherDataArr = new ArrayList<>();
        this.m_port = port;
        this.m_tcp_port = tcpport;
        this.m_http_port = httpport;
        this.m_sdt = new SensorDataHandler(m_fromClient, m_toClient, m_line, m_lines, m_tcp_port);
    }


    @Override
    public void run() {
        try {
            Socket clientSocket;
            HttpHandler httpHandler;
            MqttSubscriber mqttSub;

            ServerSocket serverSocket = new ServerSocket(m_http_port, 3000);
            mqttSub = new MqttSubscriber(weatherDataArr);
            System.err.println("HTTP connection on port: " + m_http_port);
            m_sdt.start();
            mqttSub.receive();

            while (true) {
                clientSocket = serverSocket.accept();
                httpHandler = new HttpHandler(m_lines, clientSocket);
                httpHandler.run();
            }
        } catch (IOException ex) {
            Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
