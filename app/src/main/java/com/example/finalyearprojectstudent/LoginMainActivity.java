package com.example.finalyearprojectstudent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LoginMainActivity extends AppCompatActivity {
    public EditText loginemail;
    public EditText loginpassword;
    public TextView logtosignbtn;
    public  Button  loginbtn;
boolean flag;
    FirebaseAuth firebaseAuth;
    DatabaseReference db_ref;
    String currentdate;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginmain);
        currentdate = new SimpleDateFormat("YYYY:MM:dd", Locale.getDefault()).format(new Date());

        SharedPrefrence.init(LoginMainActivity.this);

        loginemail = (EditText) findViewById(R.id.loginemail);
        loginpassword = (EditText) findViewById(R.id.loginpassword);
        logtosignbtn = (TextView) findViewById(R.id.logtosignbtn);
        loginbtn = (Button) findViewById(R.id.loginbtn);

        firebaseAuth = FirebaseAuth.getInstance();
        db_ref = FirebaseDatabase.getInstance().getReference("valets");


        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for (DataSnapshot children : snapshot.getChildren())
                    {
                        if(children.getKey().contains("month"))
                        {
                            for(DataSnapshot child: children.getChildren())
                            {  Log.v("shared ",child.getKey());
                                if( child.getKey().contains(currentdate))
                                {
                                    String cars=child.child("parkedcars").getValue().toString();
                                    SharedPrefrence.write(SharedPrefrence.mCars,cars);
                                    Log.v("shared ",SharedPrefrence.read(SharedPrefrence.mCars,""));
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        logtosignbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(LoginMainActivity.this, Signin2.class);
                startActivity(intent);
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String logemail = loginemail.getText().toString();
                final String logpassword = loginpassword.getText().toString();



                if (!TextUtils.isEmpty(logemail) && !TextUtils.isEmpty(logpassword)) {
                    Log.d("FIREBASEAUTH", "onClick: "+firebaseAuth);
                    firebaseAuth.signInWithEmailAndPassword(logemail, logpassword)
                            .addOnCompleteListener(LoginMainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("firebaseuserID", "onComplete: "+firebaseAuth.getCurrentUser());
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
//                                         final String id= db_ref.child(encodeUserEmail(logemail)).getKey();
//                                       final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                valet driver=new valet();
                                                Log.v("available","yes");
                                                 if(dataSnapshot.exists()) {
                                                     for (DataSnapshot valetsnapshot : dataSnapshot.getChildren()) {

                                                         if (encodeUserEmail(logemail).equalsIgnoreCase(valetsnapshot.getKey().toString())) {
                                                             driver = valetsnapshot.getValue(valet.class);
                                                             String type = driver.getUsertype();
                                                             String driverid=driver.getValetid();
                                                             Log.v("type", driver.getUsertype().toString());
                                                             Log.v("driverid", driver.getValetid().toString());

                                                             if (type.equalsIgnoreCase("admin")) {
                                                                 SharedPrefrence.write(SharedPrefrence.type, type);
                                                                 Log.d("logindbadminA", "onDataChange: ");
                                                                 flag = true;

                                                             } else if (type.equalsIgnoreCase("valet")) {
                                                                 SharedPrefrence.write(SharedPrefrence.type, type);
                                                                 SharedPrefrence.write(SharedPrefrence.driverid,driverid);
                                                                 SharedPrefrence.write(SharedPrefrence.parkedcars,driver.getParkedcars());
                                                                 SharedPrefrence.write(SharedPrefrence.exitcars,driver.getExitcars());
                                                                 SharedPrefrence.write(SharedPrefrence.valetname,driver.getValetname());
                                                                 SharedPrefrence.write(SharedPrefrence.phone,driver.valetmobile);
                                                                 SharedPrefrence.write(SharedPrefrence.charges,driver.getCharges());

                                                                 Log.d("parkedcars",SharedPrefrence.read(SharedPrefrence.parkedcars,""));
                                                                 Log.d("logindbadminD", "onDataChange: ");
                                                                 flag = false;


                                                             }

                                                         }

                                                     }
                                                 }
                                                     if (driver != null && SharedPrefrence.read(SharedPrefrence.type, "").equalsIgnoreCase("admin")) {
                                                         Toast.makeText(LoginMainActivity.this, "Admin LogIn Success", Toast.LENGTH_SHORT).show();
                                                         SharedPrefrence.write(SharedPrefrence.admin_email, logemail);
                                                         SharedPrefrence.write(SharedPrefrence.password,logpassword);
                                                         Intent intent = new Intent(getApplicationContext(), AdminPanel.class);
                                                         startActivity(intent);
                                                         finish();
                                                     }
                                                     if (driver != null && SharedPrefrence.read(SharedPrefrence.type, "").equalsIgnoreCase("valet")) {
                                                         Toast.makeText(LoginMainActivity.this, "Driver LogIn Success", Toast.LENGTH_SHORT).show();
                                                         SharedPrefrence.write(SharedPrefrence.user_email, logemail);
                                                         SharedPrefrence.write(SharedPrefrence.password,logpassword);
                                                         Intent intent = new Intent(getApplicationContext(), DriverPanel.class);
                                                         startActivity(intent);
                                                         finish();
                                                     }



                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        });
                                    }
                                     else if(!task.isSuccessful())
                                        Toast.makeText(LoginMainActivity.this, "LogIn Failed Try Again", Toast.LENGTH_SHORT).show();

                                }
                            });
