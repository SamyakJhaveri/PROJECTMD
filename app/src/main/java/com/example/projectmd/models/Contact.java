package com.example.projectmd.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    private String name;
    private String opdirnumber;
    private String form1;
    private String form2;
    private String opdwardnumber;
   // private  String profileImage; //file://document/images/image.png

    public Contact(String name, String opdirnumber, String form1, String form2, String opdwardnumber) {
        this.name = name;
        this.opdirnumber = opdirnumber;
        this.form1 = form1;
        this.form2 = form2;
        this.opdwardnumber = opdwardnumber;
       // this.profileImage = profileImage;
    }

    protected Contact(Parcel in) {
        name = in.readString();
        opdirnumber = in.readString();
        form1 = in.readString();
        form2 = in.readString();
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

    public String getForm1() {
        return form1;
    }

    public void setForm1(String form1) {
        this.form1 = form1;
    }
    public String getForm2() {
        return form2;
    }

    public void setForm2(String form2) {
        this.form2 = form2;
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
                ", form1='" + form1 + '\'' +
                ", form2='" + form2 + '\'' +
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
        dest.writeString(form1);
        dest.writeString(form2);
        dest.writeString(opdwardnumber);
        //dest.writeString(profileImage);
    }
}

