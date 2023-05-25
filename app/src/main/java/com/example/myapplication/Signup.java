package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
                    Toast.makeText(Signup.this, "Please enter Username and Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean checkinsertdata = userDB.insertuserdata(userTXT, passwordTXT);
                    if(checkinsertdata==true)
                    {
                        Toast.makeText(Signup.this, "Account Registered", Toast.LENGTH_SHORT).show();
                        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(Signup.this);

                        alertDialog.setTitle("Registered Successfully");
                        alertDialog.setMessage("Do you want to proceed to Log In?");

                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent goback = new Intent(Signup.this, Login.class);
                                startActivity(goback);
                            }
                        });
                        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                username.setText("");
                                password.setText("");
                                dialog.cancel();
                            }
                        });

                        // Showing Alert Message
                        alertDialog.show();
                    }
                    else
                        Toast.makeText(Signup.this, "Username already taken", Toast.LENGTH_SHORT).show();
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
