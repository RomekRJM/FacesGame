package rjm.romek.facegame.ui.activity;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

import rjm.romek.facegame.R;
import rjm.romek.facegame.common.Parameters;
import rjm.romek.facegame.data.ScoreContract;

import static rjm.romek.facegame.data.ScoreContract.ScoreEntry;

public class TopScore extends ListActivity {

    private Parameters parameters;
    private SimpleCursorAdapter adapter;
    static final String[] FROM = { ScoreEntry.PLAYER, ScoreEntry.SCORE,
            ScoreEntry.CORRECT_ANSWERS, ScoreEntry.DATE };
    static final int[] TO = { R.id.text_player, R.id.text_score, R.id.text_correct,
            R.id.text_date };

    static final ViewBinder VIEW_BINDER = new ViewBinder() {

        private long lastId = -1l;
        private int cntr = 1;

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            long id = cursor.getLong(cursor.getColumnIndex(ScoreEntry._ID));
            if (lastId == id) {
                return false;
            } else if (view.getId() == R.id.text_date) {
                long time = cursor.getLong(cursor
                        .getColumnIndex(ScoreEntry.DATE));
                CharSequence relativeTime = DateUtils
                        .getRelativeTimeSpanString(time);
                ((TextView) view).setText(relativeTime);
            } else if (view.getId() == R.id.text_correct) {
                int correct = cursor.getInt(cursor
                        .getColumnIndex(ScoreEntry.CORRECT_ANSWERS));
                String correctText = correct + " correct answers";
                ((TextView) view).setText(correctText);
            } else if (view.getId() == R.id.text_position) {
                String positionText = cntr + ".";
                ((TextView) view).setText(positionText);
            }
            ++cntr;
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.parameters = new Parameters();
        init();
    }

    public void init() {
        Cursor cursor = new ScoreContract(this).getTopScoresCursor(parameters.getLimitTopScore());

        adapter = new SimpleCursorAdapter(this, R.layout.top_score_row, cursor, FROM, TO);
        adapter.setViewBinder(VIEW_BINDER);
        setListAdapter(adapter);
    }

}
