package com.example.passwordchek;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public ProgressDialog loginprogress;
    EditText txtuser, txtpass;
    TextView adminlogin, forgetpass;
    Button btnlogin, btnsing;
    ProgressBar progressBar;
    FirebaseAuth.AuthStateListener mAuthListener;
    ProgressDialog loadingBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        txtuser = findViewById(R.id.txtuser);
        txtpass = findViewById(R.id.txtpass);
        btnlogin = findViewById(R.id.btnlogin);
        btnsing = findViewById(R.id.btnsing);
        adminlogin = findViewById(R.id.adminlogin);
        forgetpass = findViewById(R.id.forgetpass);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Toast.makeText(MainActivity.this, "Welcome  "+user.getEmail() , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, user_dashboard.class);
                startActivity(intent);
                finish();
            }
        };

        auth = FirebaseAuth.getInstance();
        //Forget Password
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();

            }
        });


//For Show and Hide Password
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


        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Admin_login.class);
                startActivity(intent);

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
                    checkUser(user, password);
                }

            }
        });

        btnsing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, registration.class);
                startActivity(i);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
       auth.addAuthStateListener(mAuthListener);
    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout = new LinearLayout(this);
        final EditText emailet = new EditText(this);

        emailet.setHint("Email");
        emailet.setMinEms(16);
        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailet);
        linearLayout.setPadding(10, 10, 10, 10);
        builder.setView(linearLayout);

        builder.setPositiveButton("Forget", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String Email = emailet.getText().toString().trim();
                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(MainActivity.this, "Enter Yor Email", Toast.LENGTH_SHORT).show();
                } else {
                    String email = emailet.getText().toString().trim();
                    beginRecovery(email);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void beginRecovery(String email) {
        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("Sending Email....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingBar.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Password Reset Email send Please check your Email box", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Toast.makeText(MainActivity.this, "Error Failed", Toast.LENGTH_LONG).show();
            }
        });
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

    private boolean validateUsername() {
        String val = txtuser.getText().toString();
        if (val.isEmpty()) {
            txtuser.setError("Email cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            Toast.makeText(MainActivity.this, "Enter Valid Email ID", Toast.LENGTH_SHORT).show();
            txtuser.setError("Enter Valid Email ID!");
            txtuser.requestFocus();
        } else {
            txtuser.setError(null);
            return true;
        }
        return false;
    }


    private void checkUser(String user, String password) {
        auth.signInWithEmailAndPassword(user, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, user_dashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        throw Objects.requireNonNull(task.getException());
                    } catch (FirebaseAuthInvalidUserException e) {
                        txtuser.setError("User dose Not Exists");
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        txtuser.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        txtuser.setError("Incorrect ID or Password");
                        txtpass.setError("Incorrect ID or Password");
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        txtuser.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}

