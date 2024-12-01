package com.example.passwordchek;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class add_health_tips extends AppCompatActivity {
    EditText tipTitle, tipDescription, tipImageUrl;
    Button btnSave;
    ImageView backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_health_tips);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());


            tipTitle = findViewById(R.id.tipTitle);
            tipDescription = findViewById(R.id.tipDescription);
            tipImageUrl = findViewById(R.id.tipImageUrl);
            btnSave = findViewById(R.id.btnSave);
            backbutton = findViewById(R.id.backbutton);

            backbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(add_health_tips.this, Admin_dashboard.class);
                    startActivity(intent);
                    finish();

                }
            });

            btnSave.setOnClickListener(new View.OnClickListener() {
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
        tipTitle.setText("");
        tipDescription.setText("");
        tipImageUrl.setText("");
    }

    private void insertData() {
        Map<String, Object> map = new HashMap<>();
        map.put("Title", tipTitle.getText().toString());
        map.put("Description", tipDescription.getText().toString());
        map.put("ImageUrl", tipImageUrl.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Diet").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(add_health_tips.this, "Added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(add_health_tips.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}