package com.vs.sensor;

import java.util.ArrayList;
import static com.vs.sensor.CustomRandomMethod.randomBoolean;
import static com.vs.sensor.CustomRandomMethod.randomInt;

/**
 * RandomSensorData randomizes object of an array
 */
class RandomSensorData extends Thread {

    /**
     *Reference to ArrayList in Sensor.java
     */
    private final ArrayList<SensorData> m_sensorArray;

    /**
     * Constructor of
     * @param sdArray Array of SensorData
     */
    RandomSensorData(ArrayList<SensorData> sdArray) {
        this.m_sensorArray = sdArray;
    }

    /**
     * Run method for generating random values in an interval between 500 - 2000
     */
    @Override
    public void run() {
        try {
            while (true) {
                sleep(randomInt(500, 2000));
                for (SensorData sd : m_sensorArray) {
                    sd.changeAll(randomInt(0, 100), randomInt(0, 100), randomInt(1000, 100000), randomBoolean());
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}