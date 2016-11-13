package fr.enac.goshopping.fragment.shop;

import android.app.FragmentManager;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import fr.enac.goshopping.MainActivity;
import fr.enac.goshopping.R;
import fr.enac.goshopping.database.GoShoppingDBHelper;
import fr.enac.goshopping.objects.Shop;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewShopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewShopFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView title;
    private EditText name;
    private EditText address;
    private EditText postCode;
    private EditText city;
    private Button saveButton;
    private FloatingActionButton fab;

    public NewShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewShopFragment newInstance(String param1, String param2) {
        NewShopFragment fragment = new NewShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Geocoder geocoder = new Geocoder(getActivity());
        final View v = inflater.inflate(R.layout.fragment_new_shop, container, false);
        title = (TextView) v.findViewById(R.id.manage_shop_title);
        name = (EditText) v.findViewById(R.id.manage_shop_name);
        address = (EditText) v.findViewById(R.id.manage_shop_address);
        city = (EditText) v.findViewById(R.id.manage_shop_city);
        postCode = (EditText) v.findViewById(R.id.manage_shop_postcode);
        saveButton = (Button) v.findViewById(R.id.manage_shop_search);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        //enregistrement du magasin cree
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Address> addresses = null;
                View toTest = getActivity().getCurrentFocus();
                if (toTest != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                try {
                    //recuperation de l'adresse
                    addresses = (ArrayList<Address>) geocoder.getFromLocationName(name.getText() + " " + address.getText() + " " + postCode.getText() + " " + city.getText(), 10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!name.getText().toString().equals("") || addresses.size() != 0 || !((Integer) addresses.size()).equals(null)) {
                    //si le magasin trouve est unique
                    if (addresses.size() == 1) {
                        Address found_address = addresses.get(0);
                        //creation du magasin
                        Shop shop = new Shop(null, found_address.getFeatureName(), found_address.getAddressLine(1),
                                found_address.getLocality(), found_address.getPostalCode(), found_address.getLatitude(), found_address.getLongitude());
                        //mise a jour de la base de donnée
                        double lastInserted = new GoShoppingDBHelper(getActivity()).addShop(shop);
                        //on retourne a la liste des magasins ou le magasin a ete cree
                        //transition d'ecran
                        FragmentManager fragmentManager = getActivity().getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_main, new ShopFragment())
                                .commit();
                        //notification en sortie
                        Toast.makeText(getContext(), "Magasin enregistré", Toast.LENGTH_SHORT).show();
                        ((MainActivity) getActivity()).createGeofenceAlert(""+lastInserted, shop.getLatitude(),shop.getLongitude());
                    } else {
                        //si il y a plusieurs magasin trouves ont consititue une liste a afficher a l'utilisateur
                        ChooseShopDialogFragment chooseShopDialogFragment = ChooseShopDialogFragment.newInstance(addresses);
                        chooseShopDialogFragment.show(getFragmentManager().beginTransaction(), "choose_shop");
                    }

                    fab.setVisibility(View.VISIBLE);
                } else {
                    //si le magasin est introuvable, notification en sortie pour informer l'utilisateur
                    Snackbar.make(view, "Nom de magasin incorrect ou introuvable", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener
                    getFragmentManager().popBackStack("ShopFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
    }


}
