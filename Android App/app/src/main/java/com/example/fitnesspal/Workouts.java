package com.example.fitnesspal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Workouts extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String userDataURL = "http://fitnessapi-dev.eu-west-1.elasticbeanstalk.com/api/UserData";
    public static final String SHARED_PREFS = "sharedPrefs";

    ArrayList<String> workoutList = new ArrayList<>();
    ArrayList<WorkoutDefine> workoutListDefined = new ArrayList<>();
    private String date, workoutDuration, workoutDetails, caloriesBurned,userId;
    MyRecyclerViewAdapter adapter;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);
        back = findViewById(R.id.back);

        loadData();
        getData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Workouts.this, MainActivity.class));
            }
        });

        RecyclerView recyclerView = findViewById(R.id.workoutlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, workoutListDefined);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                WorkoutDefine workoutItem = workoutListDefined.get(position);
                Intent intent = new Intent(Workouts.this, viewworkout.class);
                intent.putExtra("id",workoutItem.getId());
                intent.putExtra("description",workoutItem.getWorkoutDetails());
                intent.putExtra("duration",workoutItem.getWorkoutDuration());
                intent.putExtra("calburned",workoutItem.getCalBurned());
                intent.putExtra("date",workoutItem.getWorkoutDate());
                startActivity(intent);
            }
        });
    }

    public void getData() {

        OkHttpClient client = new OkHttpClient();
        String url = "http://fitnessapi-dev.eu-west-1.elasticbeanstalk.com/api/UserData";
        Request request = new Request.Builder()
                .url(url + "/" + userId)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                   final String responseStr = response.body().string().toString();

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                JSONObject details = new JSONObject(responseStr);
                                JSONArray childObj = new JSONArray();
                                for (int i = 0; i < details.length(); i++) {
                                    childObj = details.getJSONArray("workouts");

                                    JSONObject result = childObj.getJSONObject(i);
                                    String id = result.getString("id");
                                    String WorkoutDetails = result.getString("workoutDetails");
                                    String WorkoutDuration = result.getString("workoutDuration");
                                    String caloriesBurned = result.getString("caloriesBurned");
                                    String WorkOutDate = result.getString("date");
                                    WorkoutDefine w = new WorkoutDefine(WorkoutDuration, WorkoutDetails, caloriesBurned, WorkOutDate,id);
                                    workoutListDefined.add(w);
                                    adapter.notifyDataSetChanged();
                                    Log.d("abccc", workoutListDefined.get(i).getWorkoutDate());
                                }
                            }catch (JSONException e){
                                System.out.println("");
                            }

                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");
    }
}
