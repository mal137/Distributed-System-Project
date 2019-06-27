package com.vs.sensor;



/**
 * Sensor main has been outsourced in this class / seperations of concerns
 */
class SensorMain {

    /**
     * main method for Sensor
     * @param args Paramter for executing jar
     */
    public static void main(String args[]) {
        try {
            Thread sensorThread = new Sensor();

            //Determine args
            //Scheme java -jar <File.jar> localhost 1337 27015
            if(args.length == 3){
                sensorThread = new Sensor(args[0], Integer.valueOf(args[1]), Integer.valueOf(args[2]));
            }

            //For Docker
            Thread.sleep(32000);

            sensorThread.start(); //Sensorthread
        }
        catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            System.err.println("Args is out of bound");
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
