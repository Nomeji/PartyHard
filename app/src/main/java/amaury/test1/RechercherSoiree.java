package amaury.test1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class RechercherSoiree extends AppCompatActivity {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher_soiree);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void OnClickRechercherSoiree(View view) {
        //Recupère le (s) type
        int i = 0;
        ArrayList<String> soireesResult = new ArrayList<>();

        ArrayList<String> queryResult = new ArrayList<>();

        if (findViewById(R.id.optSoiree).onCheckIsTextEditor()) {
            String soiree = "soiree";
            i++;
            soireesResult.add(soiree);
        }
        if (findViewById(R.id.optConcert).onCheckIsTextEditor()) {
            String concert = "concert";
            i++;
            soireesResult.add(concert);
        }

        if (findViewById(R.id.optSortie).onCheckIsTextEditor()) {
            String sortie = "sortie";
            i++;
            soireesResult.add(sortie);
        }

        //Gratuit ?
        boolean prix = false;
        if (findViewById(R.id.optSoiree).onCheckIsTextEditor()) {
            prix = true;
        }

        //Recupere la date
        Spinner spinJour = (Spinner) findViewById(R.id.spinJour);
        Double jour = Double.parseDouble(spinJour.toString());

        Spinner spinMois = (Spinner) findViewById(R.id.spinJour);
        Double mois = Double.parseDouble(spinMois.toString());

        Spinner spinAnnee = (Spinner) findViewById(R.id.spinJour);
        Double annee = Double.parseDouble(spinAnnee.toString());

        String dateS = jour.toString() + mois.toString() + annee.toString();
        Double date = Double.parseDouble(dateS);

        //Requetes
        MySQLiteHelper bdd = new MySQLiteHelper(this);
        queryResult = bdd.getRechercheSoiree(i, prix, soireesResult);
        bdd.close();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "RechercherSoiree Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://amaury.test1/http/host/path")
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
                "RechercherSoiree Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://amaury.test1/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
