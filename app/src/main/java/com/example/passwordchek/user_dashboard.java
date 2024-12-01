package com.example.passwordchek;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class user_dashboard extends AppCompatActivity {

    DrawerLayout drawerlayout;
    NavigationView navigationview;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.user_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        drawerlayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        navigationview = findViewById(R.id.navigationview);

        setSupportActionBar(toolbar);
        navigationview.bringToFront();
        toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        loadFragment(new HomeFragment(), 0);
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    loadFragment(new HomeFragment(), 1);
                } else if (id == R.id.profile) {
                    Intent intent = new Intent(user_dashboard.this, User_Profile.class);
                    startActivity(intent);
                } else if (id == R.id.contect) {
                    loadFragment(new ContectusFragment(), 1);
                } else if (id == R.id.feespeyment) {
                    Intent intent = new Intent(user_dashboard.this, donation.class);
                    startActivity(intent);
                    //Toast.makeText(user_dashboard.this, "Fees Payment", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.healthtips) {
                    Intent intent = new Intent(user_dashboard.this, FoodActivity.class);
                    startActivity(intent);
                } else if (id == R.id.homeworkout) {
                    Intent intent = new Intent(user_dashboard.this, home_workout_view.class);
                    startActivity(intent);
                } else {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(user_dashboard.this, "Logout", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(user_dashboard.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                drawerlayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(GravityCompat.START);

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit");
            builder.setIcon(R.drawable.exit);
            builder.setMessage("Do you want to Exit?");
            builder.setPositiveButton("Yes", (dialog, which) -> finish());
            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }


    private void loadFragment(Fragment fragment, int flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag == 0) {
            ft.add(R.id.container, fragment);
        } else {
            ft.replace(R.id.container, fragment);
        }
        ft.commit();

    }
}