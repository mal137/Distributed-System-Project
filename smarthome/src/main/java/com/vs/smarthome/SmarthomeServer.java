package com.vs.smarthome;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Smarthome Server
 */
public class SmarthomeServer extends Thread{
    private int m_port;
    private int m_tcp_port;
    private int m_http_port;
    private DatagramSocket m_serverSocket;
    private byte[] m_receiveData;
    private byte[] m_sendData;

    /**
     * Constructor
     * @param port UDP Port
     * @param tcpport TCP Port
     * @param httpport HTTP Port
     * @throws SocketException for DatagramSocket
     */
    SmarthomeServer(int port, int tcpport, int httpport) throws SocketException {
        this.m_port = port;
        this.m_tcp_port = tcpport;
        this.m_http_port = httpport;
        this.m_serverSocket = new DatagramSocket(this.m_port);
        this.m_receiveData = new byte[1024];
        this.m_sendData = new byte[1024];
    }

    /**
     * Run method
     */
    @Override
    public void run() {
        try {
            menu();
            while (true) {
                //Waiting for receiving package
                DatagramPacket receivePacket = receivingPackage();

                //Extract String from Package and Print
                //String sentence = extractStringFromPackage(receivePacket);

                //Using for Debugging
                //sendPackageBackToClient(receivePacket, sentence);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print information for first start
     */
    private void menu(){
        System.out.println("Server has started...");
        System.out.println("Listing on Port " + m_port);
    }

    /**
     * sendPackageBackToClient takes the receivingPacket from the client and send it back to the client for debugging purpose
     * @param receivePacket from Client
     * @param sentence getting the string from package
     * @throws IOException from Exception
     */
    private void sendPackageBackToClient(DatagramPacket receivePacket, String sentence) throws IOException {
        // send back for user interface (sendMessage () )
        InetAddress IPAddress = receivePacket.getAddress();
        int port = receivePacket.getPort();
        String capitalizedSentence = sentence.toUpperCase();
        m_sendData = capitalizedSentence.getBytes();
        DatagramPacket sendPacket
                = new DatagramPacket(m_sendData, m_sendData.length, IPAddress, port);
        m_serverSocket.send(sendPacket);
    }

    /**
     * Creates a string and print it from the receiving packet
     * @param receivePacket from Client
     * @return String of message
     */
    private String extractStringFromPackage(DatagramPacket receivePacket) {
        //get the Info
        String sentence = new String(receivePacket.getData());
        System.out.println("RECEIVED: " + sentence);
        return sentence;
    }

    /**
     * Catch the receiving Package from Client
     * @return Receiving Datagramm from Client
     * @throws IOException for IO
     */
    private DatagramPacket receivingPackage() throws IOException {
        //receive paket
        DatagramPacket receivePacket = new DatagramPacket(m_receiveData, m_receiveData.length);
        m_serverSocket.receive(receivePacket);
        return receivePacket;
    }

}
