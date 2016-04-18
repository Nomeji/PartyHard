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
    private static final String KEY_JOUR="jour";
    private static final String KEY_MOIS="mois";
    private static final String KEY_ANNEE="annee";
    private static final String KEY_HEURE="heures";
    private static final String KEY_MINUTE="minutes";
    private static final String KEY_COORD="coordonnees";
    private static final String KEY_ORGA="organisateur";
    //Utilisateurs
    private static final String KEY_LOGIN="login";
    private static final String KEY_PSWRD="password";
    private static final String KEY_EMAIL="email";
    private static final String KEY_NOM="nom";
    private static final String KEY_PRENOM="prenom";
    //EvenementsSuivies
    private static final String KEY_IDUSER="iduser";
    private static final String KEY_IDEVENT="idevent";
    //UtilisateursSuivis
    private static final String KEY_IDSUIVEUR="idusersuiveur";
    private static final String KEY_IDSUIVI="idusersuivi";
    //Notation
    private static final String KEY_IDNOTEUR="idusernoteur";
    private static final String KEY_IDNOTE="idusernote";
    private static final String KEY_NOTE="note";



    private static final String[] COLUMNS_SOIREE = {KEY_ID,KEY_TITLE,KEY_DESC,KEY_PRIX,KEY_CURR,KEY_JOUR,KEY_MOIS,KEY_ANNEE,KEY_HEURE,KEY_MINUTE,KEY_COORD,KEY_ORGA};
    private static final String[] COLUMNS_UTILISATEUR = {KEY_ID,KEY_LOGIN,KEY_PSWRD,KEY_EMAIL,KEY_NOM,KEY_PRENOM};


    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "soireeDB";

    //Tables Name
    private static final String TABLE_SOIREES = "soirees";
    private static final String TABLE_USERS = "utilisateurs";
    private static final String TABLE_MYCREATEDEVENT = "mycreatedevent";
    private static final String TABLE_MYFOLLEWEVENT = "myfollowevent";
    private static final String TABLE_MYFOLLOWUSER = "myfollowuser";
    private static final String TABLE_USERNOTATION = "usernotation";



    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //ADMIN
    public void deleteBDD(){

        this.getWritableDatabase().execSQL("DROP TABLE IF EXISTS soirees");
        this.getWritableDatabase().execSQL("DROP TABLE IF EXISTS utilisateurs");

    }

    public void createBDD(){
        //Insérer des valeurs dans la BDD
        String createUser = "INSERT INTO utilisateurs VALUES (NULL,'user1','user1','user1','user1','user1');";
        this.getWritableDatabase().execSQL(createUser);
        createUser = "INSERT INTO utilisateurs VALUES (NULL,'user2','user2','user2','user2','user2');";
        this.getWritableDatabase().execSQL(createUser);
        createUser = "INSERT INTO utilisateurs VALUES (NULL,'user3','user3','user3','user3','user3');";
        this.getWritableDatabase().execSQL(createUser);
        createUser = "INSERT INTO utilisateurs VALUES (NULL,'user4','user4','user4','user4','user4');";
        this.getWritableDatabase().execSQL(createUser);

        String createSoiree = "INSERT INTO soirees VALUES (NULL, 'Soirée films avec Francis','Allez viens on est bien bien bien !', 15, '€', '12/04/2016', '22h30', 'Chez Francis',1);";
        this.getWritableDatabase().execSQL(createSoiree);
        createSoiree = "INSERT INTO soirees VALUES (NULL, 'Anniversaire','Allez viens on est bien bien bien !', 15, '€', '12/04/2016', '22h30', 'Chez Francis',1);";
        this.getWritableDatabase().execSQL(createSoiree);
        createSoiree = "INSERT INTO soirees VALUES (NULL, 'Tournée des bars','Allez viens on est bien bien bien !', 15, '€', '12/04/2016', '22h30', 'Chez Francis',2);";
        this.getWritableDatabase().execSQL(createSoiree);
        createSoiree = "INSERT INTO soirees VALUES (NULL, 'Allez viens','Allez viens on est bien bien bien !', 15, '€', '12/04/2016', '22h30', 'Chez Francis',2);";
        this.getWritableDatabase().execSQL(createSoiree);
        createSoiree = "INSERT INTO soirees VALUES (NULL, 'On est bien','Allez viens on est bien bien bien !', 15, '€', '12/04/2016', '22h30', 'Chez Francis',3);";
        this.getWritableDatabase().execSQL(createSoiree);
        createSoiree = "INSERT INTO soirees VALUES (NULL, 'Regarde tout ce que lon peut faire','Allez viens on est bien bien bien !', 15, '€', '12/04/2016', '22h30', 'Chez Francis',3);";
        this.getWritableDatabase().execSQL(createSoiree);

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
        values.put(KEY_JOUR, soiree.getJour());
        values.put(KEY_MOIS, soiree.getMois());
        values.put(KEY_ANNEE, soiree.getAnnee());
        values.put(KEY_HEURE, soiree.getHeures());
        values.put(KEY_MINUTE, soiree.getMinutes());
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
        soiree.setJour(Integer.parseInt(cursor.getString(5)));
        soiree.setMois(Integer.parseInt(cursor.getString(6)));
        soiree.setAnnee(Integer.parseInt(cursor.getString(7)));
        soiree.setHeures(Integer.parseInt(cursor.getString(8)));
        soiree.setMinutes(Integer.parseInt(cursor.getString(9)));
        soiree.setCoordonnees(cursor.getString(10));
        soiree.setOrganisateur(Integer.parseInt(cursor.getString(11)));

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
        Cursor cursor = db.rawQuery(query, null);

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
                soiree.setJour(Integer.parseInt(cursor.getString(5)));
                soiree.setMois(Integer.parseInt(cursor.getString(6)));
                soiree.setAnnee(Integer.parseInt(cursor.getString(7)));
                soiree.setHeures(Integer.parseInt(cursor.getString(8)));
                soiree.setMinutes(Integer.parseInt(cursor.getString(9)));
                soiree.setCoordonnees(cursor.getString(10));
                soiree.setOrganisateur(Integer.parseInt(cursor.getString(11)));

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

    //Retourne la liste des soirées créées par un user donnée
    public List<Soiree> getSoireesOrga(int id){
        List<Soiree> soirees = new LinkedList<>();

        // 1. build the query
        String query = "SELECT * FROM "+TABLE_SOIREES+" WHERE organisateur="+id;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

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
                soiree.setJour(Integer.parseInt(cursor.getString(5)));
                soiree.setMois(Integer.parseInt(cursor.getString(6)));
                soiree.setAnnee(Integer.parseInt(cursor.getString(7)));
                soiree.setHeures(Integer.parseInt(cursor.getString(8)));
                soiree.setMinutes(Integer.parseInt(cursor.getString(9)));
                soiree.setCoordonnees(cursor.getString(10));
                soiree.setOrganisateur(Integer.parseInt(cursor.getString(11)));

                soirees.add(soiree);
            }while(cursor.moveToNext());
        }

        Log.d("getSoireesOrga(" + id + ")", soirees.toString());

        // 4. return soirees
        return soirees;
    }

    //Retourne l'ID de l'organisateur pour une soirée donnée
    public int getOrgaIDSoiree(int id){
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(TABLE_SOIREES, new String[]{"organisateur"}, "id = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        // 3. if we got results get the first one
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        else return 1;
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



    //********************************
    //METHODES POUR EVENEMENTS SUIVIS
    //********************************
    //Ajouter un évènement suivi pour un utilisateur donné
    public void ajouterEvenementSuivi(int iduser, int idevent){
        //for logging
        Log.d("ajouterEvenementSuivi", iduser+" "+idevent);

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //values.put(KEY_ID,soiree.getId());
        values.put(KEY_IDUSER, iduser);
        values.put(KEY_IDEVENT, idevent);

        // 3. insert
        db.insert(TABLE_MYFOLLEWEVENT, null, values);

        // 4. close
        db.close();
    }

    //Supprimer un évènement suivi pour un utilisateur donné
    public void supprimerEvenementSuivi(int iduser, int idevent){
        //for logging
        Log.d("supprimerEvenementSuivi", iduser+" "+idevent);

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_MYFOLLEWEVENT, "iduser = ? AND idevent = ?", new String[]{String.valueOf(iduser),String.valueOf(idevent)});

        // 3. close
        db.close();
    }

    public boolean estSuiviEvent(int iduser, int idevent){
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(TABLE_MYFOLLEWEVENT, null, "iduser = ? AND idevent = ?", new String[]{String.valueOf(iduser),String.valueOf(idevent)}, null, null, null, null);

        // 3. if we got results get the first one
        if(cursor.getCount()!=0) {
            return true;
        }
        return false;
    }

    //Retourne les soirées auxquelles un utilisateur est inscrit pour un id d'utilisateur donné
    public List<Soiree> getSoireesSuivies(int id){
        List<Soiree> soirees = new LinkedList<>();

        // 1. build the query
        String query = "SELECT * FROM "+TABLE_MYFOLLEWEVENT+" WHERE iduser="+id;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Soiree soiree = null;
        if(cursor.moveToFirst()){
            do{
                soiree = new Soiree();
                Cursor cursor2 = db.rawQuery("SELECT * FROM "+TABLE_SOIREES+" WHERE id="+cursor.getInt(1),null);
                cursor2.moveToFirst();
                soiree.setId(Integer.parseInt(cursor2.getString(0)));
                soiree.setTitre(cursor2.getString(1));
                soiree.setDescription(cursor2.getString(2));
                soiree.setPrix(Double.parseDouble(cursor2.getString(3)));
                soiree.setCurrency(cursor2.getString(4));
                soiree.setJour(Integer.parseInt(cursor2.getString(5)));
                soiree.setMois(Integer.parseInt(cursor2.getString(6)));
                soiree.setAnnee(Integer.parseInt(cursor2.getString(7)));
                soiree.setHeures(Integer.parseInt(cursor2.getString(8)));
                soiree.setMinutes(Integer.parseInt(cursor2.getString(9)));
                soiree.setCoordonnees(cursor2.getString(10));
                soiree.setOrganisateur(Integer.parseInt(cursor2.getString(11)));

                soirees.add(soiree);
            }while(cursor.moveToNext());
        }

        Log.d("getSoireesSuivies(" + id + ")", soirees.toString());

        // 4. return soirees
        return soirees;
    }

    //********************************
    //METHODES POUR UTILISATEURS SUIVIS
    //********************************
    public void ajouterUtilisateurSuivi(int idsuiveur, int idsuivi){
        //for logging
        Log.d("ajouterUtilisateurSuivi", idsuiveur+" "+idsuivi);

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //values.put(KEY_ID,soiree.getId());
        values.put(KEY_IDSUIVEUR, idsuiveur);
        values.put(KEY_IDSUIVI, idsuivi);

        // 3. insert
        db.insert(TABLE_MYFOLLOWUSER, null, values);

        // 4. close
        db.close();
    }

    public boolean estSuiviUser(int idsuiveur, int idsuivi){
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(TABLE_MYFOLLOWUSER, null, "idusersuiveur = ? AND idusersuivi = ?", new String[]{String.valueOf(idsuiveur),String.valueOf(idsuivi)}, null, null, null, null);

        // 3. if we got results get the first one
        if(cursor.getCount()!=0) {
            return true;
        }
        return false;
    }

    //********************************
    //METHODES POUR NOTATION
    //********************************
    public void setNotation(int idnoteur, int idnote, float note){
        //for logging
        Log.d("setNotation", idnoteur+" "+idnote+" "+note);

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        //On regarde si l'utilisateur a déjà noté l'organisateur
        Cursor cursor = db.query(TABLE_USERNOTATION, null, "idusernoteur = ? AND idusernote = ?", new String[]{String.valueOf(idnoteur),String.valueOf(idnote)}, null, null, null, null);

        //Si il a déjà été noté, on met à jour la note, sinon on ajoute la notation
        if(cursor.getCount()!=0){
            String query = "UPDATE usernotation SET note="+note+" WHERE idusernoteur="+idnoteur+" AND idusernote="+idnote+";";
            db.execSQL(query);
        }
        else{
            // 2. create ContentValues to add key "column"/value
            ContentValues values = new ContentValues();
            //values.put(KEY_ID,soiree.getId());
            values.put(KEY_IDNOTEUR, idnoteur);
            values.put(KEY_IDNOTE, idnote);
            values.put(KEY_NOTE, note);

            // 3. insert
            db.insert(TABLE_USERNOTATION, null, values);
        }

        // 4. close
        db.close();
    }

    public float getNotation(int iduser){
        float note = 0;
        int i = 0;
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor = db.query(TABLE_USERNOTATION, new String[]{KEY_NOTE}, "idusernote = ?", new String[]{String.valueOf(iduser)}, null, null, null, null);

        // 3. if we got results get the first one
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            note += cursor.getFloat(0);
            i++;
            while(cursor.moveToNext()){
                note += cursor.getFloat(0);
                i++;
            }
            note = note / i;
        }
        return note;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL statement to create user table
        String CREATE_USER_TABLE = "CREATE TABLE "+TABLE_USERS+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "login TEXT NOT NULL,"+
                "password TEXT NOT NULL,"+
                "email TEXT NOT NULL,"+
                "nom TEXT NOT NULL,"+
                "prenom TEXT NOT NULL);";

        //SQL statement to create soiree table
        String CREATE_SOIREE_TABLE = "CREATE TABLE "+TABLE_SOIREES+" ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "titre TEXT NOT NULL,"+
                "description TEXT NOT NULL,"+
                "prix DOUBLE NOT NULL,"+
                "currency TEXT NOT NULL,"+
                "jour INTEGER NOT NULL,"+
                "mois INTEGER NOT NULL,"+
                "annee INTEGER NOT NULL,"+
                "heures INTEGER NOT NULL,"+
                "minutes INTEGER NOT NULL,"+
                "coordonnees TEXT NOT NULL,"+
                "organisateur INTEGER NOT NULL);";

        //SQL statement to create user's created events
        String CREATE_MYCREATEEVENT_TABLE = "CREATE TABLE "+TABLE_MYCREATEDEVENT+" ("+
                "iduser INTEGER NOT NULL, idevent INTEGER NOT NULL);";

        //SQL statement to create user's followed events
        String CREATE_MYFOLLOWEVENT_TABLE = "CREATE TABLE "+TABLE_MYFOLLEWEVENT+" ("+
                "iduser INTEGER NOT NULL, idevent INTEGER NOT NULL);";

        //SQL statement to create user's followed users
        String CREATE_MYFOLLOWUSER_TABLE = "CREATE TABLE "+TABLE_MYFOLLOWUSER+" ("+
                "idusersuiveur INTEGER NOT NULL, idusersuivi INTEGER NOT NULL);";

        //SQL statement to create user's notation
        String CREATE_USERNOTATION_TABLE = "CREATE TABLE "+TABLE_USERNOTATION+" ("+
                "idusernoteur INTEGER NOT NULL, idusernote INTEGER NOT NULL, note FLOAT NOT NULL);";



        //create soirees table
        db.execSQL(CREATE_SOIREE_TABLE);
        //create utilisateurs table
        db.execSQL(CREATE_USER_TABLE);
        //create others tables
        db.execSQL(CREATE_MYCREATEEVENT_TABLE);
        db.execSQL(CREATE_MYFOLLOWEVENT_TABLE);
        db.execSQL(CREATE_MYFOLLOWUSER_TABLE);
        db.execSQL(CREATE_USERNOTATION_TABLE);


        //Insérer des valeurs dans la BDD
        //Utilisateurs
        String createUser = "INSERT INTO utilisateurs VALUES (NULL,'user1','user1','user1','user1','user1');";
        db.execSQL(createUser);
        createUser = "INSERT INTO utilisateurs VALUES (NULL,'user2','user2','user2','user2','user2');";
        db.execSQL(createUser);
        createUser = "INSERT INTO utilisateurs VALUES (NULL,'user3','user3','user3','user3','user3');";
        db.execSQL(createUser);
        createUser = "INSERT INTO utilisateurs VALUES (NULL,'user4','user4','user4','user4','user4');";
        db.execSQL(createUser);

        //Soirees
        String createSoiree = "INSERT INTO soirees VALUES (NULL, 'Soirée films avec Francis','On va regarder Deadpool !', 15, '€', 12, 04, 2016, 22, 30, 'Chez Francis',1);";
        db.execSQL(createSoiree);
        createSoiree = "INSERT INTO soirees VALUES (NULL, 'Anniversaire','Y aura du gateau !', 15, '€', 12, 04, 2016, 22, 30, 'Chez Francis',1);";
        db.execSQL(createSoiree);
        createSoiree = "INSERT INTO soirees VALUES (NULL, 'Tournée des bars','On a 12 bars à faire !', 15, '€', 12, 04, 2016, 22, 30, 'Chez Francis',2);";
        db.execSQL(createSoiree);
        createSoiree = "INSERT INTO soirees VALUES (NULL, 'Allez viens','Allez viens on est bien bien bien !', 15, '€', 12, 04, 2016, 22, 30, 'Chez Francis',2);";
        db.execSQL(createSoiree);
        createSoiree = "INSERT INTO soirees VALUES (NULL, 'On est bien','On est bien bien bien bien bien !', 15, '€', 12, 04, 2016, 22, 30, 'Chez Francis',3);";
        db.execSQL(createSoiree);
        createSoiree = "INSERT INTO soirees VALUES (NULL, 'Regarde tout ce que lon peut faire','Y aura de la tarte au concombre', 15, '€', 12, 04, 2016, 22, 30, 'Chez Francis',3);";
        db.execSQL(createSoiree);

        //Notation
        String createNotation = "INSERT INTO usernotation VALUES (1,3,2.5);";
        db.execSQL(createNotation);
        createNotation = "INSERT INTO usernotation VALUES (2,3,5);";
        db.execSQL(createNotation);
        createNotation = "INSERT INTO usernotation VALUES (3,3,4);";
        db.execSQL(createNotation);
        createNotation = "INSERT INTO usernotation VALUES (4,3,3.5);";
        db.execSQL(createNotation);

        //Créer les trigger de MAJ des tables
        //String trigger = "CREATE TRIGGER trigger_notation AFTER INSERT, UPDATE ON usernotation BEGIN END;";
        //db.execSQL(trigger);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older soirees table if existed
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SOIREES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MYCREATEDEVENT);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MYFOLLEWEVENT);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MYFOLLOWUSER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERNOTATION);

        //Create fresh soirees table
        this.onCreate(db);
    }
}
