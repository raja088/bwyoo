package com.example.finalyearprojectstudent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class Signin2 extends AppCompatActivity {

    public EditText drivername;
    public EditText drivercnic;
    public TextView driverdob;
    public EditText drivercontact;
    public EditText driverfathername;
    public EditText driverfamilycontact;
    public EditText driveraddress;
    public EditText signinemail;
    public EditText signinpassword;
    public Button signinbtn;
    public TextView signtologbtn;
    String currentdate;

    SharedPreferences sharedpref;

    DatePickerDialog.OnDateSetListener setdob;
    private FirebaseAuth firebaseAuth;
    DatabaseReference db_ref;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin2);

       SharedPrefrence.init(Signin2.this);

        drivername = (EditText) findViewById(R.id.drivername);
        drivercnic = (EditText) findViewById(R.id.drivercnic);

        driverdob = (TextView) findViewById(R.id.driverdob);
        drivercontact = (EditText) findViewById(R.id.drivercontact);
        driverfathername = (EditText) findViewById(R.id.driverfathername);
        driverfamilycontact = (EditText) findViewById(R.id.driverfamilycontact);
        driveraddress = (EditText) findViewById(R.id.driveraddress);
        signtologbtn = (TextView) findViewById(R.id.signtologbtn);

        signinemail = (EditText) findViewById(R.id.signinemail);
        signinpassword = (EditText) findViewById(R.id.signinpassword);
        signinbtn = (Button) findViewById(R.id.signinbtn);

        currentdate = new SimpleDateFormat("YYYY:MM:dd", Locale.getDefault()).format(new Date());


        signtologbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intents;
                    intents = new Intent(Signin2.this, LoginMainActivity.class);
                    startActivity(intents);
            }
        });

        Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);

        driverdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Signin2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        driverdob.setText(date);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        db_ref = FirebaseDatabase.getInstance().getReference("valets");


        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storesharedpreferencedata();
                validation();
                final String signname = drivername.getText().toString().trim();
                final String signcnic = drivercnic.getText().toString().trim();
                final String signdob = driverdob.getText().toString().trim();
                final String signfathername = driverfathername.getText().toString().trim();
                final String signmobile= drivercontact.getText().toString().trim();
                final String signfamilycontact = driverfamilycontact.getText().toString().trim();
                final String signaddress = driveraddress.getText().toString().trim();
                final String signemail = signinemail.getText().toString().trim();
                final String signpassword = signinpassword.getText().toString().trim();
                final String parkedcars="0";
                final String exitcars="0";
                final String charges="0";



                if (!TextUtils.isEmpty(signemail) && !TextUtils.isEmpty(signpassword)) {
                        firebaseAuth.createUserWithEmailAndPassword(signemail, signpassword)
                                .addOnCompleteListener(Signin2.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            final FirebaseUser user = firebaseAuth.getCurrentUser();
                                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    String valetid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                    valet valetdata = new valet(valetid, exitcars,charges, parkedcars, signname, signcnic, signdob, signfathername, signmobile,
                                                            signfamilycontact, signaddress, signemail, signpassword, "valet");
                                                    db_ref.child(encodeUserEmail(signemail)).setValue(valetdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                db_ref.child("month").child(currentdate).child("parkedcars").setValue("0");
                                                                Toast.makeText(Signin2.this, "Data Successfully added", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(Signin2.this, "Unsuccessful data saving", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                    Toast.makeText(Signin2.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(getApplicationContext(), LoginMainActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                }
                                            });
                                        } else {
                                            Toast.makeText(Signin2.this, "You are Kicked Out by Admin Please contact Admin !", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

            }
        });

    }

    private void storesharedpreferencedata() {
         String signname = drivername.getText().toString();
         String signcnic = drivercnic.getText().toString();
         String signdob = driverdob.getText().toString();
         String signfathername = driverfathername.getText().toString();
         String signmobile= drivercontact.getText().toString();
         String signfamilycontact = driverfamilycontact.getText().toString();
         String signaddress = driveraddress.getText().toString();
         String signemail = signinemail.getText().toString();
         String signpassword = signinpassword.getText().toString();

         SharedPrefrence.write("drivername",signname);
         SharedPrefrence.write("drivercnic",signcnic);
        SharedPrefrence.write("driverdob",signdob);
        SharedPrefrence.write("driverfathername",signfathername);
        SharedPrefrence.write("drivercontact",signmobile);
        SharedPrefrence.write("driverfamilycontact",signfamilycontact);
        SharedPrefrence.write("driveraddress",signaddress);
//         editor.putString("signinemail",signemail);
        SharedPrefrence.write("signinpassword",signpassword);


    }
    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
    private boolean validation() {
        if (drivername.getText().toString().isEmpty()){
            drivername.setError("Field can not be empty");
            return false;
        }
        else if(drivercnic.getText().toString().isEmpty()){
            drivercnic.setError("Field can not be empty");
            return false;
        }
        else if(driverdob.getText().toString().isEmpty()){
            driverdob.setError("Field can not be empty");
            return false;
        }
        else if(drivercontact.getText().toString().isEmpty()){
            drivercontact.setError("Field can not be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(signinemail.getText().toString()).matches()){
            signinemail.setError("Incorrect Email ID.");
            return false;
        }
        else if(driverfathername.getText().toString().isEmpty()){
            driverfathername.setError("Field can not be empty");
            return false;
        }
        else if(driverfamilycontact.getText().toString().isEmpty()){
            driverfamilycontact.setError("Field can not be empty");
            return false;
        }
        else if(driveraddress.getText().toString().isEmpty()){
            driveraddress.setError("Field can not be empty");
            return false;
        }
        else if(signinemail.getText().toString().isEmpty()){
            signinemail.setError("Field can not be empty");
            return false;
        }
        else if(signinpassword.getText().toString().isEmpty()){
            signinpassword.setError("Field can not be empty");
            return false;
        }
        else if(!Pattern.compile("[0-9]{5}"+"-"+"[0-9]{7}"+"-"+"[0-9]{1}").matcher(drivercnic.getText().toString()).matches()) {
            drivercnic.setError("Incorrect CNIC");
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }
}