package com.example.fitnesspal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ViewBMI extends AppCompatActivity {
    private TextView BMITEXT, BMICAT;
    public static final String SHARED_PREFS = "sharedPrefs";
    private String userDataURL = "http://fitnessapi-dev.eu-west-1.elasticbeanstalk.com/api/UserData";
    private Button Confirm;
    private String userId;
    String bmi, category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewbmi);
        loadData();
        BMITEXT = findViewById(R.id.bmi);
        BMICAT = findViewById(R.id.bmicat);
        Confirm = findViewById(R.id.back);


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
                String responseStr = response.body().string().toString();
                try {
                    JSONObject details = new JSONObject(responseStr);
                    for (int i = 0; i < details.length(); i++) {
                        bmi = details.getString("bmiValue");
                        category = details.getString("bmiCategory");
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            updateUI(bmi, category);

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ViewBMI.this, MainActivity.class);

                startActivity(intent);

            }
        });


    }
    void updateUI(String bmi, String cat){
        BMITEXT.setText(bmi);
        BMICAT.setText(cat);
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");
    }

}
