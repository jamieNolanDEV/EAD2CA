package com.example.fitnesspal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class viewworkout extends AppCompatActivity {
    String duration, date, calburned, description, id,userId;
    TextView durationTXT, dateTXT, calburnedTXT, descriptionTXT;
    private String userDataURL = "http://fitnessapi-dev.eu-west-1.elasticbeanstalk.com/api/WorkoutData";
    public static final String SHARED_PREFS = "sharedPrefs";

    Button back, delete, update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewworkout);
        loadData();
        duration = getIntent().getStringExtra("duration");
        date = getIntent().getStringExtra("date");
        calburned = getIntent().getStringExtra("calburned");
        description = getIntent().getStringExtra("description");
        id = getIntent().getStringExtra("id");
        durationTXT = findViewById(R.id.duration);
        dateTXT = findViewById(R.id.date);
        calburnedTXT = findViewById(R.id.calburned);
        descriptionTXT = findViewById(R.id.description);
        back = findViewById(R.id.back);
        delete = findViewById(R.id.delete);
        update = findViewById(R.id.updateWorkout);
        durationTXT.setText(duration);
        dateTXT.setText(date);
        calburnedTXT.setText(calburned);
        descriptionTXT.setText(description);
        Log.d("id",id);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(viewworkout.this, Workouts.class);

                startActivity(intent);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    OkHttpClient client = new OkHttpClient();


                    Request request = new Request.Builder()
                            .url(userDataURL+"/"+id)
                            .delete()
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")

                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            String mMessage = e.getMessage().toString();
                            Log.w("failure Response", mMessage);
                            //call.cancel();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String mMessage = response.body().string();
                            Log.d("response",mMessage);

                        }
                    });
                }



        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaType MEDIA_TYPE = MediaType.parse("application/json");

                OkHttpClient client = new OkHttpClient();

                JSONObject postdata = new JSONObject();
                try {
                    postdata.put("id", id);
                    postdata.put("date", date);
                    postdata.put("workoutDuration", duration);
                    postdata.put("workoutDetails",description);
                    postdata.put("caloriesBurned",calburned);
                    postdata.put("userID",userId);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

                    Request request = new Request.Builder()
                            .url(userDataURL+"/"+id)
                            .put(body)
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")

                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            String mMessage = e.getMessage().toString();
                            Log.w("failure Response", mMessage);
                            //call.cancel();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String mMessage = response.body().string();
                            Log.d("response",mMessage);

                        }
                    });
                }



            });


            }


    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");
    }

    }

