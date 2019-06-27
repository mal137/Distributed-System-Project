package com.vs.smarthome;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;

import static com.vs.smarthome.WebServer.weatherDataArr;

public class HttpResponse {
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MqttCallBack.class);

    //for sending output to client
    private BufferedWriter m_out;
    private ArrayList<String> m_lines;
    private MqttPublisher m_mqttPub;
    private Gson gson;


    /**
     * Constructor for building a html website
     *
     * @param req
     * @param out
     * @param lines
     * @throws IOException
     */
    public HttpResponse(HttpRequest req, BufferedWriter out, ArrayList<String> lines) throws IOException {
        this.m_lines = lines;
        this.m_out = out;
        this.m_mqttPub = new MqttPublisher(m_lines);
        this.gson = new Gson();

        if (req.getReqType().equals("GET")) {
            //HTML Content will be generated here
            initializeOutBufferForWebsite();
            LOGGER.info("m_lines.isEmpty() equals: " + m_lines.isEmpty());
            if(!m_lines.isEmpty()){
                m_mqttPub.send();
            }

        }
    }


    /**
     * Creates an HTML like Website with Bootstrap and Placeholder for
     *
     * @throws IOException Whenever the buffer is faulty
     */
    private void initializeOutBufferForWebsite() throws IOException {
        m_out.write("HTTP/1.0 200 OK\r\n");
        //out.write("Date: Fri, 26 Oct 2018 23:59:59 GMT\r\n");
        //out.write("Server: Apache/0.8.4\r\n");
        m_out.write("Content-Type: text/html\r\n");
        //out.write("Content-Length: 59\r\n");
        //out.write("Expires: Sat, 01 Jan 2020 00:59:59 GMT\r\n");
        //out.write("Last-modified: Fri, 09 Oct 2018 17:21:40 GMT\r\n");
        m_out.write("\r\n"); // Important
        m_out.write("<!DOCTYPE html>\n");
        m_out.write("<html lang=\"en\">\n");
        //Website header
        initializeWebsiteHeader();
        //Website body
        initializeWebsiteBody();
        m_out.write("</html>\n");
    }

    private void initializeWebsiteHeader() throws IOException {
        m_out.write("<head>\n");
        m_out.write("<title>Smarthome</title>\n");
        m_out.write("<meta http-equiv=\"refresh\" content=\"5\">\n");
        m_out.write("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\">\n");
        // Bootstrap 4.1.3
        m_out.write("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\"></script>\n");
        //JSON2HTML Library
        //m_out.write("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/json2html/1.2.0/json2html.min.js\"></script>\n");
        //m_out.write("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js\"></script>\n");
        m_out.write("</head>\n");
    }

    private void initializeWebsiteBody() throws IOException {
        m_out.write("<body>\n");
        m_out.write("<h1>Daten der Smarthome:</h1>\n");
        m_out.write("<h2>Sensordaten:</h2>\n");

        //Prinitng existing Sensor data
        m_out.write("<div class=\"row\">\n");
        for (String str : m_lines) {
            m_out.write("<div class=\"col-md-3\">\n");
            m_out.write("<p>" + str + "</p>\n");
            m_out.write("</div>\n");
        }
        m_out.write("</div>\n");

        //Placeholder for upcoming WeatherData from REST API
        m_out.write("<h2>Wetterdaten:</h2>\n");
        m_out.write("<div class=\"container\">\n");
        initializeWeatherDataFromJSON();
        m_out.write("</div>\n");

        //Risk of abusing the button
        m_out.write("<button type=\"button\" class=\"btn btn-info\" onclick=\"window.location.reload();\">Refresh Sensors</button>\n");
        m_out.write("</body>\n");
    }

    private void initializeWeatherDataFromJSON() throws IOException {
        if (weatherDataArr.size() >= 1) {
            m_out.write("<div class=\"row\">\n");
            displayLondon();
            displayConditions();
            displayDailyWeather();
            m_out.write("</div>\n");
            m_out.write("<div class=\"row\">\n");
            displayAstro();
            displayCurrentWeather();
            m_out.write("</div>\n");
        } else {
            m_out.write("<div class=\"col-md-12\">\n");
            m_out.write("<p>" + "Weather Data is coming in a moment" + "</p>\n");
            m_out.write("</div>\n");
        }
    }

    private void displayAstro() throws IOException {
        m_out.write("<div class=\"col-md-6\">\n");
        m_out.write("<h3>" + "Astro" + "</h3>\n");
        m_out.write("<p>Sunrise: " + getLondon().getForecast().getForecastday()[0].getAstro().getSunrise() + "</p>\n");
        m_out.write("<p>Sunset: " + getLondon().getForecast().getForecastday()[0].getAstro().getSunset() + "</p>\n");
        m_out.write("<p>Moonrise: " + getLondon().getForecast().getForecastday()[0].getAstro().getMoonrise() + "</p>\n");
        m_out.write("<p>Moonset: " + getLondon().getForecast().getForecastday()[0].getAstro().getMoonset() + "</p>\n");
        m_out.write("</div>\n");
    }

    private void displayDailyWeather() throws IOException {
        m_out.write("<div class=\"col-md-4\">\n");
        m_out.write("<h3>" + "Daily Weather Situation" + "</h3>\n");
        m_out.write("<p>Last Updated: " + getLondon().getForecast().getForecastday()[0].getDate() + "</p>\n");
        m_out.write("<p>Min. Celsius: " + getLondon().getForecast().getForecastday()[0].getDay().getMintemp_c() + "</p>\n");
        m_out.write("<p>Max. Celsius: " + getLondon().getForecast().getForecastday()[0].getDay().getMaxtemp_c() + "</p>\n");
        m_out.write("<p>Min. Fahrenheit: " + getLondon().getForecast().getForecastday()[0].getDay().getMintemp_f() + "</p>\n");
        m_out.write("<p>Max. Fahrenheit: " + getLondon().getForecast().getForecastday()[0].getDay().getMaxtemp_f() + "</p>\n");
        m_out.write("<p>Average Humidity: " + getLondon().getForecast().getForecastday()[0].getDay().getAvghumidity() + "</p>\n");
        m_out.write("<p>Condition: " + getLondon().getForecast().getForecastday()[0].getDay().getCondition().getText() + "</p>\n");
        m_out.write("<img alt=\"" + getLondon().getForecast().getForecastday()[0].getDay().getCondition().getIcon() +"\"" + " src=\"" + getLondon().getForecast().getForecastday()[0].getDay().getCondition().getIcon() + "\" " + ">\n");
        m_out.write("</div>\n");
    }

    private void displayConditions() throws IOException {
        m_out.write("<div class=\"col-md-4\">\n");
        m_out.write("<h3>" + "Conditions " + "</h3>\n");
        m_out.write("<p>Wind in mph : " + getLondon().getCurrent().getWind_mph() + "</p>\n");
        m_out.write("<p>Wind in kmh : " + getLondon().getCurrent().getWind_kph() + "</p>\n");
        m_out.write("<p>Wind direction : " + getLondon().getCurrent().getWind_kph() + "</p>\n");
        m_out.write("<p>Humidity : " + getLondon().getCurrent().getHumidity() + "</p>\n");
        m_out.write("<p>Cloud percentage : " + getLondon().getCurrent().getCloud() + "</p>\n");
        m_out.write("<p>Feels like Celsius: " + getLondon().getCurrent().getFeelslike_c() + "</p>\n");
        m_out.write("<p>Feels like Fahrenheit: " + getLondon().getCurrent().getFeelslike_f() + "</p>\n");
        m_out.write("<p>Condition: " + getLondon().getCurrent().getCondition().getText() + "</p>\n");
        m_out.write("<img alt=\"" + getLondon().getCurrent().getCondition().getIcon() +"\"" + " src=\"" + getLondon().getCurrent().getCondition().getIcon() + "\" " + ">\n");
        m_out.write("</div>\n");
    }

    private void displayCurrentWeather() throws IOException {
        m_out.write("<div class=\"col-md-6\">\n");
        m_out.write("<h3>" + "Current Weather Situation" + "</h3>\n");
        m_out.write("<p>Last Updated: " + getLondon().getCurrent().getLast_updated() + "</p>\n");
        m_out.write("<p>Celsius: " + getLondon().getCurrent().getFeelslike_c() + "</p>\n");
        m_out.write("<p>Fahrenheit: " + getLondon().getCurrent().getFeelslike_f() + "</p>\n");
        m_out.write("</div>\n");
    }

    private void displayLondon() throws IOException {
        m_out.write("<div class=\"col-md-4\">\n");
        m_out.write("<h3>" + getLondon().getLocation().getName() + "</h3>\n");
        m_out.write("<p>Region: " + getLondon().getLocation().getRegion() + "</p>\n");
        m_out.write("<p>Country: " + getLondon().getLocation().getCountry() + "</p>\n");
        m_out.write("<p>Latitude: " + getLondon().getLocation().getLat() + "</p>\n");
        m_out.write("<p>Longitude: " + getLondon().getLocation().getLon() + "</p>\n");
        m_out.write("<p>Timezone: " + getLondon().getLocation().getTz_id() + "</p>\n");
        m_out.write("<p>Localtime: " + getLondon().getLocation().getLocaltime() + "</p>\n");
        m_out.write("</div>\n");
    }

    private WeatherData getLondon() {
        return weatherDataArr.get(0);
    }
}
