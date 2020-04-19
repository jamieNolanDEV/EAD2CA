package com.example.fitnesspal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private ImageView BFI,BMI, AddWorkout, Workouts,yourdetails,stats;
    private TextView quoteOfTheDay;
    public static final String SHARED_PREFS = "sharedPrefs";

    private JSONObject jsonObj = new JSONObject();
    private String name, lastname, gender, age, weightKG, heightCM,userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();

        BFI = findViewById(R.id.BFI);
        BMI = findViewById(R.id.bmi);
        Workouts = findViewById(R.id.workout);
        AddWorkout = findViewById(R.id.addWorkout);
        yourdetails = findViewById(R.id.details);
        stats = findViewById(R.id.stats);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (userId == "") {
            startActivity(new Intent(MainActivity.this, AddUser.class));
        }

        Log.d("name", userId);


        quoteOfTheDay = findViewById(R.id.quote);
        OkHttpClient client = new OkHttpClient();
        String url = "https://type.fit/api/quotes";
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONArray myResponse = new JSONArray(response.body().string());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int i = 0; i < myResponse.length(); i++) {
                                    JSONObject object = myResponse.getJSONObject(i);
                                    String quote = object.getString("text");
                                    quoteOfTheDay.setText("Quote of the day: " + quote);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        BFI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewBMR.class);
                startActivity(intent);
            }
        });
        BMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewBMI.class);
                startActivity(intent);
            }
        });
        AddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, addWorkout.class));
            }
        });
        Workouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Workouts.class));
            }
        });
        yourdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, yourdetails.class));
            }
        });
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, userstats.class));
            }
        });

    }
    public void getData(){

        OkHttpClient client = new OkHttpClient();
        String url = "http://fitnessapi-dev.eu-west-1.elasticbeanstalk.com/api/UserData";
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONArray myResponse = new JSONArray(response.body().string());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int i = 0; i < myResponse.length(); i++) {
                                    JSONObject object = myResponse.getJSONObject(i);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");
    }

    private void setStarted() {

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }



    }


