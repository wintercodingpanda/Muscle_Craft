package com.example.passwordchek;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class home_workout_view extends AppCompatActivity {
    RecyclerView homeRV;
    TextView backtext;
    home_workout_adapter homeWorkoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_workout_view);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            backtext = findViewById(R.id.backtext);
            backtext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            homeRV = findViewById(R.id.homeRV);
            homeRV.setLayoutManager(new LinearLayoutManager(this));

            FirebaseRecyclerOptions<home_workout_model> options =
                    new FirebaseRecyclerOptions.Builder<home_workout_model>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("homeWorkout"), home_workout_model.class)
                            .build();
            homeWorkoutAdapter = new home_workout_adapter(options);
            homeWorkoutAdapter.startListening();
            homeRV.setAdapter(homeWorkoutAdapter);


            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}