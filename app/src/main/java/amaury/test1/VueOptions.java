package amaury.test1;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class VueOptions extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        //MySQLiteHelper bdd = new MySQLiteHelper(this);
        //bdd.close();
    }

    public void remplirSoiree(View view){
        //On récupère la BDD
        MySQLiteHelper bdd = new MySQLiteHelper(this);
        List<Soiree> liste = bdd.getAllSoirees();
        for (Soiree s:liste
             ) {
            //L'affichage est encapsulé dans un LinearLayout pour chaque évènement
            LinearLayout tmpL = s.toSelectionLayout(this);
            tmpL.setBackgroundColor(getResources().getColor(R.color.blanc));
            tmpL.setClickable(true);
            //Teste juste le clique sur un évènement
            tmpL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),String.format("Ca marche pas !"),Toast.LENGTH_SHORT).show();
                }
            });
            LinearLayout ll = (LinearLayout) findViewById(R.id.contenuEvent);
            ll.addView(tmpL);
        }
        bdd.close();
    }


    public void onClickAccueil(View view){
        Intent da = new Intent();
        da.setClass(this, Accueil.class);
        startActivity(da);
    }

    public void onClickAjouterEvent(View view){
        /*RelativeLayout tmpL = new RelativeLayout(this);
        TextView tmpT = new TextView(this);
        tmpT.setText(String.format("Salut toi ! Ca va bien ?"));
        tmpL.addView(tmpT);*/
        Soiree s = new Soiree(1,"Titre","Description",15,"€","20/11/2017","21h45","Chez Patrick",1);
        LinearLayout tmpL = s.toSelectionLayout(this);
        tmpL.setBackgroundColor(getResources().getColor(R.color.blanc));
        tmpL.setClickable(true);
        tmpL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),String.format("Ca marche pas !"),Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayout ll = (LinearLayout) findViewById(R.id.contenuEvent);
        ll.addView(tmpL);
        //ScrollView sv = (ScrollView) findViewById(R.id.scrollViewTest);
        //sv.addView(tmpL);
    }
}
