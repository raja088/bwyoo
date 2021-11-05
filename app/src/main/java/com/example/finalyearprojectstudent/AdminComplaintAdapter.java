package com.example.finalyearprojectstudent;

import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalyearprojectstudent.AdminComplaintsList;
import com.example.finalyearprojectstudent.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminComplaintAdapter extends RecyclerView.Adapter<AdminComplaintAdapter.AComplaint_holder> {
    public List<AdminComplaintsList> adminComplaintsLists;
    public Context context;
    DatabaseReference dbRef;

    public AdminComplaintAdapter(List<AdminComplaintsList> adminComplaintsLists, Context context){
        this.adminComplaintsLists=adminComplaintsLists;
        this.context=context;
    }

    @NonNull
    @Override
    public AComplaint_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutinflater=LayoutInflater.from(context);
        View view=layoutinflater.inflate(R.layout.admincomp_list,null);
        return new  AdminComplaintAdapter.AComplaint_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AComplaint_holder complaint_holder, int position) {
        final AdminComplaintsList compl_list = adminComplaintsLists.get(position);

        if (!compl_list.getStatus().contentEquals("no")) {
            complaint_holder.driver_name.setText(compl_list.getName());
            complaint_holder.complaintmsg.setText(compl_list.getComplaint());
            Log.v("yes", compl_list.getStatus());
            Log.v("yes", compl_list.getPhone());

        }


        complaint_holder.respondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respond(compl_list.getName(),compl_list.getPhone());
                context.startActivity(new Intent(context,AdminPanel.class));
            }
        });
        complaint_holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef= FirebaseDatabase.getInstance().getReference("valets");
                dbRef.child("complaints").child(compl_list.getName()).child("status").setValue("no");
                context.startActivity(new Intent(context,AdminPanel.class));

            }
        });

        }


    @Override
    public int getItemCount() {
        return adminComplaintsLists.size();
    }

    public class AComplaint_holder extends RecyclerView.ViewHolder{
        TextView driver_name;
        TextView complaintmsg;
        Button respondBtn,delBtn;
        public AComplaint_holder(@NonNull View itemView) {
            super(itemView);
            driver_name=(TextView) itemView.findViewById(R.id.driver_name);
            complaintmsg=(TextView)itemView.findViewById(R.id.complaintmsg);
            respondBtn=(Button)itemView.findViewById(R.id.respondBtn);
            delBtn=(Button)itemView.findViewById(R.id.delBtn);

        }
    }

    void respond(String name,String mobileNumber)
    {
        Log.v("valetName",name);
        dbRef= FirebaseDatabase.getInstance().getReference("valets");
        dbRef.child("complaints").child(name).child("status").setValue("no");
        SmsManager smgr = SmsManager.getDefault();
        smgr.sendTextMessage(mobileNumber, null,  " Your complain is under process now and will be resolve soon !" , null, null);

    }

}
