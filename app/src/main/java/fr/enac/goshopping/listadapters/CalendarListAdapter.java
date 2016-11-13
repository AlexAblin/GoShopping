package fr.enac.goshopping.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.enac.goshopping.R;
import fr.enac.goshopping.objects.Reminder;

/**
 * Created by alexandre on 11/11/2016.
 */

public class CalendarListAdapter extends ArrayAdapter<Reminder> {
    private Context context;
    private int resource;
    private List<Reminder> list;

    public CalendarListAdapter(Context context, int resource, List<Reminder> objects) {
        super(context, resource, objects);
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
        TextView listName = (TextView) view.findViewById(R.id.item_calendar_list);
        TextView time=(TextView) view.findViewById(R.id.item_calendar_time);

        Reminder rem = list.get(position);
        listName.setText(rem.getList_id());
        time.setText(rem.getDate().toString());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getView(position, convertView, parent);
    }

}
