package fr.enac.goshopping.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.widget.Toast;

import java.util.ArrayList;

import fr.enac.goshopping.database.GoShoppingDBContract;
import fr.enac.goshopping.objects.Product;
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GoShoppingDBContract.ShoppingListContent.TABLE_NAME);*/

        //On crée les tables
        sqLiteDatabase.execSQL(GoShoppingDBContract.TABLE_SHOP);
        sqLiteDatabase.execSQL(GoShoppingDBContract.TABLE_SHELF);
        sqLiteDatabase.execSQL(GoShoppingDBContract.TABLE_SHOPPING_LIST);
        sqLiteDatabase.execSQL(GoShoppingDBContract.TABLE_ARTICLE_LIST);
        sqLiteDatabase.execSQL(GoShoppingDBContract.TABLE_SHOPPING_LIST_CONTENT);
        sqLiteDatabase.execSQL(GoShoppingDBContract.TABLE_REMINDER);

        //Conteneur d'un tuple à insérer
        //ContentValues values = new ContentValues();

        //On crée un magasin
        /*values.put(GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_NAME, "ENAC'Shop");
        values.put(GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_ADRESS, "Avenue Edouard Belin");
        values.put(GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_CITY, "Toulouse");
        values.put(GoShoppingDBContract.ShopTable.COLUMN_NAME_SHOP_POST_CODE, "31400");*/

        //On insère les données du contneur et on récupére l'identifiant généré, qu'on affiche
        /*long newRowId = sqLiteDatabase.insert(GoShoppingDBContract.ShopTable.TABLE_NAME,null,values);
        System.out.println("Ceci est un test : "+newRowId);*/


        //On crée un rayon
        /*values = new ContentValues();
        values.put(GoShoppingDBContract.ShoppingList.COLUMN_NAME_LIST_NAME,"Vêtement");
        values.put(GoShoppingDBContract.ShoppingList.COLUMN_NAME_LIST_SHOP,"Leclerc");*/
        //On insère le rayon
        /*newRowId = sqLiteDatabase.insert(GoShoppingDBContract.ShoppingList.TABLE_NAME,null,values);
        System.out.println(newRowId);*/

        //On crée un article
        /*values = new ContentValues();
        values.put(GoShoppingDBContract.ArticleTable.COLUMN_NAME_ARTICLE_NAME, "Sweat ENAC");
        values.put(GoShoppingDBContract.ArticleTable.COLUMN_NAME_ARTICLE_SHELF, "" + newRowId);
        //values.put(GoShoppingDBContract.ArticleTable.COLUMN_NAME_ARTICLE_QTY, "1");*/
        /*newRowId = sqLiteDatabase.insert(GoShoppingDBContract.ArticleTable.TABLE_NAME,null,values);
        System.out.println(newRowId);*/

        /*values = new ContentValues();
        values.put(GoShoppingDBContract.ArticleTable.COLUMN_NAME_ARTICLE_NAME, "Casquette IHM");
        values.put(GoShoppingDBContract.ArticleTable.COLUMN_NAME_ARTICLE_SHELF, "" + newRowId);*/
        //values.put(GoShoppingDBContract.ArticleTable.COLUMN_NAME_ARTICLE_QTY, "1");
        /*newRowId = sqLiteDatabase.insert(GoShoppingDBContract.ArticleTable.TABLE_NAME,null,values);
        System.out.println(newRowId);*/
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

    public int deleteShop(Shop shop) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("shops", "shop=?", new String[]{shop.getName()});
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
        String[] projection = {
                GoShoppingDBContract.ArticleTable._ID,
                GoShoppingDBContract.ArticleTable.COLUMN_NAME_ARTICLE_NAME,
                GoShoppingDBContract.ShoppingListContent.COLUMN_NAME_QUANTITY
        };
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
        return sqLiteDatabase.delete("articles", "Article=?", new String[]{prod.getName()});
    }

    public void addArticleToList(String listId, Product article) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(GoShoppingDBContract.ShoppingListContent.COLUMN_NAME_LIST_ID, listId);
        values.put(GoShoppingDBContract.ShoppingListContent.COLUMN_NAME_PRODUCT_ID, article.get_id());
        values.put(GoShoppingDBContract.ShoppingListContent.COLUMN_NAME_QUANTITY, article.getQuantity());

        sqLiteDatabase.insert(GoShoppingDBContract.ShoppingListContent.TABLE_NAME, null, values);
    }


}
