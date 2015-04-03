package rjm.romek.facegame.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

import rjm.romek.facegame.R;
import rjm.romek.facegame.data.ScoreContract;
import rjm.romek.facegame.model.Score;
import rjm.romek.facegame.ui.intent.EndGameIntent;
import rjm.romek.facegame.ui.intent.GameIntent;
import rjm.romek.facegame.ui.intent.MainMenuIntent;

public class EndGame extends Activity implements OnClickListener {

    private Button buttonBack;
    private Button buttonAgain;
    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_game);

        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);
        buttonAgain = (Button) findViewById(R.id.buttonAgain);
        buttonAgain.setOnClickListener(this);

        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        long score = getIntent().getLongExtra(EndGameIntent.SCORE, 0);
        int correct = getIntent().getIntExtra(EndGameIntent.CORRECT_ANSWERS, 0);
        scoreTextView.setText(getString(R.string.end_game_score) + score + " (" + correct + ")");

        saveScore(score, correct);
    }

    private void saveScore(long scoreValue, int correctAnswers) {
        Score score = new Score();
        score.setDate(new Date());
        score.setScore(scoreValue);
        score.setCorrectAnswers(correctAnswers);
        score.setPlayer("Player");
        new ScoreContract(this).saveScore(score);
    }

    @Override
    public void onClick(View v) {
        if (!(v instanceof Button)) {
            return;
        }

        if (v == buttonBack) {
            startActivity(new MainMenuIntent(this));
        } else if (v == buttonAgain) {
            startActivity(new GameIntent(this));
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new MainMenuIntent(this));
    }
}
