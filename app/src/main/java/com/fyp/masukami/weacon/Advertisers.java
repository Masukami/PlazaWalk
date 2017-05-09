package com.fyp.masukami.weacon;

import android.graphics.Bitmap;

/**
 * Created by Suhail on 5/7/2017.
 */

public class Advertisers {

    private String name, productName;
    private String description;
    private String address;
    private Bitmap pathwayImage[];
    private Bitmap logo;

    public Advertisers(){

    }

    public Advertisers(String name, String productName, String description, String address, Bitmap[] pathwayImage, Bitmap logo) {
        this.name = name;
        this.productName = productName;
        this.description = description;
        this.address = address;
        this.pathwayImage = pathwayImage;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bitmap[] getPathwayImage() {
        return pathwayImage;
    }

    public void setPathwayImage(Bitmap[] pathwayImage) {
        this.pathwayImage = pathwayImage;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }
}
