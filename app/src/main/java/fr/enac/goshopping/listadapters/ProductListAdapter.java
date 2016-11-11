package fr.enac.goshopping.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.enac.goshopping.R;
import fr.enac.goshopping.objects.Product;

/**
 * Created by alexandre on 05/11/2016.
 */

public class ProductListAdapter extends ArrayAdapter<Product> {
    private Context context;
    private int resource;
    private List<Product> list;

    public ProductListAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, R.id.ProductName, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;

    }

    // @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
        }
        TextView productName = (TextView) view.findViewById(R.id.ProductName);
        TextView productQuantity = (TextView) view.findViewById(R.id.ProductQuantity);

        Product prod = list.get(position);
        productName.setText(prod.getName());
        productQuantity.setText(String.valueOf(prod.getQuantity()));
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getView(position, convertView, parent);
    }

}
