package com.vs.smarthome;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import static com.vs.smarthome.DefaultConstants.*;

/**
 * Smarthome Main Class
 */
public class SmarthomeServerMain {
    public static void main(String args[]) throws Exception {
        try {
            int port = UDP_PORT;
            int tcp_port = TCP_PORT;
            int http_port = HTTP_PORT;

            //Scheme java -jar <File.jar> 1337 27015 8081
            if (args.length == 3) {
                port = Integer.valueOf(args[0]);
                tcp_port = Integer.valueOf(args[1]);
                http_port = Integer.valueOf(args[2]);
            }

            //For Docker
            Thread.sleep(30000);

            //Starting the server instance
            SmarthomeServer shs = new SmarthomeServer(port, tcp_port, http_port);
            WebServer ws = new WebServer(port, tcp_port, http_port);
            shs.start();
            ws.start();

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.err.println("Your Input is out of bound for args");
        }
    }
}

