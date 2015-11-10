package com.info212.expresscaff;

/**
 * Created by Edvard on 02.11.2015.
 */
public class Favorite {

    private String userName;
    private String nameShop;
    private  String addressShop;
    private Double latitude;
    private Double longitude;
    private String phoneNr;
    private int coffeePrice;
    private  int totalSum;
    private int barcode;
    private String expireDate;



    public String getUsername(){
        return userName;
    }
    public void setUsername(String userName){
        this.userName = userName;
    }

    public String getNameShop(){
        return nameShop;
    }
    public void setNameShop(String nameShop){
        this.nameShop = nameShop;
    }


    public String getAddress(){
        return addressShop;
    }
    public void setAddress(String addressShop){
        this.addressShop = addressShop;
    }

    public Double getLatitude(){
        return latitude;
    }
    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }

    public Double getLongitude(){
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPhoneNr(){
        return phoneNr;
    }
    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }


    }


