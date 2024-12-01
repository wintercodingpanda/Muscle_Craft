package com.example.passwordchek;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class home_workout extends AppCompatActivity {
    EditText exeTitle, exeDuration, exeDescription, exeImageUrl;
    Button btnExeSave;
    TextView backtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_workout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            backtext=findViewById(R.id.backtext);
            backtext.setOnClickListener(view -> {
                Intent intent = new Intent(home_workout.this, Admin_dashboard.class);
                startActivity(intent);
                finish();
            });

            exeTitle = findViewById(R.id.exeTitle);
            exeDuration = findViewById(R.id.exeDuration);
            exeDescription = findViewById(R.id.exeDescription);
            exeImageUrl = findViewById(R.id.exeImageUrl);
            btnExeSave = findViewById(R.id.btnExeSave);

            btnExeSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertData();
                    clearData();
                }
            });


            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void clearData() {
        exeTitle.setText("");
        exeDuration.setText("");
        exeDescription.setText("");
        exeImageUrl.setText("");
    }

    private void insertData() {
        Map<String, Object> map = new HashMap<>();
        map.put("Title", exeTitle.getText().toString());
        map.put("Description", exeDescription.getText().toString());
        map.put("Duration", exeDuration.getText().toString());
        map.put("ImageUrl", exeImageUrl.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("homeWorkout").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}