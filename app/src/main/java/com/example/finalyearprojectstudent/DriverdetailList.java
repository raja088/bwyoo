package com.example.finalyearprojectstudent;

public class DriverdetailList {
    private String valetname, valetcnic, valetdob, valetemail,  valetaddress, valetfathername, valetmobile, valetenteredcars, valetexitedcars, valetstatus, valetsalary,valetfamilytcontact,parkedcars,exitcars,valetpassword,valetid;

    public DriverdetailList() {
    }

    public DriverdetailList(String valetid,String valetpassword,String valetname,String exitcars, String parkedcars, String valetcnic, String valetdob, String valetemail, String valetaddress, String valetfathername, String valetmobile, String valetenteredcars, String valetexitedcars, String valetstatus, String valetsalary,String valetfamilytcontact) {

        this.valetid=valetid;
        this.valetpassword=valetpassword;
        this.valetname = valetname;
        this.parkedcars=parkedcars;
        this.exitcars=exitcars;
        this.valetcnic = valetcnic;
        this.valetdob = valetdob;
        this.valetemail = valetemail;
        this.valetaddress = valetaddress;
        this.valetfathername = valetfathername;
        this.valetmobile = valetmobile;
        this.valetenteredcars = valetenteredcars;
        this.valetexitedcars = valetexitedcars;
        this.valetstatus = valetstatus;
        this.valetsalary = valetsalary;
        this.valetfamilytcontact=valetfamilytcontact;
    }

    public String getValetid() {
        return valetid;
    }

    public String getValetpassword() {
        return valetpassword;
    }

    public String getValetname() {
        return valetname;
    }

    public String getParkedcars() {
        return parkedcars;
    }

    public String getExitcars() {
        return exitcars;
    }

    public String getvaletfamilytcontact() {
        return valetfamilytcontact;
    }

    public String getValetCnic() {
        return valetcnic;
    }

    public String getValetdob() {
        return valetdob;
    }

    public String getValetemail() {
        return valetemail;
    }

    public String getValetaddress() {
        return valetaddress;
    }

    public String getValetfathername() {
        return valetfathername;
    }

    public String getvaletmobile() {
        return valetmobile;
    }

    public String getValetenteredcars() {
        return valetenteredcars;
    }

    public String getValetexitedcars() {
        return valetexitedcars;
    }

    public String getValetstatus() {
        return valetstatus;
    }

    public String getValetsalary() {
        return valetsalary;
    }
}

