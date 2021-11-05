package com.example.finalyearprojectstudent;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public class SharedPrefrence
{
    public static String user_email="user_email";
    public static String password="password";
    public static String type="type";
    public static String admin_email="admin_email";
    public static String driverid="driverid";
    public static String parkedcars="parkedcars";
    public static String cmobile="CutomerMobile";
    public static String exitcars="Exitcars";
    public static String valetname="valetname";
    public static String carnumber="carnumber";
    public static String mCars="monthlycars";
    public static String phone="phone";
    public static String charges="charges";
    private static SharedPreferences mSharedPref;


    public SharedPrefrence()
    {

    }

    public static void init(Context context)
    {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public static boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }


    public static Long read(String key, long defValue) {
        return mSharedPref.getLong(key, defValue);
    }

    public static void write(String key, long value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putLong(key, value).apply();
    }
}
