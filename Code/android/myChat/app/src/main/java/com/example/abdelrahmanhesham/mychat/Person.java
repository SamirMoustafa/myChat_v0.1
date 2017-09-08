package com.example.abdelrahmanhesham.mychat;

import java.io.Serializable;

/**
 * Created by Samir Moustafa on 5/3/2017.
 */

public class Person implements Serializable {

    private String name;
    private double age;
    private String ID;
    private String phoneNo;
    private String eMail;
    private String pass;
    private String photo;

    public Person() {
        this("null", 0, "null", "0", "null@null.null", "null", "null");
    }

    public Person(String name, double age, String ID, String phoneNo, String eMail, String pass, String photo) {
        this.name = name;
        this.age = age;
        this.ID = ID;
        this.phoneNo = phoneNo;
        this.eMail = eMail;
        this.pass = pass;
        this.photo = photo;
    }

    public void updatePesrson(String name, int age, String ID, String phoneNo, String eMail, String address) {
        this.name = name;
        this.age = age;
        this.ID = ID;
        this.phoneNo = phoneNo;
        this.eMail = eMail;
        this.photo = address;
    }

    public String getName() {
        return this.name;
    }

    public double getAge() {
        return this.age;
    }

    public String getID() {
        return this.ID;
    }

    public String getPhone() {
        return this.phoneNo;
    }

    public String getEMail() {
        return this.eMail;
    }

    public String getPass() {
        return pass;
    }

    public String getPhoto() {
        return this.photo;
    }

}
