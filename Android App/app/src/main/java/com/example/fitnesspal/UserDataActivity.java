package com.example.fitnesspal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
    private String userDataURL = "https://localhost:5001/api/UserData";
    String firstname = "Test";
    String secondName = "TestSecondName";
    String gender = "MALE";
    String age = "5";
    String weightKG = "100";
    String heightCM = "100";
    String workouts = "100";



    String postBody="{\n" +
            "    \"firstname\": \""+firstname+"\",\n" +
            "    \"secondname\": \""+secondName+"\",\n" +
            "    \"gender\": \""+gender+"\"\n" +
            "    \"age\": \""+age+"\"\n" +
            "    \"weightKG\": \""+weightKG+"\"\n" +
            "    \"heightCM\": \""+heightCM+"\"\n" +
            "    \"workouts\": \""+workouts+"\"\n" +
            "}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final OkHttpClient client = new OkHttpClient();
        final String postUrl = userDataURL;
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

    }
    void postRequest(String postUrl,String postBody) throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TAG",response.body().string());
            }
        });
    }


}

