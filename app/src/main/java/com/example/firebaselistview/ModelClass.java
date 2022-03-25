package com.example.firebaselistview;

public class ModelClass {
    String id,name,phoneNumber,bloodGroup;

    public ModelClass() {
    }

    public ModelClass(String id, String name, String phoneNumber, String bloodGroup) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bloodGroup = bloodGroup;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
