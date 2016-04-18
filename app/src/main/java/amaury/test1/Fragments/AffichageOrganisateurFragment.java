package amaury.test1.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import amaury.test1.AffichageSoiree;
import amaury.test1.AffichageSoireeOrganisateur;
import amaury.test1.MainApplicationVariables;
import amaury.test1.MySQLiteHelper;
import amaury.test1.R;
import amaury.test1.Soiree;
import amaury.test1.Utilisateur;

/**
 * A simple {@link Fragment} subclass.
 */
public class AffichageOrganisateurFragment extends Fragment {


    public AffichageOrganisateurFragment() {
        // Required empty public constructor
    }


    private int idorga;
    private int iduser = MainApplicationVariables.getUserID();
    private Utilisateur orga;
    private ListView maListViewPerso;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_affichage_organisateur, container, false);


        //On récupère l'ID de la soirée
        idorga = getArguments().getInt("idorga");
        Toast.makeText(getActivity(), "Utilisateur : " + idorga, Toast.LENGTH_SHORT).show();

        //On recherche la soirée dans la BDD
        MySQLiteHelper bdd = new MySQLiteHelper(this.getContext());
        orga = bdd.getUtilisateur(idorga);
        bdd.close();

        TextView tv = (TextView) view.findViewById(R.id.textView25);
        String nomprenom = orga.getPrenom()+" "+orga.getNom().toUpperCase();
        tv.setText(nomprenom);

        Button btn = (Button) view.findViewById(R.id.button24);
        bdd = new MySQLiteHelper(this.getContext());
        boolean estsuivi = bdd.estSuiviUser(iduser,idorga);
        bdd.close();

        if(estsuivi){
            btn.setEnabled(false);
            btn.setText("Vous suivez déjà l'organisateur");
        }

        bdd = new MySQLiteHelper(this.getContext());
        List<Soiree> soirees = bdd.getSoireesOrga(idorga);
        bdd.close();

        //Récupération de la listview créée dans le fichier main.xml
        maListViewPerso = (ListView) view.findViewById(R.id.listView4);

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
        SimpleAdapter mSchedule = new SimpleAdapter (this.getContext(), listItem, R.xml.affichageitem,
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
                Toast.makeText(getActivity(), map.get("id"), Toast.LENGTH_SHORT).show();
                //On affichage la soiree dans une nouvelle activité
                Intent da = new Intent();
                da.setClass(v.getContext(), AffichageSoireeOrganisateur.class);
                da.putExtra("id", String.valueOf(map.get("id")));
                startActivity(da);
            }
        });

        //Remplir la barre de notation (étoiles)
        RatingBar rb = (RatingBar) view.findViewById(R.id.ratingBar2);
        rb.setIsIndicator(true);
        bdd = new MySQLiteHelper(this.getContext());
        float nota = bdd.getNotation(idorga);
        bdd.close();
        rb.setRating(nota);

        //Ajout du listener sur le rating barre de notation par l'user courant
        addListenerOnRatingBar();

        return view;
    }

    public void onClickSuivreOrga(View view){
        MySQLiteHelper bdd = new MySQLiteHelper(this.getContext());
        bdd.ajouterUtilisateurSuivi(iduser, idorga);
        bdd.close();
        Toast.makeText(getActivity(), "Organisateur bien suivi", Toast.LENGTH_SHORT).show();
        Button btn = (Button) view.findViewById(R.id.button24);
        btn.setEnabled(false);
        btn.setText("Vous suivez déjà l'organisateur");
    }

    public void addListenerOnRatingBar() {

        RatingBar  rb = (RatingBar) view.findViewById(R.id.ratingBar3);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                MySQLiteHelper bdd = new MySQLiteHelper(getContext());
                bdd.setNotation(iduser,idorga,rating);
                bdd.close();
                Toast.makeText(getActivity(), "Notation enregistrée", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
