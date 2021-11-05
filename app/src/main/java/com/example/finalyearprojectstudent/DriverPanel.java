package com.example.finalyearprojectstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriverPanel extends AppCompatActivity {
    public Button entry;
    public Button exit;
    String valetid;
    private Toolbar toolbarLayout;
    public List<adminDataList> listData;
    FirebaseAuth firebaseAuth;
    DatabaseReference db_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_panel);

        entry = (Button) findViewById(R.id.entry);
        exit = (Button) findViewById(R.id.exit);
        toolbarLayout=(Toolbar) findViewById(R.id.driverappbar);
        setSupportActionBar(toolbarLayout);

        firebaseAuth = FirebaseAuth.getInstance();
        valetid=firebaseAuth.getCurrentUser().getUid();
        db_ref = FirebaseDatabase.getInstance().getReference("valets");

        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(DriverPanel.this, entrymodule.class);
                startActivity(intent);

            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(DriverPanel.this, exitmodule.class);
                startActivity(intent);

            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.driver_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.driverprofile:
                adminData();
                return true;
            case R.id.drivercomplaint:
                complaints();
                return true;
            case R.id.driverlogout:
                logoutdriver();
                finish();
                return true;
            case R.id.driverguide:
                driverguide();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
private void driverguide()
    {

    }

    private void complaints()
    {
        Intent i;
        i=new Intent(DriverPanel.this, DriverComplaints.class);
        startActivity(i);
    }
    private void logoutdriver()
    {
        firebaseAuth.signOut();
        SharedPrefrence.write(SharedPrefrence.user_email,"");
        SharedPrefrence.write(SharedPrefrence.admin_email,"");
        startActivity(new Intent(DriverPanel.this, LoginMainActivity.class));
        finish();
    }

    private void adminData() {
        listData=new ArrayList<>();
        db_ref = FirebaseDatabase.getInstance().getReference("valets");
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(DriverPanel.this, "Data exist", Toast.LENGTH_SHORT).show();
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        if(!npsnapshot.getKey().contains("slots")  && !npsnapshot.getKey().contains("complaints") ) {
                            adminDataList l = npsnapshot.getValue(adminDataList.class);
                            Log.v("value",l.getUsertype().toString());
                            if (l.getUsertype().contains("valet")  && encodeUserEmail(l.getValetemail()).contains(encodeUserEmail(SharedPrefrence.read(SharedPrefrence.user_email,"")))) {
                                listData.add(l);
                                Log.v("adminlist",listData.get(0).getValetcnic().toString());
                            }
                        }
                    }
                    Intent i= new Intent(DriverPanel.this,DriverProfile.class);
                    i.putExtra("name",listData.get(0).getValetname());
                    i.putExtra("cnic",listData.get(0).getValetcnic());
                    i.putExtra("email",listData.get(0).getValetemail());
                    i.putExtra("mobile",listData.get(0).getValetmobile());
                    i.putExtra("dob",listData.get(0).getValetdob());
                    i.putExtra("father",listData.get(0).getValetfathername());
                    i.putExtra("homemobile",listData.get(0).getValetfamilycontact());
                    startActivity(i);

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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}