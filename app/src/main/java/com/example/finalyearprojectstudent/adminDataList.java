package com.example.finalyearprojectstudent;

public class adminDataList {
    private String valetname;
    private String usertype;
    private String valetcnic;
    private String valetemail;
    private String valetmobile;
    private String valetfamilycontact;
    private String valetfathername;
    private String valetdob;

    public adminDataList(String valetname, String usertype, String valetcnic, String valetemail, String valetmobile, String valetfamilycontact, String valetfathername, String valetdob) {
        this.valetname = valetname;
        this.usertype = usertype;
        this.valetcnic = valetcnic;
        this.valetemail = valetemail;
        this.valetmobile = valetmobile;
        this.valetfamilycontact = valetfamilycontact;
        this.valetfathername = valetfathername;
        this.valetdob = valetdob;
    }

    public String getValetcnic() {
        return valetcnic;
    }

    public String getValetemail() {
        return valetemail;
    }

    public String getValetmobile() {
        return valetmobile;
    }

    public String getValetfamilycontact() {
        return valetfamilycontact;
    }

    public String getValetfathername() {
        return valetfathername;
    }

    public String getValetdob() {
        return valetdob;
    }

    public adminDataList()
    {
    }

    public String getValetname() {
        return valetname;
    }
    public String getUsertype(){return usertype;}




}

