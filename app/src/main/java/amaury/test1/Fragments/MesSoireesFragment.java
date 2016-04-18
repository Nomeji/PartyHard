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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import amaury.test1.AffichageSoireeOrganisateur;
import amaury.test1.MainApplicationVariables;
import amaury.test1.MySQLiteHelper;
import amaury.test1.R;
import amaury.test1.Soiree;


/**
 * A simple {@link Fragment} subclass.
 */
public class MesSoireesFragment extends Fragment {


    public MesSoireesFragment() {
        // Required empty public constructor
    }


    ListView maListViewPerso;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_mes_soirees, container, false);

        //Récupération de la listview créée dans le fichier main.xml
        maListViewPerso = (ListView) view.findViewById(R.id.listView5);

        remplissageListView();

        Button b = (Button) view.findViewById(R.id.button28);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remplissageListView();
            }
        });


        setTextNbSoirees();

        return view;
    }

    public void remplissageListView(){
        MySQLiteHelper bdd = new MySQLiteHelper(this.getContext());
        List<Soiree> soirees = bdd.getSoireesSuivies(MainApplicationVariables.getUserID());
        bdd.close();

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

        setTextNbSoirees();
    }

    public void setTextNbSoirees(){
        MySQLiteHelper bdd = new MySQLiteHelper(getContext());
        int nbsoiree = bdd.nbSoireesSuivies(MainApplicationVariables.getUserID());
        int nbsoireeaujourdhui = bdd.nbSoireesSuiviesAujourdhui(MainApplicationVariables.getUserID());
        bdd.close();

        TextView t = (TextView) view.findViewById(R.id.textView29);
        t.setText(String.valueOf(nbsoiree));

        t = (TextView) view.findViewById(R.id.textView32);
        t.setText(String.valueOf(nbsoireeaujourdhui));
    }

}
