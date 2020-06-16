package com.algohary.ropt_driver.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.algohary.ropt_driver.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.zolad.zoominimageview.ZoomInImageViewAttacher;

public class Edit_images_Activity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    String[] cameraPermissions;
    String[] storagePermissions;
    ProgressDialog pd;
    Uri image_uri;
    String imagename;
    Toolbar toolbar_image;
    TextView text_image1, text_image2;
    ImageView imageView_setting;
    FloatingActionButton camera, gallery;
    TextInputLayout text_layout_image;
    TextInputEditText editText_image;
    Button update_setting;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_images_);
        toolbar_image = findViewById(R.id.toolbar_image);
        text_image1 = findViewById(R.id.textView8);
        text_image2 = findViewById(R.id.textView7);
        imageView_setting = findViewById(R.id.imageView_setting);
        camera = findViewById(R.id.camera);
        gallery = findViewById(R.id.gallery);
        text_layout_image = findViewById(R.id.textView_layout);
        editText_image = findViewById(R.id.edit_text_image);
        update_setting = findViewById(R.id.update_image);

        pd = new ProgressDialog(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("Drivers");
        storageReference = FirebaseStorage.getInstance().getReference("Drivers");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        toolbar_image.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final String title = getIntent().getStringExtra("title");
        assert title != null;
        switch (title) {

            case "driverimage":
                text_image1.setText("Update your profile image");
                text_image2.setVisibility(View.INVISIBLE);
                text_layout_image.setVisibility(View.INVISIBLE);
                imagename = "driver_s_image";
                update_setting.setVisibility(View.GONE);
                break;
            case "truckimage":
                text_image1.setText("Update your truck image");
                text_image2.setVisibility(View.INVISIBLE);
                text_layout_image.setVisibility(View.INVISIBLE);
                imagename = "truck_image";
                update_setting.setVisibility(View.GONE);
                break;
            case "driver_image":
                text_image1.setText("Upload your profile image");
                text_image2.setVisibility(View.INVISIBLE);
                text_layout_image.setVisibility(View.INVISIBLE);
                imagename = "driver_s_image";
                update_setting.setVisibility(View.GONE);
                break;
            case "truck_image":
                text_image1.setText("Upload your truck image");
                text_image2.setVisibility(View.INVISIBLE);
                text_layout_image.setVisibility(View.INVISIBLE);
                imagename = "truck_image";
                update_setting.setVisibility(View.GONE);
                break;
            case "driverlicence":
                text_image1.setText("Update your driver's licence image");
                text_image2.setText("Update your driver's licence no...");
                text_layout_image.setHint("Driver's licence no...");
                imagename = "drivers_license_image";
                break;
            case "trucklicence":
                text_image1.setText("Update your truck licence image");
                text_image2.setText("Update your truck licence no...");
                text_layout_image.setHint("truck licence no...");
                imagename = "truck_license_image";
                break;
            case "driver_licence":
                text_image1.setText("Upload your driver's licence image");
                text_image2.setVisibility(View.GONE);
                text_layout_image.setVisibility(View.GONE);
                update_setting.setVisibility(View.GONE);
                imagename = "drivers_license_image";
                break;
            case "truck_licence":
                text_image1.setText("Upload your truck licence image");
                text_image2.setVisibility(View.GONE);
                text_layout_image.setVisibility(View.GONE);
                update_setting.setVisibility(View.GONE);
                imagename = "truck_license_image";
                break;
        }

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkCameraPermission()) {
                    requestCameraPermission();
                } else {
                    pickFromCamera();
                }

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkStoragePermission()) {
                    requestStoragePermission();
                } else {
                    pickFromGallery();
                }
            }
        });

        update_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String no = editText_image.getText().toString().trim();
                switch (title) {
                    case "driverlicence":
                        if (no.isEmpty()) {
                            editText_image.setError("Please enter your Driver license number");
                            editText_image.requestFocus();
                        } else {
                            databaseReference.child(firebaseUser.getUid()).child("Driver_license_Number").setValue(no)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            onBackPressed();
                                            Toast.makeText(Edit_images_Activity.this, "Done", Toast.LENGTH_SHORT).show();

                                        }
                                    });


                        }
                        break;
                    case "trucklicence":
                        if (no.isEmpty()) {
                            editText_image.setError("Please Enter your Truck license number");
                            editText_image.requestFocus();
                        } else {
                            databaseReference.child(firebaseUser.getUid()).child("Truck_license_Number").setValue(no)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            onBackPressed();
                                            Toast.makeText(Edit_images_Activity.this, "Done", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }
                        break;


                }
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(firebaseUser.getUid()).child(imagename).exists()) {
                    String value = dataSnapshot.child(firebaseUser.getUid()).child(imagename).getValue(String.class);
                    try {
                        Picasso.get().load(value).placeholder(R.drawable.ic_image_black_250dp).into(imageView_setting);
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ZoomInImageViewAttacher mIvAttacter = new ZoomInImageViewAttacher(imageView_setting);

    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean witeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && witeStorageAccepted) {
                        pickFromCamera();

                    } else {
                        Toast.makeText(this, "Please enable camera & storage permmision", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean witeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (witeStorageAccepted) {
                        pickFromGallery();

                    } else {
                        Toast.makeText(this, "Please enable storage permmision", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                assert data != null;
                image_uri = data.getData();
                uploadimage(image_uri);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                uploadimage(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadimage(Uri uri) {
        final String uid = firebaseUser.getUid();
        String file_path = uid + "/" + "" + imagename + "/" + imagename;
        final StorageReference storageReference1 = storageReference.child(file_path);
        pd.setMessage("Uploading Image");
        pd.show();

        UploadTask uploadTask = storageReference1.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference1.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            String downloadUrl = task.getResult().toString();
                            databaseReference.child(uid).child(imagename).setValue(downloadUrl);
                            Picasso.get().load(downloadUrl).placeholder(R.drawable.ic_image_black_250dp).into(imageView_setting);
                            pd.dismiss();

                            switch (imagename) {
                                case "driver_s_image":
                                case "truck_image":
                                    Toast.makeText(Edit_images_Activity.this, "Done", Toast.LENGTH_SHORT).show();

                                    break;
                            }
                        }

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Edit_images_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Describtion");
        image_uri = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);

        if (cameraintent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraintent, IMAGE_PICK_CAMERA_CODE);
        }
    }

    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);

    }
}
