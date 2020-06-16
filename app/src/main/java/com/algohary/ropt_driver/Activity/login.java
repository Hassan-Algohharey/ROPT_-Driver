package com.algohary.ropt_driver.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.algohary.ropt_driver.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    TextInputEditText mail, password;
    ProgressBar progressBar;
    Button button;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.pwd);
        progressBar = findViewById(R.id.progressBar);
        button = findViewById(R.id.button3);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //   Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            startActivity(new Intent(login.this, home.class));
            finish();
        }

    }

    public void register(View view) {
        Intent intent = new Intent(getApplicationContext(), registertion.class);
        startActivity(intent);
    }

    public void login_user(View view) {

        String email = mail.getText().toString().trim();
        String pass = password.getText().toString().trim();
        if (email.isEmpty()) {
            mail.setError("Please enter your E-mail");
            mail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("Invalid email ");
            mail.requestFocus();
        } else if (pass.isEmpty()) {
            password.setError("Please enter your password");
            password.requestFocus();
        } else if (pass.length() < 8) {
            password.setError("Password should be at least 8 characters long");
            password.requestFocus();
        } else {
            button.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        updateUI(null);

                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.VISIBLE);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_LONG).show();


                }
            });
        }

    }



    public void reset_password(View view) {
        final TextInputEditText textInputEditText = new TextInputEditText(this);
        textInputEditText.setInputType(32);
        textInputEditText.requestFocus();
        textInputEditText.setHint("Please enter your E-mail");
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Reset password!")
                .setMessage("Please enter your E-mail")
                .setPositiveButton("send", null)
                .setNegativeButton("Cancel", null)
                .setView(textInputEditText)
                .show();

        Button send = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = textInputEditText.getText().toString().trim();
                if (email.isEmpty()) {
                    textInputEditText.setError("Please enter your E-mail");
                    textInputEditText.requestFocus();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    textInputEditText.setError("Invalid email ");
                    textInputEditText.requestFocus();
                } else {
                    progressDialog.setMessage("Sending email");
                    progressDialog.show();
                    mAuth.sendPasswordResetEmail(email)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(login.this, "OK Email sent", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                    progressDialog.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();

                                }
                            });
                }
            }
        });
    }
}
