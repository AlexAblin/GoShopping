package fr.enac.goshopping.objects;

/**
 * Created by Geekette on 03/11/2016.
 */

public class Product {

    private String name;
    private String category;
    private int quantity;

    public Product(String name,String category,int quantity){
        this.name=name;
        this.category=category;
        this.quantity=quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() { return quantity; }

    public void setCategory(String category) {
        this.category = category;
    }

}
