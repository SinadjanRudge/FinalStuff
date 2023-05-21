package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class Login extends AppCompatActivity {

    EditText username, password;
    Button login, goToSignup;
    UserDBHelper userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.btnLogin);
        goToSignup = findViewById(R.id.btngoToSignup);

        userDB = new UserDBHelper(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userTXT = username.getText().toString();
                String passTXT = password.getText().toString();
                Cursor res = userDB.getlogindata(userTXT);

                if(res.getCount()==0)
                {
                    Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Cursor chk = userDB.checklogindata(passTXT);

                    String chkpass = null;
                    String chkuser = null;
                    while (chk.moveToNext())
                    {
                        chkpass = chk.getString(2);
                        chkuser = chk.getString(1);
                    }

                    if (userTXT.equals(chkuser) == true && passTXT .equals(chkpass) == true)
                    {
                        String username1 = username.getText().toString();

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("USERNAME", username1);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        goToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }        });


    }

}