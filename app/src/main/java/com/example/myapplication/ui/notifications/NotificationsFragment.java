package com.example.myapplication.ui.notifications;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.ItemsDBHelper;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private ArrayAdapter<String> itemsAdapter;
    private ArrayList<String> items;
    private ListView listView;

    ItemsDBHelper dbHelper = new ItemsDBHelper(requireContext());


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.list_view);

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (dbHelper != null) {
            Cursor res = dbHelper.getItems();
            if (res.getCount() == 0) {
                Toast.makeText(requireContext(), "List is Empty", Toast.LENGTH_SHORT).show();
                return;
            }
            while (res.moveToNext()) {
                itemsAdapter.add("Barcode :" + res.getString(0) + "\n" +
                        "Item Name :" + res.getString(1) + "\n" +
                        "Price :" + res.getString(2));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}