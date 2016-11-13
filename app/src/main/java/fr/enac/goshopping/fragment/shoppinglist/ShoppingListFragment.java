package fr.enac.goshopping.fragment.shoppinglist;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ListView;

import java.util.ArrayList;

import fr.enac.goshopping.R;
import fr.enac.goshopping.database.GoShoppingDBHelper;
import fr.enac.goshopping.listadapters.ShoppingListAdapter;
import fr.enac.goshopping.objects.ShoppingListObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShoppingListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShoppingListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ShoppingListObject selectedList;
    private ArrayList<ShoppingListObject> list;

    private int selectedPosition;
    private OnFragmentInteractionListener mListener;
    private FloatingActionButton fab;
    private MenuItem deleteMenuItem;
    private MenuItem renameListe;
    private Fragment f=this;
    public ShoppingListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingListFragment newInstance(String param1, String param2) {
        ShoppingListFragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        View v = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        //recuperation des listes de courses dans la base de donnees
        list = new GoShoppingDBHelper(getContext()).getShoppingLists();
       final ListView listView = (ListView) v.findViewById(R.id.shopping_list_list);
        //on creer l'adapteur pour la liste
        ArrayAdapter adapter = new ShoppingListAdapter(getActivity(), R.layout.element_list_shopping_layout, list);
        listView.setAdapter(adapter);
        //si on clique sur une liste on en affiche son contenu
        //transition d'ecran
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingListObject selected = list.get(position);
                Fragment viewList = ShoppingListContent.newInstance(selected.get_ID(), selected.getList_name());
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_main, viewList)
                        .addToBackStack("ListFrag")
                        .commit();
            }
        });

        //si on maintien appuye sur une liste on affiche les poption de la supprimer ou la renommer
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedList=list.get(position);
                selectedPosition= position;
                //affichage en sortie de la selection de l'element
                listView.getChildAt(position).setBackgroundColor(Color.GRAY);
                deleteMenuItem.setVisible(true);
                renameListe.setVisible(true);
                return true;
            }
        });

        fab.setVisibility(View.VISIBLE);
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
                    getFragmentManager().popBackStack("MainActivity", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.manage_list, menu);
        deleteMenuItem = menu.findItem(R.id.DeleteList);
        deleteMenuItem.setVisible(false);
        renameListe= menu.findItem(R.id.RenameList);
        renameListe.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //suppression d'une liste
        if (id == R.id.DeleteList) {
            AlertDialog.Builder alert = new AlertDialog.Builder(
                    getActivity());
            //demande de confirmation de l'utilisateur
            alert.setTitle("Voulez-vous supprimer la liste "+ selectedList.getList_name()+" ?");
            //on suprime la liste si confirme
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //mise a jour de la base de donnees
                    new GoShoppingDBHelper(getContext()).deleteShoppingList(selectedList);
                    //on rafraichi le fragment
                    //transition sur lui meme
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(f).attach(f).commit();
                }
            });

            //annulation de l'operation
            alert.setNegativeButton("Annuler",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();

        }
        //renommer la liste
        //une boite de dialog s'affiche ou l'utilisateur rentre le nom de la liste
        if (id== R.id.RenameList){
            AlertDialog.Builder alert = new AlertDialog.Builder(
                    getActivity());
            alert.setTitle("Renommer");

            final EditText input = new EditText(getActivity());
            alert.setView(input);

            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String srt1 = input.getEditableText().toString();
                    //mise a jour de la base de donnee
                    new GoShoppingDBHelper(getContext()).updateShoppingListName(srt1);
                    //on rafraichi le fragment
                    //transition sur lui meme
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(f).attach(f).commit();
                }
            });
            //annulation de l'operation
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
}
