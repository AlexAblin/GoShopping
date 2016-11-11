package fr.enac.goshopping.listadapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import fr.enac.goshopping.R;
import fr.enac.goshopping.database.GoShoppingDBHelper;
import fr.enac.goshopping.objects.Shop;

/**
 * Created by Geekette on 04/11/2016.
 */

public class ShopListArrayAdapter extends ArrayAdapter<Shop> {

    private Context context;
    private int resource;
    private List<Shop> list;
    private boolean activated;

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
        activated=false;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
        }
        final Button geoloc=(Button)view.findViewById(R.id.activate_shop);
        geoloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!activated) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            (Activity) getContext());
                    alert.setTitle("Voulez-vous activer un rappel à proximité de ce magasin?");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //TODO: activer la localisation
                            geoloc.setBackgroundResource(R.drawable.map_alarm_icon_selected);
                            activated=true;
                        }

                    });

                    alert.setNegativeButton("Annuler",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            (Activity) getContext());
                    alert.setTitle("Voulez-vous désactiver un rappel à proximité de ce magasin?");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //TODO: activer la localisation
                            geoloc.setBackgroundResource(R.drawable.map_alarm_icon);
                            activated=false;
                        }
                    });

                    alert.setNegativeButton("Annuler",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                }

            }
        });

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
