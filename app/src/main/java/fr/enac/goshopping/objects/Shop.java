package fr.enac.goshopping.objects;

import java.util.ArrayList;

/**
 * Created by Geekette on 04/11/2016.
 */

public class Shop {

    private String _id;
    private String name;
    private String adress;
    private String city;
    private String postcode;
    private double latitude;
    private double longitude;
    private boolean activated;
    private ArrayList<ShoppingListObject> associatedList;

    public Shop(String _id, String name, String adress, String city, String postcode) {
        this._id = _id;
        this.name = name;
        this.adress = adress;
        this.city = city;
        this.postcode = postcode;
        this.activated= false;
    }

    public Shop(String _id, String name, String adress, String city, String postcode, double latitude, double longitude) {
        this(_id, name, adress, city, postcode);
        this.latitude = latitude;
        this.longitude = longitude;
        this.activated=false;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String toString() {
        return "{" + this.name + "} (" + this.adress + " " + this.city + " " + this.postcode + ")";
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public ArrayList<ShoppingListObject> getAssociatedList() { return associatedList; }
}
