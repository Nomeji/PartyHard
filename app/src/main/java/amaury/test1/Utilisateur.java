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
    private List<Integer> mesSoireesCrees;
    private List<Integer> mesSoireesSuivies;
    private List<Integer> mesUtilisateursSuivis;

    public Utilisateur(){

    }

    public Utilisateur(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        mesSoireesCrees = new LinkedList<>();
        mesSoireesSuivies = new LinkedList<>();
        mesUtilisateursSuivis = new LinkedList<>();
    }
}
