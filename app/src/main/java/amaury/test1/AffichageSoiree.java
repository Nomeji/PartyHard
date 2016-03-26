package amaury.test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AffichageSoiree extends AppCompatActivity {

    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_soiree);

        //On récupère l'ID de la soirée
        ID = Integer.parseInt(getIntent().getStringExtra("id"));
        Toast.makeText(AffichageSoiree.this, "Objet : "+ID, Toast.LENGTH_SHORT).show();

        //On recherche la soirée dans la BDD
        MySQLiteHelper bdd = new MySQLiteHelper(this);
        Soiree soiree = bdd.getSoiree(ID);
        bdd.close();

        //On change les changes TextView pour correspondre aux données de la soirée
        TextView tv;
        tv = (TextView) findViewById(R.id.tvAffTitre);
        tv.setText(soiree.getTitre());

        tv = (TextView) findViewById(R.id.tvAffDesc);
        tv.setText(soiree.getDescription());

        tv = (TextView) findViewById(R.id.tvAffIDOrga);
        tv.setText(String.valueOf(soiree.getOrganisateur()));

        tv = (TextView) findViewById(R.id.tvAffDate);
        tv.setText(String.valueOf(soiree.getDate()));

        tv = (TextView) findViewById(R.id.tvAffHeure);
        tv.setText(String.valueOf(soiree.getHeure()));

        tv = (TextView) findViewById(R.id.tvAffPrix);
        String prix = String.valueOf(soiree.getPrix())+soiree.getCurrency();
        tv.setText(prix);

        tv = (TextView) findViewById(R.id.tvAffCoor);
        tv.setText(soiree.getCoordonnees());


    }




}
