package com.vs.hersteller;

import java.util.ArrayList;
import java.util.List;

public class HerstellerMain {

    private static List<MqttHandler> myServers = new ArrayList<MqttHandler>();

    public static void main(String[] args) {
        try {
            //For Docker
            Thread.sleep(30000);

            //Server Liste (Übergebene ID muss einmalig sein)
            myServers.add(new MqttHandler("Hersteller 1"));
            myServers.add(new MqttHandler("Hersteller 2"));
            myServers.add(new MqttHandler("Hersteller 3"));

            //Server starten
            for (MqttHandler server : myServers) {
                server.run();
            }

            RestClient rest = new RestClient();
            SendWeatherDataToSmarthome s = new SendWeatherDataToSmarthome();
            rest.run();
            s.run();

            //Serverausfälle simulieren
            while (true) {
                for (MqttHandler server : myServers) {
                    try {
                        int ms = 2000;
                        server.disconnectFromServer(ms);
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
