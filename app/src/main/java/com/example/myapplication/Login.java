package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class Login extends AppCompatActivity {

    EditText username, password;
    Button login, signup;
    UserDBHelper userDB;
    //public static String USERNAME = " ";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        userDB = new UserDBHelper(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String userTXT = username.getText().toString();
                String passwordTXT = password.getText().toString();
                Cursor res = userDB.getlogindata(userTXT);
                if(res.getCount()==0){
                    Toast.makeText(Login.this, "No Such User Exists", Toast.LENGTH_SHORT).show();
                    return;
                } else {


                    String chkpass = null;
                    String chkuser = null;
                    while (res.moveToNext()) {
                        chkpass = res.getString(1);
                        chkuser = res.getString(0);
                    }
                    if (userTXT.equals(chkuser) == true && passwordTXT.equals(chkpass) == true) {
                        String name = username.getText().toString();


                        Intent intent = new Intent(Login.this, MainActivity2.class);
                        intent.putExtra("USERNAME", name);
                        startActivity(intent);


                    } else {

                        Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }        });


    }

}