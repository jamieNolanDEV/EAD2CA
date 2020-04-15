package com.example.fitnesspal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

public class AddUser extends AppCompatActivity {
    private EditText fname, lname,age, height, weight;
    private String userDataURL = "http://fitnessapi-dev.eu-west-1.elasticbeanstalk.com/api/UserData";
    private Button Confirm;
    private RadioGroup genderGroup;
    private RadioButton gender;
    String fnameStr, lnameStr, ageStr, heightStr, weightStr, genderStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_user);
        Confirm = findViewById(R.id.add);
        age = findViewById(R.id.age);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 fnameStr = fname.getText().toString();
                 lnameStr = lname.getText().toString();
                 ageStr = age.getText().toString();
                 heightStr = height.getText().toString();
                 weightStr = weight.getText().toString();
                genderGroup = findViewById(R.id.gender);

                int selectedId = genderGroup.getCheckedRadioButtonId();
                gender = (RadioButton) findViewById(selectedId);

                genderStr =gender.getText().toString();

                try {
                    postRequest(userDataURL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(AddUser.this, MainActivity.class);

                startActivity(intent);

            }
        });


    }
    public void postRequest(String userDataURL) throws IOException {

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("firstName", fnameStr);
            postdata.put("secondName", lnameStr);
            postdata.put("gender", genderStr);
            postdata.put("age",ageStr);
            postdata.put("weightKG",weightStr);
            postdata.put("heightCM",heightStr);
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
