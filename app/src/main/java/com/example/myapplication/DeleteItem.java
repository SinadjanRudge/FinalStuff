package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String the_name = username;


        resulttextview = findViewById(R.id.barcodeview);
        scanbutton = findViewById(R.id.buttonscan);
        deletebutton = findViewById(R.id.deletebutton);
        check = findViewById(R.id.checkbutton);
        item_name = findViewById(R.id.itemname_tv);
        price = findViewById(R.id.price_tv);
        db = new DBHelper(this);

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
                String barcodeTXT = the_name + resulttextview.getText().toString();
                Cursor cursor = db.getcertaindata(barcodeTXT);
                if(cursor.getCount()==0){
                    Toast.makeText(DeleteItem.this, "Item Not Found", Toast.LENGTH_SHORT).show();
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
                String barcodeTXT1 = resulttextview.getText().toString();
                String itemnameTXT = item_name.getText().toString();
                String priceTXT = price.getText().toString();
                if(!barcodeTXT1.equals("") && !itemnameTXT.equals("") && !priceTXT.equals("")) {
                    android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(DeleteItem.this);

                    alertDialog.setTitle("Delete");
                    alertDialog.setMessage("Are you sure you want to delete this item?");

                    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String barcodeTXT = the_name + resulttextview.getText().toString();
                            Boolean checkitem = db.deletedata(barcodeTXT);
                            if (checkitem == true) {
                                resulttextview.setText("");
                                item_name.setText("");
                                price.setText("");
                                Toast.makeText(DeleteItem.this, "Item Deleted Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DeleteItem.this, "Invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                }
                else
                    Toast.makeText(DeleteItem.this, "Please complete all fields", Toast.LENGTH_SHORT).show();

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