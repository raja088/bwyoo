package com.example.finalyearprojectstudent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.finalyearprojectstudent.AdminParkingSlotsBlankFragment;
import com.example.finalyearprojectstudent.AdminProfile;
import com.example.finalyearprojectstudent.LoginMainActivity;
import com.example.finalyearprojectstudent.R;
import com.example.finalyearprojectstudent.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AdminPanel extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private TabLayout tabLayout;
    private Toolbar toolbarLayout;
    private ViewPager viewPager;
    String valetid;
    int tpark = 0;
    int texit = 0;
    public List<adminDataList> listData;
    private AdminDriversBlankFragment2 fragment2;

    FirebaseAuth firebaseAuth;
    DatabaseReference db_ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        tabLayout = (TabLayout) findViewById(R.id.admintabs);
        toolbarLayout = (Toolbar) findViewById(R.id.admintoolbar);
        setSupportActionBar(toolbarLayout);
        fragment2=new AdminDriversBlankFragment2();
        viewPager = (ViewPager) findViewById(R.id.adminviewpager);
        firebaseAuth = FirebaseAuth.getInstance();
        valetid = firebaseAuth.getCurrentUser().getUid();


        loadFragmet();
        AdminParkingSlotsBlankFragment fragment=new AdminParkingSlotsBlankFragment();


        //updateStatus("online");

    }
    private void loadFragmet()
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.Addfragment(fragment2, "Drivers");
        adapter.Addfragment(new AdminParkingSlotsBlankFragment(), "Parking Slots");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void adminData() {
        listData = new ArrayList<>();
        db_ref = FirebaseDatabase.getInstance().getReference("valets");
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(AdminPanel.this, "Data exist", Toast.LENGTH_SHORT).show();
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        if (!npsnapshot.getKey().contains("slots") && !npsnapshot.getKey().contains("complaints")&&!npsnapshot.getKey().contains("month")) {
                            adminDataList l = npsnapshot.getValue(adminDataList.class);
                            Log.v("value", l.getUsertype().toString());
                            if (l.getUsertype().contains("admin")) {
                                listData.add(l);
                                Log.v("adminlist", listData.get(0).getValetcnic().toString());
                            }
                        }
                    }
                    Intent i = new Intent(AdminPanel.this, AdminProfile.class);
                    i.putExtra("name", listData.get(0).getValetname());
                    i.putExtra("cnic", listData.get(0).getValetcnic());
                    i.putExtra("email", listData.get(0).getValetemail());
                    i.putExtra("mobile", listData.get(0).getValetmobile());
                    i.putExtra("dob", listData.get(0).getValetdob());
                    i.putExtra("father", listData.get(0).getValetfathername());
                    i.putExtra("homemobile", listData.get(0).getValetfamilycontact());
                    startActivity(i);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    /*
    public void updateStatus(String status){
        String savecurrentdate;
        String savecurrenttime;

        Calendar callfordate=Calendar.getInstance();
        SimpleDateFormat currentdate= new SimpleDateFormat("MMM dd, yyyy");
        savecurrentdate=currentdate.format(callfordate.getTime());

        Calendar callfortime=Calendar.getInstance();
        SimpleDateFormat currenttime= new SimpleDateFormat("hh:mm:ss a");
        savecurrenttime=currenttime.format(callfortime.getTime());

        Map currentstatusmap= new HashMap();
        currentstatusmap.put("time",savecurrenttime);
        currentstatusmap.put("date",savecurrentdate);
        currentstatusmap.put("type",status);

        db_ref.child(valetid).child("userstatus").updateChildren(currentstatusmap);
    }
*/
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.adminsearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setQueryHint("Search People");
        searchView.setOnQueryTextListener(this);
//        searchView.setIconified(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adminprofile:
                adminData();
                return true;

