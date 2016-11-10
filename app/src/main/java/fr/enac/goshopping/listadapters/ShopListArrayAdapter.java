package fr.enac.goshopping.listadapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.enac.goshopping.R;
import fr.enac.goshopping.objects.Shop;

/**
 * Created by Geekette on 04/11/2016.
 */

public class ShopListArrayAdapter extends ArrayAdapter<Shop> {

    private Context context;
    private int resource;
    private List<Shop> list;

    public ShopListArrayAdapter(Context context, int resource, List<Shop> objects) {
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
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
        }

        TextView shopName = (TextView) view.findViewById(R.id.shopName);
        TextView shopAdress = (TextView) view.findViewById(R.id.shopAdress);
        TextView shopCity = (TextView) view.findViewById(R.id.shopCity);

        Shop shop = list.get(position);
        shopName.setText(shop.getName());
        shopAdress.setText(shop.getAdress());
        shopCity.setText(shop.getCity());

        return view;
    }


}
