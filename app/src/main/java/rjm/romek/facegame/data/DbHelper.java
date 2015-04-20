package rjm.romek.facegame.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import rjm.romek.facegame.ui.global.Global;

import static rjm.romek.facegame.data.AchievementContract.*;
import static rjm.romek.facegame.data.ScoreContract.*;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = Global.isRunningTests ? "testfaces.db" : "faces.db";
    public static final int DB_VERSION = 6;
    static final String TAG = "DbHelper";
    private static DbHelper instance;

    public static synchronized DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate with SQL: " + CREATE_SCORE_TABLE);
        db.execSQL(CREATE_SCORE_TABLE);
        Log.d(TAG, "onCreate with SQL: " + CREATE_ACHIEVEMENT_TABLE);
        db.execSQL(CREATE_ACHIEVEMENT_TABLE);
        Log.d(TAG, "onCreate, populating achievements: ");
        populateAchievements(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade from: " + oldVersion + " to: " + newVersion);
        cleanUp(db);
    }

    public void cleanUp() {
        cleanUp(getWritableDatabase());
    }

    private void cleanUp(SQLiteDatabase db) {
        dropDb(db);
        onCreate(db);
    }

    public void dropDb(SQLiteDatabase db) {
        String sql = String.format("DROP TABLE IF EXISTS %s", ScoreEntry.TABLE_NAME);
        Log.d(TAG, "delete with SQL: " + sql);
        db.execSQL(sql);
        sql = String.format("DROP TABLE IF EXISTS %s", AchievementEntry.TABLE_NAME);
        Log.d(TAG, "delete with SQL: " + sql);
        db.execSQL(sql);
    }
}
