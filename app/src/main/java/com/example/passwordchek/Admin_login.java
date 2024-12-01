package com.example.passwordchek;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class Admin_login extends AppCompatActivity {
    EditText txtuser, txtpass;
    Button btnlogin;
    ProgressBar progressBar;
    private static final String TAG = "Admin_login";
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        txtuser = findViewById(R.id.txtuser);
        txtpass = findViewById(R.id.txtpass);
        btnlogin = findViewById(R.id.btnlogin);
        progressBar = findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();

        ImageView imageViewShowHidePwd = findViewById(R.id.imageView_show_hide_pwd);
        imageViewShowHidePwd.setImageResource(R.drawable.viewpassword);
        imageViewShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtpass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    txtpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.viewpassword);
                } else {
                    txtpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.hidepassword);
                }

            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = txtuser.getText().toString();
                String password = txtpass.getText().toString();
                if (!validateUsername() | !validatePassword()) {

                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    if (user.equals("admin@gmail.com") && password.equals("admin@123")) {
                        checkUser(user, password);
                    }else {
                        progressBar.setVisibility(View.INVISIBLE);
                        //Toast.makeText(Admin_login.this, "Your are Not a Admin", Toast.LENGTH_SHORT).show();
                        checkUser(user, password);
                    }
                }

            }
        });


    }

    private boolean validateUsername() {
        String val = txtuser.getText().toString();
        if (val.isEmpty()) {
            txtuser.setError("Email cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            Toast.makeText(Admin_login.this, "Enter Valid Email ID", Toast.LENGTH_SHORT).show();
            txtuser.setError("Enter Valid Email ID!");
            txtuser.requestFocus();
        } else {
            txtuser.setError(null);
            return true;
        }
        return false;
    }

    private boolean validatePassword() {
        String val = txtpass.getText().toString();
        if (val.isEmpty()) {
            txtpass.setError("Password cannot be empty");
            return false;
        } else {
            txtpass.setError(null);
            return true;
        }
    }

    private void checkUser(String user, String password) {
        auth.signInWithEmailAndPassword(user, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Admin_login.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Admin_login.this, Admin_dashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        txtuser.setError("User dose Not Exists");
                        Toast.makeText(Admin_login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        txtuser.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        txtuser.setError("Incorrect ID or Password");
                        txtpass.setError("Incorrect ID or Password");
                        Toast.makeText(Admin_login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        txtuser.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(Admin_login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
}