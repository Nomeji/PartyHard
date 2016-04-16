package amaury.test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class RechercherSoiree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher_soiree);
    }

    public void OnClickRechercherSoiree(View view) {
        //Recupère le (s) type
        //int i = 0;
        List<Soiree> listeSoiree;

        boolean soiree = false;
        boolean concert = false;
        boolean sortie = false;

        ArrayList<Soiree> queryResult = new ArrayList<>();

        if (findViewById(R.id.optSoiree).onCheckIsTextEditor()) {
            /*String soiree = "soiree";
            i++;
            soireesResult.add(soiree);*/
            soiree = true;
        }
        if (findViewById(R.id.optConcert).onCheckIsTextEditor()) {
            /*String concert = "concert";
            i++;
            soireesResult.add(concert);*/
            concert = true;
        }

        if (findViewById(R.id.optSortie).onCheckIsTextEditor()) {
           /* String sortie = "sortie";
            i++;
            soireesResult.add(sortie);*/
            sortie = true;
        }

        //Gratuit ?
        boolean gratuit = false;
        if (findViewById(R.id.optSoiree).onCheckIsTextEditor()) {
            gratuit = true;
        }

        //Recupere la date
        Spinner spinJour = (Spinner) findViewById(R.id.spinJour);
        int jour = Integer.parseInt(spinJour.getSelectedItem().toString());

        Spinner spinMois = (Spinner) findViewById(R.id.spinMois);
        int mois = Integer.parseInt(spinMois.getSelectedItem().toString());

        Spinner spinAnnee = (Spinner) findViewById(R.id.spinAnnee);
        int annee = Integer.parseInt(spinAnnee.getSelectedItem().toString());

        //Requetes
        MySQLiteHelper bdd = new MySQLiteHelper(this);
        listeSoiree = bdd.getRechercheSoiree(gratuit, soiree, concert, sortie, jour, mois, annee);
        bdd.close();

        for (int i = 0; i < listeSoiree.size();i++){
            System.out.println(listeSoiree.get(i).getTitre());
        }
    }
}
