package fr.enac.goshopping;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
    private EditText adress;
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
        View v = inflater.inflate(R.layout.fragment_new_shop, container, false);
        title = (TextView) v.findViewById(R.id.manage_shop_title);
        name = (EditText) v.findViewById(R.id.manage_shop_name);
        adress = (EditText) v.findViewById(R.id.manage_shop_adress);
        city = (EditText) v.findViewById(R.id.manage_shop_city);
        postCode = (EditText) v.findViewById(R.id.manage_shop_postcode);
        saveButton = (Button) v.findViewById(R.id.manage_shop_save);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shop shop = new Shop(null,name.getText().toString(),adress.getText().toString(),
                        city.getText().toString(),postCode.getText().toString());
                new GoShoppingDBHelper(getActivity()).addShop(shop);
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main,new ShopFragment())
                        .commit();
                fab.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Magasin enregistré.", Toast.LENGTH_SHORT).show();
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
}
