package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class Signup extends AppCompatActivity {
    EditText username, password;
    Button signup, goToLogin;
    UserDBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.username_tv);
        password = findViewById(R.id.password_tv);
        signup = findViewById(R.id.btnSignup);
        goToLogin = findViewById(R.id.btngoToLogin);

        DB = new UserDBHelper(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userTXT = username.getText().toString();
                String passTXT = password.getText().toString();

                Boolean checkinsertdata = DB.insertuserdata("test", userTXT, passTXT, "2");
                if(checkinsertdata==true)
                    Toast.makeText(Signup.this, "Account Registered", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Signup.this, "Invalid USERNAME or PASSWORD", Toast.LENGTH_SHORT).show();
            }        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }        });

    }

}
