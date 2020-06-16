package com.algohary.ropt_driver.Activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.algohary.ropt_driver.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Edit_text_Activity extends AppCompatActivity {
    Toolbar toolbar;
    Button update;
    TextView textView_title, textView_describetion;
    TextInputEditText edit_text_profile, new_passworrd;
    TextInputLayout text_input_layout, text_layout;
    DatabaseReference myRef;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        String cId = firebaseUser.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Drivers/" + cId);


        update = findViewById(R.id.update);
        textView_title = findViewById(R.id.textView_title);
        textView_describetion = findViewById(R.id.textView_describetion);
        edit_text_profile = findViewById(R.id.edit_text_profile);
        new_passworrd = findViewById(R.id.new_passworrd);
        text_layout = findViewById(R.id.text_layout);
        text_input_layout = findViewById(R.id.text_input_layout);
        toolbar = findViewById(R.id.toolbar4);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        final String title = getIntent().getStringExtra("title");

        assert title != null;
        switch (title) {
            case "name":
                textView_title.setText("Change you name");
                textView_describetion.setText("Your name makes it easy for shipper to confirm who they're picking order for");
                text_input_layout.setHint("Enter your full name");
                edit_text_profile.setInputType(InputType.TYPE_CLASS_TEXT);

                break;
            case "mail":
                textView_title.setText("Change you E-mail");
                textView_describetion.setText("Receive info about new updates and awesome promos in your inbox");
                text_input_layout.setHint("Enter your email");
                edit_text_profile.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case "password":
                textView_title.setText("Change you password");
                textView_describetion.setText("Remember, once the password change process is successful, you cannot go back to the old password");
                text_layout.setVisibility(View.VISIBLE);
                text_input_layout.setHint("New password");
                text_input_layout.setPasswordVisibilityToggleEnabled(true);


                break;
            case "phone":
                textView_title.setText("Change you phone number");
                textView_describetion.setText("Your mobile phone number makes it easy for the shipper to easily communicate with you");
                edit_text_profile.setInputType(InputType.TYPE_CLASS_NUMBER);
                text_input_layout.setHint("Enter your phone number");
                break;

        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (title) {
                    case "name":
                        Update_name();
                        break;
                    case "mail":
                        Update_mail();
                        break;
                    case "password":
                        Update_pass();
                        break;
                    case "phone":
                        Update_phone();
                        break;
                }

            }
        });
    }

    private void Update_phone() {

        String phone = edit_text_profile.getText().toString().trim();
        if (phone.isEmpty()) {
            edit_text_profile.setError("Please enter your phone number");
        } else {
            myRef.child("Phone_num").setValue(phone).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Edit_text_Activity.this, "Done", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        }

    }

    private void Update_pass() {

        String new_pass = edit_text_profile.getText().toString().trim();
        String new_pass_config = new_passworrd.getText().toString().trim();
        if (new_pass.isEmpty()) {
            edit_text_profile.setError("Please enter your new password");
            edit_text_profile.requestFocus();
        } else if (new_pass.length() < 8) {
            edit_text_profile.setError("Password should be at least 8 characters long", null);
            edit_text_profile.requestFocus();
        } else if (new_pass_config.isEmpty()) {
            new_passworrd.setError("Please confirm your Password");
            new_passworrd.requestFocus();
        } else if (!(new_pass.equals(new_pass_config))) {
            new_passworrd.setError("Password does not match confirm password");
            new_passworrd.requestFocus();

        } else {
            firebaseUser.updatePassword(new_pass).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Edit_text_Activity.this, "Done", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    edit_text_profile.setError(e.getMessage());

                }
            });
        }

    }

    private void Update_mail() {

        String email = edit_text_profile.getText().toString().trim();
        if (email.isEmpty()) {
            edit_text_profile.setError("Please enter your new email");

        } else {
            firebaseUser.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Edit_text_Activity.this, "Done", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    edit_text_profile.setError(e.getMessage());

                }
            });
        }

    }

    private void Update_name() {
        String name = edit_text_profile.getText().toString().trim();
        if (name.isEmpty()) {
            edit_text_profile.setError("Please enter your full name");
        } else {
            myRef.child("Full_name").setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(Edit_text_Activity.this, "Done", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });

        }


    }
}
