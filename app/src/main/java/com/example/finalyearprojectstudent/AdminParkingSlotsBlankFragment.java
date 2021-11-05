package com.example.finalyearprojectstudent;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalyearprojectstudent.Parkingslotslist;
import com.example.finalyearprojectstudent.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminParkingSlotsBlankFragment extends Fragment {

    private AdminParkingSlotsBlankViewModel mViewModel;
    public Context ctx;
    public List<Parkingslotslist> parkinglistData;
    public RecyclerView p_rv;
    public AdminParkingAdapter p_adapter;
    View view;

    public DatabaseReference db_ref;

    public static AdminParkingSlotsBlankFragment newInstance() {
        return new AdminParkingSlotsBlankFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.admin_parking_slots_blank_fragment, container, false);
        ctx=getActivity();
        p_rv= view.findViewById(R.id.parklist);
        p_rv.setHasFixedSize(true);
        p_rv.setLayoutManager(new LinearLayoutManager(ctx));
        parkinglistData=new ArrayList<>();

        db_ref= FirebaseDatabase.getInstance().getReference("valets");
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(ctx, "Data exist", Toast.LENGTH_SHORT).show();
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        if(npsnapshot.getKey().contains("slots"))
                        {
                             for(DataSnapshot children:npsnapshot.getChildren())
                             {
                                 Parkingslotslist pl = children.getValue(Parkingslotslist.class);
                                 parkinglistData.add(pl);
                                 Log.v("value",children.getValue().toString());
                             }

                        }
                    }
                    Collections.sort(parkinglistData);
                    Log.v("list",parkinglistData.toString());
                    p_adapter=new AdminParkingAdapter(parkinglistData,ctx);
                    p_rv.setAdapter(p_adapter);
                }
                else {
                    Toast.makeText(ctx, "Parking Data doesn't exixts", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AdminParkingSlotsBlankViewModel.class);
        // TODO: Use the ViewModel
    }

}