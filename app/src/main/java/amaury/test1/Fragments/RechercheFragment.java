package amaury.test1.Fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import amaury.test1.AffichageSoiree;
import amaury.test1.AffichageSoireeOrganisateur;
import amaury.test1.MySQLiteHelper;
import amaury.test1.R;
import amaury.test1.Soiree;

/**
 * A simple {@link Fragment} subclass.
 */
public class RechercheFragment extends Fragment {

    private ListView maListViewPerso;
    private View view;
    //Variables des filtres
    private int prix=-1;
    private int heure=-1, minute=-1;
    private int jourdeb=-1, moisdeb=-1, anneedeb=-1;
    private int jourfin=-1, moisfin=-1, anneefin=-1;
    private int classement=0; //La position dans la liste d'items des classements, commence à 0

    public RechercheFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_recherche, container, false);

        MySQLiteHelper bdd = new MySQLiteHelper(this.getContext());

        List<Soiree> soirees = bdd.getAllSoirees();

        bdd.close();

        remplirListView(soirees);


        //Ajout des listeners aux boutons
        Button b = (Button) view.findViewById(R.id.button21);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerFiltres();
            }
        });

        //Ajout listener spinner classement
        Spinner s = (Spinner) view.findViewById(R.id.spinner2);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classement = position;
                majFiltresRecherche();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Inflate the layout for this fragment
        return view;

    }

    public void listenerFiltres(){
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(view.getContext());
        helpBuilder.setTitle("Filtres");

        LayoutInflater inflater = getLayoutInflater(Bundle.EMPTY);
        final View checkboxLayout = inflater.inflate(R.layout.popup_filtre_layout, null);
        helpBuilder.setView(checkboxLayout);

        helpBuilder.setPositiveButton("Ok",
        new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                CheckBox cb = (CheckBox) checkboxLayout.findViewById(R.id.checkboxTest1);
                boolean gratuit = cb.isChecked();
                if(gratuit)
                    prix =0;
                else
                    prix = -1;
                majFiltresRecherche();
            }
        });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }

    public void remplirListView(List<Soiree> soirees){
        //Récupération de la listview créée dans le fichier main.xml
        maListViewPerso = (ListView) view.findViewById(R.id.listView3);

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
        SimpleAdapter mSchedule = new SimpleAdapter (getContext(), listItem, R.xml.affichageitem,
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
                da.putExtra("id",String.valueOf(map.get("id")));
                startActivity(da);
            }
        });
    }

    public void majFiltresRecherche(){
        MySQLiteHelper bdd = new MySQLiteHelper(view.getContext());
        List<Soiree> liste = bdd.getAllSoireesFiltres(prix,jourdeb,moisdeb,anneedeb,jourfin,moisfin,anneefin,heure,minute,classement);
        bdd.close();
        remplirListView(liste);
        Toast.makeText(getActivity(), "Liste mise à jour", Toast.LENGTH_SHORT).show();
    }

}
