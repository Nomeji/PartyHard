package amaury.test1;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Amaury Punel on 23/03/2016.
 */
public class Utilisateur {
    private int id;
    private String login;
    private String password;
    private String email;
    private String nom;
    private String prenom;
    private List<Integer> mesSoireesCrees;
    private List<Integer> mesSoireesSuivies;
    private List<Integer> mesUtilisateursSuivis;

    public Utilisateur(){

    }

    public Utilisateur(int id, String login, String password, String email, String nom, String prenom) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        mesSoireesCrees = new LinkedList<>();
        mesSoireesSuivies = new LinkedList<>();
        mesUtilisateursSuivis = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String toString(){
        return "id : "+id+"; login : "+login+"; password : "+password+"; email : "+email+"; nom : "+nom+"; prenom : "+prenom;
    }
}
