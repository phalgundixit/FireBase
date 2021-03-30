package com.example.phalguns.firebase;


import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.google.firebase.database.core.Context;

public class user  {

    private String VehicleNo;
    private String VehicleName;
    private String To;
    private String place;

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public void setVehicleName(String vehicleName) {
        VehicleName = vehicleName;
    }

    public void setTo(String to) {
        To = to;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public String getVehicleName() {
        return VehicleName;
    }

    public String getTo() {
        return To;
    }

    public String getPlace() {
        return place;
    }
}


