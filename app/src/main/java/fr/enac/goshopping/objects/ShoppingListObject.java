package fr.enac.goshopping.objects;

import java.util.ArrayList;

/**
 * Created by Geekette on 05/11/2016.
 */

public class ShoppingListObject {

    private String _ID;
    private String list_name;
    private String shop;


    public ShoppingListObject(String _ID, String list_name, String shop) {
        this._ID = _ID;
        this.list_name = list_name;
        this.shop = shop;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

}
