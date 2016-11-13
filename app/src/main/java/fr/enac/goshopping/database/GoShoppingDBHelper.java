package fr.enac.goshopping.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

import fr.enac.goshopping.objects.Product;
import fr.enac.goshopping.objects.Reminder;
import fr.enac.goshopping.objects.Shop;
import fr.enac.goshopping.objects.ShoppingListObject;

/**
 * Created by Geekette on 04/11/2016.
 */

public class GoShoppingDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "GoShopping.db";

    private Context myContext;

    public GoShoppingDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //On supprime toute les tables (pour essai et modification pendant dévellopement)
        /*sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GoShoppingDBContract.ShopTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GoShoppingDBContract.ShelfTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GoShoppingDBContract.ShoppingList.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GoShoppingDBContract.ArticleTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GoShoppingDBContract.ShoppingListContent.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GoShoppingDBContract.ReminderTable.TABLE_NAME);*/

        //On crée les tables
        sqLiteDatabase.execSQL(GoShoppingDBContract.APP_SETTINGS);
        sqLiteDatabase.execSQL(GoShoppingDBContract.TABLE_SHOP);
        sqLiteDatabase.execSQL(GoShoppingDBContract.TABLE_SHELF);
        sqLiteDatabase.execSQL(GoShoppingDBContract.TABLE_SHOPPING_LIST);
        sqLiteDatabase.execSQL(GoShoppingDBContract.TABLE_ARTICLE_LIST);
        sqLiteDatabase.execSQL(GoShoppingDBContract.TABLE_SHOPPING_LIST_CONTENT);
        sqLiteDatabase.execSQL(GoShoppingDBContract.TABLE_REMINDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<Shop> getShops() {
        ArrayList<Shop> toReturn = new ArrayList<Shop>();
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                GoShoppingDBContract.ShopTable._ID,
                GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_NAME,
                GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_ADRESS,
                GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_CITY,
                GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_POST_CODE,
                GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_LAT,
                GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_LONG
        };
        String sortOrder = GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_NAME + " ASC";
        Cursor c = db.query(
                GoShoppingDBContract.ShopTable.TABLE_NAME,// The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        Shop toAdd;
        if (c.moveToFirst()) {
            do {
                toAdd = new Shop(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
                toReturn.add(toAdd);
            } while (c.moveToNext());
        }
        return toReturn;
    }

    public long addShop(Shop shop) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //Conteneur d'un tuple à insérer
        ContentValues values = new ContentValues();

        //On lie les données du magasin au conteneur
        values.put(GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_NAME, shop.getName());
        values.put(GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_ADRESS, shop.getAdress());
        values.put(GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_CITY, shop.getCity());
        values.put(GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_POST_CODE, shop.getPostcode());
        values.put(GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_LAT, shop.getLatitude());
        values.put(GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_LONG, shop.getLongitude());

        //On insère le tuple
        return sqLiteDatabase.insert(GoShoppingDBContract.ShopTable.TABLE_NAME, null, values);
    }

    public int deleteShop(String name) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("shops", "shop=?", new String[]{name});
    }

    public ArrayList<ShoppingListObject> getShoppingLists() {
        ArrayList<ShoppingListObject> toReturn = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                GoShoppingDBContract.ShoppingList._ID,
                GoShoppingDBContract.ShoppingList.COLUMN_NAME_LIST_NAME,
                GoShoppingDBContract.ShoppingList.COLUMN_NAME_LIST_SHOP
        };
        String sortOrder = GoShoppingDBContract.ShoppingList.COLUMN_NAME_LIST_NAME + " ASC";
        Cursor c = db.query(
                GoShoppingDBContract.ShoppingList.TABLE_NAME,// The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        ShoppingListObject toAdd;

        if (c.moveToFirst()) {
            do {
                System.out.println("Sorti de la DB " + c.getString(0));
                toAdd = new ShoppingListObject(c.getString(0), c.getString(1), "magasin");
                toReturn.add(toAdd);
            } while (c.moveToNext());
        }
        return toReturn;
    }

    public void updateShoppingListName(String newName){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues args = new ContentValues();
        args.put("ListName",newName);
        db.update("ShoppingLists",args,null,null);
    }

    public long addShoppingList(ShoppingListObject list) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //Conteneur d'un tuple à insérer
        ContentValues values = new ContentValues();

        //On lie les données du magasin au conteneur
        values.put(GoShoppingDBContract.ShoppingList.COLUMN_NAME_LIST_NAME, list.getList_name());
        values.put(GoShoppingDBContract.ShoppingList.COLUMN_NAME_LIST_SHOP, list.getShop());
        //values.put(GoShoppingDBContract.ShoppingList.COLUMN_NAME_SHOP_CITY, shop.getCity());

        //On insère le tuple
        long toReturn = sqLiteDatabase.insert(GoShoppingDBContract.ShoppingList.TABLE_NAME, null, values);
        System.out.println("Output de la DB su la création d'une shopping list : " + toReturn);
        return toReturn;
    }

    public int deleteShoppingList(ShoppingListObject shopList) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("shoppingLists", "ListName=?", new String[]{shopList.getList_name()});
    }

    public ArrayList<Product> getArticles() {
        ArrayList<Product> toReturn = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                GoShoppingDBContract.ArticleTable._ID,
                GoShoppingDBContract.ArticleTable.COLUMN_NAME_ARTICLE_NAME,
                GoShoppingDBContract.ArticleTable.COLUMN_NAME_ARTICLE_SHELF,
                // GoShoppingDBContract.ArticleTable.COLUMN_NAME_ARTICLE_QTY
        };
        String sortOrder = GoShoppingDBContract.ArticleTable.COLUMN_NAME_ARTICLE_NAME + " ASC";
        Cursor c = db.query(
                GoShoppingDBContract.ArticleTable.TABLE_NAME,// The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        Product toAdd;
        if (c.moveToFirst()) {
            do {
                toAdd = new Product(null, c.getString(0), c.getString(1), "1");
                toReturn.add(toAdd);
            } while (c.moveToNext());
        }
        return toReturn;
    }

    public ArrayList<Product> getArticles(String list_id) {
        ArrayList<Product> toReturn = new ArrayList<>();
        System.out.println("Gestion de la base de donnée : valeur recu par getArticle " + list_id);
        String[] whereArgs = new String[]{list_id};
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                GoShoppingDBContract.ShoppingListContent.COLUMN_NAME_LIST_ID,
                GoShoppingDBContract.ShoppingListContent.COLUMN_NAME_PRODUCT_ID,
                GoShoppingDBContract.ShoppingListContent.COLUMN_NAME_QUANTITY,
        };
        String sortOrder = GoShoppingDBContract.ShoppingListContent.COLUMN_NAME_PRODUCT_ID + " ASC";
        Cursor c = db.query(
                GoShoppingDBContract.ShoppingListContent.TABLE_NAME,// The table to query
                projection,                               // The columns to return
                "List_ID=?",                               // The columns for the WHERE clause
                whereArgs,                                 // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        Product toAdd;
        if (c.moveToFirst()) {
            do {
                toAdd = new Product(c.getString(0), c.getString(1), "", c.getString(2));
                toReturn.add(toAdd);
            } while (c.moveToNext());
        }
        return toReturn;
    }

    public ArrayList<Product> getListContent(String listId) {
        ArrayList<Product> toReturn = new ArrayList<>();
        String myQuery = "SELECT " + GoShoppingDBContract.ArticleTable._ID + "," + GoShoppingDBContract.ArticleTable.COLUMN_NAME_ARTICLE_NAME +
                "," + GoShoppingDBContract.ShoppingListContent.COLUMN_NAME_QUANTITY + " FROM " + GoShoppingDBContract.ShoppingListContent.TABLE_NAME + ", " +
                GoShoppingDBContract.ArticleTable.TABLE_NAME + " WHERE " + GoShoppingDBContract.ShoppingListContent.COLUMN_NAME_PRODUCT_ID + " = " +
                GoShoppingDBContract.ArticleTable._ID + " AND " + GoShoppingDBContract.ShoppingListContent.COLUMN_NAME_LIST_ID + " = " + listId;
        Cursor c = getReadableDatabase().rawQuery(myQuery, null);
        Product toAdd;
        if (c.moveToFirst()) {
            do {
                toAdd = new Product(c.getString(0), c.getString(1), "", c.getString(2));
                toReturn.add(toAdd);
            } while (c.moveToNext());
        }
        return toReturn;
    }

    public long addArticle(Product prod) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //Conteneur d'un tuple à insérer
        ContentValues values = new ContentValues();

        //On lie les données du magasin au conteneur
        values.put(GoShoppingDBContract.ArticleTable.COLUMN_NAME_ARTICLE_NAME, prod.getName());
        values.put(GoShoppingDBContract.ArticleTable.COLUMN_NAME_ARTICLE_SHELF, prod.getCategory());
        //values.put(GoShoppingDBContract.ArticleTable.COLUMN_NAME_ARTICLE_QTY, prod.getQuantity());


        //On insère le tuple
        return sqLiteDatabase.insert(GoShoppingDBContract.ArticleTable.TABLE_NAME, null, values);
    }

    public int deleteArticle(Product prod) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("ShoppingListContent","Product_Id=?",new String[]{prod.get_id()});
        sqLiteDatabase.delete("ShoppingListContent","Quantity=?",new String[]{prod.getQuantity()});
        sqLiteDatabase.delete("articles", "Article=?", new String[]{prod.getName()});
         return 0;
    }

    public void addArticleToList(String listId, Product article) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(GoShoppingDBContract.ShoppingListContent.COLUMN_NAME_LIST_ID, listId);
        values.put(GoShoppingDBContract.ShoppingListContent.COLUMN_NAME_PRODUCT_ID, article.get_id());
        values.put(GoShoppingDBContract.ShoppingListContent.COLUMN_NAME_QUANTITY, article.getQuantity());

        sqLiteDatabase.insert(GoShoppingDBContract.ShoppingListContent.TABLE_NAME, null, values);
    }

    public void updateArticle(String newName,String newQuantity){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues argArticle = new ContentValues();
        argArticle.put("Article",newName);
        ContentValues argQuantity = new ContentValues();
        argQuantity.put("Quantity",newQuantity);
        db.update("Articles",argArticle,null,null);
        db.update("ShoppingListContent",argQuantity,null,null);
    }

    public ArrayList<Reminder> getReminder(String list_id) {
        ArrayList<Reminder> toReturn = new ArrayList<>();
        String[] whereArgs = new String[]{list_id};
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                GoShoppingDBContract.ReminderTable.COLUMN_NAME_REMINDER_DATE,
                GoShoppingDBContract.ReminderTable.COLUMN_NAME_REMINDER_LIST,
        };
        String sortOrder = GoShoppingDBContract.ReminderTable.COLUMN_NAME_REMINDER_LIST + " ASC";
        Cursor c = db.query(
                GoShoppingDBContract.ReminderTable.TABLE_NAME,// The table to query
                projection,                               // The columns to return
                "ListReminder=?",                               // The columns for the WHERE clause
                whereArgs,                                 // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        Reminder toAdd;
        if (c.moveToFirst()) {
            do {
                toAdd = new Reminder(c.getString(0), new Date(c.getLong(1)));
                toReturn.add(toAdd);
            } while (c.moveToNext());
        }
        return toReturn;
    }

    public long addReminder(Reminder r, int list_id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //Conteneur d'un tuple à insérer
        ContentValues values = new ContentValues();
        String year, month, day ="", hour="", minute="", second="";
        year = ""+r.getDate().getYear();
        if(r.getDate().getDate() < 10){
            day = "0"+r.getDate().getDate();
        }else{
            day = ""+r.getDate().getDate();
        }
        if(r.getDate().getMonth()<9){
            month = "0"+(r.getDate().getMonth()+1);
        }else{
            month = ""+(r.getDate().getMonth()+1);
        }
        if(r.getDate().getHours() < 10){
            hour = "0"+r.getDate().getHours();
        }else{
            hour = ""+r.getDate().getHours();
        }
        if(r.getDate().getMinutes()<9){
            minute = "0"+r.getDate().getMinutes();
        }else{
            minute = ""+r.getDate().getMinutes();
        }
        second = "00";
        String date = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;

        //On lie les données du magasin au conteneur
        values.put(GoShoppingDBContract.ReminderTable.COLUMN_NAME_REMINDER_DATE, date);
        values.put(GoShoppingDBContract.ReminderTable.COLUMN_NAME_REMINDER_LIST, list_id);
        System.out.println("Ajout " + date + " liste " + list_id);
        //On insère le tuple
        return sqLiteDatabase.insert(GoShoppingDBContract.ReminderTable.TABLE_NAME, null, values);
    }

    public int deleteReminder(Reminder r) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("reminders", "ListReminder=?", new String[]{r.getList_id()});
    }

    public ArrayList<ShoppingListObject> getShoppingListByDate(Date d){
        ArrayList<ShoppingListObject> toReturn = new ArrayList<>();
        String dateString = "'" + d.getYear() + "-";

        if(d.getMonth() < 9){
            dateString+=("0" + (d.getMonth()+1) + "-");
            //dateString = "'" + (d.getYear()) + "-" + (d.getMonth()+1) + "-0" + d.getDate();
        }else{
            dateString+=(""+(d.getMonth()+1) + "-");
            //dateString = "'" + (d.getYear()) + "-" + (d.getMonth()+1) + "-" + d.getDate();
        }
        if(d.getDate()<10){
            dateString+=("0"+d.getDate());
        }else{
            dateString+=(""+d.getDate());
        }

        System.out.println("Date recherchée " + dateString);
        String midNight = " 00:00:00'";
        String lastSecond = " 23:59:59'";
        String request = "SELECT " + GoShoppingDBContract.ShoppingList._ID + "," + GoShoppingDBContract.ShoppingList.COLUMN_NAME_LIST_NAME + "," + GoShoppingDBContract.ReminderTable.COLUMN_NAME_REMINDER_DATE +
                " FROM " + GoShoppingDBContract.ShoppingList.TABLE_NAME + "," + GoShoppingDBContract.ReminderTable.TABLE_NAME +
                " WHERE " + GoShoppingDBContract.ReminderTable.COLUMN_NAME_REMINDER_LIST + " = " + GoShoppingDBContract.ShoppingList._ID +
                " AND " + GoShoppingDBContract.ReminderTable.COLUMN_NAME_REMINDER_DATE + " >= " + dateString + midNight +
                " AND " + GoShoppingDBContract.ReminderTable.COLUMN_NAME_REMINDER_DATE + " <= " + dateString + lastSecond;
        Cursor c = getReadableDatabase().rawQuery(request, null);
        ShoppingListObject toAdd;
        if (c.moveToFirst()) {
            do {
                toAdd = new ShoppingListObject(c.getString(0), c.getString(1), "");
                System.out.println(c.getString(2));
                toReturn.add(toAdd);
            } while (c.moveToNext());
        }
        return toReturn;
    }

}
