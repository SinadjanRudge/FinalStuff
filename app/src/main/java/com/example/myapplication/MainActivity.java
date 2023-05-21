package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class MainActivity extends AppCompatActivity {
    EditText Barcode, item_name, price;
    Button insert, delete, view;
    DBHelper DB;
    public String other_name = null;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String the_name = username;
        other_name = username;

        ActionBar actionBar = getSupportActionBar();

        //providing title for the ActionBar
        actionBar.setTitle(the_name);

        //providing subtitle for the ActionBar
        actionBar.setSubtitle("Tindahan Price Checker");

        //adding icon in the ActionBar
        actionBar.setIcon(R.mipmap.ic_launcher);

        //methods to display the icon in the ActionBar
        actionBar.setDisplayUseLogoEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_main);
        Barcode = findViewById(R.id.Barcode);
        item_name = findViewById(R.id.item_name);
        price = findViewById(R.id.price);
        insert = findViewById(R.id.btnInsert);

        delete = findViewById(R.id.btnDelete);
        //view = findViewById(R.id.btnView);
        Button button = (Button) findViewById(R.id.btnView);
        DB = new DBHelper(this);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String BarcodeTXT = Barcode.getText().toString();
                String itemTXT = the_name + item_name.getText().toString();
                String priceTXT = price.getText().toString();

                Boolean checkinsertdata = DB.insertuserdata(BarcodeTXT, itemTXT, priceTXT, the_name);
                if(checkinsertdata==true)
                    Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
            }        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemTXT = item_name.getText().toString();
                itemTXT = the_name + itemTXT;
                Boolean checkudeletedata = DB.deletedata(itemTXT);
                if(checkudeletedata==true)
                    Toast.makeText(MainActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
            }        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ToDoList.class);
                String next_username = the_name;
                intent.putExtra("USERNAME", next_username);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //methods to control the operation that will
    // happen when user clicks on the action buttons


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.profile:
                Intent gotoprofile = new Intent(MainActivity.this, Profile.class);
                String next_username = other_name;
                gotoprofile.putExtra("USERNAME", next_username);
                startActivity(gotoprofile);
                break;

            case R.id.logout:
                android.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                alertDialog.setTitle("Log Out");
                alertDialog.setMessage("Are you sure you want to log out?");

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent goback = new Intent(MainActivity.this, Login.class);
                        startActivity(goback);

                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
                return true;

        }


        return super.onOptionsItemSelected(item);
    }



}

