package fr.enac.goshopping.fragment.shop;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.enac.goshopping.R;
import fr.enac.goshopping.database.GoShoppingDBHelper;
import fr.enac.goshopping.listadapters.LinkedListAdapter;
import fr.enac.goshopping.objects.ShoppingListObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ManageShopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ManageShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageShopFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String shopName;

    private OnFragmentInteractionListener mListener;

    public ManageShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param shopName Parameter 1.
     * @return A new instance of fragment ManageShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManageShopFragment newInstance(String shopName) {
        ManageShopFragment fragment = new ManageShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, shopName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shopName=getArguments().getString(ARG_PARAM1);
           }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_manage_shop, container, false);
        TextView t= (TextView) v.findViewById(R.id.manage_link_shop_name);
        t.setText(shopName);
        ListView listToLink = (ListView) v.findViewById(R.id.manage_link_list);
        final ArrayList<ShoppingListObject> list = new GoShoppingDBHelper(getContext()).getShoppingLists();
        ArrayList<String> listName = new ArrayList<>();
        for (ShoppingListObject s : list) {
            listName.add(s.getList_name());
        }
        ArrayAdapter adapter = new LinkedListAdapter(getActivity(), R.layout.element_list_linked_shop, listName);
        listToLink.setAdapter(adapter);

        listToLink.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox c= (CheckBox) view.findViewById(R.id.checkBox);
                c.setChecked(!c.isChecked());

            }
        });

        final Button deleteShop= (Button)v.findViewById(R.id.DeleteShop);
        deleteShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoShoppingDBHelper db=new GoShoppingDBHelper(getContext());
                db.deleteShop(shopName);
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.content_main, new ShopFragment())
                        .addToBackStack("ShopFrag")
                        .commit();
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
