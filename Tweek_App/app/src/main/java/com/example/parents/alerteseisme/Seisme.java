package com.example.parents.alerteseisme;

/**
 * Seisme permet de générer des Objets Seismes contenant 11 paramètres présents dans le fichier pointé par url (voir le lien dans
 * MainActivity)
 */
import android.os.Parcel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class Seisme implements Serializable  {

    public Seisme(String title, String type, String place, String alert, String status, String code, long timeEpoch, String coordinates,String mag) {
        this.title = title;
        this.type = type;
        this.place = place;
        this.alert = alert;
        this.status = status;
        this.code = code;
        this.timeEpoch = timeEpoch;
        this.coordinates = coordinates;
        this.mag = mag;
        setLatitudeLongitude(coordinates);
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public String getAlert() {
        return alert;
    }
    public void setAlert(String alert) {
        this.alert = alert;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public long getTime() {
        return timeEpoch;
    }
    public void setTime(int time) {
        this.timeEpoch = time;
    }
    public String getMag() {
        return mag;
    }
    public void setMag(String getMag) {
        this.mag = mag;
    }

    public String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.FRANCE);
        return simpleDateFormat.format(new Date(timeEpoch));
    }

    public String getCoordinates(){return coordinates;}

    public void setLatitudeLongitude(String coordinates){
        String temp = coordinates.substring(1,coordinates.length()-1);
        String[] ary = temp.split(",");
        longitude=Float.valueOf(ary[0]);
        latitude=Float.valueOf(ary[1]);
    }

    public float getLatitude(){return latitude;}

    public float getLongitude(){return longitude;}

    @Override
    public String toString() {

        return "Seisme{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", place='" + place + '\'' +
                ", alert='" + alert + '\'' +
                ", status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", time=" + getDate() +
                ", mag=" + getMag() +
                '}';
    }



    private String title;
    private String type;
    private String place;
    private String alert;
    private String status;
    private String code;
    private long timeEpoch;
    private String coordinates;
    private float latitude;
    private float longitude;
    private String mag;


}