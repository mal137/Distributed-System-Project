/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vs.sensor;

/**
 * @author Mal.
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mal.
 */
public class TCPClient extends Thread {
    private String m_ipAddress;
    private int m_tcp_port;

    private String m_line;
    private Socket m_socket;
    private BufferedReader m_fromServer;
    private DataOutputStream m_toServer;//Abschicken von java Datentypen(string, int etc.)
    private BufferedReader m_inFromUser;
    private String m_tmp;
    private ArrayList<SensorData> m_sdArray = new ArrayList();

    public TCPClient(ArrayList<SensorData> wdA, String ipA, int tcpPort) {
        this.m_sdArray = wdA;
        this.m_ipAddress = ipA;
        this.m_tcp_port = tcpPort;
    }

    /**
     * Run method for tcp client
     */
    @Override
    public void run() {
        try {
            //waitForServer();
            System.out.println("TCP-Server ist online!");
            m_socket = new Socket(InetAddress.getByName(m_ipAddress), m_tcp_port);
            System.out.println("TCP Connection on Port " + m_tcp_port + "!");
            m_toServer = new DataOutputStream( // Datastream FROM Server
                    m_socket.getOutputStream());
            // System.out.println("to Server initialized");
            m_fromServer = new BufferedReader( // Datastream TO Server
                    new InputStreamReader(m_socket.getInputStream()));
            m_inFromUser = new BufferedReader(new InputStreamReader(System.in));

            while (true) {              // Send requests while connected
                sendRequest();
                receiveResponse();      // Process server's answer
                sleep(1000);
            }
        } catch (SocketException ex){
            ex.printStackTrace();
            System.err.println("Socket has been terminated");
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean sendRequest() throws IOException, InterruptedException {
        boolean holdTheLine = true;          // Connection exists
        //System.out.println("Enter message for the ServerStringBuilder everything = new StringBuilder();
        m_tmp = "";

        for (SensorData wd : m_sdArray) {
            m_tmp = m_tmp + wd.getSensorDataForTcp()+ "\n";

        }

        m_toServer.writeBytes(m_tmp);
        return holdTheLine;
    }

    private void receiveResponse() throws IOException {

        System.out.println("\nServer answers: ");

        for (SensorData wd : m_sdArray) {
            String tmp = new String(m_fromServer.readLine());
            System.out.println(tmp);
        }

    }
}


