package com.example.finalyearprojectstudent;

public class Driverslist {
    private String valetname;
    private String usertype;
    private String valetemail;

    public Driverslist()
{
}

    public String getValetname() {
        return valetname;
    }
    public String getUsertype(){return usertype;}
    public String getValetemail(){return valetemail;}


    public Driverslist(String valetname,String usertype,String valetemail)
    {
        this.valetname = valetname;
        this.usertype=usertype;
        this.valetemail=valetemail;
    }


}

