package com.example.myapplication.ui.manage_items;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.AddItem;
import com.example.myapplication.DeleteItem;
import com.example.myapplication.databinding.FragmentManageItemsBinding;

public class ManageItemsFragment extends Fragment {

    private FragmentManageItemsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ManageItemsViewModel manageItemsViewModel =
                new ViewModelProvider(this).get(ManageItemsViewModel.class);

        binding = FragmentManageItemsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button gotoAddItem = binding.goToAddItem;
        gotoAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddItem.class);
                startActivity(intent);
            }
        });

        final Button gotoDeleteItem = binding.goToDeleteItem;
        gotoDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DeleteItem.class);
                startActivity(intent);
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