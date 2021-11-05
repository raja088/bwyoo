package com.example.finalyearprojectstudent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearprojectstudent.DriverDetails;
import com.example.finalyearprojectstudent.Driverslist;
import com.example.finalyearprojectstudent.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AdminDriversAdapter extends RecyclerView.Adapter<AdminDriversAdapter.DriversListViewHolder> implements Filterable {
    private List<Driverslist> listData;
    public Context context;
    List<Driverslist> copyList;
    List<Driverslist> copyListed;

    public AdminDriversAdapter(List<Driverslist> listdata, Context context) {
        this.listData = listdata;
        this.context = context;
        copyList=listdata;
        copyListed=listdata;

    }



    public class DriversListViewHolder extends RecyclerView.ViewHolder {
        public TextView dname, email;

        public DriversListViewHolder(@NonNull View itemView) {
            super(itemView);
            dname = (TextView) itemView.findViewById(R.id.nameofdrivertv);

        }

    }


    @NonNull
    @Override
    public DriversListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.driverslist, null);
        return new DriversListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DriversListViewHolder holder, int position) {
        final Driverslist dl = listData.get(position);
        holder.dname.setText(dl.getValetname());


        holder.dname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DriverDetails.class);
                i.putExtra("email", dl.getValetemail());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<Driverslist> filteredResults = null;
                    if (constraint.length()<1) {
                        filteredResults = copyListed;
//                        listData.addAll(copyListed);
                        Log.v("copyListed is Empty",filteredResults.toString());
                    } else {
                        filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                    }

                   FilterResults results = new FilterResults();
                    results.values = filteredResults;


                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    copyList = (List<Driverslist>) results.values;

                    if(!copyList.isEmpty())
                    {
                    listData=copyList;
                        Log.v("values",copyList.toString());
                    }
                    else if(copyList.isEmpty()){
                        listData=copyListed;
                        Log.v("copyListed is ",copyListed.toString());
                        Toast.makeText(context, "  Please Search a Valid User ", Toast.LENGTH_SHORT).show();
                    }

                    notifyDataSetChanged();
                }

                protected List<Driverslist> getFilteredResults(String constraint) {
                    List<Driverslist> results = new ArrayList<>();

                    for (Driverslist item : listData) {
                        if (item.getValetname().toLowerCase().contains(constraint)) {
                            results.add(item);
                        }
                    }
                    return results;
                }
            };


        }


//    public void filter(String queryText) {
//
//         int index;
//       Driverslist  name=null;
//       List<Driverslist> remaings=null;
//        Log.v("name ", copyList.toString());
//
//        copyList = listData;
////        Log.v("name ", name.getValetname());
//
//
//        if (queryText.isEmpty()) {
//            listData.addAll(copyList);
////            listData.addAll(remaings);
//            Log.v("name is empty ", copyList.toString());
//
//        }
//        else if(!copyList.isEmpty()){
//            Log.v("name ", copyList.toString());
//            Log.v("copy list size ",""+copyList.size());
//
//
//            for(int i=0;i<copyList.size();i++)
//              {
//                name=copyList.get(i);
//
//                if (name.getValetname().toLowerCase().contains(queryText.toLowerCase())) {
//
//                        listData.clear();
//                        listData.add(name);
//                        Log.v("name after clear ", copyList.toString());
//                        Log.v("name ", name.getValetname());
//
//                }
////                else if(!name.getValetname().isEmpty() && !(name.getValetname().toLowerCase().contains(queryText.toLowerCase())))
////                {
////                    Log.v("remaings ", name.getValetname().toString());
////                    remaings.add(name);
////                }
//
//              }
//        }
//
//        notifyDataSetChanged();
//    }
}
