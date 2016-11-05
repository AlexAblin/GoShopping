package fr.enac.goshopping.database;

import android.provider.BaseColumns;

/**
 * Created by Geekette on 03/11/2016.
 */

public class GoShoppingDBContract {

    private GoShoppingDBContract(){}

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static final String TABLE_SHELF = "CREATE TABLE " + ShelfTable.TABLE_NAME
            + "(" + ShelfTable._ID + TEXT_TYPE + " PRIMARY KEY" + COMMA_SEP + ShelfTable.COLUMN_NAME_SHELF_NAME + TEXT_TYPE + ")";

    public static final String TABLE_SHOP = "CREATE TABLE " + ShopTable.TABLE_NAME + "(" + ShopTable._ID + TEXT_TYPE + " PRIMARY KEY" +
            COMMA_SEP + ShopTable.COLUMN_NAME_SHOP_NAME + TEXT_TYPE + COMMA_SEP + ShopTable.COLUMN_NAME_SHOP_ADRESS +
            COMMA_SEP + ShopTable.COLUMN_NAME_SHOP_CITY + TEXT_TYPE + COMMA_SEP +
            ShopTable.COLUMN_NAME_SHOP_POST_CODE + TEXT_TYPE +")";

    public static final String TABLE_SHOPPING_LIST = "CREATE TABLE " + ShoppingList.TABLE_NAME + "(" + ShoppingList._ID + TEXT_TYPE + " PRIMARY KEY" +
            COMMA_SEP + ShoppingList.COLUMN_NAME_LIST_NAME + TEXT_TYPE + COMMA_SEP + ShoppingList.COLUMN_NAME_LIST_SHOP + TEXT_TYPE + COMMA_SEP +
            "constraint fk_list_shop FOREIGN KEY (" + ShoppingList.COLUMN_NAME_LIST_SHOP + ") REFERENCES " + ShopTable.TABLE_NAME + "(" + ShopTable._ID + "))";

    public static final String TABLE_ARTICLE_LIST = "CREATE TABLE " + ArticleTable.TABLE_NAME + "(" + ArticleTable._ID + TEXT_TYPE + " PRIMARY KEY" +
            COMMA_SEP + ArticleTable.COLUMN_NAME_ARTICLE_NAME + TEXT_TYPE + COMMA_SEP + ArticleTable.COLUMN_NAME_ARTICLE_SHELF + TEXT_TYPE + COMMA_SEP +
            "constraint fk_article_shelf FOREIGN KEY (" + ArticleTable.COLUMN_NAME_ARTICLE_SHELF +  ") REFERENCES " + ShelfTable.TABLE_NAME + "(" + ShelfTable._ID + "))";

    //private static final String TABLE_ARTICLE;

    public static class ArticleTable implements BaseColumns{
        public static final String TABLE_NAME = "Articles";
        public static final String COLUMN_NAME_ARTICLE_NAME = "Article";
        public static final String COLUMN_NAME_ARTICLE_SHELF = "Shelf";
        public static final String COLUMN_NAME_ARTICLE_QTY = "Quantification";
    }

    public static class ShopTable implements BaseColumns{
        public static final String TABLE_NAME = "Shops";
        public static final String COLUMN_NAME_SHOP_NAME = "Shop";
        public static final String COLUMN_NAME_SHOP_ADRESS = "Shop_Adress";
        public static final String COLUMN_NAME_SHOP_CITY = "Shop_City";
        public static final String COLUMN_NAME_SHOP_POST_CODE = "Shop_Post_Code";
    }

    public static class ShelfTable implements BaseColumns{
        public static final String TABLE_NAME = "Shelf";
        public static final String COLUMN_NAME_SHELF_NAME = "Shelf";
    }

    public static class ShoppingList implements BaseColumns{
        public static final String TABLE_NAME = "ShoppingLists";
        public static final String COLUMN_NAME_LIST_NAME = "ListName";
        public static final String COLUMN_NAME_LIST_SHOP = "ListShop";
        public static final String COLUMN_NAME_LIST_REMINDER_TYPE = "ListReminder";
        //A completer
    }

    public static class Reminders implements BaseColumns{
        public static final String TABLE_NAME = "";
        public static final String COLUMN_NAME_REMINDER_NAME = "";
        public static final String COLUMN_NAME_REMINDER_TYPE = "";
        public static final String COLUMN_NAME_REMINDER_LOCATION = "";
        public static final String COLUMN_NAME_REMINDER_DATE = "";
    }
}
