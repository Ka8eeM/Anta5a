package com.example.karim.anta5a.models;

public class User {
    private long id;
    String Name;
    String Email;
    String PhoneNumber;
    String Password;
    String Address;
    String Image;
    String Gender;

    public String getGender() {
        return Gender;
    }

    public User(long id, String name, String email, String phoneNumber, String password, String address, String gender , String image) {
        this.id = id;
        Name = name;
        Email = email;
        PhoneNumber = phoneNumber;
        Password = password;
        Address = address;
        Gender = gender;
        Image = image;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getPassword() {
        return Password;
    }

    public String getAddress() {
        return Address;
    }

    public String getImage() {
        return Image;
    }
}
