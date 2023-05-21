package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class Signup extends AppCompatActivity {
    EditText username, password;
    Button login, signup;
    UserDBHelper userDB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        //view = findViewById(R.id.btnView);

        userDB = new UserDBHelper(this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userTXT = username.getText().toString();
                String passwordTXT = password.getText().toString();
                if(userTXT.equals("")==true || passwordTXT.equals("")==true)
                {
                    Toast.makeText(Signup.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else{
                    Boolean checkinsertdata = userDB.insertuserdata(userTXT, passwordTXT);
                    if(checkinsertdata==true)
                        Toast.makeText(Signup.this, "account registered", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Signup.this, "invalid username or password", Toast.LENGTH_SHORT).show();
                }

            }        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }        });

    }

}
