package com.example.parents.alerteseisme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import static android.R.*;
import static android.R.layout.simple_expandable_list_item_1;

/**
 * ListActivity reçoit un conteneur de Seismes et le stocke dans une ArrayList puis affiche ici uniquement le lieu dans une
 * ListView de l'interface activity_list.xml . Si un lieu est séléctionné, ListActivity envoie vers l'interface activity_seisme_selection.xml qui
 * affichera plus de détails sur le Seisme en question.
 */
public class ListActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    ArrayList<Seisme> conteneurRecu = new ArrayList<Seisme>();
    private ListView lv;
    private String[] monTab2;
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    protected double mLatitudeLabel;
    protected double mLongitudeLabel;
    protected static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        buildGoogleApiClient();
        lv = (ListView) findViewById(R.id.Seisme_listView);

        Intent intent = getIntent();
        assert conteneurRecu != null;

        conteneurRecu = (ArrayList<Seisme>) intent.getSerializableExtra("listeSeismedanstransition");

        //On récupère les noms des Seismes dans un tableau
        monTab2 = new String[conteneurRecu.size()];
        for (int j = 0; j < conteneurRecu.size(); j++) {
            monTab2[j] = conteneurRecu.get(j).getPlace().toString();
        }
        //si il n'y a pas de d'objet Seisme dans le conteneur
        if(monTab2.length==0){
            monTab2= new String[]{"(( Pas Seisme enregistré ))"};
        }
        
        //On affiche le nom des lieux des Seismes dans la ListView
        assert lv != null;
        lv.setAdapter(new ArrayAdapter<String>(ListActivity.this, android.R.layout.simple_list_item_1 , monTab2));

                lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Object seismeSelectionne = conteneurRecu.get(position);

                        Intent intent = new Intent(ListActivity.this, SeismeActivity.class);
                        intent.putExtra("SeismeEnvoye", (Seisme) seismeSelectionne);
                        startActivity(intent);
                    }
                }
        );
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        boolean pref = settings.getBoolean("magnitude", true);

        if(pref==true)
            Collections.sort(conteneurRecu, new SortBasedOnMag());
        else
            Collections.sort(conteneurRecu, new SortBasedOnLoc());

        }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeLabel = mLastLocation.getLatitude();
            mLongitudeLabel = mLastLocation.getLongitude();
        }
    }
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }
    }
