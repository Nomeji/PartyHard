package amaury.test1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Amaury Punel on 23/03/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    //Column Names
    private static final String KEY_ID="id";
    private static final String KEY_TITLE="titre";
    private static final String KEY_DESC="description";
    private static final String KEY_PRIX="prix";
    private static final String KEY_CURR="currency";
    private static final String KEY_DATE="date";
    private static final String KEY_HEURE="heure";
    private static final String KEY_COORD="coordonnees";
    private static final String KEY_ORGA="organisateur";

    private static final String[] COLUMNS = {KEY_ID,KEY_TITLE,KEY_DESC,KEY_PRIX,KEY_CURR,KEY_DATE,KEY_HEURE,KEY_COORD,KEY_ORGA};


    //Database Version
    private static final int DATABASE_VERSION = 2;

    //Database Name
    private static final String DATABASE_NAME = "soireeDB";

    //Tables Name
    private static final String TABLE_SOIREES = "soirees";
    private static final String TABLE_USERS = "utilisateurs";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //METHODES POUR LES SOIREES
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
        Cursor cursor = db.query(TABLE_SOIREES, COLUMNS, "id = ?", new String[]{String.valueOf(id)}, null, null, null, null);

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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS soirees");

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older soirees table if existed
        db.execSQL("DROP TABLE IF EXISTS soirees");

        //Create fresh soirees table
        this.onCreate(db);
    }
}
