package com.example.fitnesspal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Workouts extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<String> workoutList = new ArrayList<>();
    private String userDataURL = "http://fitnessapi-dev.eu-west-1.elasticbeanstalk.com/api/UserData";
    String arm = "Arm Workout";
    MyRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);
        workoutList.add("Arm");
        workoutList.add("Leg");
        workoutList.add("Leg");
        workoutList.add("Leg");
        workoutList.add("Leg");
        RecyclerView recyclerView = findViewById(R.id.workoutlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, workoutList);
        recyclerView.setAdapter(adapter);




    }

    public void get() throws IOException {

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url(userDataURL)
                .get()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
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
                                    mAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
