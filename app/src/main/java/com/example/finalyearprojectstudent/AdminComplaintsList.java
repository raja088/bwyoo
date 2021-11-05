package com.example.finalyearprojectstudent;

public class AdminComplaintsList {
    public String complaint;
    public String name;
    public String status;
    public String phone;

    public  AdminComplaintsList(String complaint,String status,String phone)
    {
        this.complaint=complaint;
        this.status=status;
        this.phone=phone;
    }
    public AdminComplaintsList(){}

    public void setName(String name )
    {
        this.name=name;
    }
    public  String getName()
    {
        return name;
    }
    public String getComplaint() {
        return complaint;
    }

    public String getStatus() {
        return status;
    }

    public String getPhone() {
        return phone;
    }
}
