package com.example.finalyearprojectstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Report extends AppCompatActivity {


    RadioButton charges,entry,exit;
    Spinner nameSpinner;
    TextView tx1, txt2;
    List<String> nameList,resultList;
    String stringR1,stringR2,selectedName,sal;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        nameSpinner=findViewById(R.id.valetName);

        charges=findViewById(R.id.Charges);
        entry=findViewById(R.id.Entry);
        exit=findViewById(R.id.Exit);
        tx1=findViewById(R.id.res1);
        txt2 =findViewById(R.id.res2);

        nameList=new ArrayList<>();

        dbRef= FirebaseDatabase.getInstance().getReference("valets");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                nameList.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        if(!npsnapshot.getKey().contains("slots") && !npsnapshot.getKey().contains("complaints") && !npsnapshot.getKey().contains("month")) {
                            Driverslist l = npsnapshot.getValue(Driverslist.class);
//                            Log.v("value",l.getUsertype());
                            if (l.getUsertype().contains("valet")) {

                                nameList.add(l.getValetname());
                                Log.v("value",l.getUsertype());
                            }
                        }
                    }

                }
                nameSpinner.setAdapter(new ArrayAdapter<>(Report.this,R.layout.support_simple_spinner_dropdown_item,nameList));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedName=nameSpinner.getSelectedItem().toString();
                Log.v("StringR1",selectedName);
                search();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        Log.v("StringR1",selectedName);


    }

    void seletion()
    {
        Log.v("enter","entrr in selector");

    }

    void search()
    {
        Log.v("enter","entrr in search");

        if(charges.isChecked())
          {
              stringR1="charges";

          }else if(entry.isChecked())
          {
              stringR1="parkedcars";

          }else if(exit.isChecked())
          {
              stringR1="exitcars";

          }
        Log.v("StringR1",stringR1);
        dbRef= FirebaseDatabase.getInstance().getReference("valets");
          dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  for(DataSnapshot snapshot1:snapshot.getChildren())
                  {   if(snapshot1.exists()) {
                      if (!snapshot1.getKey().contains("slots") && !snapshot1.getKey().contains("complaints") && !snapshot1.getKey().contains("month")) {
                          Log.v("firstchild", snapshot1.getKey());
                          for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                              if (snapshot2.exists()) {
                                  if (snapshot2.getKey().contains(stringR1)) {
                                      Log.v("secondchild", snapshot2.getValue().toString());
                                      stringR2 = snapshot2.getValue().toString();
                                      tx1.setText(stringR1);
                                      txt2.setText(stringR2);
                                  }
                                  if (snapshot2.getValue().toString().equals(selectedName)) {
                                      return;
                                  }
                              }
                          }
                      }
                     }
                  }
                  Log.v("string2",stringR2);
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });



    }



}