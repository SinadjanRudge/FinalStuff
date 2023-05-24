package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddItem extends AppCompatActivity {

    public static TextView resulttextview;
    Button scanbutton, addbutton;
    EditText item_name, price;
    DBHelper db;
    public String other_name = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String the_name = username;
        other_name = username;

        resulttextview = findViewById(R.id.barcodeview);
        scanbutton = findViewById(R.id.buttonscan);
        addbutton = findViewById(R.id.addbutton);
        item_name = findViewById(R.id.itemname_tv);
        price = findViewById(R.id.price_tv);
        db = new DBHelper(this);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);


        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String BarcodeTXT = the_name + resulttextview.getText().toString();
                String itemTXT = item_name.getText().toString();
                String priceTXT = price.getText().toString();

                Boolean checkinsertdata = db.insertuserdata(BarcodeTXT, itemTXT, priceTXT, the_name);
                if(checkinsertdata==true) {
                    resulttextview.setText("");
                    item_name.setText("");
                    price.setText("");
                    Toast.makeText(AddItem.this, "New Item Added", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(AddItem.this, "Add Item Invalid", Toast.LENGTH_SHORT).show();
            }        });

        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class));
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