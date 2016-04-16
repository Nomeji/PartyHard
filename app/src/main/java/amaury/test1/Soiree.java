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
    //private String date;
    private int jour, mois, annee;
    //private String heure;
    private int heures, minutes;
    private String coordonnees;
    private int organisateur;

    public Soiree(){
    }

    public Soiree(int id, String titre, String description, double prix, String currency, int jour, int mois, int annee, int heures, int minutes, String coordonnees, int organisateur) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.currency = currency;
        this.jour = jour;
        this.mois = mois;
        this.annee = annee;
        this.heures = heures;
        this.minutes = minutes;
        this.coordonnees = coordonnees;
        this.organisateur = organisateur;
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

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getHeures() {
        return heures;
    }

    public void setHeures(int heures) {
        this.heures = heures;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
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
        return "titre : "+titre+"; description : "+description+"; prix : "+prix+" "+currency+"; date : "+annee+mois+jour+"; heure : "+heures+minutes+"; coordonnees : "+coordonnees+"; organisateur : "+organisateur;
    }
}
