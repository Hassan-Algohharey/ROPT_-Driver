package com.algohary.ropt_driver.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.algohary.ropt_driver.Adapters.AdapterScheduled;
import com.algohary.ropt_driver.Models.ModelSecheduled;
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
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class scheduled_fragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterScheduled adapterScheduled;
    private List<ModelSecheduled> userList;

    public scheduled_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scheduled, container, false);

        recyclerView = view.findViewById(R.id.Scheduled_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        userList = new ArrayList<>();
        get_secheduled_order();

        return view;
    }

    private void get_secheduled_order() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Orders");
        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelSecheduled modelSecheduled = dataSnapshot1.getValue(ModelSecheduled.class);


                    assert modelSecheduled != null;
                    assert firebaseUser != null;
                    if (Objects.equals(modelSecheduled.getoStatus(), "Accepted..."))
                        if (Objects.equals(modelSecheduled.getcId(), firebaseUser.getUid()))
                            userList.add(modelSecheduled);

                    adapterScheduled = new AdapterScheduled(getActivity(), userList);
                    recyclerView.setAdapter(adapterScheduled);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
