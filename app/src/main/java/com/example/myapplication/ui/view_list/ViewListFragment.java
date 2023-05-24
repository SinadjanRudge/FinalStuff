package com.example.myapplication.ui.view_list;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.DBHelper;
import com.example.myapplication.ItemsDBHelper;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentViewListBinding;


import java.util.ArrayList;

public class ViewListFragment extends Fragment {

    private FragmentViewListBinding binding;

    private ArrayAdapter<String> itemsAdapter;
    TextView itemcount;

    DBHelper dbHelper;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentViewListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        ListView listView = root.findViewById(R.id.list_view);
        itemcount = root.findViewById(R.id.itemCount);

        ArrayList<String> items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        dbHelper = new DBHelper(getActivity());
        if(getArguments() != null) {
            String username = getArguments().getString("USERNAME");
            Cursor res = dbHelper.getdata(username);
            String count = String.valueOf(res.getCount());
            if (res.getCount() == 0) {
                return;
            }
            while (res.moveToNext()) {
                int theamount = username.length();
                String headerremove = res.getString(0);
                itemsAdapter.add("Barcode : " + headerremove.substring(theamount) + "\n" +
                        "Item Name : " + res.getString(1) + "\n" +
                        "Price : Php " + res.getString(2));
            }
            itemcount.setText(count);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}