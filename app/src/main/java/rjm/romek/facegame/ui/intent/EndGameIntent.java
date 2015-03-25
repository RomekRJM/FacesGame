package rjm.romek.facegame.ui.intent;

import android.content.Context;
import android.content.Intent;

import rjm.romek.facegame.ui.activity.EndGame;

public class EndGameIntent extends Intent {
    public static final String SCORE = "score";
    public static final String CORRECT_ANSWERS = "correct";

    public EndGameIntent(Context context, int correctAnswers, long score) {
        super(context, EndGame.class);
        this.putExtra(CORRECT_ANSWERS, correctAnswers);
        this.putExtra(SCORE, score);
    }
}
