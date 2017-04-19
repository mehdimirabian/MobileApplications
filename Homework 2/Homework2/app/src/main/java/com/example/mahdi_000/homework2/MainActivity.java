package com.example.mahdi_000.homework2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.*;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {
    private String user_id;
    public static String LOG_TAG = "MyApplication";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void click(View v){

        showMe();
    }

    public void showMe(){

        //now here I need to get the data from base url
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        user_id = settings.getString("user_id", null);
        if (user_id == null) {
            // Creates a random one, and sets it.
            SecureRandomString srs = new SecureRandomString();
            user_id = srs.nextString();
            SharedPreferences.Editor e = settings.edit();
            e.putString("user_id", user_id);
            e.commit();
        }


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://luca-teaching.appspot.com/weather/")	//We are using Foursquare API to get data
                .addConverterFactory(GsonConverterFactory.create())	//parse Gson string
                .client(httpClient)	//add logging
                .build();


        WeatherService service = retrofit.create(WeatherService.class);

        Call<ClassData> queryResponseCall =
                service.getWeather();

        //call retrofit asynchronously
        queryResponseCall.enqueue(new Callback<ClassData>() {
            @Override
            public void onResponse(Response<ClassData> response) {
                Log.i(LOG_TAG, "Code is: " + response.code());
                Log.i(LOG_TAG, "The result is: " + response.body().response);

                TextView weath, loc, wind, hum, temp;
                weath = (TextView)findViewById(R.id.weather);
                weath.setText(response.body().response.conditions.weather);


                loc = (TextView)findViewById(R.id.location);
                loc.setText(response.body().response.conditions.observationLocation.city);

                hum = (TextView)findViewById(R.id.humidity);
                hum.setText("Relative Humidity is: " + response.body().response.conditions.relativeHumidity);

                temp = (TextView)findViewById(R.id.temperature);
                temp.setText("Temperature is: " + Double.toString(response.body().response.conditions.tempF) + " (F)");

                wind = (TextView)findViewById(R.id.windSpeed);
                wind.setText("Wind Speed is: " + Double.toString(response.body().response.conditions.windMph) + " (Mph)");




//                Conditions cond = new Conditions();
//                String weat;
//                weat = cond.weather;
//                TextView txt2;
//                txt2 = (TextView)findViewById(R.id.weather);
//                txt2.setText(weat);


            }

            @Override
            public void onFailure(Throwable t) {
                // Log error here since request failed
            }
        });


        //here i parse data received and save it in the class data




    }



    public interface WeatherService {
        @GET("default/get_weather")
        Call<ClassData> getWeather();
    }


}






//here i set texts to the data recieved

