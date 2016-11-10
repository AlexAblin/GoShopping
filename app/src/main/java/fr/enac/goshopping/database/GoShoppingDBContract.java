package fr.enac.goshopping.database;

import android.provider.BaseColumns;

/**
 * Created by Geekette on 03/11/2016.
 */

public class GoShoppingDBContract {

    private GoShoppingDBContract(){}

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER ";
    private static final String COMMA_SEP = ",";

    public static final String TABLE_SHELF = "CREATE TABLE IF NOT EXISTS " + ShelfTable.TABLE_NAME
            + "(" + ShelfTable._ID + INT_TYPE + " PRIMARY KEY" + COMMA_SEP + ShelfTable.COLUMN_NAME_SHELF_NAME + TEXT_TYPE + ")";

    public static final String TABLE_SHOP = "CREATE TABLE IF NOT EXISTS " + ShopTable.TABLE_NAME + "(" + ShopTable._ID + INT_TYPE + " PRIMARY KEY" +
            COMMA_SEP + ShopTable.COLUMN_NAME_SHOP_NAME + TEXT_TYPE + COMMA_SEP + ShopTable.COLUMN_NAME_SHOP_ADRESS +
            COMMA_SEP + ShopTable.COLUMN_NAME_SHOP_CITY + TEXT_TYPE + COMMA_SEP +
            ShopTable.COLUMN_NAME_SHOP_POST_CODE + TEXT_TYPE + COMMA_SEP + ShopTable.COLUMN_NAME_SHOP_LAT + TEXT_TYPE + COMMA_SEP +
            ShopTable.COLUMN_NAME_SHOP_LONG + TEXT_TYPE + ")";

    public static final String TABLE_SHOPPING_LIST = "CREATE TABLE IF NOT EXISTS " + ShoppingList.TABLE_NAME + "(" + ShoppingList._ID + INT_TYPE + " PRIMARY KEY" +
            COMMA_SEP + ShoppingList.COLUMN_NAME_LIST_NAME + TEXT_TYPE + COMMA_SEP + ShoppingList.COLUMN_NAME_LIST_SHOP + TEXT_TYPE + COMMA_SEP +
            "constraint fk_list_shop FOREIGN KEY (" + ShoppingList.COLUMN_NAME_LIST_SHOP + ") REFERENCES " + ShopTable.TABLE_NAME + "(" + ShopTable._ID + "))";

    public static final String TABLE_ARTICLE_LIST = "CREATE TABLE IF NOT EXISTS " + ArticleTable.TABLE_NAME + "(" + ArticleTable._ID + INT_TYPE + " PRIMARY KEY" +
            COMMA_SEP + ArticleTable.COLUMN_NAME_ARTICLE_NAME + TEXT_TYPE + COMMA_SEP + ArticleTable.COLUMN_NAME_ARTICLE_SHELF + TEXT_TYPE + COMMA_SEP +
            "constraint fk_article_shelf FOREIGN KEY (" + ArticleTable.COLUMN_NAME_ARTICLE_SHELF +  ") REFERENCES " + ShelfTable.TABLE_NAME + "(" + ShelfTable._ID + "))";

    public static final String TABLE_SHOPPING_LIST_CONTENT = "CREATE TABLE IF NOT EXISTS " + ShoppingListContent.TABLE_NAME +"(" + ShoppingListContent.COLUMN_NAME_LIST_ID + INT_TYPE + COMMA_SEP +
            ShoppingListContent.COLUMN_NAME_PRODUCT_ID + INT_TYPE + COMMA_SEP + ShoppingListContent.COLUMN_NAME_QUANTITY + " INTEGER NOT NULL DEFAULT 1 " + COMMA_SEP +
            "CONSTRAINT fk_list FOREIGN KEY (" + ShoppingListContent.COLUMN_NAME_LIST_ID + ") REFERENCES " + ShoppingList.TABLE_NAME + "(" + ShoppingList._ID + ")" + COMMA_SEP +
            "CONSTRAINT fk_article FOREIGN KEY (" + ShoppingListContent.COLUMN_NAME_PRODUCT_ID + ") REFERENCES " + ArticleTable.TABLE_NAME + "(" + ArticleTable._ID + "))";

    public static final String TABLE_REMINDER = "CREATE TABLE IF NOT EXISTS " + ReminderTable.TABLE_NAME + "(" + ReminderTable.COLUMN_NAME_REMINDER_LIST + INT_TYPE + COMMA_SEP +
            ReminderTable.COLUMN_NAME_REMINDER_DATE + " DATETIME" + COMMA_SEP + "CONSTRAINT fk_list_reminder FOREIGN KEY (" + ReminderTable.COLUMN_NAME_REMINDER_LIST + ")" +
            " REFERENCES " + ShoppingList.TABLE_NAME + "(" + ShoppingList._ID + "))";

    public static class AppSettings{
        public static final String TABLE_NAME = "Settings";
        public static final String COLUMN_NAME_SETTING = "Setting";
        public static final String COLUMN_NAME_VALUE = "Value";
    }

    public static class ArticleTable implements BaseColumns{
        public static final String TABLE_NAME = "Articles";
        public static final String COLUMN_NAME_ARTICLE_NAME = "Article";
        public static final String COLUMN_NAME_ARTICLE_SHELF = "Shelf";
    }

    public static class ShopTable implements BaseColumns{
        public static final String TABLE_NAME = "Shops";
        public static final String COLUMN_NAME_SHOP_NAME = "Shop";
        public static final String COLUMN_NAME_SHOP_ADRESS = "Shop_Adress";
        public static final String COLUMN_NAME_SHOP_CITY = "Shop_City";
        public static final String COLUMN_NAME_SHOP_POST_CODE = "Shop_Post_Code";
        public static final String COLUMN_NAME_SHOP_LAT = "Shop_Lat";
        public static final String COLUMN_NAME_SHOP_LONG = "Shop_Long";
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

    public static class ShoppingListContent implements BaseColumns{
        public static final String TABLE_NAME = "ShoppingListContent";
        public static final String COLUMN_NAME_LIST_ID = "List_ID";
        public static final String COLUMN_NAME_PRODUCT_ID = "Product_ID";
        public static final String COLUMN_NAME_QUANTITY = "Quantity";
    }

    public static class ReminderTable implements BaseColumns{
        public static final String TABLE_NAME = "Reminders";
        public static final String COLUMN_NAME_REMINDER_LIST = "ListReminder";
        public static final String COLUMN_NAME_REMINDER_DATE = "DateReminder";
    }
}
