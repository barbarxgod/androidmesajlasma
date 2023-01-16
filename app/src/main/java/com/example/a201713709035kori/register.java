package com.example.a201713709035kori;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.Toast;

public class register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText emailText;
    EditText passwordText;
    Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();
        emailText=(EditText)findViewById(R.id.editTextTextPersonName);
        passwordText=(EditText)findViewById(R.id.editTextTextPassword);
        submitButton=(Button)findViewById(R.id.button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email Boş Bırakılamaz", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Şifre Alanı Boş Bırakılamaz", Toast.LENGTH_SHORT).show();
                    passwordText.setError("Password alanı boş bırakılamaz");
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(register.this, task ->
                {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Kayıt Oluşturuldu", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(register.this, signin.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Kayıt Oluşturulamadı", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}