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
import android.widget.Toast;

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

public class AddBMR extends AppCompatActivity {
    private EditText BMR;
    private String userDataURL = "http://fitnesswebapi-dev.eu-west-1.elasticbeanstalk.com/api/UserData";
    private Button Confirm;
    private String userId;
    String bmr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_b_m_r);
        Confirm = findViewById(R.id.addbmr);

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmr = Confirm.getText().toString();

                try {
                    postRequest(userDataURL, userId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(AddBMR.this, MainActivity.class);

                startActivity(intent);

            }
        });


    }
    public void postRequest(String userDataURL,String userId) throws IOException {

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("bmr", bmr);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(userDataURL + "/" + userId)
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
                Toast.makeText(AddBMR.this, "Updated BMR!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
