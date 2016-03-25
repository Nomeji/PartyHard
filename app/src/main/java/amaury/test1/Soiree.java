package amaury.test1;

import android.content.Context;
import android.text.Layout;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Amaury Punel on 15/03/2016.
 */
public class Soiree {

    private int id;
    private String titre;
    private String description;
    private double prix;
    private String currency;
    private String date;
    private String heure;
    private String coordonnees;
    private int organisateur;

    public Soiree(){
    }

    public Soiree(int id, String titre, String description, double prix, String currency, String date, String heure, String coordonnees, int organisateur) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.currency = currency;
        this.date = date;
        this.heure = heure;
        this.coordonnees = coordonnees;
        this.organisateur = organisateur;
    }

    public LinearLayout toSelectionLayout(Context context){
        LinearLayout temp = new LinearLayout(context);
        temp.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 2.0f;
        TextView tvTitre = new TextView(context);
        tvTitre.setText(this.titre);
        tvTitre.setLayoutParams(params);

        TextView tvPrix = new TextView(context);
        String prixT = this.prix+this.currency;
        tvPrix.setText(prixT);

        /*TextView tvDesc = new TextView(context);
        tvDesc.setText(this.description);*/

        /*TextView tvDate = new TextView(context);
        tvDate.setText(this.date);

        TextView tvHeure = new TextView(context);
        tvHeure.setText(this.heure);*/

        TextView tvDateHeure = new TextView(context);
        String dateHeure = "Le "+this.date+" à "+this.heure;
        tvDateHeure.setText(dateHeure);
        tvDateHeure.setLayoutParams(params);

        TextView tvCoordonnees = new TextView(context);
        tvCoordonnees.setText(this.coordonnees);

       /* temp.addView(tvTitre);
        //temp.addView(tvDesc);
        temp.addView(tvPrix);
       // temp.addView(tvDate);
       // temp.addView(tvHeure);
        temp.addView(tvDateHeure);
        temp.addView(tvCoordonnees);*/

        LinearLayout ll1 = new LinearLayout(context);
        ll1.setOrientation(LinearLayout.HORIZONTAL);
        tvPrix.setGravity(3);
        ll1.addView(tvTitre);
        ll1.addView(tvPrix);

        LinearLayout ll2 = new LinearLayout(context);
        ll2.setOrientation(LinearLayout.HORIZONTAL);
        ll2.addView(tvDateHeure);
        ll2.addView(tvCoordonnees);

        temp.addView(ll1);
        temp.addView(ll2);
        return temp;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(String coordonnees) {
        this.coordonnees = coordonnees;
    }

    public int getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(int organisateur) {
        this.organisateur = organisateur;
    }

    @Override
    public String toString(){
        return "titre : "+titre+"; description : "+description+"; prix : "+prix+" "+currency+"; date : "+date+"; heure : "+heure+"; coordonnees : "+coordonnees+"; organisateur : "+organisateur;
    }
}
