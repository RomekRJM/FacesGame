package rjm.romek.facegame.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import rjm.romek.facegame.model.Score;

public class ScoreContract {
    private final Context context;

    public ScoreContract(Context context) {
        this.context = context;
    }

    public static abstract class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "scores";
        public static final String PLAYER = "player";
        public static final String CORRECT_ANSWERS = "correct_answers";
        public static final String SCORE = "score";
        public static final String DATE = "date";
    }

    public Cursor getTopScoresCursor(int limit) {
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String[] projection = {ScoreEntry._ID, ScoreEntry.PLAYER, ScoreEntry.CORRECT_ANSWERS,
                ScoreEntry.SCORE, ScoreEntry.DATE};
        String sortOrder = String.format("%s DESC LIMIT %d", ScoreEntry.SCORE, limit);

        return db.query(
                ScoreEntry.TABLE_NAME,                    // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
    }

    public void saveScore(Score score) {
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ScoreEntry.PLAYER, score.getPlayer());
        values.put(ScoreEntry.CORRECT_ANSWERS, score.getCorrectAnswers());
        values.put(ScoreEntry.SCORE, score.getScore());
        values.put(ScoreEntry.DATE, score.getDate().getTime());
        db.insertWithOnConflict(ScoreEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }
}
