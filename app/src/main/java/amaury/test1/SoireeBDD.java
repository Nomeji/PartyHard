package amaury.test1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Amaury Punel on 23/03/2016.
 */
public class SoireeBDD {

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

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "soireeDB";

    //Tables Name
    private static final String TABLE_SOIREES = "soirees";
    private static final String TABLE_USERS = "utilisateurs";

    //Things
    private SQLiteDatabase bdd;
    private MySQLiteHelper mySQLiteHelper;

    public SoireeBDD(Context context){
        //On crée la BDD et sa table
        mySQLiteHelper = new MySQLiteHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = mySQLiteHelper.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public void addSoiree(Soiree soiree){
       // SQLiteDatabase db = this.getWritableDatabase();

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

        bdd.insert(TABLE_SOIREES, null, values);

        //db.close();
    }

    public Soiree getSoiree(int id){
        //SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = bdd.query(TABLE_SOIREES, null, "id = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
        else return null;

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

        return soiree;
    }

    public List<Soiree> getAllSoirees(){
        List<Soiree> soirees = new LinkedList<>();

        String query = "SELECT * FROM "+TABLE_SOIREES;

       // SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = bdd.rawQuery(query,null);

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

        return soirees;
    }

    public void deleteSoiree(Soiree soiree){
        //SQLiteDatabase db = this.getWritableDatabase();

        bdd.delete(TABLE_SOIREES, KEY_ID + " = ?", new String[]{String.valueOf(soiree.getId())});

        //db.close();
    }
}
