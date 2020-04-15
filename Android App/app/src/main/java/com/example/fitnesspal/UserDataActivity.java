package com.example.fitnesspal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class UserDataActivity extends AppCompatActivity {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String userDataURL = "http://fitnessapi-dev.eu-west-1.elasticbeanstalk.com/api/UserData";
    String firstname = "Test";
    String secondName = "TestSecondName";
    String gender = "MALE";
    Button Post;
    int age = 5;
    int weightKG = 5;
    int heightCM = 5;
    String workouts = "100";
    String testforSonar;
    String postBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final OkHttpClient client = new OkHttpClient();
        final String postUrl = userDataURL;
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_data);
        Post = findViewById(R.id.post);


        try {
            postRequest(postUrl);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("stacktrace" ,e.toString());
        }
        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    postRequest(postUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void postRequest(String postUrl) throws IOException {

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("firstName", "a");
            postdata.put("secondName", "a");
            postdata.put("gender", "a");
            postdata.put("age","19");
            postdata.put("weightKG","6");
            postdata.put("heightCM","6");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
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
            }
        });
    }


}

