package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class DeleteItem extends AppCompatActivity {

    public static TextView resulttextview;
    Button scanbutton, deletebutton, check;
    EditText item_name, price;
    ItemsDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        resulttextview = findViewById(R.id.barcodeview);
        scanbutton = findViewById(R.id.buttonscan);
        deletebutton = findViewById(R.id.deletebutton);
        check = findViewById(R.id.checkbutton);
        item_name = findViewById(R.id.itemname_tv);
        price = findViewById(R.id.price_tv);
        db = new ItemsDBHelper(this);

        resulttextview.setEnabled(false);
        item_name.setEnabled(false);
        price.setEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivitydel.class));
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcodeTXT = resulttextview.getText().toString();
                Cursor cursor = db.getCertainItem(barcodeTXT);
                if(cursor.getCount()==0){
                    Toast.makeText(DeleteItem.this, "No Item Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                while(cursor.moveToNext()) {
                    item_name.setText(cursor.getString(1));
                    price.setText(cursor.getString(2));
                }
            }
        });

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcodeTXT = resulttextview.getText().toString();
                Boolean checkitem = db.deleteitem(barcodeTXT);
                if(checkitem==true){
                    resulttextview.setText("");
                    item_name.setText("");
                    price.setText("");
                    Toast.makeText(DeleteItem.this, "Item Deleted Successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DeleteItem.this, "Invalid", Toast.LENGTH_SHORT).show();
                }
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