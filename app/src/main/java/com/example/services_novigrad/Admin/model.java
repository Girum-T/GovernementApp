package com.example.services_novigrad.Admin;

public class model {
    String mUserName, madmin, mEmail, mPassword, mid;

    public model(){

    }
    public model(String mUserName, String madmin, String mEmail, String mPassword, String mid){
        this.mUserName = mUserName;
        this.madmin = madmin;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mid = mid;

    }

    public String getmId() {
        return mid;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmadmin() {
        return madmin;
    }

    public void setmadmin(String madmin) {
        this.madmin = madmin;
    }

}
