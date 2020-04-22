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
                if (!hasValidationErrors(heightCM, age, weightKG, firstname, secondname)) {
                    OkHttpClient client = new OkHttpClient();

                    JSONObject postdata = new JSONObject();
                    try {
                        postdata.put("id", userId);
                        postdata.put("firstName", firstname);
                        postdata.put("secondName", secondname);
                        postdata.put("age", age);
                        postdata.put("gender", "Male");
                        postdata.put("weightKG", weightKG);
                        postdata.put("heightCM", heightCM);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

                    Request request = new Request.Builder()
                            .url(userDataURL + "/" + userId)
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
                            Log.d("response", mMessage);

                        }
                    });
                    Intent intent = new Intent(yourdetails.this, MainActivity.class);
                    startActivity(intent);

                }else{

                }
            }



        });






        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(yourdetails.this, MainActivity.class));
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
                .url(userDataURL+"/"+userId)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject myResponse = new JSONObject(response.body().string());
                    firstname = myResponse.getString("firstName");
                    secondname = myResponse.getString("secondName");
                    age = myResponse.getString("age");
                    weightKG = myResponse.getString("weightKG");
                    heightCM = myResponse.getString("heightCM");
                    Log.d("testId", firstname);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                updateUI(firstname,secondname,age,weightKG,heightCM);

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
    private boolean hasValidationErrors(String heightin, String agein, String weightin, String fnameIn,  String lnameIn) {
        if (fnameIn.isEmpty()) {
            firstNameTXT.setError("Required");
            firstNameTXT.requestFocus();
            return true;
        }
        if (lnameIn.isEmpty()) {
            secondNameTXT.setError("Required");
            secondNameTXT.requestFocus();
            return true;
        }
        if(!heightin.isEmpty() && !agein.isEmpty() && !weightin.isEmpty()) {
            double heightDouble = Double.parseDouble(heightin);
            int ageint = Integer.parseInt(agein);
            double weightDouble = Double.parseDouble(weightin);
            if (ageint <= 4 && ageint >= 111) {
                ageTXT.setError("Required");
                ageTXT.requestFocus();
                return true;
            }
            if (heightDouble <= 5 && heightDouble >= 220) {
                heightTXT.setError("Height must be between 5 and 220 CM");
                heightTXT.requestFocus();
                return true;
            }
            if (weightDouble <= 5 && weightDouble >= 150) {
                weightTXT.setError("KG must be between 5 and 150");
                weightTXT.requestFocus();
                return true;
            }
        }
        if (heightin.isEmpty()) {
            heightTXT.setError("Required");
            heightTXT.requestFocus();
            return true;
        }
        if (agein.isEmpty()) {
            ageTXT.setError("Required");
            ageTXT.requestFocus();
            return true;
        }
        if (weightin.isEmpty()) {
            weightTXT.setError("Required");
            weightTXT.requestFocus();
            return true;
        }

        return false;

    };



}
