package com.example.karim.anta5a.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainServices {
    @SerializedName("Id")
    @Expose
    long Id;
    @SerializedName("ServiceName")
    @Expose
    String ServiceName;

    public MainServices(long id, String serviceName) {
        Id = id;
        ServiceName = serviceName;
    }

    public long getId() {
        return Id;
    }

    public String getServiceName() {
        return ServiceName;
    }
}
