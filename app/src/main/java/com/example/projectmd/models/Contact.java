package com.example.projectmd.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    private String name;
    private String opdirnumber;
    private String device;
    private String opdwardnumber;
   // private  String profileImage; //file://document/images/image.png

    public Contact(String name, String opdirnumber, String device, String opdwardnumber) {
        this.name = name;
        this.opdirnumber = opdirnumber;
        this.device = device;
        this.opdwardnumber = opdwardnumber;
       // this.profileImage = profileImage;
    }

    protected Contact(Parcel in) {
        name = in.readString();
        opdirnumber = in.readString();
        device = in.readString();
        opdwardnumber = in.readString();
        //profileImage = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpdirnumber() {
        return opdirnumber;
    }

    public void setOpdirnumber(String opdirnumber) {
        this.opdirnumber = opdirnumber;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getOpdwardnumber() {
        return opdwardnumber;
    }

    public void setOpdwardnumber(String opdwardnumber) {
        this.opdwardnumber = opdwardnumber;
    }

    /*public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }*/

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", opdirnumber='" + opdirnumber + '\'' +
                ", device='" + device + '\'' +
                ", opdwardnumber='" + opdwardnumber + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(opdirnumber);
        dest.writeString(device);
        dest.writeString(opdwardnumber);
        //dest.writeString(profileImage);
    }
}