//                SearchView searchView=(SearchView) item.getActionView();
//                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                    @Override
//                    public boolean onQueryTextSubmit(String query) {
//
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onQueryTextChange(String newText) {
//                        return false;
//                    }
//                });


            case R.id.admincomplaints:
                complaints();
                return true;
            case R.id.logout:
                logoutadmin();
                return true;
            case R.id.adminreport:
                startActivity(new Intent(AdminPanel.this,Report.class));
                return true;
            case R.id.monthly:
                monthlyreport();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void monthlyreport()
    {           db_ref = FirebaseDatabase.getInstance().getReference("valets");
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 ArrayList<Integer> allcars=new ArrayList<>();
                ArrayList<String> allcarsdates=new ArrayList<>();
                for(DataSnapshot children : snapshot.getChildren())
                {
                    if(children.getKey().contains("month")){

                        for(DataSnapshot child:children.getChildren())
                        {   Log.v("child",child.getKey());
                            if(child.exists())
                            {
                                for (DataSnapshot parkedcar:child.getChildren())
                                {  Log.v("child",parkedcar.getKey());
                                   allcars.add(Integer.valueOf(parkedcar.getValue().toString()));
                                   allcarsdates.add(child.getKey());
                                   Log.v("cars",allcars.toString());
                                    Log.v("carsdates",allcarsdates.toString());
                                }
                            }
                        }
                    }
                }
                Integer i = Collections.max(allcars);
                int index=getIndexOfLargest(allcars);
                Log.v("cars index",""+index);

                String maxdate=allcarsdates.get(index);
                Log.v("maxdate",maxdate);


                Date date = new Date();
                SimpleDateFormat date_format = new SimpleDateFormat("YYYY:MM:dd");
                try {
                    date = date_format.parse(maxdate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(date);
                int goal= Integer.parseInt(String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));

                Log.v("day=",""+goal);
                String day="";
                switch (goal)
                {
                    case 0:
                        day="Monday";
                        break;
                    case 1:
                        day="Tuesday";
                        break;
                    case 2:
                        day="Wednesday";
                        break;
                    case 3:
                        day="Thrusday";
                        break;
                    case 4:
                        day="Friday";
                        break;
                    case 5:
                    day="Saturday";
                    break;
                    case 6:
                        day="Sunday";
                        break;
                }

            Log.v("day==",day);



                Log.v("maimum",""+i);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(AdminPanel.this);
                builder1.setMessage("peak Days of month " + day );
                builder1.setTitle("Peak Day of Month");
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void report() {
//        final int[] park = new int[1];
//        final int[] epark = new int[1];
//
//        DatabaseReference db_ref;
//        db_ref = FirebaseDatabase.getInstance().getReference("valets");
//
//        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                        if (!dataSnapshot.getKey().contains("complaints") && !dataSnapshot.getKey().contains("slots")&&!dataSnapshot.getKey().contains("month") && dataSnapshot.exists()) {
//                            Log.v("valueee", dataSnapshot.child("usertype").getValue().toString());
//                            if (dataSnapshot.child("usertype").getValue().equals("valet")) {
//                                park[0] = Integer.parseInt(dataSnapshot.child("parkedcars").getValue().toString());
//                                Log.v("parkedcars", "=" + park[0]);
//                                tpark = tpark + park[0];
//                                epark[0] = Integer.parseInt(dataSnapshot.child("exitcars").getValue().toString());
//                                texit = texit + epark[0];
////                           Log.v("totalentry","="+tpark);
//                                Log.v("totalexit", "=" + texit);
//
//                            }
//                        }
//                    }
//                }
//                Log.v("totalentry", "=" + tpark);
//
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(AdminPanel.this);
//                builder1.setMessage("Total Cars Parked By All Drivers :) " + tpark + "\nTotal Cars Exit By All Drivers :) " + texit);
//                builder1.setTitle("Report");
//                builder1.setIcon(R.mipmap.ic_logo_foreground);
//                builder1.setCancelable(true);
//
//                builder1.setPositiveButton(
//                        "Ok",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//
//                            }
//                        });
//
//                AlertDialog alert11 = builder1.create();
//                alert11.show();
//                tpark = 0;
//                texit = 0;
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//    }

    private void logoutadmin() {
        firebaseAuth.signOut();
        //      updateStatus("offline");
        SharedPrefrence.write(SharedPrefrence.user_email, "");
        SharedPrefrence.write(SharedPrefrence.admin_email, "");
        startActivity(new Intent(AdminPanel.this, LoginMainActivity.class));
        finish();

    }




    private void complaints() {
        startActivity(new Intent(AdminPanel.this, AdminComplaints.class));
        finish();
    }
//
//    @Override
//    protected void onStop() {
//        //    updateStatus("offline");
//        super.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        // updateStatus("offline");
//        super.onDestroy();
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.isEmpty())
        {
            fragment2.doSearch(" ");
        }
        else {
            fragment2.doSearch(newText);
        }
        return true;
    }



    public int getIndexOfLargest( ArrayList<Integer> array )
    {
        if ( array == null || array.size() == 0 ) return -1; // null or empty

        int largest = 0;
        for ( int i = 1; i < array.size(); i++ )
        {
            if ( array.get(i) > array.get(largest) ) largest = i;
        }
        return largest; // position of the first largest found
    }


}
