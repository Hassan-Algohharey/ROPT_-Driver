package com.algohary.ropt_driver.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.algohary.ropt_driver.Adapters.AdapterHistory;
import com.algohary.ropt_driver.Models.ModelHistory;
import com.algohary.ropt_driver.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class history_fragment extends Fragment {

    String uId;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private RecyclerView recyclerView;
    private AdapterHistory adapterHistory;
    private List<ModelHistory> userList;

    public history_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.history_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uId = firebaseUser.getUid();


        userList = new ArrayList<>();
        get_history_order();


        return view;
    }

    private void get_history_order() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Drivers/" + uId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Toast.makeText(getActivity(), firebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                userList.clear();
                if (dataSnapshot.child("Orders").exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.child("Orders").getChildren()) {

                        ModelHistory modelHistory = dataSnapshot1.getValue(ModelHistory.class);
                        userList.add(modelHistory);


                        adapterHistory = new AdapterHistory(getActivity(), userList);
                        recyclerView.setAdapter(adapterHistory);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}
