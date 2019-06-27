package com.vs.sensor;

import static com.vs.sensor.CustomRandomMethod.randomInt;
import static com.vs.sensor.DefaultConstants.ROOMARRAY;

/**
 * This class defined the attribute of reading the sensor
 * @author Mal.
 */
class SensorData {

    private double m_temperature;
    private int m_humidity;
    private int m_air_pressure;
    private String m_singleRoom;
    private boolean m_windowsState;


    /**
     * Constructor for initializing all attribute of the object
     * @param temperature random temperature
     * @param humidity random humidity
     * @param windowsState random windows state
     */
    public SensorData(double temperature, int humidity, int air_pressure, boolean windowsState) {
        this.m_temperature = temperature;
        this.m_humidity = humidity;
        this.m_air_pressure = air_pressure;
        this.m_windowsState = windowsState;
        this.m_singleRoom = ROOMARRAY[randomInt(0,3)];
    }

    /**
     * Takes the param and apply them to the attribute of the object
     * @param temperature t
     * @param humidity h
     * @param air_pressure air_pr
     * @param windowsState w
     */
    public void changeAll(double temperature, int humidity, int air_pressure, boolean windowsState) {
        this.m_temperature = temperature;
        this.m_humidity = humidity;
        this.m_air_pressure = air_pressure;
        this.m_windowsState = windowsState;
        this.m_singleRoom = ROOMARRAY[randomInt(0,3)];
    }


    private double getTemperature() {
        return m_temperature;
    }

    private int getHumidity() {
        return m_humidity;
    }

    private boolean isWindowsState() {
        return m_windowsState;
    }

    public void setWindowsState(boolean windowsState) {
        this.m_windowsState = windowsState;
    }

    /**
     * Standard toString generation
     * @return All information of an object
     */
    @Override
    public String toString() {
        return "\n\nTemperatur: " + m_temperature + " Celsius\n"
                + "Humidity: " + m_humidity + " %\n"
                + (m_windowsState ? "Window State: gekippt\n" : "Window State: geschlossen\n")
                + "Air Pressure: " + m_air_pressure + " Pa\n"
                + "Room: " + m_singleRoom + "\n\n";
    }

    /**
     * Returns like toString but without '\n'
     * @return a non-'\n' String
     */
    public String getSensorDataForTcp() {
        return "Temperatur: " + m_temperature + " Celsius "
                + "Humidity: " + m_humidity + " % "
                + (m_windowsState ? "Window State: gekippt " : "Window State: geschlossen ")
                + "Air Pressure: " + m_air_pressure + " Pa "
                + "Room: " + m_singleRoom;
    }


}

