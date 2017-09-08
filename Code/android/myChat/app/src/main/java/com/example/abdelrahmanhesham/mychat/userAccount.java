package com.example.abdelrahmanhesham.mychat;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Samir Moustafa on 5/3/2017.
 */
public class userAccount extends Person implements Serializable {

    List<userAccount> friends;

    public userAccount(String name, double age, String ID, String phoneNo, String eMail, String pass, String photo) {
        super(name, age, ID, phoneNo, eMail, pass, photo);
    }


    public String getName() {
        return super.getName();
    }

    public double getAge() {
        return super.getAge();
    }

    public String getID() {
        return super.getID();
    }

    public String getPhone() {
        return super.getPhone();
    }

    public String getEMail() {
        return super.getEMail();
    }

    public String getPass() {
        return super.getPass();
    }

    public String getPhoto() {
        return super.getPhoto();
    }

}
