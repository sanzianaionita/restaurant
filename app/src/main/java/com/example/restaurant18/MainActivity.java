package com.example.restaurant18;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurant18.DAO.UserDAO;
import com.example.restaurant18.entity.User;
import com.example.restaurant18.utils.DatabaseHandler;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant18.databinding.ActivityMainBinding;
import com.example.restaurant18.entity.Product;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private ImageView imageViewUser;
    private TextView textViewUserName, textViewEmail;
    private User user;
    private Boolean guest_received;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Intent intent = getIntent();
        user = (User) getIntent().getSerializableExtra("user");
        guest_received = intent.getBooleanExtra("guest", false);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);



        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_restaurant_menu, R.id.nav_home, R.id.nav_new_order, R.id.nav_profile, R.id.nav_favorite_product)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View viewHeader = navigationView.getHeaderView(0);
        imageViewUser = viewHeader.findViewById(R.id.imageViewUserLogo);
        textViewUserName = viewHeader.findViewById(R.id.textViewUserName);
        textViewEmail = viewHeader.findViewById(R.id.textViewEmail);

        if(!guest_received){
            if (user.getAppellative().equalsIgnoreCase("Mr"))
                imageViewUser.setImageResource(R.mipmap.png_man);
            else
                imageViewUser.setImageResource(R.mipmap.png_woman);
            textViewUserName.setText("Hello, "+user.getFirstname()+" "+user.getLastname());
            textViewEmail.setText(user.getEmail());
        } else{
            imageViewUser.setImageResource(R.mipmap.ic_launcher_round);
            textViewUserName.setText("Hello, "+user.getFirstname()+" "+user.getLastname());
            textViewEmail.setText("guest");
        }

        if(guest_received)
        {
            navigationView.getMenu().findItem(R.id.nav_favorite_product).setEnabled(false);
            navigationView.getMenu().findItem(R.id.nav_order_history).setEnabled(false);
            navigationView.getMenu().findItem(R.id.nav_reservation).setEnabled(false);
            navigationView.getMenu().findItem(R.id.nav_profile).setEnabled(false);
        }

        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                Logout();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void Logout() {
        try
        {
            Intent intent = new Intent(MainActivity.this, LoginSignupActivity.class);
            startActivity(intent);
            finish();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Couldn't logged you out", Toast.LENGTH_SHORT).show();

        }
    }

    public User getUser() {
        return user;
    }

    public boolean checkIfGuestUser()
    {
        if(guest_received)
            return true;
        else
            return false;
    }

    public View returnView()
    {
        View viewHeader = navigationView.getHeaderView(0);
        return viewHeader;
    }
}