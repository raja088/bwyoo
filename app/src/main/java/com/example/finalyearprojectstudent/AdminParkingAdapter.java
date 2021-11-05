package com.example.finalyearprojectstudent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
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

public class AdminParkingAdapter extends RecyclerView.Adapter<AdminParkingAdapter.ParkingSlotsListViewHolder> {

    private List<Parkingslotslist> parkinglistData;
    public Context context;


    public AdminParkingAdapter(List<Parkingslotslist> parkinglistdata,Context context){
        this.parkinglistData=parkinglistdata;
        this.context=context;
    }

    @NonNull
    @Override
    public ParkingSlotsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.parkingslotslist,null);
        return new AdminParkingAdapter.ParkingSlotsListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ParkingSlotsListViewHolder holder, int position) {
        final Parkingslotslist pl=parkinglistData.get(position);
        holder.pslot.setText(pl.getslot());
         holder.status.setText(pl.getStatus());
        //Log.e("reserved recycle", "onBindViewHolder: " + pl.getStatus() + "position " + pl.getslot() );
         if(pl.getStatus().contentEquals("reserved")  && Integer.parseInt(pl.getslot()) == (position+1))
         {
             holder.relativeLayout.setCardBackgroundColor(Color.parseColor("#ff0000"));
             Log.e("reserved",pl.getslot()+" status "+pl.getStatus() + " position " + position+1 );
         }
         holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i=new Intent(context,ReservedSlotClick.class);
                 i.putExtra("slotid",pl.getslot());
                 context.startActivity(i);
             }
         });

    }

    @Override
    public int getItemCount() {
        return parkinglistData.size();
    }

    public class ParkingSlotsListViewHolder extends RecyclerView.ViewHolder{
        public TextView pslot,status;
        public CardView relativeLayout;
        public ParkingSlotsListViewHolder(@NonNull View itemView) {
            super(itemView);
            pslot=(TextView) itemView.findViewById(R.id.slot);
            status=(TextView)itemView.findViewById(R.id.status);
            relativeLayout=(CardView) itemView.findViewById(R.id.relative);
        }
    }

}
