package fr.enac.goshopping.fragment.shop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.location.Address;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

import fr.enac.goshopping.MainActivity;
import fr.enac.goshopping.R;
import fr.enac.goshopping.database.GoShoppingDBHelper;
import fr.enac.goshopping.objects.Shop;

/**
 * Created by Geekette on 08/11/2016.
 */

/**
 * Gestion de la liste de suggestion des magasins possibles obtenu par la géolocalisation
 */
public class ChooseShopDialogFragment extends DialogFragment {

    ArrayList<Address> addresses;
    CharSequence[] toDisplay;

    private static final String ARG_PARAM1 = "param1";

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    public static ChooseShopDialogFragment newInstance(ArrayList<Address> shopsAddresses) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, shopsAddresses);
        ChooseShopDialogFragment fragment = new ChooseShopDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setList(ArrayList<Address> mapShops) {
        int i = 0;
        this.addresses = mapShops;
        CharSequence current = "";
        Address currentAddress;
        toDisplay = new CharSequence[addresses.size()];
        this.addresses = mapShops;
        for (Iterator<Address> it = mapShops.iterator(); it.hasNext(); ) {
            currentAddress = it.next();
            current = currentAddress.getFeatureName() + " (" + currentAddress.getAddressLine(1) + ", " + currentAddress.getLocality() + ")";
            toDisplay[i] = current;
            i++;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ArrayList<Parcelable> paramList = (ArrayList<Parcelable>) getArguments().get(ARG_PARAM1);
            ArrayList<Address> toPass = new ArrayList<>();
            for (int i = 0; i < paramList.size(); i++) {
                toPass.add((Address) paramList.get(i));
            }
            setList(toPass);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        System.out.println("Coucou");
        //affichage de la fenetre de choix des magasins
        builder.setTitle("Choisissez votre magasin")
                .setItems(toDisplay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Address shop = addresses.get(i);
                        Shop toAdd = new Shop(null, shop.getFeatureName(), shop.getAddressLine(1), shop.getLocality(), shop.getPostalCode(), shop.getLatitude(), shop.getLongitude());
                        long id = new GoShoppingDBHelper(getActivity()).addShop(toAdd);
                        toAdd.set_id("" + id);
                        //on retourne sur la liste des magasins avec le magasin crée
                        //Transition d'écran
                        FragmentManager fragmentManager = getActivity().getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_main, new ShopFragment())
                                .commit();
                        Toast.makeText(getContext(), "Magasin enregistré.", Toast.LENGTH_SHORT).show();
                        ((MainActivity) getActivity()).createGeofenceAlert("" + id, shop.getLatitude(), shop.getLongitude());
                    }
                });
        return builder.show();
    }
}
