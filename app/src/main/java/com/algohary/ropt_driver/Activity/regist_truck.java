package com.algohary.ropt_driver.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.algohary.ropt_driver.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class regist_truck extends AppCompatActivity {

    public String truck_type;
    Button truck_image_btn, truck_license_image_btn, driver_s_image_btn, drivers_license_image_btn, finish_regist;
    Spinner spinner;
    boolean truck_license_image, drivers_license_image, truck_image, driver_image;
    Drawable error, img;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth1;
    private DatabaseReference databaseReference;
    private String uid;
    private TextInputEditText date_of_birth, truck_license_no, driver_license_no;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_truck);
        img = getResources().getDrawable(R.drawable.ic_done_green_24dp);
        error = getResources().getDrawable(R.drawable.ic_error_black_24dp);
        progressDialog = new ProgressDialog(this);
        mAuth1 = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Drivers");
        spinner = findViewById(R.id.spinner);
        truck_image_btn = findViewById(R.id.truck_image_btn);
        truck_license_no = findViewById(R.id.truck_license_no);
        truck_license_image_btn = findViewById(R.id.truck_license_image_btn);
        driver_s_image_btn = findViewById(R.id.driver_s_image_btn);
        driver_license_no = findViewById(R.id.driver_license_no);
        drivers_license_image_btn = findViewById(R.id.drivers_license_image_btn);
        date_of_birth = findViewById(R.id.date_birth);
        progressBar = findViewById(R.id.progressBar1);
        finish_regist = findViewById(R.id.button7);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.truck_type, R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = spinner.getSelectedItem().toString();
                setTruck_type(value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        truck_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Edit_images_Activity.class);
                intent.putExtra("title", "truck_image");
                startActivity(intent);

            }
        });
        truck_license_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Edit_images_Activity.class);
                intent.putExtra("title", "truck_licence");
                startActivity(intent);

            }
        })
        ;
        driver_s_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Edit_images_Activity.class);
                intent.putExtra("title", "driver_image");
                startActivity(intent);
            }
        });

        drivers_license_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Edit_images_Activity.class);
                intent.putExtra("title", "driver_licence");
                startActivity(intent);

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                driver_image = dataSnapshot.child(uid).child("driver_s_image").exists();
                truck_image = dataSnapshot.child(uid).child("truck_image").exists();
                drivers_license_image = dataSnapshot.child(uid).child("drivers_license_image").exists();
                truck_license_image = dataSnapshot.child(uid).child("truck_license_image").exists();


                if (dataSnapshot.child(uid).child("driver_s_image").exists()) {
                    //driver_s_image_btn.setEnabled(false);
                    driver_s_image_btn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, img, null);
                }
                if (dataSnapshot.child(uid).child("truck_image").exists()) {
                    //truck_image_btn.setEnabled(false);
                    truck_image_btn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, img, null);
                }
                if (dataSnapshot.child(uid).child("drivers_license_image").exists()) {
                    //drivers_license_image_btn.setEnabled(false);
                    drivers_license_image_btn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, img, null);
                }
                if (dataSnapshot.child(uid).child("truck_license_image").exists()) {
                    //truck_license_image_btn.setEnabled(false);
                    truck_license_image_btn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, img, null);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    //////////END onCreate

    ///////////////////////////////////////////////////////


    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth1.getCurrentUser();
        updateUI(currentUser);
    }


    public void setTruck_type(String truck_type) {
        this.truck_type = truck_type;
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {
            currentUser.getUid();
            uid = currentUser.getUid();
        } else startActivity(new Intent(this, login.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void send_data(View view) {
        String Truck_license_no, Driver_license_no, Date_of_birth;
        Truck_license_no = truck_license_no.getText().toString().trim();
        Driver_license_no = driver_license_no.getText().toString().trim();
        Date_of_birth = date_of_birth.getText().toString().trim();

        if (uid != null) {
            if (truck_type.isEmpty()) {
                spinner.performClick();

            } else if (!truck_image) {
                truck_image_btn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, error, null);
            } else if (Truck_license_no.isEmpty()) {
                truck_license_no.setError("Please Enter your Truck license number");
                truck_license_no.requestFocus();
            } else if (!truck_license_image) {
                truck_license_image_btn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, error, null);
            } else if (!driver_image) {
                driver_s_image_btn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, error, null);
            } else if (Driver_license_no.isEmpty()) {
                driver_license_no.setError("Please enter your Driver license number");
                driver_license_no.requestFocus();

            } else if (!drivers_license_image) {
                drivers_license_image_btn.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, error, null);
            } else if (Date_of_birth.isEmpty()) {
                date_of_birth.setError("Please enter your Date of birth");
                date_of_birth.requestFocus();
            } else {
                finish_regist.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                String truck;

                switch (truck_type){
                    case "Quarter":
                         truck = "1";
                        break;
                    case "Half":
                        truck="2";
                        break;
                    case "Full":
                        truck="3";
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + truck_type);
                }


                databaseReference.child(uid).child("Truck_type").setValue(truck);
                databaseReference.child(uid).child("Truck_license_Number").setValue(Truck_license_no);
                databaseReference.child(uid).child("Driver_license_Number").setValue(Driver_license_no);
                databaseReference.child(uid).child("Date_Of_Birth").setValue(Date_of_birth).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(getApplicationContext(), home.class));
                        finish();
                    }
                });


            }

        }
    }

    public void get_date_birth(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(regist_truck.this, android.R.style.Theme_Holo_Dialog_MinWidth, dateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                date_of_birth.setText(date);
            }
        };
    }


}
