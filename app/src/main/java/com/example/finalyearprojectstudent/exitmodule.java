package com.example.finalyearprojectstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class exitmodule extends AppCompatActivity {
   EditText mob;
   Button exit;
   DatabaseReference db_ref;
    DatabaseReference dbref;
    String slotid,carnumber,parktime,parkhours,parkminute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exitmodule);
        mob=findViewById(R.id.customermobno);
        exit=findViewById(R.id.exitbtn);

        if (ContextCompat.checkSelfPermission(exitmodule.this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Ask for permision
            ActivityCompat.requestPermissions(exitmodule.this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mobile=mob.getText().toString();
                if(TextUtils.isEmpty(mobile) || mobile.length()>11 || mobile.length()<11)
                {
                    Toast.makeText(exitmodule.this, "Please Enter Mobile Number First", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    db_ref = FirebaseDatabase.getInstance().getReference("valets");
                    db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(exitmodule.this, "Data exist", Toast.LENGTH_SHORT).show();
                                for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                                    if (npsnapshot.getKey().contains("slots")) {
                                        for (DataSnapshot children : npsnapshot.getChildren()) {
                                            if (children.exists()) {
                                                Parkingslotslist pl = children.getValue(Parkingslotslist.class);
                                                Log.v("number",mobile);
                                                if ( pl.getCmobile()!=null && mobile.contains(pl.getCmobile().toString())) {
                                                    slotid = pl.getslot();
                                                    carnumber = pl.getCarnumber();
                                                    parktime = pl.getParktime();
                                                    dbref = db_ref.child("slots").child(children.getKey());

                                                    Log.v("value", dbref.toString());
                                                }
                                                else
                                                {
                                                    Toast.makeText(exitmodule.this, "No Entry At this Number", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        }

                                    }
                                }
                                dbref.child("status").setValue("free");
                                dbref.child("cmobile").removeValue();
                                dbref.child("carnumber").removeValue();
                                dbref.child("parktime").removeValue();
                                dbref.child("valetname").removeValue();
                                String exitcars = SharedPrefrence.read(SharedPrefrence.exitcars, "");
                                exitcars = String.valueOf(Integer.parseInt(exitcars) + 1);
                                db_ref.child(encodeUserEmail(SharedPrefrence.read(SharedPrefrence.user_email, ""))).child("exitcars").setValue(exitcars);
                                SharedPrefrence.write(SharedPrefrence.exitcars, exitcars);

                                String currentTime = new SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(new Date());

                                double charges = 0;
                                try {
                                    charges = payment(parktime, currentTime);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                SmsManager smgr = SmsManager.getDefault();
                                smgr.sendTextMessage(mobile, null, carnumber + " Parking ID " + slotid + " Exit at " + currentTime + " \n Charges = " + charges, null, null);

                                Toast.makeText(exitmodule.this, " Exit car Successfully ", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(exitmodule.this);
                                builder1.setMessage("Parking Id : " + slotid + "\nCarNo : " + carnumber + "\nParked for Time : " + parkhours + "h :" + parkminute + "m\nCharges : " + charges);
                                builder1.setTitle("Exit");
                                builder1.setIcon(R.mipmap.ic_logo_foreground);
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                startActivity(new Intent(exitmodule.this, DriverPanel.class));
                                                finish();
                                            }
                                        });
                                builder1.setCancelable(false);

                                AlertDialog alert11 = builder1.create();
                                alert11.show();


                            } else {
                                Toast.makeText(exitmodule.this, "Parking Data doesn't exixts", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }


        });






    }

    private double payment(String parkitime, String currenttime) throws ParseException
    {  int dhour,dminute;
       double charges;
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a");
        Date date1 = format.parse(parkitime);
        Date date2 = format.parse(currenttime);
        long milis=date2.getTime()-date1.getTime();

        dhour= (int) (milis/(1000 * 60 * 60));
        dminute=(int)((milis/(1000*60)) % 60);
        parkhours= String.valueOf(dhour);
        parkminute= String.valueOf(dminute);

           Log.v("diffhour","is = "+dhour);
        Log.v("diffminute","is = "+dminute);

        double hourcharge=dhour*100;
        int minutecharge= (int) (dminute*1.6);


        Log.v("hour charges",""+hourcharge);
        Log.v("minute charges",""+minutecharge);

        charges=hourcharge+minutecharge;

            Log.v("charges","is = "+charges);

             String chargess=SharedPrefrence.read(SharedPrefrence.charges,"");
             chargess= String.valueOf(charges+Double.parseDouble(chargess));
             Log.v("charges",chargess);
            db_ref.child(encodeUserEmail(SharedPrefrence.read(SharedPrefrence.user_email,""))).child("charges").setValue(chargess);

            return charges;

    }
    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

}