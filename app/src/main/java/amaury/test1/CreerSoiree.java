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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class CreerSoiree extends AppCompatActivity {

    Button timepicker;
    Button datepicker;
    Calendar c = Calendar.getInstance();
    int heure,minute,jour,mois,annee;

    public void onClickajouterSoiree(View view){
        //Recupère le titre
        EditText champTitre = (EditText) findViewById(R.id.champTitre);
        String titre = champTitre.getText().toString();
        //Recupere la description
        EditText champDesc = (EditText) findViewById(R.id.champDescription);
        String desc = champDesc.getText().toString();
        //Recupere le prix
        EditText champPrix = (EditText) findViewById(R.id.champPrix);
        Double prix = Double.parseDouble(champPrix.getText().toString());
        //Recupere le type d argent
        Spinner champCurr = (Spinner) findViewById(R.id.champCurrency);
        String curr = String.valueOf(champCurr.getSelectedItem());
        //Recupere les coordonnees
        EditText champCoor = (EditText) findViewById(R.id.champCoordonnes);
        String coor = champCoor.getText().toString();

        int orga = MainApplicationVariables.getUserID();

        MySQLiteHelper bdd = new MySQLiteHelper(this);
        Soiree soiree = new Soiree(0,titre,desc,prix,curr,jour,mois,annee,heure,minute,coor,orga);
        bdd.addSoiree(soiree);
        bdd.close();
        Toast.makeText(CreerSoiree.this, "Soirée créée", Toast.LENGTH_SHORT).show();
        Intent da = new Intent(this,Accueil.class);
        startActivity(da);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_soiree);
        timepicker=(Button)findViewById(R.id.buttonChoisirHeure);
        timepicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TimePickerDialog pickerDialog = new TimePickerDialog(CreerSoiree.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour,
                                          int selectedMinute) {
                        timepicker.setText(selectedHour + "h" + selectedMinute);
                        heure = selectedHour;
                        minute = selectedMinute;
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
                pickerDialog.show();
            }
        });
        datepicker=(Button)findViewById(R.id.buttonChoisirDate);
        datepicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog pickerDialog = new DatePickerDialog(CreerSoiree.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        jour=dayOfMonth;
                        mois=monthOfYear+1;
                        annee=year;
                        datepicker.setText(jour+"/"+mois+"/"+annee);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
            }
        });
    }
    }

