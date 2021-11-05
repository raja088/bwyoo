package com.example.finalyearprojectstudent;

public class valet {
    String valetid;
    String valetname;
    String valetcnic;
    String valetdob;
    String valetfathername;
    String valetmobile;
    String valetfamilytcontact;
    String valetaddress;
    String valetemail;
    String valetpassword;
    String usertype;
    String parkedcars;
    String exitcars;
    String charges;


    public valet(){

    }
    public valet(String valetid,String exitcars, String charges,String parkedcars, String valetname, String valetcnic, String valetdob, String valetfathername, String valetmobile,
                    String valetfamilytcontact, String valetaddress, String valetemail, String valetpassword, String usertype)
    {
        this.valetid=valetid;
        this.parkedcars=parkedcars;
        this.exitcars=exitcars;
        this.usertype=usertype;
        this.valetname=valetname;
        this.valetcnic=valetcnic;
        this.valetdob=valetdob;
        this.valetfathername=valetfathername;
        this.valetmobile=valetmobile;
        this.valetfamilytcontact=valetfamilytcontact;
        this.valetaddress=valetaddress;
        this.valetemail=valetemail;
        this.valetpassword=valetpassword;
        this.charges=charges;
    }

    public String getCharges() {
        return charges;
    }

    public String getUsertype() {
        return usertype;
    }

    public String getParkedcars() {
        return parkedcars;
    }

    public String getExitcars() {
        return exitcars;
    }

    public String getValetpassword() {
        return valetpassword;
    }

    public String getValetemail() {
        return valetemail;
    }

    public String getValetaddress() {
        return valetaddress;
    }

    public String getValetfamilytcontact() {
        return valetfamilytcontact;
    }

    public String getValetmobile() {
        return valetmobile;
    }

    public String getValetfathername() {
        return valetfathername;
    }

    public String getValetdob() {
        return valetdob;
    }

    public String getValetcnic() {
        return valetcnic;
    }

    public String getValetname() {
        return valetname;
    }

    public String getValetid() {
        return valetid;
    }
}