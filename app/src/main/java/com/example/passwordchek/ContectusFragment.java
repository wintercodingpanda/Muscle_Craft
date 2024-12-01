package com.example.passwordchek;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class ContectusFragment extends Fragment {

    public ContectusFragment() {
        // Required empty public constructor
    }
    EditText your_name, your_email, your_subject, your_message;
    Button postmasseg;
    ProgressBar progressBar;
@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contectus, container, false);

        your_name = view.findViewById(R.id.your_name);
        your_email = view.findViewById(R.id.your_email);
        your_subject = view.findViewById(R.id.your_subject);
        your_message = view.findViewById(R.id.your_message);
        postmasseg = view.findViewById(R.id.postmasseg);
        progressBar = view.findViewById(R.id.progressBar);

        postmasseg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=your_name.getText().toString();
                String email=your_email.getText().toString();
                String subject=your_subject.getText().toString();
                String message=your_message.getText().toString();

                if (name.isEmpty()) {
                    your_name.setError("Enter Your Name");
                    your_name.requestFocus();
                } else if (email.isEmpty()) {
                    your_email.setError("Enter Your Email");
                    your_email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(getContext(), "Enter Valid Email ID", Toast.LENGTH_SHORT).show();
                    your_email.setError("Enter Valid Email ID!");
                    your_email.requestFocus();
                } else if (subject.isEmpty()) {
                    your_subject.setError("Enter Your Subject");
                    your_subject.requestFocus();
                } else if (message.isEmpty()) {
                    your_message.setError("Enter Your Message");
                    your_message.requestFocus();
                } else {
                    String adminmail= "9712080593rohityadav@gmail.com";
                    Intent sendemail=new Intent(Intent.ACTION_SEND);
                    sendemail.putExtra(Intent.EXTRA_EMAIL, new String[]{ adminmail});
                    sendemail.putExtra(Intent.EXTRA_SUBJECT, subject);
                    sendemail.putExtra(Intent.EXTRA_TEXT,"Name: "+name+"\n"+"\n"+message);

                    sendemail.setType("message/rfc822");

                    startActivity(Intent.createChooser(sendemail, "Choose an Email client :"));


                    HashMap<String,Object> h = new HashMap<String, Object>();
                    h.put("Email",email);
                    h.put("Name",name);
                    h.put("Subject",subject);
                    h.put("Message",message);
                    FirebaseDatabase.getInstance().getReference().child("FeedBack_Complain").push().setValue(h);

                    Toast.makeText(getContext(), "Submit Successful", Toast.LENGTH_SHORT).show();
                    your_name.setText("");
                    your_email.setText("");
                    your_subject.setText("");
                    your_message.setText("");
                    your_name.requestFocus();
                }
            }
        });
        return view;
    }
}