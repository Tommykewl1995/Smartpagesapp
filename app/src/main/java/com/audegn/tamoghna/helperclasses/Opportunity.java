package com.audegn.tamoghna.helperclasses;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Opportunity implements Parcelable{
    private Bitmap bit;
    private String name;
    private String tag;
    private String opportunity;
    private String createdat;
    private String expiresat;
    private String price;
    private String mobileno;
    private String opportunity_id;

    public Opportunity(){

    }

    public String getOpportunity_id() {
        return opportunity_id;
    }

    public Opportunity setOpportunity_id(String opportunity_id) {
        this.opportunity_id = opportunity_id;
        return this;
    }

    public String getMobileno() {
        return mobileno;
    }

    public Opportunity setMobileno(String mobileno) {
        this.mobileno = mobileno;
        return this;
    }

    public Bitmap getBit() {
        return bit;
    }

    public Opportunity setBit(Bitmap bit) {
        this.bit = bit;
        return this;
    }

    public String getName() {
        return name;
    }

    public Opportunity setName(String name) {
        this.name = name;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public Opportunity setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getOpportunity() {
        return opportunity;
    }

    public Opportunity setOpportunity(String opportunity) {
        this.opportunity = opportunity;
        return this;
    }

    public String getCreatedat() {
        return createdat;
    }

    public Opportunity setCreatedat(String createdat) {
        this.createdat = createdat;
        return this;
    }

    public String getExpiresat() {
        return expiresat;
    }

    public Opportunity setExpiresat(String expiresat) {
        this.expiresat = expiresat;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public Opportunity setPrice(String price) {
        this.price = price;
        return this;
    }

    public Opportunity(Parcel in){
        String[] data = new String[8];
        in.readStringArray(data);
        this.name = data[0];
        this.tag = data[1];
        this.opportunity = data[2];
        this.createdat = data[3];
        this.expiresat = data[4];
        this.price = data[5];
        this.mobileno = data[6];
        this.opportunity_id = data[7];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.name,
                this.tag, this.opportunity, this.createdat, this.expiresat, this.price,this.mobileno, this.opportunity_id});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Opportunity createFromParcel(Parcel in) {
            return new Opportunity(in);
        }

        public Opportunity[] newArray(int size) {
            return new Opportunity[size];
        }
    };

}
