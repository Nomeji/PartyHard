package amaury.test1.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.GregorianCalendar;

import amaury.test1.Accueil;
import amaury.test1.MainApplicationVariables;
import amaury.test1.ModifierSoiree;
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
    private int idorga;
    private int iduser = MainApplicationVariables.getUserID();
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_affichage_soiree, container, false);

        final int ID = getArguments().getInt("id");

        //On recherche la soirée dans la BDD
        MySQLiteHelper bdd = new MySQLiteHelper(getContext());
        soiree = bdd.getSoiree(ID);
        idorga = bdd.getOrgaIDSoiree(soiree.getId());
        bdd.close();

        iduser = MainApplicationVariables.getUserID();

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


        //Configuration du bouton inscrire/désinscrire
        MySQLiteHelper bddd = new MySQLiteHelper(getContext());
        boolean afficheBoutonSuivi = bddd.estSuiviEvent(MainApplicationVariables.getUserID(), soiree.getId());
        bddd.close();
        if (afficheBoutonSuivi) {
            Button btn = (Button) view.findViewById(R.id.button27);
            btn.setText("Se désinscrire");
            btn.setOnClickListener(new listenerDesincrire());
        } else {
            Button btn = (Button) view.findViewById(R.id.button27);
            btn.setText("S'inscrire");
            btn.setOnClickListener(new listenerInscrire());
        }


        //On affiche soit le bouton inscription/desinscription ou bien modifier/supprimer
        //En fonction de l'ID de l'utilisateur

        //Cas 1 : si l'organisateur est l'utilisateur courant, afficher modifier/supprimer
        if(idorga==iduser) {
            LinearLayout l = (LinearLayout) view.findViewById(R.id.linearLayoutBtnInscrireModifier);
            l.removeAllViews();
            Button b = new Button(view.getContext());
            b.setText("Modifier");
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(view.getContext(), ModifierSoiree.class);
                    i.putExtra("id",String.valueOf(soiree.getId()));
                    startActivity(i);
                }
            });
            l.addView(b);
            b = new Button(view.getContext());
            b.setText("Supprimer");
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder helpBuilder = new AlertDialog.Builder(view.getContext());
                    helpBuilder.setTitle("Filtres");
                    helpBuilder.setMessage("Êtes-vous sûr de vouloir supprimer cette soirée ?");

                    LayoutInflater inflater = getLayoutInflater(Bundle.EMPTY);
                    final View checkboxLayout = inflater.inflate(R.layout.popup_vide, null);
                    helpBuilder.setView(checkboxLayout);

                    helpBuilder.setPositiveButton("Oui",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    MySQLiteHelper bdd = new MySQLiteHelper(view.getContext());
                                    bdd.supprimerSoiree(soiree.getId());
                                    bdd.close();
                                    Toast.makeText(getActivity(), "Soirée supprimée", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(view.getContext(), Accueil.class);
                                    startActivity(i);
                                }
                            });

                    helpBuilder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Ne fait rien
                        }
                    });

                    // Remember, create doesn't show the dialog
                    AlertDialog helpDialog = helpBuilder.create();
                    helpDialog.show();
                }
            });
            l.addView(b);

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
