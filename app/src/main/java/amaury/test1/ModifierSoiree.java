package amaury.test1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ModifierSoiree extends AppCompatActivity {

    Button timepicker;
    Button datepicker;
    Calendar c = Calendar.getInstance();
    int heure,minute,jour,mois,annee;
    Soiree soiree;
    int idsoiree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_soiree);

        idsoiree = Integer.parseInt(getIntent().getStringExtra("id"));

        MySQLiteHelper bdd = new MySQLiteHelper(this);
        soiree = bdd.getSoiree(idsoiree);
        bdd.close();

        EditText champTitre = (EditText) findViewById(R.id.editText);
        champTitre.setText(soiree.getTitre());

        EditText champDesc = (EditText) findViewById(R.id.editText2);
        champDesc.setText(soiree.getDescription());

        EditText champPrix = (EditText) findViewById(R.id.editText3);
        champPrix.setText(String.valueOf(soiree.getPrix()));

        Spinner champCurr = (Spinner) findViewById(R.id.spinner);
        if(soiree.getCurrency()=="$")
            champCurr.setSelection(1);
        else
            champCurr.setSelection(0);

        EditText champCoor = (EditText) findViewById(R.id.editText4);
        champCoor.setText(soiree.getCoordonnees());

        heure = soiree.getHeures();
        minute = soiree.getMinutes();
        annee = soiree.getAnnee();
        mois = soiree.getMois();
        jour = soiree.getJour();


        //Ajout des listener aux boutons
        timepicker=(Button)findViewById(R.id.button12);
        timepicker.setText(heure + "h" + minute);
        timepicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TimePickerDialog pickerDialog = new TimePickerDialog(ModifierSoiree.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour,
                                          int selectedMinute) {
                        timepicker.setText(selectedHour + "h" + selectedMinute);
                        heure = selectedHour;
                        minute = selectedMinute;
                    }
                }, heure, minute, true);
                pickerDialog.show();
            }
        });
        datepicker=(Button)findViewById(R.id.button7);
        datepicker.setText(jour+"/"+mois+"/"+annee);
        datepicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog pickerDialog = new DatePickerDialog(ModifierSoiree.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        jour=dayOfMonth;
                        mois=monthOfYear+1;
                        annee=year;
                        datepicker.setText(jour+"/"+mois+"/"+annee);
                    }
                }, annee, mois-1, jour);
                pickerDialog.show();
            }
        });


    }


    public void onClickModifierSoiree(View view){
        //Recupère le titre
        EditText champTitre = (EditText) findViewById(R.id.editText);
        String titre = champTitre.getText().toString();
        //Recupere la description
        EditText champDesc = (EditText) findViewById(R.id.editText2);
        String desc = champDesc.getText().toString();
        //Recupere le prix
        EditText champPrix = (EditText) findViewById(R.id.editText3);
        Double prix = Double.parseDouble(champPrix.getText().toString());
        //Recupere le type d argent
        Spinner champCurr = (Spinner) findViewById(R.id.spinner);
        String curr = String.valueOf(champCurr.getSelectedItem());
        //Recupere les coordonnees
        EditText champCoor = (EditText) findViewById(R.id.editText4);
        String coor = champCoor.getText().toString();

        int orga = MainApplicationVariables.getUserID();

        MySQLiteHelper bdd = new MySQLiteHelper(this);
        Soiree soireeTemp = new Soiree(soiree.getId(),titre,desc,prix,curr,jour,mois,annee,heure,minute,coor,orga);
        bdd.modifierSoiree(soireeTemp);
        bdd.close();
        Toast.makeText(ModifierSoiree.this, "Soirée modifiée", Toast.LENGTH_SHORT).show();
        Intent da = new Intent(this,Accueil.class);
        startActivity(da);
    }
}
