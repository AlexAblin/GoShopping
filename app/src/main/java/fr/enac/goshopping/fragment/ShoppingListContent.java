package fr.enac.goshopping.fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import fr.enac.goshopping.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShoppingListContent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShoppingListContent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingListContent extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String LIST_ID = "param1";
    private static final String LIST_NAME = "param2";

    private FloatingActionButton fabButton;
    private ListView liste;
    private TextView title;

    // TODO: Rename and change types of parameters
    private String list_id;
    private String list_name;

    private OnFragmentInteractionListener mListener;

    public ShoppingListContent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param list_id The list database identifier.
     * @param list_name The list name.
     * @return A new instance of fragment ShoppingListContent.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingListContent newInstance(String list_id, String list_name) {
        ShoppingListContent fragment = new ShoppingListContent();
        Bundle args = new Bundle();
        args.putString(LIST_ID, list_id);
        args.putString(LIST_NAME, list_name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list_id = getArguments().getString(LIST_ID);
            list_name = getArguments().getString(LIST_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_shopping_list_content, container, false);
        title = (TextView) v.findViewById(R.id.list_content_list_title);
        title.setText(list_name);
        liste = (ListView) v.findViewById(R.id.list_content_list);
        fabButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        //System.out.println(fabButton);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewArticleFragment newArticleFragment = NewArticleFragment.newInstance(list_id,list_name);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, newArticleFragment)
                        .addToBackStack(null)
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
}
