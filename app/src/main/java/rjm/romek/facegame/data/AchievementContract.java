package rjm.romek.facegame.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import rjm.romek.facegame.model.Achievement;

public class AchievementContract {
    private final Context context;

    public AchievementContract(Context context) {
        this.context = context;
    }

    public static final String CREATE_ACHIEVEMENT_TABLE =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            " %s text, %s text, %s text, %s text, %s int, %s int)",
                    AchievementEntry.TABLE_NAME, AchievementEntry._ID, AchievementEntry.NAME,
                    AchievementEntry.DESCRIPTION, AchievementEntry.DATA, AchievementEntry.PRIZE,
                    AchievementEntry.UNLOCKED, AchievementEntry.LAST_UPDATED);

    public static abstract class AchievementEntry implements BaseColumns {
        public static final String TABLE_NAME = "achievements";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String DATA = "data";
        public static final String PRIZE = "prize";
        public static final String UNLOCKED = "unlocked";
        public static final String LAST_UPDATED = "last_updated";
    }

    public Cursor getAchievements() {
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String[] projection = {AchievementEntry._ID, AchievementEntry.NAME,
                AchievementEntry.DESCRIPTION, AchievementEntry.DATA, AchievementEntry.PRIZE,
                AchievementEntry.UNLOCKED, AchievementEntry.LAST_UPDATED};
        String sortOrder = String.format("%s ASC", AchievementEntry._ID);

        return db.query(
                AchievementEntry.TABLE_NAME,              // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
    }

    public void createAchievement(Achievement achievement) {
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AchievementEntry.NAME, achievement.getName());
        values.put(AchievementEntry.DESCRIPTION, achievement.getDescription());
        values.put(AchievementEntry.DATA, achievement.getData());
        values.put(AchievementEntry.PRIZE, achievement.getPrize());
        values.put(AchievementEntry.UNLOCKED, achievement.isUnlocked());
        db.insertWithOnConflict(AchievementEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public void updateAchievement(Achievement achievement) {
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AchievementEntry.DATA, achievement.getData());
        values.put(AchievementEntry.LAST_UPDATED, System.currentTimeMillis());
        db.insertWithOnConflict(AchievementEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }
}
