package rjm.romek.facegame.ui.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.games.leaderboard.Leaderboards.SubmitScoreResult;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import rjm.romek.facegame.R;
import rjm.romek.facegame.common.GooglePlayable;
import rjm.romek.facegame.common.Parameters;
import rjm.romek.facegame.data.ScoreContract;
import rjm.romek.facegame.model.Score;
import rjm.romek.facegame.ui.adapter.TopScoreAdapter;
import rjm.romek.facegame.ui.manager.GooglePlayManager;

import static rjm.romek.facegame.data.ScoreContract.ScoreEntry;

public class TopScore extends ListActivity implements View.OnClickListener, GooglePlayable {

    private Parameters parameters;
    private ScoreContract scoreContract;
    private ArrayAdapter<Score> adapter;
    private Button shareButton;
    private Score currentScore;
    private LeaderBoardSubmitScoreCallback leaderBoardSubmitScoreCallback;
    private LeaderBoardLoadPlayerScoreCallback leaderBoardLoadPlayerScoreCallback;
    private GooglePlayManager googlePlayManager;
    static final String TAG = "TopScore";

    private class LeaderBoardSubmitScoreCallback implements ResultCallback<SubmitScoreResult> {
        @Override
        public void onResult(SubmitScoreResult res) {
            if (res.getStatus().isSuccess()) {
                currentScore.setPublished(true);
                scoreContract.updateScore(currentScore);

                Games.Leaderboards.loadCurrentPlayerLeaderboardScore(
                        googlePlayManager.getGoogleApiClient(),
                        getString(R.string.leaderboard_best_players),
                        LeaderboardVariant.TIME_SPAN_ALL_TIME,
                        LeaderboardVariant.COLLECTION_PUBLIC)
                        .setResultCallback(leaderBoardLoadPlayerScoreCallback);
            }
        }
    }

    private class LeaderBoardLoadPlayerScoreCallback implements ResultCallback<Leaderboards.LoadPlayerScoreResult> {
        @Override
        public void onResult(Leaderboards.LoadPlayerScoreResult result) {
            LeaderboardScore score = result.getScore();
            String name = score.getScoreHolder().getDisplayName();

            List<Score> topScores = scoreContract.getTopScores(parameters.getLimitTopScore());

            for(Score s : topScores) {
                if(s.getPlayer() == null) {
                    s.setPlayer(name);
                    scoreContract.updateScore(s);
                }
            }

            repaintList();
        }
    }

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

        init();
    }

    public void init() {
        this.parameters = new Parameters();
        this.scoreContract = new ScoreContract(this);

        adapter = new TopScoreAdapter(this, R.layout.top_score_row, getTopScoresArray());
        setListAdapter(adapter);
        leaderBoardSubmitScoreCallback = new LeaderBoardSubmitScoreCallback();
        leaderBoardLoadPlayerScoreCallback = new LeaderBoardLoadPlayerScoreCallback();
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

    public void repaintList() {
        adapter.clear();
        Score [] scores = getTopScoresArray();

        for (Score score : scores) {
            adapter.add(score);
        }

        adapter.notifyDataSetChanged();
    }

    private List<Score> getTopScores() {
        return scoreContract.getTopScores(parameters.getLimitTopScore());
    }

    private Score[] getTopScoresArray() {
        List<Score> topScores = getTopScores();
        return topScores.toArray(new Score[topScores.size()]);
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
    public boolean isEmpty() {
        return scoreContract.getTopScores(1).isEmpty();
    }
}
