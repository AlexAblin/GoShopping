package fr.enac.goshopping.objects;

/**
 * Created by Geekette on 04/11/2016.
 */

public class Shop {

    private String _id;
    private String name;
    private String adress;
    private String city;
    private String postcode;

    public Shop(String _id, String name, String adress, String city, String postcode){
        this._id = _id;
        this.name = name;
        this.adress = adress;
        this.city = city;
        this.postcode = postcode;
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

    public String toString(){
        return "{" + this.name + "} (" + this.adress + " "+ this.city + " " + this.postcode + ")";
    }
}
