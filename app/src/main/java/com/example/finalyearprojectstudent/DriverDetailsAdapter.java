package com.example.finalyearprojectstudent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DriverDetailsAdapter extends RecyclerView.Adapter<DriverDetailsAdapter.D_DetailHolder> {
    private List<DriverdetailList> d_detaillist;
    public Context context;

    public DriverDetailsAdapter(List<DriverdetailList> d_detailslist, Context context){
        this.d_detaillist=d_detailslist;
        this.context=context;
    }
    @NonNull
    @Override
    public D_DetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutinflater=LayoutInflater.from(context);
        View view=layoutinflater.inflate(R.layout.d_details,null);
        return new  DriverDetailsAdapter.D_DetailHolder(view);
        // View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_driver_details,parent,false);
        // D_DetailHolder d_holder=new D_DetailHolder(view);
        //  return d_holder;

    }

    @Override
    public void onBindViewHolder(@NonNull DriverDetailsAdapter.D_DetailHolder d_holder, int position) {
        final DriverdetailList dd_l=d_detaillist.get(position);

        int salary =(Integer.parseInt(dd_l.getParkedcars())+Integer.parseInt(dd_l.getExitcars()))*300;
        d_holder.dname.setText(dd_l.getValetname());
        d_holder.did.setText(dd_l.getValetCnic());
        d_holder.d_mobno.setText(dd_l.getvaletmobile());
        d_holder.d_emailid.setText(dd_l.getValetemail());
        d_holder.d_dob.setText(dd_l.getValetdob());
        d_holder.d_address.setText(dd_l.getValetaddress());
        d_holder.d_fname.setText(dd_l.getValetfathername());
        d_holder.d_hcontact.setText(dd_l.getvaletfamilytcontact());
        d_holder.enteredcars.setText(dd_l.getParkedcars());
        d_holder.exitedcars.setText(dd_l.getExitcars());
        d_holder.status.setText("offline");
        d_holder.salary.setText("Rs "+salary);


        Log.v("email ",dd_l.getValetemail());
        Log.v("password ",dd_l.getParkedcars());

        d_holder.d_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                // Get auth credentials from the user for re-authentication. The example below shows
                // email and password credentials but there are multiple possible providers,
                // such as GoogleAuthProvider or FacebookAuthProvider.
                AuthCredential credential = EmailAuthProvider
                        .getCredential(dd_l.getValetemail(),dd_l.getValetpassword());

                // Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("Driver ", "User account deleted.");
                                                }
                                            }
                                        });

                            }
                        });


                DatabaseReference db_ref;
                db_ref=FirebaseDatabase.getInstance().getReference("valets");
                db_ref.child(encodeUserEmail(dd_l.getValetemail())).removeValue();
                context.startActivity(new Intent(context,AdminPanel.class));

            }
        });
        d_holder.d_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getRootView().getContext());
                builder1.setMessage("Total Parked Cars :) "+dd_l.getParkedcars());
                builder1.setTitle("Report");
                builder1.setIcon(R.mipmap.ic_logo_foreground);
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return d_detaillist.size();
    }

    public class D_DetailHolder extends RecyclerView.ViewHolder{
        TextView dname,salarycount, salary,dID,did,d_mob, d_mobno,d_email, d_emailid,d_dateofbirth, d_dob,numofcarsentered, enteredcars,
                d_homeaddress, d_address,d_fathername, d_fname,d_familycontact, d_hcontact,numofcarsexited, exitedcars,activestatus, status;
        Button d_report, d_delete;
        public D_DetailHolder(View view) {
            super(view);
            dname=(TextView) view.findViewById(R.id.dname);
            dID=(TextView) view.findViewById(R.id.dID);
            did=(TextView) view.findViewById(R.id.did);
            d_mob=(TextView) view.findViewById(R.id.d_mob);
            d_mobno=(TextView) view.findViewById(R.id.d_mobno);
            d_email=(TextView) view.findViewById(R.id.d_email);
            d_emailid=(TextView) view.findViewById(R.id.d_emailid);
            d_dateofbirth=(TextView) view.findViewById(R.id.d_dateofbirth);
            d_dob=(TextView) view.findViewById(R.id.d_dob);
            d_homeaddress=(TextView) view.findViewById(R.id.d_homeaddress);
            d_address=(TextView) view.findViewById(R.id.d_address);
            d_fathername=(TextView) view.findViewById(R.id.d_fathername);
            d_fname=(TextView) view.findViewById(R.id.d_fname);
            d_familycontact=(TextView) view.findViewById(R.id.d_familycontact);
            d_hcontact=(TextView) view.findViewById(R.id.d_hcontact);
            numofcarsentered=(TextView) view.findViewById(R.id.numofcarsentered);
            enteredcars=(TextView) view.findViewById(R.id.enteredcars);
            numofcarsexited=(TextView) view.findViewById(R.id.numofcarsexited);
            exitedcars=(TextView) view.findViewById(R.id.exitedcars);
            activestatus=(TextView) view.findViewById(R.id.activestatus);
            status=(TextView) view.findViewById(R.id.statuss);
            salarycount=(TextView) view.findViewById(R.id.salarycount);
            salary=(TextView) view.findViewById(R.id.salary);
            d_report=(Button) view.findViewById(R.id.d_report);
            d_delete=(Button) view.findViewById(R.id.d_delete);
        }
    }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

}
