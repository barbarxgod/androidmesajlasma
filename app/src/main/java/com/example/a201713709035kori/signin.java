package com.example.a201713709035kori;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class signin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText emailText;
    EditText passwordText;
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth=FirebaseAuth.getInstance();
        emailText=(EditText)findViewById(R.id.editTextTextPersonName2);
        passwordText=(EditText)findViewById(R.id.editTextTextPassword2);
        submitButton=(Button)findViewById(R.id.button3);
        findViewById(R.id.button3).setOnClickListener(view ->
                startActivity(new Intent(signin.this,register.class)));

        submitButton.setOnClickListener(view ->
        {

            String password=passwordText.getText().toString();
            String email=emailText.getText().toString();
            if (email.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"Email Boş Bırakılamaz",Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"Şifre Alanı Boş Bırakılamaz",Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(signin.this,task ->
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Başarılı Giriş",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(signin.this, MainActivity.class));
                }
                else {
                    Toast.makeText(getApplicationContext(),"Başarısız Giriş",Toast.LENGTH_SHORT).show();

                }
            });
        });
    }
}