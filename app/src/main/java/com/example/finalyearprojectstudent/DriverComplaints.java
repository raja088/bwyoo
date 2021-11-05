package com.example.finalyearprojectstudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverComplaints extends AppCompatActivity {
    public TextView drivercomptv;
    public EditText dcomplaint_et;
    public Button dcomplaint_btn;
    DatabaseReference db_ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_complaints);

        drivercomptv=(TextView)findViewById(R.id.drivercomptv);
        dcomplaint_et=(EditText)findViewById(R.id.dcomplaint_et);
        dcomplaint_btn=(Button)findViewById(R.id.dcomplaint_btn);


        dcomplaint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String complaint=dcomplaint_et.getText().toString();
                if(TextUtils.isEmpty(complaint))
                {
                    Toast.makeText(DriverComplaints.this, "Write Complaint First !", Toast.LENGTH_SHORT).show();
                    return;
                }
                db_ref= FirebaseDatabase.getInstance().getReference("valets");
                db_ref.child("complaints").child(SharedPrefrence.read(SharedPrefrence.valetname,"")).child("complaint").setValue(complaint);
                db_ref.child("complaints").child(SharedPrefrence.read(SharedPrefrence.valetname,"")).child("phone").setValue(SharedPrefrence.read(SharedPrefrence.phone,""));
                db_ref.child("complaints").child(SharedPrefrence.read(SharedPrefrence.valetname,"")).child("status").setValue("yes");



                startActivity(new Intent(DriverComplaints.this,DriverPanel.class));
                finish();

            }
        });

    }
    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");}
}