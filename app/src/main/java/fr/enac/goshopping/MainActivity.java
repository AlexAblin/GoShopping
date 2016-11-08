package fr.enac.goshopping;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import fr.enac.goshopping.database.GoShoppingDBHelper;
import fr.enac.goshopping.fragment.CalendarFragment;
import fr.enac.goshopping.fragment.NewArticleFragment;
import fr.enac.goshopping.fragment.NewListFragment;
import fr.enac.goshopping.fragment.NewShopFragment;
import fr.enac.goshopping.fragment.RappelsFragment;
import fr.enac.goshopping.fragment.SettingsFragment;
import fr.enac.goshopping.fragment.ShopFragment;
import fr.enac.goshopping.fragment.ShoppingListFragment;
import fr.enac.goshopping.notification.LocationNotificationActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SettingsFragment.OnFragmentInteractionListener,
        CalendarFragment.OnFragmentInteractionListener, ShoppingListFragment.OnFragmentInteractionListener,
        ShopFragment.OnFragmentInteractionListener, NewShopFragment.OnFragmentInteractionListener,
        NewArticleFragment.OnFragmentInteractionListener, NewListFragment.OnFragmentInteractionListener,
        RappelsFragment.OnFragmentInteractionListener{

    private int MY_PERMISSIONS_REQUEST_FINE_LOCATION;
    private GoShoppingDBHelper dbHelper;
    private FloatingActionButton fab;
    private int state;
    private int MY_PERMISSIONS_REQUEST_COARSE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new GoShoppingDBHelper(this);
        dbHelper.onCreate(dbHelper.getWritableDatabase());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                handleFabButton(view);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //testLocation();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getFragmentManager();
        Fragment toCommit = null;

        if (id == R.id.nav_go_shopping) {

        } else if (id == R.id.nav_calendar) {
            state = id;
            toCommit = new CalendarFragment();
        } else if (id == R.id.nav_shopping_list) {
            state = id;
            toCommit = new ShoppingListFragment();
        } else if (id == R.id.nav_shop) {
            state = id;
            toCommit = new ShopFragment();
        } else if (id == R.id.nav_settings) {
            state = id;
            toCommit = new SettingsFragment();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.content_main, toCommit)
                .addToBackStack(null)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void handleFabButton(View view){
        switch (state){
            case R.id.nav_go_shopping:
                break;
            case R.id.nav_calendar:
                break;
            case R.id.nav_shopping_list:
                //fab.setVisibility(View.INVISIBLE);
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_main,new NewListFragment())
                        .commit();
                break;
            case R.id.nav_shop:
                fab.setVisibility(View.INVISIBLE);
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.content_main, new NewShopFragment())
                        .commit();
                break;
            case R.id.nav_settings:
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void testLocation() {
        System.out.println("Début du test location");
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Intent resultIntent = new Intent(this, LocationNotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return;
        }
        Toast.makeText(this, "Demande de géolocalisation enregistré", Toast.LENGTH_LONG).show();
        locationManager.addProximityAlert(43.6043657, 1.4411526, 250, -1, pendingIntent);
        System.out.println("Fin du test location");
    }
}
