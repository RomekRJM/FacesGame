package rjm.romek.facegame.ui.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;

import java.util.List;

import rjm.romek.facegame.R;
import rjm.romek.facegame.common.Parameters;
import rjm.romek.facegame.data.ScoreContract;
import rjm.romek.facegame.model.Score;

import static rjm.romek.facegame.data.ScoreContract.ScoreEntry;

public class TopScore extends ListActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Parameters parameters;
    private ScoreContract scoreContract;
    private SimpleCursorAdapter adapter;
    static final String TAG = "TopScore";
    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 9001;
    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;
    private static final int RC_UNUSED = 5001;
    static final String[] FROM = { ScoreEntry.PLAYER, ScoreEntry.SCORE,
            ScoreEntry.CORRECT_ANSWERS, ScoreEntry.DATE, ScoreEntry._ID };
    static final int[] TO = { R.id.text_player, R.id.text_score, R.id.text_correct,
            R.id.text_date, R.id.text_position };
    private static int positionCounter;

    static final ViewBinder VIEW_BINDER = new ViewBinder() {

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
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
        this.parameters = new Parameters();
        this.scoreContract = new ScoreContract(this);
        init();
    }

    public void init() {
        positionCounter = 1;
        Cursor cursor = scoreContract.getTopScoresCursor(parameters.getLimitTopScore());

        adapter = new SimpleCursorAdapter(this, R.layout.top_score_row, cursor, FROM, TO);
        adapter.setViewBinder(VIEW_BINDER);
        setListAdapter(adapter);

        // Create the Google API Client with access to Plus and Games
        try {
            // Create the Google Api Client with access to the Play Game services
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                    .build();

        } catch (Exception exc) {
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        List<Score> topScores = scoreContract.getTopScores(10);

        for(Score score : topScores) {
            Games.Leaderboards.submitScore(mGoogleApiClient,
                    getString(R.string.leaderboard_world_top_scores), score.getScore());
        }

        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                getString(R.string.leaderboard_world_top_scores)), 10);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
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
            mSignInClicked = false;
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
            }
        }
    }

    // Call when the sign-in button is clicked
    private void signInClicked() {
        mSignInClicked = true;
        mGoogleApiClient.connect();
    }

    // Call when the sign-out button is clicked
    private void signOutclicked() {
        mSignInClicked = false;
        Games.signOut(mGoogleApiClient);
    }


}
