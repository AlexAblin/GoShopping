package fr.enac.goshopping;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import fr.enac.goshopping.database.GoShoppingDBHelper;
import fr.enac.goshopping.fragment.calendar.CalendarFragment;
import fr.enac.goshopping.fragment.shop.ManageShopFragment;
import fr.enac.goshopping.fragment.product.NewArticleFragment;
import fr.enac.goshopping.fragment.shoppinglist.NewListFragment;
import fr.enac.goshopping.fragment.shop.NewShopFragment;
import fr.enac.goshopping.fragment.calendar.RappelsFragment;
import fr.enac.goshopping.fragment.shop.ShopFragment;
import fr.enac.goshopping.fragment.shoppinglist.ShoppingListContent;
import fr.enac.goshopping.fragment.shoppinglist.ShoppingListFragment;
import fr.enac.goshopping.notification.GeofenceTransitionIntentService;
//import fr.enac.goshopping.notification.LocationNotificationActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,CalendarFragment.OnFragmentInteractionListener,
        ShoppingListFragment.OnFragmentInteractionListener,ShopFragment.OnFragmentInteractionListener,
        NewShopFragment.OnFragmentInteractionListener, NewArticleFragment.OnFragmentInteractionListener,
        NewListFragment.OnFragmentInteractionListener, RappelsFragment.OnFragmentInteractionListener,
        ShoppingListContent.OnFragmentInteractionListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status>,
        LocationListener, ManageShopFragment.OnFragmentInteractionListener {


    private int MY_PERMISSIONS_REQUEST_FINE_LOCATION;
    private GoShoppingDBHelper dbHelper;
    private FloatingActionButton fab;
    private Location mLastLocation;
    private int state;
    private int MY_PERMISSIONS_REQUEST_COARSE_LOCATION;

    GoogleApiClient mGoogleApiClient;
    ArrayList<Geofence> mGeofenceList = new ArrayList<>();
    PendingIntent mGeofencePendingIntent;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private String mLastUpdateTime;
    private GeofencingRequest geofencingRequest;

    /**
     * Creer l'activite
     * Présentation
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createLocationRequest();
        //Création de l'instance de l'API Google
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

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

        this.state = R.id.nav_calendar;
        getFragmentManager().beginTransaction()
                .replace(R.id.content_main, new CalendarFragment())
                .commit();
    }

    @Override
    protected void onResume() {
        mGoogleApiClient.connect();
        super.onResume();

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleFabButton(v);
                }
            });
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //gestion du navigation en fonction de l'option selectionne
    //l'utilisateur est redirige vers le fragment correspondant
    //transition d'ecran
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getFragmentManager();
        Fragment toCommit = null;

        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFabButton(v);
            }
        });
        fab.show();

        if (id == R.id.nav_calendar) {
            state = id;
            toCommit = new CalendarFragment();
        } else if (id == R.id.nav_shopping_list) {
            state = id;
            toCommit = new ShoppingListFragment();
        } else if (id == R.id.nav_shop) {
            state = id;
            toCommit = new ShopFragment();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, toCommit)
                .addToBackStack("MainActivity")
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // gestion du floating button dans les different fragment de l'application
    //transition d'ecran
    public void handleFabButton(View view) {
        switch (state) {
             case R.id.nav_calendar:
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_main, new RappelsFragment())
                        .addToBackStack("CallendarFrag")
                        .commit();
                break;
            case R.id.nav_shopping_list:
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_main, new NewListFragment())
                        .addToBackStack("ListFrag")
                        .commit();
                break;
            case R.id.nav_shop:
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.content_main, new NewShopFragment())
                        .addToBackStack("ShopFrag")
                        .commit();
                break;
            }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    @Override
    public void onResult(@NonNull Status status) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
    }

    private Geofence createGeofence(String id,double latitude, double longitude, int radius){
        return new Geofence.Builder()
                .setRequestId(id)
                .setCircularRegion(
                        latitude,
                        longitude,
                        radius
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build();
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }

    public void createGeofenceAlert(String id, double latitude, double longtitude){
        mGeofenceList.add(createGeofence(id, latitude, longtitude, 750));
        geofencingRequest = getGeofencingRequest();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.GeofencingApi.addGeofences(
                mGoogleApiClient,
                geofencingRequest,
                getGeofencePendingIntent()
        ).setResultCallback(this);
    }

    public void setState(int state){
        this.state = state;
    }

}
