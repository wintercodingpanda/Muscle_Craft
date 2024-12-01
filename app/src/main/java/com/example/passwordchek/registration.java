package com.example.passwordchek;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class registration extends AppCompatActivity {
    EditText txtname, txtlastname, txtuserid, txtpassword, txtrepassword, editTextDate, txtmobile;
    TextView login;
    Button btnsingup, date;
    ProgressBar progressBar;
    RadioGroup radioGroupRegisterGender;
    RadioButton radioButtonRegisterGenderSelected;
    FirebaseAuth auth;
    FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registration);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        login = findViewById(R.id.login);
        txtname = findViewById(R.id.txtname);
        txtlastname = findViewById(R.id.txtlastname);
        txtrepassword = findViewById(R.id.txtrepassword);
        txtuserid = findViewById(R.id.txtuserid);
        txtpassword = findViewById(R.id.txtpassword);
        btnsingup = findViewById(R.id.btnsingup);
        date = findViewById(R.id.date);
        progressBar = findViewById(R.id.progressBar);
        editTextDate = findViewById(R.id.editTextDate);
        txtmobile = findViewById(R.id.txtmobile);

        radioGroupRegisterGender = findViewById(R.id.radiogroupgender);
        radioGroupRegisterGender.clearCheck();
        auth = FirebaseAuth.getInstance();

        //This Is for Date Selection
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c;
                c = Calendar.getInstance();
                int date1 = Calendar.DAY_OF_MONTH;
                int month = Calendar.MONTH;
                int year = Calendar.YEAR;
                DatePickerDialog datePickerDialog = new DatePickerDialog(registration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        editTextDate.setText(i2 + "/" + (i1 + 1) + "/" + i);
                    }
                }, date1, month, year);
                datePickerDialog.show();

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registration.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        btnsingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);
                progressBar.setVisibility(View.VISIBLE);
                String fname = txtname.getText().toString();
                String lname = txtlastname.getText().toString();
                String email = txtuserid.getText().toString();
                String password = txtpassword.getText().toString();
                String repassword = txtrepassword.getText().toString();
                String number = txtmobile.getText().toString();
                String date = editTextDate.getText().toString();


                if (TextUtils.isEmpty(fname)) {
                    Toast.makeText(registration.this, "Enter Your First Name", Toast.LENGTH_SHORT).show();
                    txtname.setError("First Name is Required!");
                    txtname.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (TextUtils.isEmpty(lname)) {
                    Toast.makeText(registration.this, "Enter Your Last Name", Toast.LENGTH_SHORT).show();
                    txtlastname.setError("Last Name is Required!");
                    txtlastname.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (radioGroupRegisterGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(registration.this, "Select Your Gender", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (TextUtils.isEmpty(number)) {
                    Toast.makeText(registration.this, "Enter Your Mobile Number", Toast.LENGTH_SHORT).show();
                    txtmobile.setError("Mobile Number is Required!");
                    txtmobile.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (number.length() != 10) {
                    Toast.makeText(registration.this, "Mobile Number should be 10 digit", Toast.LENGTH_SHORT).show();
                    txtmobile.setError("Mobile Number should be 10 digit!");
                    txtmobile.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (TextUtils.isEmpty(date)) {
                    Toast.makeText(registration.this, "Enter Your Date of Birth", Toast.LENGTH_SHORT).show();
                    editTextDate.setError("Date of Birth is Required!");
                    editTextDate.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(registration.this, "Enter Your Email", Toast.LENGTH_SHORT).show();
                    txtuserid.setError("Email is Required!");
                    txtuserid.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(registration.this, "Enter Valid Email ID", Toast.LENGTH_SHORT).show();
                    txtuserid.setError("Enter Valid Email ID!");
                    txtuserid.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(registration.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
                    txtpassword.setError("Password is Required!");
                    txtpassword.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (password.length() < 6) {
                    Toast.makeText(registration.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
                    txtpassword.setError("Password should not be less 6 digit!");
                    txtpassword.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (TextUtils.isEmpty(repassword)) {
                    Toast.makeText(registration.this, "Enter Your Conform Password", Toast.LENGTH_SHORT).show();
                    txtrepassword.setError("Conform Password is Required!");
                    txtrepassword.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (!password.equals(repassword)) {
                    Toast.makeText(registration.this, "Both passwords are not the same", Toast.LENGTH_SHORT).show();
                    txtrepassword.setError("Both passwords are not the same!");
                    txtrepassword.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    String gender = radioButtonRegisterGenderSelected.getText().toString();

                    //Create User With Email And Password in FirebaseAuth
                    String email1 = txtuserid.getText().toString();
                    String pass = txtpassword.getText().toString();
                    auth.createUserWithEmailAndPassword(email1, pass)
                            .addOnCompleteListener((task)->  {
                            if (task.isSuccessful()) {

                                FirebaseUser user = auth.getCurrentUser();
                                assert user != null;
                                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(registration.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();

                                    }

                                    }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(registration.this, "Verification Email Sent" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });





                                progressBar.setVisibility(View.INVISIBLE);
                                 user = auth.getCurrentUser();

                                //Store User's Registration Data in Firebase Realtime database
                                user_data_holder obj = new user_data_holder(fname, lname, gender, number, date, email, password);
                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference node = db.getReference("User_Detail");
                                node.child(user.getUid()).setValue(obj);

                                Toast.makeText(registration.this, "Sing Up Successes Full", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(registration.this, MainActivity.class);
                                startActivity(i);
                            } else {
                                try {
                                    throw Objects.requireNonNull(task.getException());
                                } catch (FirebaseAuthUserCollisionException e) {
                                    Toast.makeText(registration.this, "Email is already registered" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    txtuserid.requestFocus();
                                    txtuserid.setError("Email is already registered!");
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(registration.this, "Sing Up Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
//                  (Option) This is HashMap use for store data in Realtime database
//                    HashMap<String,Object> h = new HashMap<String, Object>();
//                    h.put("Email",email);
//                    h.put("Password",password);
//                    h.put("First_Name",fname);
//                    h.put("Last_name",lname);
//                    h.put("Mobile_No",number);
//                    h.put("Date",date);
//                    h.put("Gender",gender);
//                    FirebaseDatabase.getInstance().getReference().child("User_Detail").child(fname).push().setValue(h);

                }

            }
        });
    }
}