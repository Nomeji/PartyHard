package amaury.test1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Amaury Punel on 23/03/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    //Column Names
    //Soiree
    private static final String KEY_ID="id";
    private static final String KEY_TITLE="titre";
    private static final String KEY_DESC="description";
    private static final String KEY_PRIX="prix";
    private static final String KEY_CURR="currency";
    private static final String KEY_DATE="date";
    private static final String KEY_HEURE="heure";
    private static final String KEY_COORD="coordonnees";
    private static final String KEY_ORGA="organisateur";
    //Utilisateurs
    private static final String KEY_LOGIN="login";
    private static final String KEY_PSWRD="password";
    private static final String KEY_EMAIL="email";
    private static final String KEY_NOM="nom";
    private static final String KEY_PRENOM="prenom";


    private static final String[] COLUMNS_SOIREE = {KEY_ID,KEY_TITLE,KEY_DESC,KEY_PRIX,KEY_CURR,KEY_DATE,KEY_HEURE,KEY_COORD,KEY_ORGA};
    private static final String[] COLUMNS_UTILISATEUR = {KEY_ID,KEY_LOGIN,KEY_PSWRD,KEY_EMAIL,KEY_NOM,KEY_PRENOM};


    //Database Version
    private static final int DATABASE_VERSION = 3;

    //Database Name
    private static final String DATABASE_NAME = "soireeDB";

    //Tables Name
    private static final String TABLE_SOIREES = "soirees";
    private static final String TABLE_USERS = "utilisateurs";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //ADMIN
    public void deleteBDD(){

    }

    //*************************
    //METHODES POUR LES SOIREES
    //*************************
    //Ajoute une soirée à la BDD
    public void addSoiree(Soiree soiree){
        //for logging
        Log.d("addSoiree", soiree.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //values.put(KEY_ID,soiree.getId());
        values.put(KEY_TITLE, soiree.getTitre());
        values.put(KEY_DESC, soiree.getDescription());
        values.put(KEY_PRIX, soiree.getPrix());
        values.put(KEY_CURR, soiree.getCurrency());
        values.put(KEY_DATE, soiree.getDate());
        values.put(KEY_HEURE, soiree.getHeure());
        values.put(KEY_COORD, soiree.getCoordonnees());
        values.put(KEY_ORGA, soiree.getOrganisateur());

        // 3. insert
        db.insert(TABLE_SOIREES, null, values);

        // 4. close
        db.close();
    }

    //Obtenir une soiree grace a son ID
    public Soiree getSoiree(int id){
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(TABLE_SOIREES, COLUMNS_SOIREE, "id = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
        else return null;

        // 4. build Soiree object
        Soiree soiree = new Soiree();
        soiree.setId(Integer.parseInt(cursor.getString(0)));
        soiree.setTitre(cursor.getString(1));
        soiree.setDescription(cursor.getString(2));
        soiree.setPrix(Double.parseDouble(cursor.getString(3)));
        soiree.setCurrency(cursor.getString(4));
        soiree.setDate(cursor.getString(5));
        soiree.setHeure(cursor.getString(6));
        soiree.setCoordonnees(cursor.getString(7));
        soiree.setOrganisateur(Integer.parseInt(cursor.getString(8)));

        //log
        Log.d("getSoiree(" + id + ")", soiree.toString());

        return soiree;
    }

    //Obtenir toutes les soirées
    public List<Soiree> getAllSoirees(){
        List<Soiree> soirees = new LinkedList<>();

        // 1. build the query
        String query = "SELECT * FROM "+TABLE_SOIREES;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        // 3. go over each row, build book and add it to list
        Soiree soiree = null;
        if(cursor.moveToFirst()){
            do{
                soiree = new Soiree();
                soiree.setId(Integer.parseInt(cursor.getString(0)));
                soiree.setTitre(cursor.getString(1));
                soiree.setDescription(cursor.getString(2));
                soiree.setPrix(Double.parseDouble(cursor.getString(3)));
                soiree.setCurrency(cursor.getString(4));
                soiree.setDate(cursor.getString(5));
                soiree.setHeure(cursor.getString(6));
                soiree.setCoordonnees(cursor.getString(7));
                soiree.setOrganisateur(Integer.parseInt(cursor.getString(8)));

                soirees.add(soiree);
            }while(cursor.moveToNext());
        }

        Log.d("getAllSoirees()", soirees.toString());

        // 4. return soirees
        return soirees;
    }

    //Supprimer une soirée
    public void deleteSoiree(Soiree soiree){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_SOIREES, KEY_ID + " = ?", new String[]{String.valueOf(soiree.getId())});

        // 3. close
        db.close();

        //log
        Log.d("deleteSoiree", soiree.toString());
    }



    //*************************
    //METHODES POUR LES UTILISATEURS
    //*************************
    //Ajoute un utilisateur à la BDD
    public void addUtilisateur(Utilisateur utilisateur){
        //for logging
        Log.d("addUtilisateur", utilisateur.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //values.put(KEY_ID,soiree.getId());
        values.put(KEY_LOGIN, utilisateur.getLogin());
        values.put(KEY_PSWRD, utilisateur.getPassword());
        values.put(KEY_EMAIL, utilisateur.getEmail());
        values.put(KEY_NOM, utilisateur.getNom());
        values.put(KEY_PRENOM, utilisateur.getPrenom());

        // 3. insert
        db.insert(TABLE_USERS, null, values);

        // 4. close
        db.close();
    }

    //Obtenir un utilisateur grace a son ID
    public Utilisateur getUtilisateur(int id){
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(TABLE_USERS, COLUMNS_UTILISATEUR, "id = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
        else return null;

        // 4. build Soiree object
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(Integer.parseInt(cursor.getString(0)));
        utilisateur.setLogin(cursor.getString(1));
        utilisateur.setPassword(cursor.getString(2));
        utilisateur.setEmail(cursor.getString(3));
        utilisateur.setNom(cursor.getString(4));
        utilisateur.setPrenom(cursor.getString(5));

        //log
        Log.d("getSoiree(" + id + ")", utilisateur.toString());

        return utilisateur;
    }

    //Savoir si un utilisateur existe avec son login et password
    //Retourne son ID
    public int utilisateurExiste(String login, String password){
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_ID}, "login = ? AND password = ?", new String[]{String.valueOf(login),String.valueOf(password)}, null, null, null, null);

        // 3. if we got results get the first one
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return -1;
    }

    //Obtenir tous les utilisateurs
    public List<Utilisateur> getAllUtilisateurs(){
        List<Utilisateur> utilisateurs = new LinkedList<>();

        // 1. build the query
        String query = "SELECT * FROM "+TABLE_USERS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Utilisateur utilisateur = null;
        if(cursor.moveToFirst()){
            do{
                utilisateur = new Utilisateur();
                utilisateur.setId(Integer.parseInt(cursor.getString(0)));
                utilisateur.setLogin(cursor.getString(1));
                utilisateur.setPassword(cursor.getString(2));
                utilisateur.setEmail(cursor.getString(3));
                utilisateur.setNom(cursor.getString(4));
                utilisateur.setPrenom(cursor.getString(5));

                utilisateurs.add(utilisateur);
            }while(cursor.moveToNext());
        }

        Log.d("getAllSoirees()", utilisateurs.toString());

        // 4. return soirees
        return utilisateurs;
    }

    //Supprimer un utilisateur
    public void deleteUtilisateur(Utilisateur utilisateur){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_USERS, KEY_ID + " = ?", new String[]{String.valueOf(utilisateur.getId())});

        // 3. close
        db.close();

        //log
        Log.d("deleteSoiree", utilisateur.toString());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS soirees");

        //SQL statement to create user table
        String CREATE_USER_TABLE = "CREATE TABLE utilisateurs ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "login TEXT NOT NULL,"+
                "password TEXT NOT NULL,"+
                "email TEXT NOT NULL,"+
                "nom TEXT NOT NULL,"+
                "prenom TEXT NOT NULL);";

        //SQL statement to create soiree table
        String CREATE_SOIREE_TABLE = "CREATE TABLE soirees ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "titre TEXT NOT NULL,"+
                "description TEXT NOT NULL,"+
                "prix DOUBLE NOT NULL,"+
                "currency TEXT NOT NULL,"+
                "date TEXT NOT NULL,"+
                "heure TEXT NOT NULL,"+
                "coordonnees TEXT NOT NULL,"+
                "organisateur INTEGER NOT NULL);";

        //create soirees table
        db.execSQL(CREATE_SOIREE_TABLE);
        //create utilisateurs table
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older soirees table if existed
        db.execSQL("DROP TABLE IF EXISTS soirees");
        db.execSQL("DROP TABLE IF EXISTS utilisateurs");

        //Create fresh soirees table
        this.onCreate(db);
    }
}
