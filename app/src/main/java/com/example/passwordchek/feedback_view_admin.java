package com.example.passwordchek;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class feedback_view_admin extends AppCompatActivity {
    RecyclerView feedbackRV;
    TextView backtext;
    FeedbackViewAdapter fdAdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feedback_view_admin);

        backtext = findViewById(R.id.backtext);
        backtext.setOnClickListener(v -> {
            Intent intent = new Intent(feedback_view_admin.this, Admin_dashboard.class);
            startActivity(intent);
            finish();
        });

        feedbackRV = findViewById(R.id.feedbackRV);
        feedbackRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FirebaseRecyclerOptions<ViewFeedbackModel> options =
                new FirebaseRecyclerOptions.Builder<ViewFeedbackModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("FeedBack_Complain"), ViewFeedbackModel.class)
                        .build();

        fdAdp = new FeedbackViewAdapter(options);
        feedbackRV.setAdapter(fdAdp);

    }

    @Override
    protected void onStart() {
        super.onStart();
        fdAdp.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fdAdp.stopListening();
    }


}

