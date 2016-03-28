package amaury.test1;

import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.GregorianCalendar;

public class AffichageSoiree extends AppCompatActivity {

    private int ID;
    private Soiree soiree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_soiree);

        //On récupère l'ID de la soirée
        ID = Integer.parseInt(getIntent().getStringExtra("id"));
        Toast.makeText(AffichageSoiree.this, "Objet : "+ID, Toast.LENGTH_SHORT).show();

        //On recherche la soirée dans la BDD
        MySQLiteHelper bdd = new MySQLiteHelper(this);
        soiree = bdd.getSoiree(ID);
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


        MySQLiteHelper bddd = new MySQLiteHelper(this);

        boolean afficheBoutonSuivi = bddd.estSuiviEvent(MainApplicationVariables.getUserID(), soiree.getId());
        if (afficheBoutonSuivi){
            Button btn = (Button) findViewById(R.id.buttonInscrireSoiree);
            btn.setEnabled(false);
            btn.setText("Vous êtes déjà inscrit");
        }

    }


    public void onClickPartageSMS(View view){
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        String message = "Je vais à la soirée "+soiree.getTitre()+" le "+soiree.getDate()+" à "+soiree.getHeure()+" à "+soiree.getCoordonnees()+", ça te dit ?";
        smsIntent.putExtra("sms_body"  , message);

        try {
            startActivity(smsIntent);
            finish();
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AffichageSoiree.this,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }


    public void onClickAjouterCalendrier (View view){
        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, soiree.getTitre());
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, soiree.getCoordonnees());
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, soiree.getDescription());

        GregorianCalendar calDate = new GregorianCalendar(2016,03,30);
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                calDate.getTimeInMillis());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                calDate.getTimeInMillis());

        startActivity(calIntent);
    }

    public void onClickSinscrireEvent(View view){
        MySQLiteHelper bdd = new MySQLiteHelper(this);
        bdd.ajouterEvenementSuivi(MainApplicationVariables.getUserID(), soiree.getId());
        bdd.close();
        Toast.makeText(AffichageSoiree.this, "Inscription confirmée", Toast.LENGTH_SHORT).show();
        Button btn = (Button) findViewById(R.id.buttonInscrireSoiree);
        btn.setEnabled(false);
        btn.setText("Vous êtes déjà inscrit");
    }

    public void onClickVoirOrga(View view){
        Intent da = new Intent();
        da.setClass(view.getContext(), AffichageUtilisateur.class);
        da.putExtra("id", String.valueOf(soiree.getOrganisateur()));
        startActivity(da);
    }

}
