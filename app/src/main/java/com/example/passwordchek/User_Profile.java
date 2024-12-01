package com.example.passwordchek;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Profile extends AppCompatActivity {
    TextView textViewWelcome, textViewFullName, textViewEmail, textViewDoB, textViewMobile, txtgender, backtext;
    Button resendcode;
    RelativeLayout Verification_email;
    String name, email, dob, mobile, gender;
    ImageView delete_profile;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);

        backtext = findViewById(R.id.backtext);
        backtext.setOnClickListener(v1 -> finish());

        textViewWelcome = findViewById(R.id.username);
        textViewFullName = findViewById(R.id.name);
        textViewEmail = findViewById(R.id.email);
        textViewDoB = findViewById(R.id.dob);
        textViewMobile = findViewById(R.id.mobile);
        txtgender = findViewById(R.id.gender);
        delete_profile = findViewById(R.id.delete_profile);
        resendcode = findViewById(R.id.resendcode);
        Verification_email = findViewById(R.id.Verification_email);
        auth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        if (!user.isEmailVerified()) {

            resendcode.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    resendcode.setVisibility(View.GONE);
                    Verification_email.setVisibility(View.GONE);


                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(User_Profile.this, "Verification Email Send", Toast.LENGTH_SHORT).show();

                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@androidx.annotation.NonNull Exception e) {
                            Toast.makeText(User_Profile.this, "Verification Email unable to Send", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User_Detail");
        //Toast.makeText(this, user.getUid(), Toast.LENGTH_SHORT).show();

        databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    email = snapshot.child("email").getValue().toString();
                    mobile = snapshot.child("number").getValue().toString();
                    name = snapshot.child("fname").getValue().toString();
                    dob = snapshot.child("date").getValue().toString();
                    gender = snapshot.child("gender").getValue().toString();


                    textViewWelcome.setText("Wellcome : " + name);
                    textViewFullName.setText("Name : " + name);
                    textViewMobile.setText("Mobile No : " + mobile);
                    textViewEmail.setText("Email : " + email);
                    txtgender.setText("Gender : " + gender);
                    textViewDoB.setText("DOB : " + dob);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        delete_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(User_Profile.this);
                builder.setTitle("Delete User Account");
                builder.setIcon(R.drawable.delete);
                builder.setMessage("Do you want to Delete Your Profile?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user pressed "yes", then he is allowed to Delete user account from application
                        //User Delete from Realtime database
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        databaseReference = firebaseDatabase.getReference("User_Detail");
                        databaseReference.child(user.getUid()).removeValue();

                        //User delete from Authentication
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(User_Profile.this, "User account deleted.", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(User_Profile.this, MainActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    }
                                });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


    }
}