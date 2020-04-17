package com.example.fitnesspal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
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

public class  yourdetails extends AppCompatActivity {
    private String userDataURL = "http://fitnessapi-dev.eu-west-1.elasticbeanstalk.com/api/UserData";
    public static final String SHARED_PREFS = "sharedPrefs";
    Button delete,update, back;
    String userId, firstname, secondname,age,weightKG,heightCM;
    EditText firstNameTXT, secondNameTXT, genderTXT, ageTXT, weightTXT, heightTXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourdetails);
        loadData();
        firstNameTXT = findViewById(R.id.fname);
        secondNameTXT = findViewById(R.id.lname);
        ageTXT = findViewById(R.id.age);
        weightTXT = findViewById(R.id.weight);
        heightTXT = findViewById(R.id.height);
        delete = findViewById(R.id.delete);
        update = findViewById(R.id.update);
        back = findViewById(R.id.back);
        getData();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaType MEDIA_TYPE = MediaType.parse("application/json");
                firstname = firstNameTXT.getText().toString();
                secondname = secondNameTXT.getText().toString();
                age = ageTXT.getText().toString();
                weightKG = weightTXT.getText().toString();
                heightCM = heightTXT.getText().toString();
                OkHttpClient client = new OkHttpClient();

                JSONObject postdata = new JSONObject();
                try {
                    postdata.put("id", userId);
                    postdata.put("firstName", firstname);
                    postdata.put("secondName", secondname);
                    postdata.put("age",age);
                    postdata.put("gender","Male");
                    postdata.put("weightKG",weightKG);
                    postdata.put("heightCM",heightCM);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

                Request request = new Request.Builder()
                        .url(userDataURL+"/"+userId)
                        .put(body)
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
                        Log.d("response",mMessage);

                    }
                });
                Intent intent = new Intent(yourdetails.this, MainActivity.class);
                startActivity(intent);

            }



        });









        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpClient client = new OkHttpClient();


                Request request = new Request.Builder()
                        .url(userDataURL+"/"+userId)
                        .delete()
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
                        Log.d("response",mMessage);

                    }
                });
                Intent intent = new Intent(yourdetails.this, AddUser.class);
                startActivity(intent);
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
                                    firstname = object.getString("firstName");
                                    secondname = object.getString("secondName");
                                    age = object.getString("age");
                                    weightKG = object.getString("weightKG");
                                    heightCM = object.getString("heightCM");
                                    Log.d("testId", firstname);
                                }
                                updateUI(firstname,secondname,age,weightKG,heightCM);

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
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");
    }
    void updateUI(String fname,String lname, String age,String weight,String height){
        firstNameTXT.setText(fname);
        secondNameTXT.setText(lname);
        ageTXT.setText(age);
        weightTXT.setText(weight);
        heightTXT.setText(height);

    }



}
