package com.rentapp.model;

import java.io.Serializable;
import java.util.List;

public class Anouncement implements Serializable {
    private String brand;
    private String yearOfCar;
    private String city;
    private String type;
    private int price;
    private String userID;
    private List<String> imagesUrl;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Anouncement(String brand, String yearOfCar, String city, String type, int price, String userID, List<String> imagesUrl) {
        this.brand = brand;
        this.yearOfCar = yearOfCar;
        this.city = city;
        this.type = type;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
