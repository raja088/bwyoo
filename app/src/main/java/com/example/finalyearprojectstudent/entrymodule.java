package com.example.finalyearprojectstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.finalyearprojectstudent.Parkingslotslist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class entrymodule extends AppCompatActivity {
    RecyclerView entryrv;
    public Context context;
    List<Parkingslotslist> parkingslotslists;
    EntryParkingslotslistAdapter entryParkingslotslistAdapter;
    public DatabaseReference db_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrymodule);
        entryrv=(RecyclerView)findViewById(R.id.entryrv);
        entryrv.setHasFixedSize(true);
        entryrv.setLayoutManager(new LinearLayoutManager(context));
        parkingslotslists=new ArrayList<>();
        context=getApplicationContext();


        db_ref= FirebaseDatabase.getInstance().getReference("valets");
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(context, "Data exist", Toast.LENGTH_SHORT).show();
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        if(npsnapshot.getKey().contains("slots"))
                        {
                            for(DataSnapshot children:npsnapshot.getChildren())
                            {
                                Parkingslotslist pl = children.getValue(Parkingslotslist.class);
                                if(pl.getStatus().contains("reserved"))
                                {

                                }
                                else {
                                    parkingslotslists.add(pl);
                                    Log.v("value", children.getValue().toString());
                                }
                            }

                        }
                    }
                    Collections.sort(parkingslotslists);
                    Log.v("list",parkingslotslists.toString());
                    entryParkingslotslistAdapter=new EntryParkingslotslistAdapter(parkingslotslists,context);
                    entryrv.setAdapter(entryParkingslotslistAdapter);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



    }
}