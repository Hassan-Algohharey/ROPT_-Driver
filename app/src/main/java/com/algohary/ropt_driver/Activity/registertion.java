package com.algohary.ropt_driver.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.algohary.ropt_driver.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registertion extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputEditText name, mail, password, confirmPassword, phone, city;
    ProgressBar progressBar;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registertion);
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail_reg);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        city = findViewById(R.id.address);
        confirmPassword = findViewById(R.id.confirmPassword);
        progressBar = findViewById(R.id.progressBar1);
        button = findViewById(R.id.button);


    }


    public void back(View view) {
        onBackPressed();
    }

    private void save_drivers_data() {
        FirebaseUser user = mAuth.getCurrentUser();
        String Uid, full_name, email, phone_num, city_address;
        assert user != null;
        Uid = user.getUid();
        full_name = name.getText().toString().trim();
        email = mail.getText().toString().trim();
        phone_num = phone.getText().toString().trim();
        city_address = city.getText().toString().trim();
        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put("Full_name", full_name);
        hashMap.put("Email", email);
        hashMap.put("Phone_num", phone_num);
        hashMap.put("City_address", city_address);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Drivers");
        databaseReference.child(Uid).setValue(hashMap);
    }


    public void register_1(View view) {
        String full_name, email, pass, phone_num, city_address, confirmpassword;
        full_name = name.getText().toString().trim();
        email = mail.getText().toString().trim();
        phone_num = phone.getText().toString().trim();
        city_address = city.getText().toString().trim();
        pass = password.getText().toString().trim();
        confirmpassword = confirmPassword.getText().toString().trim();

        if (full_name.isEmpty()) {
            name.setError("Please enter your full name");
            name.requestFocus();
        } else if (email.isEmpty()) {
            mail.setError("Please enter your E-mail");
            mail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mail.setError("Invalid email ");
            mail.requestFocus();
        } else if (phone_num.isEmpty()) {
            phone.setError("Please enter your phone number");
            phone.requestFocus();
        } else if (city_address.isEmpty()) {
            city.setError("Please enter your city");
            city.requestFocus();
        } else if (pass.isEmpty()) {
            password.setError("Please enter your password");
            password.requestFocus();
        } else if (pass.length() < 8) {
            password.setError("Password should be at least 8 characters long", null);
            password.requestFocus();
        } else if (confirmpassword.isEmpty()) {
            confirmPassword.setError("Please confirm your Password");
            confirmPassword.requestFocus();
        } else if (!(pass.equals(confirmpassword))) {
            confirmPassword.setError("Password does not match confirm password");
            confirmPassword.requestFocus();

        } else {
            progressBar.setVisibility(View.VISIBLE);
            button.setVisibility(View.INVISIBLE);
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        save_drivers_data();
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Toast.makeText(registertion.this, "register failed.", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.VISIBLE);
                }
                private void updateUI(FirebaseUser user) {
                    if (user != null) {
                        startActivity(new Intent(registertion.this, regist_truck.class));
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(registertion.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    public void signOut(View view){

        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), login.class));
        finish();
    }
}
