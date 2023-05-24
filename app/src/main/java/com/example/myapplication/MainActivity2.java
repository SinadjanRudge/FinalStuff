package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityMain2Binding;
import com.example.myapplication.ui.manage_items.ManageItemsFragment;
import com.example.myapplication.ui.scan.ScanFragment;
import com.example.myapplication.ui.view_list.ViewListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    public String other_name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        other_name = username;

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ActionBar actionBar = getSupportActionBar();

        //providing title for the ActionBar
        actionBar.setTitle(other_name);

        //providing subtitle for the ActionBar
        actionBar.setSubtitle("Tindahan Price Checker");

        //adding icon in the ActionBar
        actionBar.setIcon(R.mipmap.ic_launcher);

        //methods to display the icon in the ActionBar
        actionBar.setDisplayUseLogoEnabled(true);


        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Create the fragments
        ViewListFragment viewListFragment = new ViewListFragment();
        ManageItemsFragment manageItemsFragment = new ManageItemsFragment();
        ScanFragment scanFragment = new ScanFragment();

        // Set arguments for the fragments
        Bundle args = new Bundle();
        args.putString("USERNAME", username);
        viewListFragment.setArguments(args);
        manageItemsFragment.setArguments(args);
        scanFragment.setArguments(args);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_scan, R.id.navigation_manage_items, R.id.navigation_view_list)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Add the fragments to the Bottom Navigation
        navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_view_list:
                    navController.navigate(R.id.navigation_view_list);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment_activity_main2, viewListFragment)
                            .commit();
                    break;
                case R.id.navigation_manage_items:
                    navController.navigate(R.id.navigation_manage_items);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment_activity_main2, manageItemsFragment)
                            .commit();
                    break;
                case R.id.navigation_scan:
                    navController.navigate(R.id.navigation_scan);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment_activity_main2, scanFragment)
                            .commit();
                    break;
                default:
                    return false;
            }
            return true;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //methods to control the operation that will
    // happen when user clicks on the action buttons

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.profile:
                Intent gotoprofile = new Intent(MainActivity2.this, Profile.class);
                String next_username = other_name;
                gotoprofile.putExtra("USERNAME", next_username);
                startActivity(gotoprofile);
                break;

            case R.id.logout:
                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(MainActivity2.this);

                alertDialog.setTitle("Log Out");
                alertDialog.setMessage("Are you sure you want to log out?");

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent goback = new Intent(MainActivity2.this, Login.class);
                        startActivity(goback);
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}