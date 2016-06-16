package com.example.parents.alerteseisme;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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
 * SplashActivity load l'interface bidon à l'execution de l'application et passe la main
 * à la MainActivity
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public ArrayList<Seisme> conteneur = new ArrayList();
    //on définit deux conteneurs

    //Activité de transition
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bidon);

        //Création d'une AsyncTask
        DownloadTask task = new DownloadTask();
        task.execute("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Splash Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.parents.alerteseisme/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Splash Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.parents.alerteseisme/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                //On récupere le premier lien et on s'y connecte et récupére les données "getInput"
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                //Création d'un reader de data
                InputStreamReader reader = new InputStreamReader(inputStream);
                //On lit les data qu'on enverra
                int data = reader.read();

                while ((data != -1)) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //On fait un try car les datas pourraient ne pas être un JSON
            try {
                JSONObject jsonObject = new JSONObject(result);

                String typeFeatures = jsonObject.getString("features");
                JSONArray arrayFeatures = new JSONArray(typeFeatures);

                //On récupère les informations sur les objets voulus, ici les données contenues dans les properties
                for (int i = 0; i < arrayFeatures.length(); i++) {
                    JSONObject jsonPart = arrayFeatures.getJSONObject(i);

                    String infoProper = jsonPart.getString("properties");
                    JSONObject ObjProper = new JSONObject(infoProper);

                    String infoGeo = jsonPart.getString("geometry");
                    JSONObject ObjGeo = new JSONObject(infoGeo);


                    //on crée notre objet Séisme :)
                    conteneur.add(new Seisme(
                            ObjProper.getString("title"),
                            ObjProper.getString("type"),
                            ObjProper.getString("place"),
                            ObjProper.getString("alert"),
                            ObjProper.getString("status"),
                            ObjProper.getString("code"),
                            ObjProper.getLong("time"),
                            ObjGeo.getString("coordinates"),
                            ObjProper.getString("mag")

                    ));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
