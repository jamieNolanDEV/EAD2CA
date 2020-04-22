package com.example.fitnesspal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class addWorkout extends AppCompatActivity {
    private String userId, workoutDuration, workoutDetails, caloriesBurned, isoDate,day, month, year;
    public static final String SHARED_PREFS = "sharedPrefs";
    EditText workoutDurationText, workoutDetailsText, caloriesBurnedText, dayText, monthText, yearText;
    private String userDataURL = "http://fitnessapi-dev.eu-west-1.elasticbeanstalk.com/api/WorkoutData";
    Button addWorkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        loadData();
        addWorkout = findViewById(R.id.add);
        workoutDurationText = findViewById(R.id.wDuration);
        workoutDetailsText = findViewById(R.id.wDescription);
        caloriesBurnedText = findViewById(R.id.calBurned);
        dayText = findViewById(R.id.Day);
        monthText = findViewById(R.id.Month);
        yearText = findViewById(R.id.Year);



        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutDuration = workoutDurationText.getText().toString();
                workoutDetails = workoutDetailsText.getText().toString();
                caloriesBurned = caloriesBurnedText.getText().toString();
                day = dayText.getText().toString();
                month = monthText.getText().toString();
                year = yearText.getText().toString();
                if (!hasValidationErrors(day, month, year, workoutDuration, caloriesBurned, workoutDetails)) {
                    try {
                        postRequest(userDataURL);
                        Intent intent = new Intent(addWorkout.this, MainActivity.class);
                        startActivity(intent);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast abs = Toast.makeText(addWorkout.this, "Error, Please verify the passwords match and all input fields have been used.", Toast.LENGTH_LONG);
                    abs.show();
                }

            }
        });


    }
    public void postRequest(String userDataURL) throws IOException {

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("date", year+"-"+month+"-"+day);
            postdata.put("workoutDuration", workoutDuration);
            postdata.put("workoutDetails", workoutDetails);
            postdata.put("caloriesBurned", caloriesBurned);
            postdata.put("userID", userId);
            Log.d("useridposted", userId);
            Log.d("workoutDuration", workoutDuration);
            Log.d("workoutDetails", workoutDetails);
            Log.d("caloriesBurned", caloriesBurned);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(userDataURL)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")

                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String mMessage = response.body().string();
                Log.d("response",mMessage);

            }
        });
    }
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");
    }
    private boolean hasValidationErrors(String dayin, String monthin, String yearin, String duration, String calburned, String description) {

        if(!day.isEmpty() && !monthin.isEmpty() && !yearin.isEmpty()) {
            int dayint = Integer.parseInt(dayin);
            int monthint = Integer.parseInt(monthin);
            int yearint = Integer.parseInt(yearin);
            if (dayint <= 0 && dayint >= 31) {
                dayText.setError("Input correct day");
                dayText.requestFocus();
                return true;
            }
            if (monthint <= 0 && monthint >= 13) {
                monthText.setError("Input correct Month");
                monthText.requestFocus();
                return true;
            }
            if (yearint <= 1980 && yearint >= 2100) {
                yearText.setError("Input correct year");
                yearText.requestFocus();
                return true;
            }
        }
        if (duration.isEmpty()) {
            workoutDurationText.setError("Required");
            workoutDurationText.requestFocus();
            return true;
        }
        if (calburned.isEmpty()) {
            caloriesBurnedText.setError("Required");
            caloriesBurnedText.requestFocus();
            return true;
        }
        if (description.isEmpty()) {
            workoutDetailsText.setError("Required");
            workoutDetailsText.requestFocus();
            return true;
        }

        return false;

    };
}







