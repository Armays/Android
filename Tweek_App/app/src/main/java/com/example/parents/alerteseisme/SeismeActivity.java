package com.example.parents.alerteseisme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * SeismeActivity récupère l'objet Seisme séléctionné dans la ListView et dispatch dans l'interface activiy_seisme_selection.xml
 * les informations pour pouvoir afficher les infos en détails du Seisme.
 */
public class SeismeActivity extends AppCompatActivity {
    Seisme seisme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seisme_selection);

        Intent intent = getIntent();

        TextView placeTV = (TextView) findViewById(R.id.place);
        TextView typeTV = (TextView) findViewById(R.id.type);
        TextView alertTV = (TextView) findViewById(R.id.alert);
        TextView statusTV = (TextView) findViewById(R.id.status);
        TextView codeTV = (TextView) findViewById(R.id.code);
        TextView timeTV = (TextView) findViewById(R.id.temps);
        TextView magTV = (TextView) findViewById(R.id.magnitude);

        seisme = (Seisme) intent.getSerializableExtra("SeismeEnvoye");


        assert placeTV != null;
        assert typeTV != null;
        assert alertTV != null;
        assert statusTV != null;
        assert codeTV != null;
        assert timeTV != null;
        assert magTV != null;


        placeTV.setText(seisme.getPlace());
        typeTV.setText(seisme.getType());
        alertTV.setText(seisme.getAlert());
        statusTV.setText(seisme.getStatus());
        codeTV.setText(seisme.getCode());
        timeTV.setText(seisme.getDate());
        magTV.setText("((" +" "+seisme.getMag()+" "+"))");


    }


}

