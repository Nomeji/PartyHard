package amaury.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AffichageUtilisateur extends AppCompatActivity {

    private int idorga;
    private int iduser = MainApplicationVariables.getUserID();
    private Utilisateur orga;
    private ListView maListViewPerso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_utilisateur);

        //On récupère l'ID de la soirée
        idorga = Integer.parseInt(getIntent().getStringExtra("id"));
        Toast.makeText(AffichageUtilisateur.this, "Utilisateur : " + idorga, Toast.LENGTH_SHORT).show();

        //On recherche la soirée dans la BDD
        MySQLiteHelper bdd = new MySQLiteHelper(this);
        orga = bdd.getUtilisateur(idorga);
        bdd.close();

        TextView tv = (TextView) findViewById(R.id.tvUserNomPrenom);
        String nomprenom = orga.getPrenom()+" "+orga.getNom().toUpperCase();
        tv.setText(nomprenom);

        Button btn = (Button) findViewById(R.id.buttonSuivreOrganisateur);
        bdd = new MySQLiteHelper(this);
        boolean estsuivi = bdd.estSuiviUser(iduser,idorga);
        bdd.close();

        if(estsuivi){
            btn.setEnabled(false);
            btn.setText("Vous suivez déjà l'organisateur");
        }

        bdd = new MySQLiteHelper(this);
        List<Soiree> soirees = bdd.getSoireesOrga(idorga);
        bdd.close();

        //Récupération de la listview créée dans le fichier main.xml
        maListViewPerso = (ListView) findViewById(R.id.listViewUserProchainEvent);

        //Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        //On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;

        for (Soiree s:soirees
                ) {
            //Création d'une HashMap pour insérer les informations du premier item de notre listView
            map = new HashMap<String, String>();
            //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
            map.put("titre", s.getTitre());
            //on insère un élément description que l'on récupérera dans le textView description créé dans le fichier affichageitem.xml
            map.put("description", s.getDescription());
            //on insère la référence à l'image (convertit en String car normalement c'est un int) que l'on récupérera dans l'imageView créé dans le fichier affichageitem.xml
            map.put("img", String.valueOf(R.drawable.ic_info_black_24dp));
            //On insère l'ID
            map.put("id", String.valueOf(s.getId()));
            //enfin on ajoute cette hashMap dans la arrayList
            listItem.add(map);
        }

        //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.xml.affichageitem,
                new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});

        //On attribut à notre listView l'adapter que l'on vient de créer
        maListViewPerso.setAdapter(mSchedule);

        //Enfin on met un écouteur d'évènement sur notre listView
        maListViewPerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //on récupère la HashMap contenant les infos de notre item (titre, description, img)
                HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
                //on créer une boite de dialogue
                Toast.makeText(AffichageUtilisateur.this, map.get("id"), Toast.LENGTH_SHORT).show();
                //On affichage la soiree dans une nouvelle activité
                Intent da = new Intent();
                da.setClass(v.getContext(), AffichageSoiree.class);
                da.putExtra("id", String.valueOf(map.get("id")));
                startActivity(da);
            }
        });

    }

    public void onClickSuivreOrga(View view){
        MySQLiteHelper bdd = new MySQLiteHelper(this);
        bdd.ajouterUtilisateurSuivi(iduser, idorga);
        bdd.close();
        Toast.makeText(AffichageUtilisateur.this, "Organisateur bien suivi", Toast.LENGTH_SHORT).show();
        Button btn = (Button) findViewById(R.id.buttonSuivreOrganisateur);
        btn.setEnabled(false);
        btn.setText("Vous suivez déjà l'organisateur");
    }
}
