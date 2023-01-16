package com.example.a201713709035kori;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent (splash.this, MainActivity.class));
            return;
        }
        else startActivity(new Intent(splash.this, signin.class));


        findViewById(R.id.girisyap).setOnClickListener(viewClickListener ->
                startActivity(new Intent(splash.this, signin.class)));

        findViewById(R.id.kayitol).setOnClickListener(viewClickListener ->
                startActivity(new Intent(splash.this, register.class)));

    }
}