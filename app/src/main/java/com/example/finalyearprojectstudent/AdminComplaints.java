package com.example.finalyearprojectstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminComplaints extends AppCompatActivity {
    RecyclerView admincomplaintrv;
    public Context context;
    TextView complainTxt;
    public DatabaseReference db_ref;
    List<AdminComplaintsList> adminComplaintsList;
   AdminComplaintAdapter acomp_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaints);
        admincomplaintrv=(RecyclerView)findViewById(R.id.admincomplaintrv);
        complainTxt=findViewById(R.id.complian);
        admincomplaintrv.setHasFixedSize(true);
        admincomplaintrv.setLayoutManager(new LinearLayoutManager(context));
        context=getApplicationContext();
        adminComplaintsList=new ArrayList<>();

        if (ContextCompat.checkSelfPermission(AdminComplaints.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Ask for permision
            ActivityCompat.requestPermissions(AdminComplaints.this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        db_ref= FirebaseDatabase.getInstance().getReference("valets");
        db_ref.child("complaints").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    adminComplaintsList.clear();
                    Toast.makeText(context, "Data exist", Toast.LENGTH_SHORT).show();
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        AdminComplaintsList ac_list= npsnapshot.getValue(AdminComplaintsList.class);
                        ac_list.setName(npsnapshot.getKey());
                        if(ac_list.getStatus().contains("yes"))
                        {adminComplaintsList.add(ac_list);}
                    }
                    acomp_adapter=new AdminComplaintAdapter(adminComplaintsList,context);
                    admincomplaintrv.setAdapter(acomp_adapter);
                    if(adminComplaintsList.isEmpty())
                    {
                        complainTxt.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminComplaints.this,AdminPanel.class));
        finish();
    }
}