package com.example.parents.alerteseisme;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * MapsActivity récupère dans une ArrayList les coordonnées GPS tous les Seismes
 * mis à jour sur l'url pointé en amont dans la MainActivity. La classe Lance l'API map de
 * Google et affiche tous les points d'intérêts.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Intent intent;
    ArrayList arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //On récupére les informations passé de l'activité précédente
        intent = getIntent();
        arrayList = intent.getParcelableArrayListExtra("listeSeisme");

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
       try{

        mMap = googleMap;
        ArrayList<Seisme> ListeDeSeime = arrayList;

        LatLng seime = null;


        //Affichage des points d'intérêts sur la carte
        for (Seisme s : ListeDeSeime){
            float lat = s.getLatitude();
            float lon = s.getLongitude();
            String name = s.getTitle();
            seime = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(seime).title(name));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seime));
       }
       catch(Exception e){
           e.getMessage();
       }


    }
}

