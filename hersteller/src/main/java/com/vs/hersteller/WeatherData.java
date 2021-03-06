package com.vs.hersteller;

import java.io.Serializable;

public class WeatherData implements Serializable
{
    private Forecast forecast;

    private Location location;

    private Current current;

    public Forecast getForecast ()
    {
        return forecast;
    }

    public void setForecast (Forecast forecast)
    {
        this.forecast = forecast;
    }

    public Location getLocation ()
    {
        return location;
    }

    public void setLocation (Location location)
    {
        this.location = location;
    }

    public Current getCurrent ()
    {
        return current;
    }

    public void setCurrent (Current current)
    {
        this.current = current;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [forecast = "+forecast+", location = "+location+", current = "+current+"]";
    }
}