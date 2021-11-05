package com.example.finalyearprojectstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriverDetails extends AppCompatActivity {
    RecyclerView d_detailsrv;
    public Context context;
    List<DriverdetailList> d_detaillist;
    DriverDetailsAdapter d_detailadapter;
    public DatabaseReference db_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);
        d_detailsrv=(RecyclerView)findViewById(R.id.driverdetail);
        d_detailsrv.setHasFixedSize(true);
        d_detailsrv.setLayoutManager(new LinearLayoutManager(context));
        d_detaillist=new ArrayList<>();
        context=getApplicationContext();

        db_ref= FirebaseDatabase.getInstance().getReference("valets");
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
//                    Toast.makeText(context, "Data exist", Toast.LENGTH_SHORT).show();
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        if(npsnapshot.getKey().contains(encodeUserEmail(getIntent().getStringExtra("email"))))
                        {
                        DriverdetailList ddl= npsnapshot.getValue(DriverdetailList.class);
                        d_detaillist.add(ddl);
                        }
                    }
                    d_detailadapter=new DriverDetailsAdapter(d_detaillist,context);
                    d_detailsrv.setAdapter(d_detailadapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
}