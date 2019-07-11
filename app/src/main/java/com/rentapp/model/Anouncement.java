package com.rentapp.model;

import com.google.firebase.auth.FirebaseUser;
import com.rentapp.App;

import java.util.Date;
import java.util.List;

public class Anouncement {
    private String title;
    private String yearOfCar;
    private String city;
    private int price;
    private String userID;
    private List<String> imagesUrl;

    public Anouncement(String title, String yearOfCar, String city, int price, String userID, List<String> imagesUrl) {
        this.title = title;
        this.yearOfCar = yearOfCar;
        this.city = city;
        this.price = price;
        this.userID = userID;
        this.imagesUrl = imagesUrl;
    }

    public Anouncement() {

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYearOfCar() {
        return yearOfCar;
    }

    public void setYearOfCar(String yearOfCar) {
        this.yearOfCar = yearOfCar;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getImagesUrl() {
        return imagesUrl;
    }

    public void setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }
}
