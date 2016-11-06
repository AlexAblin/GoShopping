package fr.enac.goshopping.listadapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.enac.goshopping.NewArticleFragment;
import fr.enac.goshopping.NewShopFragment;
import fr.enac.goshopping.R;
import fr.enac.goshopping.ShoppingListFragment;
import fr.enac.goshopping.database.GoShoppingDBHelper;
import fr.enac.goshopping.objects.Product;

import static fr.enac.goshopping.R.id.fab;

/**
 * Created by alexandre on 05/11/2016.
 */

public class ProductListAdapter extends ArrayAdapter<Product> {
    private Context context;
    private int resource;
    private List<Product> list;

    public ProductListAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource,R.id.ProductName, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;

    }

   // @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE); view = inflater.inflate(resource, parent, false);
        }
        TextView productName = (TextView) view.findViewById(R.id.ProductName);
        TextView productCategory = (TextView) view.findViewById(R.id.ProductCategorie);
        TextView productQuantity= (TextView) view.findViewById(R.id.ProductQuantity);

        Product prod = list.get(position);
        productName.setText(prod.getName());
        productCategory.setText(prod.getCategory());
        productQuantity.setText(String.valueOf(prod.getQuantity()));
        Button b= (Button) view.findViewById(R.id.buttonAdd);
        if(position==list.size()-1){
            b.setVisibility(View.VISIBLE);
        } else {
            b.setVisibility(View.GONE);
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((Activity) context).getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.content_main,new NewArticleFragment())
                        .commit();
            }
        });
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getView(position, convertView, parent);
    }


}
