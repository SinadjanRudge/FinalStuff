package com.example.myapplication.ui.scan;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.DBHelper;
import com.example.myapplication.R;
import com.example.myapplication.ScanCodeActivitysearch;
import com.example.myapplication.databinding.FragmentScanBinding;
import com.bumptech.glide.Glide;


public class ScanFragment extends Fragment {

    private FragmentScanBinding binding;
    public static TextView resulttextview;
    public String other_name = null;
    DBHelper DB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ScanViewModel scanViewModel =
                new ViewModelProvider(this).get(ScanViewModel.class);

        binding = FragmentScanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button scanbtn = binding.scanbutton;
        resulttextview = root.findViewById(R.id.barcodeview);
        resulttextview.setEnabled(false);

        ImageView gif = root.findViewById(R.drawable.barcode_scan);

        Glide.with(this).asGif().load(R.drawable.barcode_scan).into(gif);

        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScanCodeActivitysearch.class);
                startActivity(intent);
            }
        });

        final Button searchbtn = binding.searchbutton;
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB = new DBHelper(getActivity());
                if(getArguments() != null) {
                    String username = getArguments().getString("USERNAME");
                    String barcodeText = username + resulttextview.getText().toString();
                    Cursor res = DB.getcertaindata(barcodeText);
                    if (res.getCount() == 0) {
                        Toast.makeText(requireContext(), "Item Not Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        int theamount = username.length();
                        String headerremove = res.getString(0);
                        buffer.append("Barcode : " + headerremove.substring(theamount) + "\n");
                        buffer.append("Item Name : " + res.getString(1) + "\n");
                        buffer.append("Item Price : Php " + res.getString(2) + "\n\n");
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setCancelable(true);
                    builder.setTitle("Item Found!");
                    builder.setMessage(buffer.toString());
                    builder.show();

                    resulttextview.setText("");
                }
                else
                    Toast.makeText(requireContext(), "null", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}