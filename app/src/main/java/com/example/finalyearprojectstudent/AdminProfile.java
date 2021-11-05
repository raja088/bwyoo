package com.example.finalyearprojectstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminProfile extends AppCompatActivity {
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
        setContentView(R.layout.activity_admin_profile);
        Intent i = getIntent();
        profilename = (TextView) findViewById(R.id.profilename);
        profilecnic = (TextView) findViewById(R.id.profilecnic);
        profileemail = (TextView) findViewById(R.id.profileemail);
        profilecontact = (TextView) findViewById(R.id.profilecontact);
        profiledob = (TextView) findViewById(R.id.profiledob);
        profilefathername = (TextView) findViewById(R.id.profilefathername);
        profilefamilycontact = (TextView) findViewById(R.id.profilefamilycontact);
        passwordchange = (Button) findViewById(R.id.passwordchange);

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
                Intent i=new Intent(AdminProfile.this,AdminPasswordChange.class);
                i.putExtra("email",getIntent().getStringExtra("email"));
                startActivity(i);
                finish();
            }
        });


    }


}
