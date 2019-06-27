package com.vs.smarthome;

import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SensorDataHandler extends Thread {

    //private BufferedReader m_in;
    //private BufferedWriter m_out;
    //**************************
    private BufferedReader m_fromClient;
    private DataOutputStream m_toClient;
    private String m_line;
    private ArrayList<String> m_lines;
    private int m_tcp_port;

    SensorDataHandler(){
        this.m_tcp_port = 27015;
    }

    SensorDataHandler(BufferedReader fromClient, DataOutputStream toClient,
                      String line, ArrayList<String> lines, int tcp_port){
            this.m_fromClient = fromClient;
            this.m_toClient = toClient;
            this.m_line = line;
            this.m_lines = lines;
            this.m_tcp_port = tcp_port;
    }

    SensorDataHandler(BufferedReader fromClient, DataOutputStream toClient,
                      String line, ArrayList<String> lines){
        this.m_fromClient = fromClient;
        this.m_toClient = toClient;
        this.m_line = line;
        this.m_lines = lines;
        this.m_tcp_port = 27015;
    }

    @Override
    public void run() {
        System.out.println("Server on port " + m_tcp_port);
        handleRequests();
    }

    /**
     *Handling the request to the TCP Client on port 27015 aswell as receiving packages
     */
    void handleRequests() {
        try {
            ServerSocket contactSocket = new ServerSocket(m_tcp_port);
            Socket client = contactSocket.accept();  // create communication to socket
            System.out.println("TCP Connection with: " + client.getRemoteSocketAddress());

            m_fromClient = new BufferedReader(       // Datastream FROM Client
                    new InputStreamReader(client.getInputStream()));
            m_toClient = new DataOutputStream(
                    client.getOutputStream());       // Datastream TO Client
            while (receiveRequest()) {               // As long as connection exists
                prepareResponse();
                sleep(1000);
            }
            m_fromClient.close();
            m_toClient.close();
            client.close();
            System.out.println("Session ended, Server remains active");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Takes all necessary information of string and prints them out
     * @throws IOException if the m_line is null
     */
    boolean receiveRequest() throws IOException {
        boolean holdTheLine = true;
        System.out.println("Received over TCP: ");
        for (int i = 0; i < 4; i++) {
            System.out.println("Line: " + (m_line = m_fromClient.readLine()));
            m_lines.add(m_line);
        }
        System.out.println("List has been filled");

        return true;
    }

    /**
     * Preaparing the String array for sending
     * @throws IOException
     */
    void prepareResponse() throws IOException {
        for (int i = 0; i < m_lines.size(); i++) {
            if (m_lines.size() > 4) {
                for (int j = 0; j < m_lines.size() - 4; j++) {
                    m_lines.remove(j);
                }
            }
            m_toClient.writeBytes(m_lines.get(i).toUpperCase() + '\n');  // Send answer
        }

        System.out.println("List sent answer back to TCP-Client");
        System.out.println("List-Size:\t" + m_lines.size());
        m_toClient.flush();
    }
}
