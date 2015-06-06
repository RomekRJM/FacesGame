package rjm.romek.facegame.ui.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.leaderboard.Leaderboards.SubmitScoreResult;

import java.util.List;

import rjm.romek.facegame.R;
import rjm.romek.facegame.common.GooglePlayable;
import rjm.romek.facegame.common.Parameters;
import rjm.romek.facegame.data.ScoreContract;
import rjm.romek.facegame.model.Score;
import rjm.romek.facegame.ui.manager.GooglePlayManager;

import static rjm.romek.facegame.data.ScoreContract.ScoreEntry;

public class TopScore extends ListActivity implements View.OnClickListener, GooglePlayable {

    private Parameters parameters;
    private ScoreContract scoreContract;
    private SimpleCursorAdapter adapter;
    private Button shareButton;
    private Score currentScore;
    private LeaderBoardSubmitScoreCallback leaderBoardSubmitScoreCallback;
    private GooglePlayManager googlePlayManager;
    static final String[] FROM = {ScoreEntry.PLAYER, ScoreEntry.SCORE,
            ScoreEntry.CORRECT_ANSWERS, ScoreEntry.DATE, ScoreEntry._ID};
    static final int[] TO = {R.id.text_player, R.id.text_score, R.id.text_correct,
            R.id.text_date, R.id.text_position};
    static final String TAG = "TopScore";

    private class LeaderBoardSubmitScoreCallback implements ResultCallback<SubmitScoreResult> {
        @Override
        public void onResult(SubmitScoreResult res) {
            if (res.getStatus().isSuccess()) {
                currentScore.setPublished(true);
                scoreContract.updateScore(currentScore);
            }
        }
    }

    static final ViewBinder VIEW_BINDER = new ViewBinder() {

        private int positionCounter;

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

            if (cursor.isFirst()) {
                positionCounter = 1;
            }

            if (view.getId() == R.id.text_date) {
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
                String positionText = positionCounter + ".";
                ((TextView) view).setText(positionText);
                ++positionCounter;
            } else if (view.getId() == R.id.text_score) {
                String scoreText = "" + cursor.getLong(cursor
                        .getColumnIndex(ScoreEntry.SCORE));
                ((TextView) view).setText(scoreText);
            }
            return true;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        googlePlayManager.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googlePlayManager.stop();
    }

    //TODO: Connect this
    public void showLeaderboardsRequested() {
        if (googlePlayManager.isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(
                            googlePlayManager.getGoogleApiClient()), GooglePlayManager.RC_UNUSED);
        } else {
            Log.d(TAG, "Leaderboards not available!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_scores);
        this.parameters = new Parameters();
        this.scoreContract = new ScoreContract(this);
        init();
    }

    public void init() {
        Cursor cursor = scoreContract.getTopScoresCursor(parameters.getLimitTopScore());

        adapter = new SimpleCursorAdapter(this, R.layout.top_score_row, cursor, FROM, TO);
        adapter.setViewBinder(VIEW_BINDER);
        setListAdapter(adapter);
        leaderBoardSubmitScoreCallback = new LeaderBoardSubmitScoreCallback();
        shareButton = (Button) findViewById(R.id.shareScoreButton);
        shareButton.setOnClickListener(this);

        googlePlayManager = new GooglePlayManager(this);
        googlePlayManager.init();
        googlePlayManager.updateShareButton();
    }

    @Override
    public void onClick(View v) {

        googlePlayManager.setConnectionFailed(false);

        if (!googlePlayManager.isShareButtonActive() || !googlePlayManager.isSignedIn()) return;

        List<Score> topScores = scoreContract.getTopScores(1);
        currentScore = topScores.get(0);

        if (!currentScore.isPublished()) {
            Games.Leaderboards.submitScoreImmediate(googlePlayManager.getGoogleApiClient(),
                    getString(R.string.leaderboard_best_players), currentScore.getScore())
                    .setResultCallback(leaderBoardSubmitScoreCallback);
        }

        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
                googlePlayManager.getGoogleApiClient(), getString(R.string.leaderboard_best_players)),
                GooglePlayManager.CODE_OK);

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Button getShareButton() {
        return shareButton;
    }

    @Override
    public boolean needsPublishing() {
        List<Score> topScores = scoreContract.getTopScores(1);
        return !topScores.get(0).isPublished();
    }

    @Override
    public boolean containsScores() {
        return scoreContract.getTopScores(1).size() > 0;
    }
}
