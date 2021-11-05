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
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearprojectstudent.Driverslist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminDriversBlankFragment2 extends Fragment implements ListenFromAdminActivity {

    public AdminDriversBlankViewModel mViewModel;
    public Context context;
    public List<Driverslist> listData;
    public RecyclerView rv;
    public AdminDriversAdapter adapter;
    View view;

    public DatabaseReference db_ref;
    public static AdminDriversBlankFragment2 newInstance() {
        return new AdminDriversBlankFragment2();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.admin_drivers_blank_fragment2, container, false);
        context=getActivity();
        rv= view.findViewById(R.id.dlist);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(context));
        listData=new ArrayList<>();

        db_ref= FirebaseDatabase.getInstance().getReference("valets");
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(context, "Data exist", Toast.LENGTH_SHORT).show();
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                       if(!npsnapshot.getKey().contains("slots") && !npsnapshot.getKey().contains("complaints") && !npsnapshot.getKey().contains("month")) {
                           Driverslist l = npsnapshot.getValue(Driverslist.class);
                           Log.v("value",l.getUsertype());
                           if (l.getUsertype().contains("valet")) {

                               listData.add(l);
                           }
                       }
                    }
                    adapter=new AdminDriversAdapter(listData,context);
                    rv.setAdapter(adapter);

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
        mViewModel = ViewModelProviders.of(this).get(AdminDriversBlankViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void doSearch(String query) {
        adapter.getFilter().filter(query);
    }


}