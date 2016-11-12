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
import fr.enac.goshopping.objects.Reminder;

/**
 * Created by alexandre on 12/11/2016.
 */

public class LinkedListAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resource;
    private List<String> list;

    public LinkedListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource,objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
        }
        TextView productName = (TextView) view.findViewById(R.id.manage_link_name);
        String name = list.get(position);
        productName.setText(name);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getView(position, convertView, parent);
    }

}
