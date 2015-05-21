package rjm.romek.facegame.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rjm.romek.facegame.model.Score;

public class ScoreContract {
    private final Context context;
    public static final String CREATE_SCORE_TABLE =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            " %s text, %s int, %s int, %s int, %s int)",
                    ScoreEntry.TABLE_NAME, ScoreEntry._ID, ScoreEntry.PLAYER,
                    ScoreEntry.SCORE, ScoreEntry.CORRECT_ANSWERS,
                    ScoreEntry.DATE, ScoreEntry.PUBLISHED);

    public ScoreContract(Context context) {
        this.context = context;
    }

    public static abstract class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "scores";
        public static final String PLAYER = "player";
        public static final String CORRECT_ANSWERS = "correct_answers";
        public static final String SCORE = "score";
        public static final String DATE = "date";
        public static final String PUBLISHED = "published";
    }

    public Cursor getTopScoresCursor(int limit) {
        SQLiteDatabase db = DbHelper.getInstance(context).getReadableDatabase();

        String[] projection = {ScoreEntry._ID, ScoreEntry.PLAYER, ScoreEntry.CORRECT_ANSWERS,
                ScoreEntry.SCORE, ScoreEntry.DATE, ScoreEntry.PUBLISHED};
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

    public List<Score> getTopScores(int limit) {
        Cursor cursor = getTopScoresCursor(limit);
        List<Score> scores = new ArrayList<>();

        if (!cursor.moveToFirst()) {
            cursor.close();
            return scores;
        }

        do {
            Score score = new Score();
            score.setId(cursor.getLong(cursor.getColumnIndex(ScoreEntry._ID)));
            score.setCorrectAnswers(cursor.getInt(cursor.getColumnIndex(ScoreEntry.CORRECT_ANSWERS)));
            score.setDate(new Date(cursor.getLong(cursor.getColumnIndex(ScoreEntry.DATE))));
            score.setPlayer(cursor.getString(cursor.getColumnIndex(ScoreEntry.PLAYER)));
            score.setScore(cursor.getLong(cursor.getColumnIndex(ScoreEntry.SCORE)));
            score.setPublished(cursor.getInt(cursor.getColumnIndex(ScoreEntry.PUBLISHED)) != 0);
            scores.add(score);
        } while (cursor.moveToNext());

        cursor.close();
        return scores;
    }

    public void saveScore(Score score) {
        SQLiteDatabase db = DbHelper.getInstance(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ScoreEntry.PLAYER, score.getPlayer());
        values.put(ScoreEntry.CORRECT_ANSWERS, score.getCorrectAnswers());
        values.put(ScoreEntry.SCORE, score.getScore());
        values.put(ScoreEntry.DATE, score.getDate().getTime());
        values.put(ScoreEntry.PUBLISHED, score.isPublished());

        long id = db.insertWithOnConflict(ScoreEntry.TABLE_NAME, null,
                values, SQLiteDatabase.CONFLICT_IGNORE);
        score.setId(id);
    }

    public void updateScore(Score score) {
        SQLiteDatabase db = DbHelper.getInstance(context).getWritableDatabase();
        String whereClause = ScoreEntry._ID + "=?";
        String [] whereArg = new String[]{score.getId().toString()};

        ContentValues values = new ContentValues();
        values.put(ScoreEntry.PUBLISHED, score.isPublished());
        db.updateWithOnConflict(ScoreEntry.TABLE_NAME, values, whereClause,
                whereArg, SQLiteDatabase.CONFLICT_IGNORE);
    }
}
