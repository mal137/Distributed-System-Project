package com.vs.hersteller;

import java.io.Serializable;
import java.util.List;

public class Forecast implements Serializable
{
    private List<Forecastday> forecastday;

    public List<Forecastday> getForecastday ()
    {
        return forecastday;
    }

    public void setForecastday (List<Forecastday> forecastday)
    {
        this.forecastday = forecastday;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [forecastday = "+forecastday+"]";
    }
}