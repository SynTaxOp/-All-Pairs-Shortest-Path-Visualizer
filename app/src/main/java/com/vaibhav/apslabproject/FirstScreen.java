package com.vaibhav.apslabproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

public class FirstScreen extends AppCompatActivity {

    ImageView img;
    int x = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        img = findViewById(R.id.logo);
        CountDownTimer t = new CountDownTimer(3000, 100) {
            public void onTick(long millisUntilFinished) {
                if(x < 21) {
                    x++;
                }
                img.setAlpha((float) x/20);
            }

            public void onFinish() {
                Intent intent = new Intent(FirstScreen.this, Details.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}