package fr.enac.goshopping.fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

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
        View v = inflater.inflate(R.layout.fragment_new_shop, container, false);
        title = (TextView) v.findViewById(R.id.manage_shop_title);
        name = (EditText) v.findViewById(R.id.manage_shop_name);
        address = (EditText) v.findViewById(R.id.manage_shop_address);
        city = (EditText) v.findViewById(R.id.manage_shop_city);
        postCode = (EditText) v.findViewById(R.id.manage_shop_postcode);
        saveButton = (Button) v.findViewById(R.id.manage_shop_search);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocationName(name.getText() + " " + address.getText() + " " + postCode.getText() + " " + city.getText(), 3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!name.getText().toString().equals("") || addresses.size() == 0) {
                        Address found_address = addresses.get(0);
                        Shop shop = new Shop(null, found_address.getFeatureName(), found_address.getAddressLine(1),
                                found_address.getLocality(), found_address.getPostalCode(), found_address.getLatitude(), found_address.getLongitude());
                        new GoShoppingDBHelper(getActivity()).addShop(shop);
                        FragmentManager fragmentManager = getActivity().getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_main, new ShopFragment())
                                .commit();
                        Toast.makeText(getContext(), "Magasin enregistré", Toast.LENGTH_SHORT).show();
                        fab.setVisibility(View.VISIBLE);
                    /*name.setSelected(false);
                    address.setSelected(false);
                    city.setSelected(false);
                    postCode.setSelected(false);
                    name.clearFocus();
                    address.clearFocus();
                    city.clearFocus();
                    postCode.clearFocus();
                    name.setFocusable(false);
                    address.setFocusable(false);
                    postCode.setFocusable(false);
                    city.setFocusable(false);*/

                    //ChooseShopDialogFragment.

                    /*ChooseShopDialogFragment chooseShopDialogFragment = new ChooseShopDialogFragment();
                    chooseShopDialogFragment.setList(addresses);
                    chooseShopDialogFragment.show(getFragmentManager(),"");*/
                    //chooseShopDialogFragment.setShowsDialog(true);

                    //chooseShopDialogFragment
                    //chooseShopDialogFragment.show(getFragmentManager(),null);
                } else {
                    Snackbar.make(view, "Nom de magasin incorrect", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        /*fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
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


}
