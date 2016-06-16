package com.example.parents.alerteseisme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * MainActivity va traiter et stocker dans une ArrayList tous les Seismes recencés à partir d'un fichier pointé par une url de USGS.
 * La structure de la ressource en ligne est en Json. La connexion en AsyncTask sera également gérée au sein de la classe avec un test de connexion
 * pour savoir dans quel mode la connexion est établie.
 */
public class MainActivity extends AppCompatActivity {
    public ArrayList<Seisme> conteneur = new ArrayList();
    //on définit deux conteneurs
    //private ImageButton b1, b2;
    ImageButton carte;
    ImageButton vue_de_seisme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        //Changement d'activité avec l'image button pour aller sur GoogleMap
        carte = (ImageButton) findViewById(R.id.carte_imageButton);

        //Changement d'activité avec l'image button pour aller sur les vues en détails
        vue_de_seisme = (ImageButton) findViewById(R.id.liste_imageButton);




        //Test de la connexion
        Connectivity();

        vue_de_seisme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afficherleslistesvues(v);
            }
        });

    }


    //Fonction appelant la Google Map
    public void afficherGoogleMap(View view) {

        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("listeSeisme", conteneur);
        startActivity(intent);

    }

    //Fonction appelant les listes vues

    public void afficherleslistesvues(View view) {

        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        intent.putExtra("listeSeismedanstransition", conteneur);
        startActivity(intent);

    }

    public void afficherPreferences(View view) {

        Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
        intent.putExtra("listePreferences", conteneur);
        startActivity(intent);

    }

    public void Connectivity() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);


        //On récupère les informations sur l'état de la connexion
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        assert activeNetwork != null;
        boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;


        //On affiche le résultat
        if (isConnected) {
            if (isWiFi) {
                Toast.makeText(this, "Wifi", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "3G", Toast.LENGTH_LONG).show();
            }

        }
        else
            Toast.makeText(this, "Pas de connexion", Toast.LENGTH_LONG).show();
    }


}