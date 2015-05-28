package rjm.romek.facegame.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.Set;

import rjm.romek.facegame.model.Question;

public class QuestionContract {
    private final Context context;

    public QuestionContract(Context context) {
        this.context = context;
    }

    public static final String CREATE_QUESTION_TABLE =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY NOT NULL," +
                            " %s text, %s text, %s text, %s int, %s text, %s text," +
                            " %s text, %s int, %s int)",
                    QuestionEntry.TABLE_NAME, QuestionEntry._ID, QuestionEntry.GAME_UUID,
                    QuestionEntry.PERSON, QuestionEntry.COUNTRIES, QuestionEntry.DATE,
                    QuestionEntry.CORRECT_ANSWER, QuestionEntry.GIVEN_ANSWER,
                    QuestionEntry.DIFFICULTY, QuestionEntry.ANSWER_TIME, QuestionEntry.TIMED_OUT);

    public static abstract class QuestionEntry implements BaseColumns {
        public static final String TABLE_NAME = "questions";
        public static final String GAME_UUID = "game_uuid";
        public static final String PERSON = "person";
        public static final String COUNTRIES = "countries";
        public static final String DATE = "date";
        public static final String CORRECT_ANSWER = "correct_answer";
        public static final String GIVEN_ANSWER = "given_answer";
        public static final String DIFFICULTY = "difficulty";
        public static final String ANSWER_TIME = "answer_time";
        public static final String TIMED_OUT = "timed_out";
    }

    public void saveQuestions(Set<Question> questions) {
        SQLiteDatabase db = DbHelper.getInstance(context).getWritableDatabase();

        for (Question question : questions) {
            ContentValues values = new ContentValues();
            values.put(QuestionEntry.GAME_UUID, question.getGameUUID());
            values.put(QuestionEntry.PERSON, question.getPerson().getName());
            values.put(QuestionEntry.COUNTRIES, question.getCountriesCSV());
            values.put(QuestionEntry.DATE, question.getDate().getTime());
            values.put(QuestionEntry.CORRECT_ANSWER, question.getCorrectAnswer().getName());
            values.put(QuestionEntry.GIVEN_ANSWER, question.getGivenAnswer().getName());
            values.put(QuestionEntry.DIFFICULTY, question.getDifficulty().name());
            values.put(QuestionEntry.ANSWER_TIME, question.getAnswerTime());
            values.put(QuestionEntry.TIMED_OUT, question.isTimedOut());
            db.insertWithOnConflict(QuestionEntry.TABLE_NAME, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE);
        }
    }

    public long countUniqueRightGuessesForCountry(String country) {
        SQLiteDatabase db = DbHelper.getInstance(context).getReadableDatabase();

        String sql =
                "SELECT COUNT(DISTINCT " + QuestionEntry.PERSON + ") " +
                        "FROM " + QuestionEntry.TABLE_NAME + " " +
                        "WHERE " + QuestionEntry.CORRECT_ANSWER + " = " + QuestionEntry.GIVEN_ANSWER +
                        " AND " + QuestionEntry.CORRECT_ANSWER + " LIKE ?";

        Cursor cursor = db.rawQuery(sql, new String[]{country});
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        cursor.close();

        return count;
    }

    public int countConsecutiveDaysPlaying(int target) {
        String inClause = "0";

        if (target <= 0) {
            target = 1;
        }

        for (int i = 1; i < target; ++i) {
            inClause += ",";
            inClause += i;
        }

        SQLiteDatabase db = DbHelper.getInstance(context).getReadableDatabase();

        String sql =
                "SELECT COUNT(*) " +
                        "FROM ( " +
                        "SELECT DISTINCT julianday(date('now')) - julianday(" + QuestionEntry.DATE + ") AS x " +
                        "FROM " + QuestionEntry.TABLE_NAME + " " +
                        "WHERE x IN (" + inClause + ")" +
                        ")";

        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }
}
