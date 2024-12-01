package com.example.passwordchek;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_dashboard extends AppCompatActivity {
    RecyclerView recview;
    FloatingActionButton floatingActionButton;
    admin_Adapter adapter;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth auth;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Admin_dashboard.this, Admin_login.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchview, menu);
        MenuItem item = menu.findItem(R.id.Search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Searchitem(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newtext) {
                Searchitem(newtext);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private void Searchitem(String query) {
        FirebaseRecyclerOptions<user_data_holder> optionss =
                new FirebaseRecyclerOptions.Builder<user_data_holder>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("User_Detail").orderByChild("fname").startAt(query).endAt(query + "~"), user_data_holder.class)
                        .build();

        adapter = new admin_Adapter(optionss);
        adapter.startListening();
        recview.setAdapter(adapter);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        setContentView(R.layout.admin_dashboard);

            recview = findViewById(R.id.recview);
            recview.setLayoutManager(new LinearLayoutManager(this));
            floatingActionButton = findViewById(R.id.floatingActionButton);
            toolbar = findViewById(R.id.toolbar);
            bottomNavigationView = findViewById(R.id.bottomNavigationView);
            setSupportActionBar(toolbar);


            FirebaseRecyclerOptions<user_data_holder> options =
                    new FirebaseRecyclerOptions.Builder<user_data_holder>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("User_Detail"), user_data_holder.class)
                            .build();

            adapter = new admin_Adapter(options);
            recview.setAdapter(adapter);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Admin_dashboard.this, adduserdata.class);
                    startActivity(intent);

                }
            });

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
                    Intent intent;
                    if (itemId == R.id.adminfeedback) {
                        intent = new Intent(Admin_dashboard.this, feedback_view_admin.class);
                    } else if (itemId == R.id.addhomeworkout) {
                        intent = new Intent(Admin_dashboard.this, home_workout.class);
                    } else {
                        intent = new Intent(Admin_dashboard.this, add_health_tips.class);
                    }
                    startActivity(intent);
                    return true;
                }
            });




    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onResume() {
        super.onResume();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
        //FirebaseAuth.getInstance().signOut();
    }

}
