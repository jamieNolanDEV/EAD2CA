package com.example.fitnesspal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class userstats extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";

    String userId,workoutTimeMonth,workoutTime30Days,workoutTime7days,longestWorkout,caloriesBurnedLast30Days,caloriesBurnedLast7Days,monthlyCalBurned;
    TextView cal7, wTimeMonth, WTime7, longest, Cal30;
    Button back;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userstats);
        loadData();
        cal7 = findViewById(R.id.c7days);
        wTimeMonth = findViewById(R.id.w30days);
        WTime7 = findViewById(R.id.w7Days);
        longest = findViewById(R.id.lWorkout);
        Cal30 = findViewById(R.id.c30Days);
        back = findViewById(R.id.back);
        getData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(userstats.this, MainActivity.class);

                startActivity(intent);

            }
        });

    }
    public void getData(){

        OkHttpClient client = new OkHttpClient();
        String url = "http://fitnessapi-dev.eu-west-1.elasticbeanstalk.com/api/UserData";
        Request request = new Request.Builder()
                .url(url+"/"+userId)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    final JSONObject myResponse = new JSONObject(response.body().string());
                    caloriesBurnedLast7Days = myResponse.getString("caloriesBurnedLast7Days");
                    workoutTime30Days = myResponse.getString("workoutTime30Days");
                    workoutTime7days = myResponse.getString("workoutTime7Days");
                    longestWorkout = myResponse.getString("longestWorkout");
                    caloriesBurnedLast30Days = myResponse.getString("caloriesBurnedLast30Days");
                    Log.d("longest", longestWorkout);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                updateUI(caloriesBurnedLast7Days,workoutTime30Days,workoutTime7days,longestWorkout,caloriesBurnedLast30Days);

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
    void updateUI(String cal7days, String w30days, String work7days, String longests,String cal30days){
        cal7.setText(cal7days);
        wTimeMonth.setText(w30days);
        WTime7.setText(work7days);
        longest.setText(longests);
        Cal30.setText(cal30days);

    }


}
