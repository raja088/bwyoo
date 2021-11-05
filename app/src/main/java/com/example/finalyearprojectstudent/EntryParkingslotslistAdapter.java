package com.example.finalyearprojectstudent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.finalyearprojectstudent.Parkingslotslist;
import com.example.finalyearprojectstudent.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EntryParkingslotslistAdapter extends RecyclerView.Adapter<EntryParkingslotslistAdapter.entryparklist_Holder> {
    private List<Parkingslotslist> entryparklist;
    public Context context;

    public EntryParkingslotslistAdapter(List<Parkingslotslist> entryparklist, Context context){
        this.entryparklist=entryparklist;
        this.context=context;
    }

    @NonNull
    @Override
    public entryparklist_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutinflater=LayoutInflater.from(context);
        View view=layoutinflater.inflate(R.layout.parkingslotslist,null);
        return new  EntryParkingslotslistAdapter.entryparklist_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final entryparklist_Holder holder, int position) {
        final Parkingslotslist pl=entryparklist.get(position);
        holder.pslot.setText(pl.getslot());
        holder.status.setText(pl.getStatus());
         holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i=new Intent(context,CutomerBookingParkingId.class);
                 i.putExtra("slotid",pl.slot);
                 context.startActivity(i);
             }
         });

          }

    @Override
    public int getItemCount() {
        return entryparklist.size();
    }

    public class entryparklist_Holder extends RecyclerView.ViewHolder{
        TextView pslot,status;
        public RelativeLayout relativeLayout;

        public entryparklist_Holder(@NonNull View itemView) {
            super(itemView);
            pslot=(TextView) itemView.findViewById(R.id.slot);
            status=(TextView)itemView.findViewById(R.id.status);
            relativeLayout=(RelativeLayout) itemView.findViewById(R.id.relativelayout);
        }
    }
}
