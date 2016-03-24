package amaury.test1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Amaury Punel on 23/03/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {


    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL statement to create soiree table
        String CREATE_SOIREE_TABLE = "CREATE TABLE soirees ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "titre TEXT,"+
                "description TEXT"+
                "prix DOUBLE"+
                "currency TEXT"+
                "date TEXT"+
                "heure TEXT"+
                "coordonnees TEXT"+
                "organisateur INTEGER";

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
