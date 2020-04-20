package com.example.fitnesspal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class UpdateWeight extends AppCompatActivity {
    private EditText weight;
    public static final String SHARED_PREFS = "sharedPrefs";
    private String userDataURL = "http://fitnessapi-dev.eu-west-1.elasticbeanstalk.com/api/UserData";
    private Button Confirm;
    private String userId,firstname,secondName,gender,age,weightKG,heightCM;
    String newWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_b_m_i);
        loadData();

        Confirm = findViewById(R.id.addbmi);
        getData();
        Log.d("userid", userId);
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newWeight = Confirm.getText().toString();

                try {
                    postRequest(userDataURL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(UpdateWeight.this, MainActivity.class);

                startActivity(intent);

            }
        });


    }
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");
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
                String responseStr = response.body().string().toString();
                try {
                    JSONObject details = new JSONObject(responseStr);
                    for (int i = 0; i < details.length(); i++) {
                        firstname = details.getString("firstName");
                        secondName = details.getString("secondName");
                        gender = details.getString("gender");
                        age = details.getString("age");
                        weightKG = details.getString("weightKG");
                        heightCM = details.getString("heightCM");
                    }
                    } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void postRequest(String userDataURL) throws IOException {

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("firstName", firstname);
            postdata.put("secondName", secondName);
            postdata.put("gender", gender);
            postdata.put("age",age);
            postdata.put("weightKG",newWeight);
            postdata.put("heightCM",heightCM);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(userDataURL+"/"+"9")
                .put(body)
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


}
