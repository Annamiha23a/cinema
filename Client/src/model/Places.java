package model;

import java.io.Serializable;

public class Places implements Serializable {
    private String date;
    private String time;
    private String place;
    private String registrationTime;
    private String phoneNumber;
    private String comment;

    public Places(){
        this.date = "";
        this.time = "";
        this.place="";
        this.registrationTime="";
        this.phoneNumber="";
        this.comment="";
    }

    public Places(String date, String time, String place, String registrationTime, String phoneNumber, String comment){
        setDate(date);
        setTime(time);
        setPlace(place);
        setRegistrationTime(registrationTime);
        setPhoneNumber(phoneNumber);
        setComment(comment);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
