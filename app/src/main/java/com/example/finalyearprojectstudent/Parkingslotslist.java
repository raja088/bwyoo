package com.example.finalyearprojectstudent;

public class Parkingslotslist  implements Comparable<Parkingslotslist> {
    public String slot;
    public String status;
    public String cmobile;
    public String carnumber;
    public String parktime;
    public String valetname;

    public Parkingslotslist(){
    }

    public String getslot(){
        return slot;
    }
    public String getStatus(){
        return status;
    }
    public String getCmobile(){
        return cmobile;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public String getParktime() {
        return parktime;
    }

    public String getValetname() {
        return valetname;
    }

    public void setParkingID(String slot, String status, String cmobile, String carnumber, String parktime, String valetname)
    {
        this.slot = slot;
        this.status=status;
        this.cmobile=cmobile;
        this.carnumber=carnumber;
        this.parktime=parktime;
        this.valetname=valetname;
    }

    @Override
    public int compareTo(Parkingslotslist f) {
        int slot=Integer.parseInt(this.slot);
        if (slot > Integer.parseInt(f.slot)) {
            return 1;
        }
        else if (slot < Integer.parseInt(f.slot)) {
            return -1;
        }
        else {
            return 0;
        }

    }
}
