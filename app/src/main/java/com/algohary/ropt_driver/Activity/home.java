package com.algohary.ropt_driver.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.algohary.ropt_driver.Fragment.HomeFragment;
import com.algohary.ropt_driver.Fragment.history_fragment;
import com.algohary.ropt_driver.Fragment.scheduled_fragment;
import com.algohary.ropt_driver.Fragment.settings_fragment;
import com.algohary.ropt_driver.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Toolbar toolbar;
    TextView Profile_user_name;
    CircleImageView circleimageView_user;
    private DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.menu_home);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        circleimageView_user = navigationView.getHeaderView(0).findViewById(R.id.image_user);
        Profile_user_name = navigationView.getHeaderView(0).findViewById(R.id.profile_user_name);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            toolbar.setTitle(R.string.menu_home);
            //toolbar.setTitleMargin(275, 275, 0, 0);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Drivers/" + user.getUid());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

       /* if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            boolean emailVerified = user.isEmailVerified();
            String uid = user.getUid();
        }*/

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("driver_s_image").exists()) {
                    String user_image = dataSnapshot.child("driver_s_image").getValue().toString();
                    try {
                        Picasso.get().load(user_image).rotate(-90).fit().centerCrop().placeholder(R.drawable.ic_account_circle_125dp).into(circleimageView_user);

                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.ic_account_circle_125dp).fit().centerCrop().into(circleimageView_user);
                    }
                }
                String user_name = dataSnapshot.child("Full_name").getValue().toString();


                Profile_user_name.setText(user_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                toolbar.setTitle(R.string.menu_home);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_scheduled:
                toolbar.setTitle("Order");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new scheduled_fragment()).commit();
                break;
            case R.id.nav_history:
                toolbar.setTitle("History");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new history_fragment()).commit();
                break;
            case R.id.nav_Settings:
                toolbar.setTitle(R.string.settings);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new settings_fragment()).commit();
                break;
            case R.id.nav_logout:
                sign_Out();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void sign_Out() {
        mAuth.signOut();
        startActivity(new Intent(home.this, login.class));
        finish();

    }
}
