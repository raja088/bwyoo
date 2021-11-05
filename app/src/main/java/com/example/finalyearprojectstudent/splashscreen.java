package com.example.finalyearprojectstudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

public class splashscreen extends AppCompatActivity {
    private TextView welcomevalet;
    private ImageView fyplogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_splashscreen);
        fyplogo= (ImageView) findViewById(R.id.fyplogo);
        welcomevalet= (TextView) findViewById(R.id.welcomevalet);
        Animation myanim=AnimationUtils.loadAnimation(this, R.anim.splashtransition);
        welcomevalet.startAnimation(myanim);
        fyplogo.startAnimation(myanim);
        SharedPrefrence.init(splashscreen.this);


        Thread timer= new Thread(){
            public  void run(){
                try {
                    sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{

                    if(!TextUtils.isEmpty(SharedPrefrence.read(SharedPrefrence.user_email,"")))
                    {
                        startActivity(new Intent(splashscreen.this,DriverPanel.class));
                        finish();

                    }
                    else if(!TextUtils.isEmpty(SharedPrefrence.read(SharedPrefrence.admin_email,"")))
                    {
                        startActivity(new Intent(splashscreen.this,AdminPanel.class));
                        finish();
                    }
                    else
                    {
                        startActivity(new Intent(splashscreen.this,LoginMainActivity.class));
                        finish();
                    }

                }
            }
        };
            timer.start();



    }
}