package rjm.romek.facegame.ui.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.leaderboard.Leaderboards.SubmitScoreResult;
import com.google.example.games.basegameutils.BaseGameUtils;

import java.util.List;

import rjm.romek.facegame.R;
import rjm.romek.facegame.common.Parameters;
import rjm.romek.facegame.data.ScoreContract;
import rjm.romek.facegame.model.Score;

import static rjm.romek.facegame.data.ScoreContract.ScoreEntry;

public class TopScore extends ListActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private Parameters parameters;
    private ScoreContract scoreContract;
    private SimpleCursorAdapter adapter;
    private Button shareButton;
    private Score currentScore;
    private LeaderBoardSubmitScoreCallback leaderBoardSubmitScoreCallback;
    static final String TAG = "TopScore";
    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 9001;
    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean shareButtonActive = false;
    private boolean connectionFailed = false;
    private static final int RC_UNUSED = 5001;
    static final String[] FROM = {ScoreEntry.PLAYER, ScoreEntry.SCORE,
            ScoreEntry.CORRECT_ANSWERS, ScoreEntry.DATE, ScoreEntry._ID};
    static final int[] TO = {R.id.text_player, R.id.text_score, R.id.text_correct,
            R.id.text_date, R.id.text_position};

    private class LeaderBoardSubmitScoreCallback implements ResultCallback<SubmitScoreResult> {
        @Override
        public void onResult(SubmitScoreResult res) {
            if (res.getStatus().getStatusCode() == 0) {
                currentScore.setPublished(true);
                scoreContract.updateScore(currentScore);
            } else {
                // problem sharing
            }
        }
    }

    static final ViewBinder VIEW_BINDER = new ViewBinder() {

        private int positionCounter;

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

            if(cursor.isFirst()) {
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
        Log.d(TAG, "onStart(): connecting");
        try {
            mGoogleApiClient.connect();
        } catch (Exception exc) {
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop(): disconnecting");
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private boolean isSignedIn() {
        return (mGoogleApiClient != null && mGoogleApiClient.isConnected());
    }


    public void showLeaderboardsRequested() {
        if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient),
                    RC_UNUSED);
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
        updateShareButton();


        // Create the Google API Client with access to Plus and Games
        try {
            // Create the Google Api Client with access to the Play Game services
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                    .build();

        } catch (Exception exc) {
            connectionFailed = true;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        updateShareButton();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        connectionFailed = true;

        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, getString(R.string.signin_other_error))) {
                mResolvingConnectionFailure = false;
            }
        }

        updateShareButton();
        // Put code here to display the sign-in button
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        mGoogleApiClient.connect();
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, R.string.signin_failure);
                connectionFailed = true;
            }
        }
        updateShareButton();
    }

    @Override
    public void onClick(View v) {

        connectionFailed = false;

        if (!shareButtonActive) return;

        List<Score> topScores = scoreContract.getTopScores(10);

        for (Score score : topScores) {
            if (score.isPublished()) continue;

            currentScore = score;

            Games.Leaderboards.submitScoreImmediate(mGoogleApiClient,
                    getString(R.string.leaderboard_world_top_scores), currentScore.getScore())
                    .setResultCallback(leaderBoardSubmitScoreCallback);


        }

        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                getString(R.string.leaderboard_world_top_scores)), RESULT_OK);

    }

    private void updateShareButton() {
        if (!containsScores()) {
            shareButton.setText(getText(R.string.top_score_share_button_nothing_to_publish));
            shareButtonActive = false;
        } else if(connectionFailed) {
            shareButtonActive = true;
            shareButton.setText(getText(R.string.top_score_share_button_retry_connection));
        }
        else if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {

            shareButtonActive = true;
            if (needsPublishing()) {
                shareButton.setText(getText(R.string.top_score_share_button_connected));
            } else {
                shareButton.setText(getText(R.string.top_score_share_button_all_published));
            }
        } else {
            shareButton.setText(getText(R.string.top_score_share_button_connecting));
            shareButtonActive = false;
        }
    }

    private boolean needsPublishing() {
        List<Score> topScores = scoreContract.getTopScores(parameters.getLimitTopScore());

        for (Score score : topScores) {
            if (!score.isPublished()) {
                return true;
            }
        }

        return false;
    }

    private boolean containsScores() {
        return scoreContract.getTopScores(1).size() > 0;
    }
}
