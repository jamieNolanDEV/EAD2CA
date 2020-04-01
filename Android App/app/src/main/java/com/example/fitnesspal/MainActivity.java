package com.example.fitnesspal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    ImageView BFI;
    private TextView quoteOfTheDay;
    JSONObject jsonObj = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BFI = findViewById(R.id.BFI);

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
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    try {
                         jsonObj = new JSONObject(myResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                quoteOfTheDay.setText(jsonObj.getString("text"));
                            } catch (JSONException e) {
                                quoteOfTheDay.setText("No quote found!");
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        BFI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserDataActivity.class));
            }
        });
    }
}


