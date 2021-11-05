package com.example.finalyearprojectstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CutomerBookingParkingId extends AppCompatActivity {
    private static final int REQUEST_CODE = 9001;
    EditText mobile,car;
    String currentdate;

    DatabaseReference db_ref;
Button book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutomer_booking_parking_id);

        mobile=findViewById(R.id.customermobno);
        db_ref = FirebaseDatabase.getInstance().getReference("valets");
        car=findViewById(R.id.carno);
        book=findViewById(R.id.bookbtn);
         currentdate = new SimpleDateFormat("YYYY:MM:dd", Locale.getDefault()).format(new Date());
         Log.v("date ",currentdate);


        if (ContextCompat.checkSelfPermission(CutomerBookingParkingId.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Ask for permision
            ActivityCompat.requestPermissions(CutomerBookingParkingId.this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mobileNumber,carNumber;
                mobileNumber=mobile.getText().toString();
                carNumber=car.getText().toString();
                SharedPrefrence.write(SharedPrefrence.carnumber,carNumber);
                String currentTime = new SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(new Date());

                if((TextUtils.isEmpty(mobileNumber) && mobileNumber.length()<13) || TextUtils.isEmpty(carNumber))
                {
                    Toast.makeText(CutomerBookingParkingId.this, "Please Fill both Fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                     //SharedPrefrence.write(SharedPrefrence.cmobile,mobileNumber);
                    String parkedcars = SharedPrefrence.read(SharedPrefrence.parkedcars, "");
                    parkedcars = String.valueOf(Integer.parseInt(parkedcars)+1);
                    Log.v("parkedcars", parkedcars);
                    db_ref.child("slots").child("id" + getIntent().getStringExtra("slotid")).child("cmobile").setValue(mobileNumber);
                    db_ref.child("slots").child("id" + getIntent().getStringExtra("slotid")).child("status").setValue("reserved");
                    db_ref.child("slots").child("id" + getIntent().getStringExtra("slotid")).child("slot").setValue(getIntent().getStringExtra("slotid"));
                    db_ref.child("slots").child("id" + getIntent().getStringExtra("slotid")).child("carnumber").setValue(carNumber);
                    db_ref.child("slots").child("id" + getIntent().getStringExtra("slotid")).child("parktime").setValue(currentTime);
                    db_ref.child("slots").child("id" + getIntent().getStringExtra("slotid")).child("valetname").setValue(SharedPrefrence.read(SharedPrefrence.valetname,""));

                    db_ref.child(encodeUserEmail(SharedPrefrence.read(SharedPrefrence.user_email, ""))).child("parkedcars").setValue(parkedcars);


                    //  db_ref.child("slots").child("id" + getIntent().getStringExtra("slotid")).child("valetid").setValue(SharedPrefrence.read(SharedPrefrence.driverid,""));

                    SharedPrefrence.write(SharedPrefrence.parkedcars,parkedcars);

//                    Intent intent=new Intent(getApplicationContext(),DriverPanel.class);
//                    PendingIntent pi= PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
                    SmsManager smgr = SmsManager.getDefault();
                    smgr.sendTextMessage(mobileNumber, null, carNumber + " Parking ID " + getIntent().getStringExtra("slotid") + " Parked at " + currentTime, null, null);
                    Toast.makeText(CutomerBookingParkingId.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CutomerBookingParkingId.this,DriverPanel.class));
                    finish();
                }

                catch (Exception e)
                {
                    Toast.makeText(CutomerBookingParkingId.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                String mCars=SharedPrefrence.read(SharedPrefrence.mCars,"");
                mCars= String.valueOf(Integer.parseInt(mCars)+1);
                db_ref.child("month").child(currentdate).child("parkedcars").setValue(mCars);



            }




        });

    }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }


}