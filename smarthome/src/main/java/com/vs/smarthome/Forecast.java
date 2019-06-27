package com.vs.smarthome;

import java.io.Serializable;

public class Forecast implements Serializable
{
    private Forecastday[] forecastday;

    public Forecastday[] getForecastday ()
    {
        return forecastday;
    }

    public void setForecastday (Forecastday[] forecastday)
    {
        this.forecastday = forecastday;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [forecastday = "+forecastday+"]";
    }
}