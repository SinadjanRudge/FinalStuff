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

    EditText name, contact, dob;
    Button insert, update, delete, view;
    UserDBHelper userDB;
    //public static String USERNAME = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        contact = findViewById(R.id.contact);
        dob = findViewById(R.id.dob);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);

        //view = findViewById(R.id.btnView);

        userDB = new UserDBHelper(this);


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String contactTXT = contact.getText().toString();
                String dobTXT = dob.getText().toString();
                Cursor res = userDB.getlogindata(contactTXT);
                if(res.getCount()==0){
                    Toast.makeText(Login.this, "No Such User Exists", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Cursor chk = userDB.checklogindata(dobTXT);

                    String chkpass = null;
                    while (chk.moveToNext()) {
                        chkpass = chk.getString(1);

                    }
                    if (contactTXT.equals(chkpass) == true) {
                        String username = contact.getText().toString();


                        Intent intent = new Intent(Login.this, MainActivity.class);

                        intent.putExtra("USERNAME", username);
                        startActivity(intent);


                    } else {

                        Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }        });


    }

}