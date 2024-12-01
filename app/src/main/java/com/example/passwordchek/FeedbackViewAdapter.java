package com.example.passwordchek;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackViewAdapter extends FirebaseRecyclerAdapter<ViewFeedbackModel,FeedbackViewAdapter.myviewholder> {

    public FeedbackViewAdapter(@NonNull FirebaseRecyclerOptions<ViewFeedbackModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder myviewholder, int i, @NonNull ViewFeedbackModel viewFeedbackModel) {
        myviewholder.feedname.setText("Name:- "+viewFeedbackModel.getName());
        myviewholder.feedemail.setText("Email:- "+viewFeedbackModel.getEmail());
        myviewholder.feedsubject.setText("Sub:- "+viewFeedbackModel.getSubject());
        myviewholder.message.setText("Massage:- "+"\n"+viewFeedbackModel.getMessage());

        myviewholder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("FeedBack_Complain")
                        .child(getRef(i).getKey()).removeValue();
            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_feedback_row_layout, parent, false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        TextView feedname,feedemail,feedsubject,message;
        Button btnDelete;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            feedname=itemView.findViewById(R.id.feedname);
            feedemail=itemView.findViewById(R.id.feedemail);
            feedsubject=itemView.findViewById(R.id.feedsubject);
            message=itemView.findViewById(R.id.message);
            btnDelete=itemView.findViewById(R.id.btnDelete);

        }
    }
}
