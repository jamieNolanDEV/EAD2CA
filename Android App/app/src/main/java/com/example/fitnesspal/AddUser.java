package com.example.fitnesspal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddUser extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    String userId;
    private EditText fname, lname,age, height, weight;
    private String userDataURL = "http://fitnessapi-dev.eu-west-1.elasticbeanstalk.com/api/UserData";
    private Button Confirm;
    ArrayList<String> userIdJSON = new ArrayList<String>();
    private RadioGroup genderGroup;
    private RadioButton gender;
    String fnameStr, lnameStr, ageStr, heightStr, weightStr, genderStr,  id,realId;
    int IdInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId="";
        setContentView(R.layout.activity_add_user);
        Confirm = findViewById(R.id.add);
        age = findViewById(R.id.age);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        getData();
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
                getData();
                IdInt = IdInt +1;
                realId = String.valueOf(IdInt);
                id = realId;
                saveData();
                Intent intent = new Intent(AddUser.this, MainActivity.class);
                intent.putExtra("id", id);
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
                try {
                    final JSONObject getasObj = new JSONObject(response.body().string());
                    Log.d("JSONOBJ", getasObj.getString("firstName"));

                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void getData(){

        OkHttpClient client = new OkHttpClient();
        String url = "http://fitnessapi-dev.eu-west-1.elasticbeanstalk.com/api/UserData";
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
                try {
                    final JSONArray myResponse = new JSONArray(response.body().string());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for (int i = 0; i < myResponse.length(); i++) {
                                    JSONObject object = myResponse.getJSONObject(i);
                                    id = object.getString("id");
                                    IdInt = Integer.parseInt(id);
                                    Log.d("testId", id);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", id);
        editor.apply();

    }




    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");
    }



}
