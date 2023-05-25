package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class Profile extends AppCompatActivity {

    EditText username, password;
    Button change, update;
    UserDBHelper userDB;

    String the_name = " ";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");

        the_name = username;

        ActionBar actionBar = getSupportActionBar();


        //providing title for the ActionBar
        actionBar.setTitle(the_name);

        //providing subtitle for the ActionBar
        actionBar.setSubtitle("Tindahan Price Checker");

        actionBar.setDisplayHomeAsUpEnabled(true);

        password = findViewById(R.id.password);
        update = findViewById(R.id.update);


        userDB = new UserDBHelper(this);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(Profile.this);

                alertDialog.setTitle("Are you sure?");
                alertDialog.setMessage("New password: " + password.getText().toString());

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String passwordTXT = password.getText().toString();

                        Boolean checkupdatedata = userDB.updateuserdata(the_name, passwordTXT);
                        if (checkupdatedata == true) {
                            Toast.makeText(Profile.this, "New Password Updated", Toast.LENGTH_SHORT).show();
                            password.setText("");
                        }
                        else
                            Toast.makeText(Profile.this, "Update Error", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getApplicationContext(), "Password Unchanged", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });


                alertDialog.show();
                // return true;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}