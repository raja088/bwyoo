package com.example.finalyearprojectstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class DriverPasswordChange extends AppCompatActivity {

    EditText confirm_newpw;
    Button updatepw;
    String email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_password_change);
        confirm_newpw=(EditText)findViewById(R.id.confirm_newpw);
        updatepw=(Button)findViewById(R.id.updatepw);
        email=getIntent().getStringExtra("email");

        updatepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg =null;
                final String  cnfrmpass;
                cnfrmpass = updatepw.getText().toString();

                if (TextUtils.isEmpty(cnfrmpass)) {
                    msg = "Please Enter Email First !";
                }
                if (!TextUtils.isEmpty(msg)) {
                    Toast.makeText(DriverPasswordChange.this, msg, Toast.LENGTH_SHORT).show();
                    return;
                } else
                {
                    Log.v("email=",email);
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(DriverPasswordChange.this, "Password Reset Email Sent!", Toast.LENGTH_SHORT).show();
//
                            }else
                            {
                                Toast.makeText(DriverPasswordChange.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                    FirebaseAuth.getInstance().signOut();
                    SharedPrefrence.write(SharedPrefrence.user_email,"");
                    startActivity(new Intent(DriverPasswordChange.this,LoginMainActivity.class));
                    finish();


                }




            }
        });
    }
    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }
}