package fr.enac.goshopping.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import fr.enac.goshopping.R;
import fr.enac.goshopping.database.GoShoppingDBHelper;
import fr.enac.goshopping.objects.Shop;

/**
 * Created by Geekette on 08/11/2016.
 */

public class ChooseShopDialogFragment extends DialogFragment {

    List<Address> addresses;
    CharSequence[] toDisplay;

    /*public ChooseShopDialogFragment(List<Address> addresses){
        super();
        int i = 0;
        CharSequence current = "";
        Address currentAddress;
        toDisplay = new CharSequence[addresses.size()];
        this.addresses = addresses;
        for(Iterator<Address> it = addresses.iterator();it.hasNext();){
            currentAddress = it.next();
            current = currentAddress.getFeatureName() + " (" + currentAddress.getLocality() + ")";
            toDisplay[i] = current;
            i++;
        }
    }*/

    public void setList(List<Address> mapShops){
        int i = 0;
        CharSequence current = "";
        Address currentAddress;
        toDisplay = new CharSequence[addresses.size()];
        this.addresses = mapShops;
        for(Iterator<Address> it = mapShops.iterator();it.hasNext();){
            currentAddress = it.next();
            current = currentAddress.getFeatureName() + " (" + currentAddress.getLocality() + ")";
            toDisplay[i] = current;
            i++;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choisissez votre magasin")
                .setItems(toDisplay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Address shop = addresses.get(i);
                        Shop toAdd = new Shop(null,shop.getFeatureName(),shop.getAddressLine(1),shop.getLocality(),shop.getPostalCode(),shop.getLatitude(),shop.getLongitude());
                        new GoShoppingDBHelper(getActivity()).addShop(toAdd);
                        FragmentManager fragmentManager = getActivity().getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_main, new ShopFragment())
                                .commit();
                        Toast.makeText(getContext(), "Magasin enregistré.", Toast.LENGTH_SHORT).show();
                    }
                });

        return super.onCreateDialog(savedInstanceState);
    }
}
