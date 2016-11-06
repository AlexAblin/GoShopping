package fr.enac.goshopping.objects;

/**
 * Created by Geekette on 03/11/2016.
 */

public class Product {
    private String _id;
    private String name;
    private String category;
    private String quantity;

    public Product(String _id,String name,String category,String quantity){
        this._id = _id;
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

    public String getQuantity() { return quantity; }

    public void setCategory(String category) {
        this.category = category;
    }

}
