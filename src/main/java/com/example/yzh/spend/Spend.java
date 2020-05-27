package com.example.yzh.spend;

import java.util.Date;
import java.util.UUID;

public class Spend {

    private UUID mId;
    private String mType;
    private Date mDate;
    private String mCost;
    private String mDetail;
    private String mAddress;


    public Spend(){
        this(UUID.randomUUID());
    }

    public Spend(UUID id){
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public Date getDate() {

        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;

    }

       public String getCost() {
        return mCost;
    }

    public void setCost(String cost) {
        mCost = cost;
    }

    public String getDetail(){
        return mDetail;
    }
    public void setDetail(String detail){
        mDetail = detail;
    }
    public String getAddress(){
        return mAddress;
    }
    public void setAddress(String address){
        mAddress = address;
    }


}
