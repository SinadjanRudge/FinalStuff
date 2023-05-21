package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ToDoList extends AppCompatActivity {

    private ArrayList<String> items;

    private ArrayAdapter<String> itemsAdapter;

    private ListView listView;


    private Button button;


    String the_name = " ";
    int theamount;
    DBHelper DB;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");

        ActionBar actionBar = getSupportActionBar();

        //providing title for the ActionBar
        actionBar.setTitle(username);

        //providing subtitle for the ActionBar
        actionBar.setSubtitle("Tindahan Price Checker");
        setContentView(R.layout.activity_to_do_list);
        DB = new DBHelper(this);
        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);


        the_name = username;
        theamount = the_name.length();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });



        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, items);
        listView.setAdapter(itemsAdapter);





    }
    @Override
    protected void onStart()
    {

        super.onStart();
        Cursor res = DB.getdata(the_name);
        if(res.getCount()==0){
            Toast.makeText(ToDoList.this, "List is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        while(res.moveToNext()){

            String headerremove =  res.getString(1);

            itemsAdapter.add("Item :"+headerremove.substring(theamount)+"\n" +
                    "Price :"+res.getString(2)+"\n");

        }
    }



    private void addItem(View view) {
        EditText input = findViewById(R.id.editText2);
        String itemText = input.getText().toString();
        itemText = the_name + itemText;
        Cursor res = DB.getcertaindata(itemText);
        if(res.getCount()==0){
            Toast.makeText(ToDoList.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            String headerremove =  res.getString(1);
            buffer.append("Item :"+headerremove.substring(theamount)+"\n");
            buffer.append("Price :"+res.getString(2)+"\n");
            buffer.append("Barcode :"+res.getString(0)+"\n\n");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ToDoList.this);
        builder.setCancelable(true);
        builder.setTitle("Item Info");
        builder.setMessage(buffer.toString());
        builder.show();

    }


}