package com.example.finalyearprojectstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReservedSlotClick extends AppCompatActivity {
TextView parkIdView,statuView,carView,mobView,dnameview,ptimeView;
    public DatabaseReference db_ref;
    List<Parkingslotslist> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_slot_click);

        data=new ArrayList<>();

        parkIdView=findViewById(R.id.park_id);
        statuView=findViewById(R.id.park_status);
        carView=findViewById(R.id.park_carno);
        mobView=findViewById(R.id.park_mobno);
        dnameview=findViewById(R.id.park_drivername);
        ptimeView=findViewById(R.id.park_time);

        db_ref= FirebaseDatabase.getInstance().getReference("valets");

        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(ReservedSlotClick.this, "Data exist", Toast.LENGTH_SHORT).show();
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        if(npsnapshot.getKey().contains("slots"))
                        {
                            for(DataSnapshot children:npsnapshot.getChildren())
                            {
                                if(children.getKey().contains("id"+getIntent().getStringExtra("slotid"))) {
                                    Parkingslotslist pl = children.getValue(Parkingslotslist.class);
                                    data.add(pl);
                                    Log.v("value", children.getValue().toString());
                                }
                            }

                        }
                    }
                   parkIdView.setText(data.get(0).getslot());
                    statuView.setText(data.get(0).getStatus());
                    carView.setText(data.get(0).getCarnumber());
                    mobView.setText(data.get(0).getCmobile());
                    ptimeView.setText(data.get(0).getParktime());
                    dnameview.setText(data.get(0).getValetname());



                }
                else {
                    Toast.makeText(ReservedSlotClick.this, "Parking Data doesn't exixts", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }
}