//                    db_ref.child(encodeUserEmail(logemail)).child("valetpassword").setValue(logpassword);

                }
            }
        });

//
//       db_ref.child("slots").child("id1").child("slot").setValue("1");
//        db_ref.child("slots").child("id1").child("status").setValue("free");
//        db_ref.child("slots").child("id2").child("slot").setValue("2");
//        db_ref.child("slots").child("id2").child("status").setValue("free");
//        db_ref.child("slots").child("id3").child("slot").setValue("3");
//        db_ref.child("slots").child("id3").child("status").setValue("free");
//        db_ref.child("slots").child("id4").child("slot").setValue("4");
//        db_ref.child("slots").child("id4").child("status").setValue("free");
//        db_ref.child("slots").child("id5").child("slot").setValue("5");
//        db_ref.child("slots").child("id5").child("status").setValue("free");
//        db_ref.child("slots").child("id6").child("slot").setValue("6");
//        db_ref.child("slots").child("id6").child("status").setValue("free");
//        db_ref.child("slots").child("id7").child("slot").setValue("7");
//        db_ref.child("slots").child("id7").child("status").setValue("free");
//        db_ref.child("slots").child("id8").child("slot").setValue("8");
//        db_ref.child("slots").child("id8").child("status").setValue("free");
//        db_ref.child("slots").child("id9").child("slot").setValue("9");
//        db_ref.child("slots").child("id9").child("status").setValue("free");
//        db_ref.child("slots").child("id10").child("slot").setValue("10");
//        db_ref.child("slots").child("id10").child("status").setValue("free");
//        db_ref.child("slots").child("id11").child("slot").setValue("11");
//        db_ref.child("slots").child("id11").child("status").setValue("free");
//        db_ref.child("slots").child("id12").child("slot").setValue("12");
//        db_ref.child("slots").child("id12").child("status").setValue("free");
//        db_ref.child("slots").child("id13").child("slot").setValue("13");
//        db_ref.child("slots").child("id13").child("status").setValue("free");
//        db_ref.child("slots").child("id14").child("slot").setValue("14");
//        db_ref.child("slots").child("id14").child("status").setValue("free");
//        db_ref.child("slots").child("id15").child("slot").setValue("15");
//        db_ref.child("slots").child("id15").child("status").setValue("free");



    }

    private boolean validatelogin() {
        if (loginemail.getText().toString().isEmpty() || loginpassword.getText().toString().isEmpty()) {
            loginemail.setError("Empty Fields are not allowed");
            loginpassword.setError("Empty Fields are not allowed");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(loginemail.getText().toString()).matches()) {
            loginemail.setError("Incorrect Email ID.");
            return false;
        }
        else
        {
            return true;
        }
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