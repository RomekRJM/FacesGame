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

    public static void populateAchievements(SQLiteDatabase db) {

        Achievement [] achievements = new Achievement[]{
                new Achievement("Grucho", "Guess 3 in a row", "face_1.png"),
                new Achievement("Teether", "Guess 5 in a row", "face_2.png"),
                new Achievement("Grumpo", "Guess 7 in a row", "face_3.png"),
                new Achievement("Huggy", "Guess 10 in a row", "face_4.png"),
                new Achievement("Shamo", "Have total 30 correct guesses", "face_5.png"),
                new Achievement("Kissulla", "Have total 100 correct guesses", "face_6.png"),
                new Achievement("Silencio", "Have total 250 correct guesses", "face_7.png"),
                new Achievement("Yawner", "Have total 500 correct guesses", "face_8.png"),
                new Achievement("Blusho", "Have total 1000 correct guesses", "face_9.png"),
        };

        for(Achievement a : achievements) {
            createAchievement(a, db);
        }
    }

    public Cursor getAchievements() {
        SQLiteDatabase db = DbHelper.getInstance(context).getReadableDatabase();

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

    public Achievement findByName(String name) {
        SQLiteDatabase db = DbHelper.getInstance(context).getReadableDatabase();

        String[] projection = {AchievementEntry._ID, AchievementEntry.NAME,
                AchievementEntry.DESCRIPTION, AchievementEntry.DATA, AchievementEntry.PRIZE,
                AchievementEntry.UNLOCKED, AchievementEntry.LAST_UPDATED};

        Cursor cursor = db.query(
                AchievementEntry.TABLE_NAME,              // The table to query
                projection,                               // The columns to return
                AchievementEntry.NAME + " LIKE %s ",      // The columns for the WHERE clause
                new String[]{ name },                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        cursor.moveToFirst();

        Achievement achievement = new Achievement();
        achievement.setName(cursor.getString(cursor.getColumnIndex(AchievementEntry.NAME)));
        achievement.setData(cursor.getString(cursor.getColumnIndex(AchievementEntry.DATA)));
        achievement.setDescription(cursor.getString(cursor.getColumnIndex(AchievementEntry.DESCRIPTION)));
        achievement.setIsUnlocked(cursor.getInt(cursor.getColumnIndex(AchievementEntry.UNLOCKED)) != 0);
        achievement.setPrize(cursor.getString(cursor.getColumnIndex(AchievementEntry.PRIZE)));
        return achievement;
    }

    private static void createAchievement(Achievement achievement, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(AchievementEntry.NAME, achievement.getName());
        values.put(AchievementEntry.DESCRIPTION, achievement.getDescription());
        values.put(AchievementEntry.DATA, achievement.getData());
        values.put(AchievementEntry.PRIZE, achievement.getPrize());
        values.put(AchievementEntry.UNLOCKED, achievement.isUnlocked());
        db.insertWithOnConflict(AchievementEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public void updateAchievement(Achievement achievement) {
        SQLiteDatabase db = DbHelper.getInstance(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AchievementEntry.DATA, achievement.getData());
        values.put(AchievementEntry.LAST_UPDATED, System.currentTimeMillis());
        db.updateWithOnConflict(AchievementEntry.TABLE_NAME, values,
                AchievementEntry.NAME + " LIKE %s ", new String[]{ achievement.getName() },
                SQLiteDatabase.CONFLICT_IGNORE);
    }
}
