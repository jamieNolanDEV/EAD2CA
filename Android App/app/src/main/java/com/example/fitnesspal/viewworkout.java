package com.example.fitnesspal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class viewworkout extends AppCompatActivity {
    String duration, date, calburned, description;
    TextView durationTXT, dateTXT, calburnedTXT, descriptionTXT;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewworkout);
        duration = getIntent().getStringExtra("duration");
        date = getIntent().getStringExtra("date");
        calburned = getIntent().getStringExtra("calburned");
        description = getIntent().getStringExtra("description");

        durationTXT = findViewById(R.id.duration);
        dateTXT = findViewById(R.id.date);
        calburnedTXT = findViewById(R.id.calburned);
        descriptionTXT = findViewById(R.id.description);
        back = findViewById(R.id.back);


        durationTXT.setText(duration);
        dateTXT.setText(date);
        calburnedTXT.setText(calburned);
        descriptionTXT.setText(description);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(viewworkout.this, Workouts.class);

                startActivity(intent);

            }
        });

    }
}
