package fr.enac.goshopping.fragment.shoppinglist;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.enac.goshopping.R;
import fr.enac.goshopping.database.GoShoppingDBHelper;
import fr.enac.goshopping.fragment.product.NewArticleFragment;
import fr.enac.goshopping.listadapters.ProductListAdapter;
import fr.enac.goshopping.objects.Product;

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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FloatingActionButton fabButton;
    private ListView liste;
    private TextView title;
    private Fragment f= this;
    private Product selectedArticle;
    private int selectedPosition;
    private MenuItem deleteMenuItem;
    private MenuItem renameMenuItem;

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
     * @param list_id   The list database identifier.
     * @param list_name The list name.
     * @return A new instance of fragment ShoppingListContent.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingListContent newInstance(String list_id, String list_name) {
        ShoppingListContent fragment = new ShoppingListContent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, list_id);
        args.putString(ARG_PARAM2, list_name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            list_id = getArguments().getString(ARG_PARAM1);
            list_name = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shopping_list_content, container, false);
        title = (TextView) v.findViewById(R.id.list_content_list_title);
        title.setText(list_name);
        liste = (ListView) v.findViewById(R.id.list_content_list);
        fabButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        final ArrayList<Product> list = new GoShoppingDBHelper(getContext()).getListContent(list_id);
        liste = (ListView) v.findViewById(R.id.list_content_list);
        ArrayAdapter<Product> adapter = new ProductListAdapter(getActivity(), R.layout.element_list_product_layout, list);
        liste.setAdapter(adapter);
        //acces a l'ecran de creation d'un nouvel article
        //transition d'ecran
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewArticleFragment newArticleFragment = NewArticleFragment.newInstance(list_id, list_name);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, newArticleFragment)
                        .addToBackStack("ListContentFrag")
                        .commit();
            }
        });

        //permet a l'utilisateur de cocher et decocher les articles dans la liste
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!list.get(position).isChecked()){
                    TextView prodName = (TextView) view.findViewById(R.id.ProductName);
                    TextView quantity = (TextView) view.findViewById(R.id.ProductQuantity);
                    prodName.setPaintFlags(prodName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    quantity.setPaintFlags(quantity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    list.get(position).setChecked(true);
                } else {
                    TextView prodName = (TextView) view.findViewById(R.id.ProductName);
                    TextView quantity = (TextView) view.findViewById(R.id.ProductQuantity);
                    prodName.setPaintFlags(0);
                    quantity.setPaintFlags(0);
                    list.get(position).setChecked(false);
                }
            }
        });

        //permet d'afficher le menu pour renommer ou supprimer les articles si maintenu appuyé
        liste.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedArticle=list.get(position);
                selectedPosition= position;
                //affichage en sortie de la selection de l'element
                liste.getChildAt(position).setBackgroundColor(Color.GRAY);
                deleteMenuItem.setVisible(true);
                renameMenuItem.setVisible(true);
                return true;
            }
        });
        fabButton.setVisibility(View.VISIBLE);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.manage_article, menu);
        deleteMenuItem = menu.findItem(R.id.DeleteArticle);
        deleteMenuItem.setVisible(false);
        renameMenuItem= menu.findItem(R.id.RenameArticle);
        renameMenuItem.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //suppression de l'article
        if (id == R.id.DeleteArticle) {
            //affichage d'une boite de dialog pour confirmer le choix de l'utilisateur
            AlertDialog.Builder alert = new AlertDialog.Builder(
                    getActivity());
            alert.setTitle("Voulez-vous supprimer l'article "+ selectedArticle.getName()+" ?");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    new GoShoppingDBHelper(getContext()).deleteArticle(selectedArticle);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(f).attach(f).commit();
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
        //renommer l'article
        if (id== R.id.RenameArticle){
            //affichage d'une boite de dialog ou l'utilisateur entre le nouveau nom et la nouvelle
            //quantite de l'article
            LinearLayout layout = new LinearLayout(getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            AlertDialog.Builder alert = new AlertDialog.Builder(
                    getActivity());
            alert.setTitle("Modifier l'article");

            final EditText inputName = new EditText(getActivity());
            inputName.setHint("Nom");
            layout.addView(inputName);

            final EditText inputQuant = new EditText(getActivity());
            inputQuant.setHint("Quantité");
            inputQuant.setInputType(InputType.TYPE_CLASS_NUMBER);
            layout.addView(inputQuant);
            alert.setView(layout);

            //confirmation
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(inputName.getText().toString().equals("") || inputQuant.getText().toString().equals("")){
                        //si le nom ou la quantité est vide, on annule
                        dialog.cancel();
                    } else {
                        String name = inputName.getEditableText().toString();
                        String quantity = inputQuant.getEditableText().toString();
                        //mise a jour de la base de donnee
                        new GoShoppingDBHelper(getContext()).updateArticle(name, quantity);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        //on raffraichi le fragment pour afficher la mise a jour
                        //transition vers lui même
                        ft.detach(f).attach(f).commit();
                    }
                }
            });

            //on annule l'operation
            alert.setNegativeButton("Annuler",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();

        }
        return super.onOptionsItemSelected(item);
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
                    getFragmentManager().popBackStack("ListFrag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
    }
}
