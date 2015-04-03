package rjm.romek.facegame.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static rjm.romek.facegame.data.ScoreContract.*;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "faces.db";
    public static final int DB_VERSION = 1;
    static final String TAG = "StatusData";

    public static final String CREATE_SCORE_TABLE =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            " %s text, %s int, %s int, %s int)",
                    ScoreEntry.TABLE_NAME, ScoreEntry._ID, ScoreEntry.PLAYER,
                    ScoreEntry.SCORE, ScoreEntry.CORRECT_ANSWERS, ScoreEntry.DATE);

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = CREATE_SCORE_TABLE;
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade from: " + oldVersion + " to: " + newVersion);
        String sql = String.format("DROP IF EXISTS %s", ScoreEntry.TABLE_NAME);
        Log.d(TAG, "onUpgrade with SQL: " + sql);
        db.execSQL(sql);
        onCreate(db);
    }
}
