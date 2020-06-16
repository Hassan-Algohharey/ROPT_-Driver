package com.algohary.ropt_driver.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.algohary.ropt_driver.Activity.Edit_images_Activity;
import com.algohary.ropt_driver.Activity.Edit_text_Activity;
import com.algohary.ropt_driver.Activity.login;
import com.algohary.ropt_driver.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class settings_fragment extends Fragment {
    Button sign_out_button, about_but,d_image,t_image,dl_image,tl_image;
    private LinearLayout name, mail, pass, phone;
    TextView terms, name_driver, mail_driver, phone_driver;
    FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    public settings_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference myRef;
        assert firebaseUser != null;
        String cId = firebaseUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Drivers/" + cId);

        sign_out_button = view.findViewById(R.id.logout);
        terms = view.findViewById(R.id.terms_txt);
        about_but = view.findViewById(R.id.about_but);
        name = view.findViewById(R.id.name_lay);
        mail = view.findViewById(R.id.mail_lay);
        pass = view.findViewById(R.id.pass_lay);
        phone = view.findViewById(R.id.phone_lay);
        name_driver = view.findViewById(R.id.name_view);
        mail_driver = view.findViewById(R.id.mail_view);
        phone_driver = view.findViewById(R.id.phone_view);
        d_image =view.findViewById(R.id.D_image);
        t_image =view.findViewById(R.id.T_image);
        dl_image =view.findViewById(R.id.DL_image);
        tl_image =view.findViewById(R.id.TL_image);

        tl_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Edit_images_Activity.class);
                intent.putExtra("title", "trucklicence");
                startActivity(intent);
            }
        });

        dl_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Edit_images_Activity.class);
                intent.putExtra("title", "driverlicence");
                startActivity(intent);
            }
        });

        t_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Edit_images_Activity.class);
                intent.putExtra("title", "truckimage");
                startActivity(intent);
            }
        });

        d_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Edit_images_Activity.class);
                intent.putExtra("title", "driverimage");
                startActivity(intent);
            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name_driver.setText(dataSnapshot.child("Full_name").getValue().toString());
                phone_driver.setText(dataSnapshot.child("Phone_num").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mail_driver.setText(firebaseUser.getEmail());

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Edit_text_Activity.class);
                intent.putExtra("title", "name");
                startActivity(intent);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Edit_text_Activity.class);
                intent.putExtra("title", "mail");
                startActivity(intent);
            }
        });

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Edit_text_Activity.class);
                intent.putExtra("title", "password");
                startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Edit_text_Activity.class);
                intent.putExtra("title", "phone");
                startActivity(intent);
            }
        });

        //firebaseAuth = FirebaseAuth.getInstance();
        about_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://ropt.surge.sh";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        sign_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_out();

            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://ropt.surge.sh/terms.html";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        return view;
    }

    public void log_out() {
        firebaseAuth.signOut();
        startActivity(new Intent(getActivity(), login.class));
        requireActivity().finish();

    }
}
