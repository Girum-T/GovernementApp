package com.example.services_novigrad;

public class SuccursaleList {

    private String mName;
    private String mAddress;
    private String mPhone;
    private String mDescription;

    private String mMonday;
    private String  mTuesday;
    private String mWednesday;
    private String mThursday;

    private String  mFriday;
    private String mSaturday;
    private String  mSunday;
    private String id;

    public SuccursaleList(){
        //empty constructor needed
    }

    public String getmMonday() {
        return mMonday;
    }

    public String getmTuesday() {
        return mTuesday;
    }

    public String getmWednesday() {
        return mWednesday;
    }

    public String getmThursday() {
        return mThursday;
    }

    public String getmFriday() {
        return mFriday;
    }

    public String getmSaturday() {
        return mSaturday;
    }

    public String getmSunday() {
        return mSunday;
    }

    public SuccursaleList(String mName, String id, String mAddress, String mPhone, String mDescription, String mMonday, String mTuesday, String mWednesday, String mThursday, String mFriday , String mSaturday , String mSunday){

        this.mName = mName;
        this.id=id;
        this.mAddress=mAddress;
        this.mPhone=mPhone;
        this.mDescription = mDescription;

        this.mMonday = mMonday;
        this.mTuesday = mTuesday;
        this.mWednesday = mWednesday;
        this.mThursday = mThursday;
        this.mFriday = mFriday;
        this.mSaturday = mSaturday;
        this.mSunday = mSunday;

    }





    public String getmName() {
        return mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getId() {
        return id;
    }}


