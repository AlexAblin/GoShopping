package fr.enac.goshopping.fragment;

import android.app.FragmentManager;
import android.content.Context;
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
import android.widget.Toast;

import fr.enac.goshopping.R;
import fr.enac.goshopping.database.GoShoppingDBHelper;
import fr.enac.goshopping.objects.Product;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewArticleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewArticleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText name;
    private EditText quantity;
    private Button saveButton;
    private FloatingActionButton fab;

    public NewArticleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewArticleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewArticleFragment newInstance(String param1, String param2) {
        NewArticleFragment fragment = new NewArticleFragment();
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
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_new_article, container, false);
        name = (EditText) v.findViewById(R.id.manage_article_name);
        quantity = (EditText) v.findViewById(R.id.manage_article_quantity);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        saveButton= (Button) v.findViewById(R.id.buttonNewArticle) ;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().equals("")) {
                    Product prod = new Product(null, name.getText().toString(), "test", quantity.getText().toString());
                    new GoShoppingDBHelper(getActivity()).addArticle(prod);
                    FragmentManager fragmentManager = getActivity().getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_main, new ShoppingListFragment())
                            .commit();
                    Toast.makeText(getContext(), "Article enregistré.", Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(view, "Nom d'article incorrect", Snackbar.LENGTH_LONG)
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
}