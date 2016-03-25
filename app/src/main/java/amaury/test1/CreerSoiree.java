package amaury.test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreerSoiree extends AppCompatActivity {


    public void onClickajouterSoiree(View view){
        EditText champTitre = (EditText) findViewById(R.id.champTitre);
        String titre = champTitre.getText().toString();
        EditText champDesc = (EditText) findViewById(R.id.champDescription);
        String desc = champDesc.getText().toString();
        EditText champPrix = (EditText) findViewById(R.id.champPrix);
        Double prix = Double.parseDouble(champPrix.getText().toString());
        Spinner champCurr = (Spinner) findViewById(R.id.champCurrency);
        String curr = String.valueOf(champCurr.getSelectedItem());
        //String curr = "€";
        EditText champDate = (EditText) findViewById(R.id.champDate);
        String date = champDate.getText().toString();
        EditText champHeure = (EditText) findViewById(R.id.champHeure);
        String heure = champHeure.getText().toString();
        EditText champCoor = (EditText) findViewById(R.id.champCoordonnes);
        String coor = champCoor.getText().toString();

        //Changer orga pour récupérer l'id de l'utilisateur

        int orga = 1;

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
}
