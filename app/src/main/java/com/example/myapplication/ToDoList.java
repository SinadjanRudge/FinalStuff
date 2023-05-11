package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
    private ArrayList<String> seconditems;
    private ArrayAdapter<String> itemsAdapter;
    private ArrayAdapter<String> seconditemsAdapter;
    private ListView listView;

    private EditText editText;
    private Button button;
    private Button clear;

    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        DB = new DBHelper(this);
        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);


        clear = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if(res.getCount()==0){
                    Toast.makeText(ToDoList.this, "List is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                while(res.moveToNext()){


                    itemsAdapter.add("Name :"+res.getString(0)+"\n" +
                            "Price :"+res.getString(1)+"\n");

                }
            }
        });


        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, items);
        listView.setAdapter(itemsAdapter);

        seconditems = new ArrayList<>();
        seconditemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, seconditems);

        setUpListViewListener();

    }
    @Override
    protected void onStart()
    {
        super.onStart();
        Cursor res = DB.getdata();
        if(res.getCount()==0){
            Toast.makeText(ToDoList.this, "List is Empty", Toast.LENGTH_SHORT).show();
            return;
        }

        while(res.moveToNext()){


            itemsAdapter.add("Name :"+res.getString(0)+"\n" +
                    "Price :"+res.getString(1)+"\n");

        }
    }
    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ToDoList.this);

                alertDialog.setTitle("Deleting...");
                alertDialog.setMessage("Are you sure you want delete this?");


                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        items.remove(i);
                        seconditems.remove(i);
                        itemsAdapter.notifyDataSetChanged();

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

        });

    }


    private void addItem(View view) {
        EditText input = findViewById(R.id.editText2);
        String itemText = input.getText().toString();
        Cursor res = DB.getcertaindata(itemText);
        if(res.getCount()==0){
            Toast.makeText(ToDoList.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            buffer.append("Name :"+res.getString(0)+"\n");
            buffer.append("Contact :"+res.getString(1)+"\n");
            buffer.append("Date of Birth :"+res.getString(2)+"\n\n");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ToDoList.this);
        builder.setCancelable(true);
        builder.setTitle("User Entries");
        builder.setMessage(buffer.toString());
        builder.show();

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.item_done){
            String itemSelected = "Selected items: \n";
            for(int i=0;i<listView.getCount();i++){
                if(listView.isItemChecked(i)){
                    itemSelected += listView.getItemAtPosition(i) + "\n";

                }
            }
            Toast.makeText(this,itemSelected,Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}