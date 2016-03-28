package amaury.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreerSoiree extends AppCompatActivity {


    public void onClickajouterSoiree(View view){
        //Recupère le titre
        EditText champTitre = (EditText) findViewById(R.id.champTitre);
        String titre = champTitre.getText().toString();
        //Recupere la description
        EditText champDesc = (EditText) findViewById(R.id.champDescription);
        String desc = champDesc.getText().toString();
        //Recupere le prix
        EditText champPrix = (EditText) findViewById(R.id.champPrix);
        Double prix = Double.parseDouble(champPrix.getText().toString());
        //Recupere le type d argent
        Spinner champCurr = (Spinner) findViewById(R.id.champCurrency);
        String curr = String.valueOf(champCurr.getSelectedItem());
        //Recupere la date
        EditText champDate = (EditText) findViewById(R.id.champDate);
        String date = champDate.getText().toString();
        //Recupere l heure
        EditText champHeure = (EditText) findViewById(R.id.champHeure);
        String heure = champHeure.getText().toString();
        //Recupere les coordonnees
        EditText champCoor = (EditText) findViewById(R.id.champCoordonnes);
        String coor = champCoor.getText().toString();

        //Changer orga pour récupérer l'id de l'utilisateur

        int orga = MainApplicationVariables.getUserID();

        MySQLiteHelper bdd = new MySQLiteHelper(this);
        Soiree soiree = new Soiree(0,titre,desc,prix,curr,date,heure,coor,orga);
        bdd.addSoiree(soiree);
        bdd.close();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_soiree);
    }

    public void onClickRetour(View view){
        Intent da = new Intent();
        da.setClass(this, Accueil.class);
        startActivity(da);
    }
}
