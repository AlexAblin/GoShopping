package fr.enac.goshopping.listadapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import fr.enac.goshopping.R;
import fr.enac.goshopping.fragment.shop.ManageShopFragment;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
        }

        TextView shopName = (TextView) view.findViewById(R.id.shopName);
        TextView shopAdress = (TextView) view.findViewById(R.id.shopAdress);
        TextView shopCity = (TextView) view.findViewById(R.id.shopCity);

        final Shop shop = list.get(position);
        shopName.setText(shop.getName());
        shopAdress.setText(shop.getAdress());
        shopCity.setText(shop.getCity());

        //activer la geolocalisation
        final Button geoloc=(Button)view.findViewById(R.id.activate_shop);
        geoloc.setTag(position);
        geoloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!shop.isActivated()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            (Activity) getContext());
                    //boite de dialog pour demander la confirmation
                    //Entree/Sortie
                    alert.setTitle("Voulez-vous activer un rappel à proximité de ce magasin?");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //TODO: activer la localisation
                            geoloc.setBackgroundResource(R.drawable.map_alarm_icon_selected);
                            shop.setActivated(true);
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
                    //desactiver la notfication
                    //demande confimation de l'utilisateur
                    //Entree/Sortie
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            (Activity) getContext());
                    alert.setTitle("Voulez-vous désactiver un rappel à proximité de ce magasin?");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //TODO: activer la localisation
                            geoloc.setBackgroundResource(R.drawable.map_alarm_icon);
                            shop.setActivated(false);
                        }
                    });

                    //annulation
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

        //si l'utilisateur selectionne le bouton de parametre du magasin
        //il est redirige vers ce fragment
        //transition d'ecran
        final Button manageButton= (Button) view.findViewById(R.id.setting_shop);
        manageButton.setTag(position);
        manageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageShopFragment newManageShopFragment= new ManageShopFragment().newInstance(list.get(position).getName());
                ((Activity)getContext()).getFragmentManager().beginTransaction()
                        .replace(R.id.content_main, newManageShopFragment)
                        .addToBackStack("ShopFrag")
                        .commit();
            }
        });

        return view;
    }

}
