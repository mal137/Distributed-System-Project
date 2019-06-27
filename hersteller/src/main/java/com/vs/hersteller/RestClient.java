package com.vs.hersteller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.vs.hersteller.MqttHandler.EXECUTED_REST_REQUEST;
import static com.vs.hersteller.MqttHandler.WEATHERDATA_ARRAY;

public class RestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
    private CopyOnWriteArrayList<String> m_weatherArrApi;

    public RestClient() {
        m_weatherArrApi = WEATHERDATA_ARRAY;
    }

    public void run() {

        // Only one time
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        HttpResponse<WeatherData> weatherResponse = null;
        String wdObj;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            if(!EXECUTED_REST_REQUEST) {
                wdObj = Unirest.
                        get("http://api.apixu.com/v1/forecast.json?key=f1072e91115249cbbe9220934181911&q=Frankfurt").
                        asJson().getBody().toString();
                m_weatherArrApi.add(wdObj);
                EXECUTED_REST_REQUEST = true;
            }
        }
        catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}