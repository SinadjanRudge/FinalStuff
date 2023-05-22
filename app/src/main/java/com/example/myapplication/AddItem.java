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
    ItemsDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        resulttextview = findViewById(R.id.barcodeview);
        scanbutton = findViewById(R.id.buttonscan);
        addbutton = findViewById(R.id.addbutton);
        item_name = findViewById(R.id.itemname_tv);
        price = findViewById(R.id.price_tv);
        db = new ItemsDBHelper(this);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);


        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcodeTXT = resulttextview.getText().toString();
                String item_nameTXT = item_name.getText().toString();
                String priceTXT = price.getText().toString();

                Boolean checkitem = db.additem(barcodeTXT, item_nameTXT, priceTXT);
                if(checkitem==true){
                    resulttextview.setText("");
                    item_name.setText("");
                    price.setText("");
                    Toast.makeText(AddItem.this, "New Item has been added", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddItem.this, "Invalid Item", Toast.LENGTH_SHORT).show();
                }

            }
        });

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