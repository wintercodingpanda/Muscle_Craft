package com.example.passwordchek;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class FoodActivity extends AppCompatActivity {

    RecyclerView tipsRV;
    tipAdapter adp;
    ImageView backbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        EdgeToEdge.enable(this);

        tipsRV = findViewById(R.id.tipsRV);
        backbutton = findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tipsRV.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<diet_tips_Model> options =
                new FirebaseRecyclerOptions.Builder<diet_tips_Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Diet"), diet_tips_Model.class)
                        .build();

        adp = new tipAdapter(options);
        tipsRV.setAdapter(adp);

    }
    @Override
    protected void onStart() {
        super.onStart();
        adp.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adp.stopListening();
    }

}
