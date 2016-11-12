package fr.enac.goshopping;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import fr.enac.goshopping.database.GoShoppingDBHelper;
import fr.enac.goshopping.fragment.CalendarFragment;
import fr.enac.goshopping.fragment.ManageShopFragment;
import fr.enac.goshopping.fragment.NewArticleFragment;
import fr.enac.goshopping.fragment.NewListFragment;
import fr.enac.goshopping.fragment.NewShopFragment;
import fr.enac.goshopping.fragment.RappelsFragment;
import fr.enac.goshopping.fragment.SettingsFragment;
import fr.enac.goshopping.fragment.ShopFragment;
import fr.enac.goshopping.fragment.ShoppingListContent;
import fr.enac.goshopping.fragment.ShoppingListFragment;
import fr.enac.goshopping.notification.GeofenceTransitionIntentService;
import fr.enac.goshopping.notification.LocationNotificationActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SettingsFragment.OnFragmentInteractionListener,
        CalendarFragment.OnFragmentInteractionListener, ShoppingListFragment.OnFragmentInteractionListener,
        ShopFragment.OnFragmentInteractionListener, NewShopFragment.OnFragmentInteractionListener,
        NewArticleFragment.OnFragmentInteractionListener, NewListFragment.OnFragmentInteractionListener,
        RappelsFragment.OnFragmentInteractionListener, ShoppingListContent.OnFragmentInteractionListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status>,
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createLocationRequest();
        System.out.println("COOOOOOOOOUUUUUUUUCCCCOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOUUUUUUU");
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
        /*createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());*/
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

    /*
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
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
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

        /*if (id == R.id.nav_go_shopping) {

        } else*/
        if (id == R.id.nav_calendar) {
            state = id;
            toCommit = new CalendarFragment();
        } else if (id == R.id.nav_shopping_list) {
            state = id;
            toCommit = new ShoppingListFragment();
        } else if (id == R.id.nav_shop) {
            //thirdLocationTest();
            state = id;
            toCommit = new ShopFragment();
        } else if (id == R.id.nav_settings) {
            state = id;
            toCommit = new SettingsFragment();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.content_main, toCommit)
                .addToBackStack("MainActivity")
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void handleFabButton(View view) {
        switch (state) {
            /*case R.id.nav_go_shopping:
                break;*/
            case R.id.nav_calendar:
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_main, new RappelsFragment())
                        .addToBackStack("CallendarFrag")
                        .commit();
                break;
            case R.id.nav_shopping_list:
                //fab.setVisibility(View.INVISIBLE);
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_main, new NewListFragment())
                        .addToBackStack("ListFrag")
                        .commit();
                break;
            case R.id.nav_shop:
                //fab.setVisibility(View.INVISIBLE);
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.content_main, new NewShopFragment())
                        .addToBackStack("ShopFrag")
                        .commit();
                break;
            case R.id.nav_settings:
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
        mGeofenceList.add(createGeofence("1", 50.6292496, 3.057256, 750));
        geofencingRequest = getGeofencingRequest();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.GeofencingApi.addGeofences(
                mGoogleApiClient,
                geofencingRequest,
                getGeofencePendingIntent()
        ).setResultCallback(this);
    }

    protected void startLocationUpdates() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        /*Location test = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        System.out.println(test.getLatitude() + ","  + test.getLongitude());*/
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
        System.out.println(location.getLatitude() + "," + location.getLongitude());
        //updateUI();
    }

    public Geofence createGeofence(String id,double latitude, double longitude, int radius){
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
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        return PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }

}
