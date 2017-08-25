package hr.marijanbistre.algebrafinalapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Created by Marijan Bistre on 19.8.2017..
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "scores.db";
    private static final String TABLE_NAME = "scores";
    private static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_SCORE = "score";

    // SQL FOR CREATE TABLE
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_AGE + " integer not null, "
            + COLUMN_GENDER + " text not null, "
            + COLUMN_SCORE + " integer not null );";

    // SQL FOR DELETING TABLE
    private static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME + ";";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    // CREATE TABLE
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    // DELETE TABLE AND MAKE NEW ONE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    // SET TABLE FOR INSERTION
    public long insert(ContentValues values) {
        return getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    public Cursor query (String id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(TABLE_NAME);
        if (id != null) {
            sqLiteQueryBuilder.appendWhere("_id" + " = " + id);
        }
        Cursor cursor = sqLiteQueryBuilder.query(getReadableDatabase(), projection, selection,
                selectionArgs, null, null, sortOrder);
        return cursor;
    }

}
