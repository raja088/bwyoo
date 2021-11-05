package com.example.finalyearprojectstudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverProfile extends AppCompatActivity {
    TextView profilename;
    TextView profilecnic;
    TextView profileemail;
    TextView profilecontact;
    TextView profiledob;
    TextView profilefathername;
    TextView profilefamilycontact;
    Button passwordchange;

    FirebaseAuth firebaseAuth;
    DatabaseReference db_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);


        Intent i = getIntent();

       profilename = (TextView) findViewById(R.id.d_profilename);
       profilecnic = (TextView) findViewById(R.id.d_profilecnic);
       profileemail = (TextView) findViewById(R.id.d_profileemail);
       profilecontact = (TextView) findViewById(R.id.d_profilecontact);
       profiledob = (TextView) findViewById(R.id.d_profiledob);
       profilefathername = (TextView) findViewById(R.id.d_profilefathername);
       profilefamilycontact = (TextView) findViewById(R.id.d_profilefamilycontact);
       passwordchange = (Button) findViewById(R.id.d_passwordchange);

        profilename.setText(i.getStringExtra("name"));
        profilecnic.setText(i.getStringExtra("cnic"));
        profileemail.setText(i.getStringExtra("email"));
        profilecontact.setText(i.getStringExtra("mobile"));
        profiledob.setText(i.getStringExtra("dob"));
        profilefathername.setText(i.getStringExtra("father"));
        profilefamilycontact.setText(i.getStringExtra("homemobile"));

        passwordchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DriverProfile.this,DriverPasswordChange.class);
                i.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(i);
                finish();
            }
        });

    }
}