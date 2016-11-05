package fr.enac.goshopping.listadapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.enac.goshopping.R;
import fr.enac.goshopping.database.GoShoppingDBHelper;
import fr.enac.goshopping.objects.Product;
import fr.enac.goshopping.objects.ShoppingListObject;

/**
 * Created by alexandre on 05/11/2016.
 */

public class ShoppingListAdapter extends ArrayAdapter<ShoppingListObject> {
    private Context context;
    private int resource;
    private List<ShoppingListObject> list;

    public ShoppingListAdapter(Context context, int resource, List<ShoppingListObject> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE); view = inflater.inflate(resource, parent, false);
        }

        ArrayList<Product> listProduct = new GoShoppingDBHelper(getContext()).getArticles();
        ProductListAdapter adapter= new ProductListAdapter(getContext(),R.layout.element_list_product_layout,listProduct);

        ShoppingListObject listItem=list.get(position);
        Spinner p= (Spinner) view.findViewById(R.id.spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p.setPrompt(listItem.getList_name());
        p.setAdapter(adapter);

        TextView t= (TextView) view.findViewById(R.id.textViewTest);
        t.setText(listItem.getList_name());

        return view;
    }
}
