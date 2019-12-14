package com.example.weather_application;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Requester {

    private final String apiKey = "kl6Dr8KruNfVJxicyAnEZ94vKUmK9Ll8";
    private final String urlWeather = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/";
    private final OkHttpClient client = new OkHttpClient();

    public Weather getWeather() {
        try {
            return getWeatherByLocationKey("17253");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }



    private Weather getWeatherByLocationKey(String locationKey) throws IOException, JSONException, InterruptedException {
        HttpUrl.Builder httpBuilder = Objects.requireNonNull(HttpUrl.parse(urlWeather + locationKey))
                .newBuilder();
        httpBuilder.addQueryParameter("apikey", apiKey);
        httpBuilder.addQueryParameter("metric", "true");

        Request request = new Request.Builder()
                .url(httpBuilder.build()).build();


        final Response[] response = new Response[1];
        client.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, @NotNull Response resp) throws IOException {


                response[0] = resp;
            }

        });

        TimeUnit.SECONDS.sleep(1);
        JSONObject jsonObject = new JSONObject(response[0].body().string());
        Weather weather = new Weather();
        weather.setMaxTemperature(getMaximumTemp(jsonObject));
        weather.setMinTemperature(getMinimumTemp(jsonObject));
        weather.setDay(getDay(jsonObject));
        weather.setNight(getNight(jsonObject));
        System.out.println(getMaximumTemp(jsonObject));
        System.out.println(getMinimumTemp(jsonObject));
        System.out.println(getDay(jsonObject));
        System.out.println(getNight(jsonObject));
        response[0].close();
        return weather;
    }


    private int getMinimumTemp(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONArray("DailyForecasts")
                .getJSONObject(0)
                .getJSONObject("Temperature")
                .getJSONObject("Minimum")
                .getInt("Value");
    }

    private int getMaximumTemp(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONArray("DailyForecasts")
                .getJSONObject(0)
                .getJSONObject("Temperature")
                .getJSONObject("Maximum")
                .getInt("Value");
    }

    private String getDay(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONArray("DailyForecasts")
                .getJSONObject(0)
                .getJSONObject("Day")
                .getString("IconPhrase");
    }

    private String getNight(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONArray("DailyForecasts")
                .getJSONObject(0)
                .getJSONObject("Day")
                .getString("IconPhrase");
    }

}

