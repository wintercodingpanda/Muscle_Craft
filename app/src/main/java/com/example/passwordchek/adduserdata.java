package com.example.passwordchek;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class adduserdata extends AppCompatActivity {
    EditText txtname, txtlastname, txtuserid, txtpassword, editTextDate, txtmobile;
    Button btnadd, btnback, date;
    ProgressDialog dialog;
    RadioGroup radioGroupRegisterGender;
    RadioButton radioButtonRegisterGenderSelected;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduserdata);
        txtname = findViewById(R.id.txtname);
        txtlastname = findViewById(R.id.txtlastname);
        txtuserid = findViewById(R.id.txtuserid);
        txtpassword = findViewById(R.id.txtpassword);
        date = findViewById(R.id.date);
        editTextDate = findViewById(R.id.editTextDate);
        txtmobile = findViewById(R.id.txtmobile);
        btnadd = findViewById(R.id.btnadd);
        btnback = findViewById(R.id.btnback);
        dialog = new ProgressDialog(this);
        radioGroupRegisterGender = findViewById(R.id.radiogroupgender);
        radioGroupRegisterGender.clearCheck();
        auth = FirebaseAuth.getInstance();

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adduserdata.this, Admin_dashboard.class);
                startActivity(intent);
                finish();

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c;
                c = Calendar.getInstance();
                int date1 = Calendar.DAY_OF_MONTH;
                int month = Calendar.MONTH;
                int year = Calendar.YEAR;
                DatePickerDialog datePickerDialog = new DatePickerDialog(adduserdata.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        editTextDate.setText(i2 + "/" + (i1 + 1) + "/" + i);
                    }
                }, date1, month, year);
                datePickerDialog.show();

            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);
                dialog.setMessage("Adding User, Please Wait!");
                dialog.show();
                String fname = txtname.getText().toString();
                String lname = txtlastname.getText().toString();
                String email = txtuserid.getText().toString();
                String password = txtpassword.getText().toString();
                String number = txtmobile.getText().toString();
                String date = editTextDate.getText().toString();

                if (TextUtils.isEmpty(fname) && TextUtils.isEmpty(lname) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(number) && TextUtils.isEmpty(date)) {
                    Toast.makeText(adduserdata.this, "File the All Details", Toast.LENGTH_SHORT).show();
                    txtname.requestFocus();
                    dialog.dismiss();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    txtuserid.setError("Enter Valid Email ID!");
                    txtuserid.requestFocus();
                    dialog.dismiss();
                } else {
                    String gender = radioButtonRegisterGenderSelected.getText().toString();
                    regis(email, password);
                    user_data_holder obj = new user_data_holder(fname, lname, gender, number, date, email, password);
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference node = db.getReference("User_Detail");
                    node.child(fname).setValue(obj);
                    txtname.setText("");
                    txtlastname.setText("");
                    txtuserid.setText("");
                    txtpassword.setText("");
                    editTextDate.setText("");
                    txtmobile.setText("");
                    txtname.requestFocus();
                }

            }
        });
    }

    private void regis(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(adduserdata.this, "Sing Up Successes Full", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthUserCollisionException e) {
                        Toast.makeText(adduserdata.this, "Email is already registered", Toast.LENGTH_SHORT).show();
                        txtuserid.requestFocus();
                        txtuserid.setError("Email is already registered!");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    dialog.dismiss();
                    Toast.makeText(adduserdata.this, "Sing Up Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}