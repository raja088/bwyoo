package com.example.finalyearprojectstudent;

public class ModelReport {

    String valetname,charges,exitcars,parkedcars,usertype;

    public ModelReport(String valetname, String charges, String exitcars, String parkedcars, String usertype) {
        this.valetname = valetname;
        this.charges = charges;
        this.exitcars = exitcars;
        this.parkedcars = parkedcars;
        this.usertype = usertype;
    }

    public String getUsertype() {
        return usertype;
    }

    public String getValetname() {
        return valetname;
    }

    public String getCharges() {
        return charges;
    }

    public String getExitcars() {
        return exitcars;
    }

    public String getParkedcars() {
        return parkedcars;
    }
}
