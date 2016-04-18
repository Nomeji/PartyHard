package amaury.test1.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.GregorianCalendar;

import amaury.test1.MainApplicationVariables;
import amaury.test1.MySQLiteHelper;
import amaury.test1.R;
import amaury.test1.Soiree;

/**
 * A simple {@link Fragment} subclass.
 */
public class AffichageSoireeFragment extends Fragment {


    public AffichageSoireeFragment() {
        // Required empty public constructor
    }


    private Soiree soiree;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_affichage_soiree, container, false);

        int ID = getArguments().getInt("id");

        //On recherche la soirée dans la BDD
        MySQLiteHelper bdd = new MySQLiteHelper(getContext());
        soiree = bdd.getSoiree(ID);
        bdd.close();

        //On change les changes TextView pour correspondre aux données de la soirée
        TextView tv;
        tv = (TextView) view.findViewById(R.id.textView13);
        tv.setText(soiree.getTitre());

        tv = (TextView) view.findViewById(R.id.textView22);
        tv.setText(soiree.getDescription());

        tv = (TextView) view.findViewById(R.id.textView15);
        String dateSoiree = soiree.getJour()+"/"+soiree.getMois()+"/"+soiree.getAnnee();
        tv.setText(dateSoiree);

        tv = (TextView) view.findViewById(R.id.textView17);
        String heureSoiree = soiree.getHeures()+"h"+soiree.getMinutes();
        tv.setText(heureSoiree);

        tv = (TextView) view.findViewById(R.id.textView19);
        String prix = String.valueOf(soiree.getPrix())+soiree.getCurrency();
        tv.setText(prix);

        tv = (TextView) view.findViewById(R.id.textView21);
        tv.setText(soiree.getCoordonnees());


        MySQLiteHelper bddd = new MySQLiteHelper(getContext());


        boolean afficheBoutonSuivi = bddd.estSuiviEvent(MainApplicationVariables.getUserID(), soiree.getId());
        bdd.close();
        if (afficheBoutonSuivi){
            Button btn = (Button) view.findViewById(R.id.button27);
            btn.setText("Se désinscrire");
            btn.setOnClickListener(new listenerDesincrire());
        }
        else{
            Button btn = (Button) view.findViewById(R.id.button27);
            btn.setText("S'inscrire");
            btn.setOnClickListener(new listenerInscrire());
        }

        //Ajout des listeners aux boutons
        Button b;
        b = (Button) view.findViewById(R.id.button25);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);

                smsIntent.setData(Uri.parse("smsto:"));
                smsIntent.setType("vnd.android-dir/mms-sms");
                String message = "Je vais à la soirée "+soiree.getTitre()+" le "+soiree.getJour()+"/"+soiree.getMois()+"/"+soiree.getAnnee()+" à "+soiree.getHeures()+"h"+soiree.getMinutes()+" à "+soiree.getCoordonnees()+", ça te dit ?";
                smsIntent.putExtra("sms_body"  , message);

                try {
                    startActivity(smsIntent);
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(),
                            "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b = (Button) view.findViewById(R.id.button26);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setType("vnd.android.cursor.item/event");
                calIntent.putExtra(CalendarContract.Events.TITLE, soiree.getTitre());
                calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, soiree.getCoordonnees());
                calIntent.putExtra(CalendarContract.Events.DESCRIPTION, soiree.getDescription());

                GregorianCalendar calDate = new GregorianCalendar(soiree.getAnnee(),soiree.getMois(),soiree.getJour(),soiree.getHeures(),soiree.getMinutes());
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        calDate.getTimeInMillis());
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                        calDate.getTimeInMillis());

                startActivity(calIntent);
            }
        });

        return view;
    }

    private class listenerInscrire implements View.OnClickListener {

        public listenerInscrire(){
        }

        @Override
        public void onClick(View v) {
            MySQLiteHelper bdd = new MySQLiteHelper(getContext());
            bdd.ajouterEvenementSuivi(MainApplicationVariables.getUserID(), soiree.getId());
            bdd.close();
            Toast.makeText(getActivity(), "Inscription confirmée", Toast.LENGTH_SHORT).show();
            Button btn = (Button) v.findViewById(R.id.button27);
            btn.setText("Se désinscrire");
            btn.setOnClickListener(new listenerDesincrire());
        }
    }

    private class listenerDesincrire implements View.OnClickListener{

        public listenerDesincrire(){
        }

        @Override
        public void onClick(View v) {
            MySQLiteHelper bdd = new MySQLiteHelper(getContext());
            bdd.supprimerEvenementSuivi(MainApplicationVariables.getUserID(), soiree.getId());
            bdd.close();
            Toast.makeText(getActivity(), "Désinscription confirmée", Toast.LENGTH_SHORT).show();
            Button btn = (Button) v.findViewById(R.id.button27);
            btn.setText("S'inscrire");
            btn.setOnClickListener(new listenerInscrire());
        }
    }

}
