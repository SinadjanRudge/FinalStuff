package com.example.myapplication.ui.scan;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.ItemsDBHelper;
import com.example.myapplication.R;
import com.example.myapplication.ScanCodeActivitysearch;
import com.example.myapplication.databinding.FragmentScanBinding;

public class ScanFragment extends Fragment {

    private FragmentScanBinding binding;
    public static TextView resulttextview;
    ItemsDBHelper DB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ScanViewModel scanViewModel =
                new ViewModelProvider(this).get(ScanViewModel.class);

        binding = FragmentScanBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button scanbtn = binding.scanbutton;
        resulttextview = root.findViewById(R.id.barcodeview);
        resulttextview.setEnabled(false);
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
                DB = new ItemsDBHelper(getActivity());
                String barcodeText = resulttextview.getText().toString();
                Cursor res = DB.getCertainItem(barcodeText);
                if(res.getCount()==0){
                    Toast.makeText(requireContext(), "Item Not Found", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Barcode : "+res.getString(0)+"\n");
                    buffer.append("Item Name : "+res.getString(1)+"\n");
                    buffer.append("Item Price : Php "+res.getString(2)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setCancelable(true);
                builder.setTitle("Item Found!");
                builder.setMessage(buffer.toString());
                builder.show();

                resulttextview.setText("");
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