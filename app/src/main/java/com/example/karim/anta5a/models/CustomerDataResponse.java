package com.example.karim.anta5a.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDataResponse {

    @SerializedName("Activated")
    @Expose
    private boolean Activated;
    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Gender")
    @Expose
    private String Gender;
    @SerializedName("Id")
    @Expose
    private long Id;
    @SerializedName("PhoneNumber")
    @Expose
    private String PhoneNumber;
    @SerializedName("RegiterationDate")
    @Expose
    private String RegiterationDate;
    @SerializedName("Image")
    @Expose
    private String Image;

    public CustomerDataResponse(boolean activated, String address, String email, String name, String gender, long id, String phonenumber, String regiterationDate, String image) {
        this.Activated = activated;
        this.Address = address;
        this.Email = email;
        this.Gender = gender;
        this.Id = id;
        this.Name = name;
        this.PhoneNumber = phonenumber;
        RegiterationDate = regiterationDate;
        this.Image = image;
    }

    public boolean isActivated() {
        return this.Activated;
    }

    public String getAddress() {
        return Address;
    }

    public String getEmail() {
        return Email;
    }

    public String getName() {
        return Name;
    }

    public String getGender() {
        return Gender;
    }

    public long getId() {
        return Id;
    }

    public String getPhonenumber() {
        return PhoneNumber;
    }

    public String getRegiterationDate() {
        return RegiterationDate;
    }

    public String getImage() {
        return Image;
    }
}