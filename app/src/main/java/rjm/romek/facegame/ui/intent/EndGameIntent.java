package rjm.romek.facegame.ui.intent;

import android.content.Context;
import android.content.Intent;

import rjm.romek.facegame.ui.activity.EndGame;
import rjm.romek.facegame.ui.activity.LoadingScreen;

public class EndGameIntent extends Intent {
    public static final String SCORE = "score";

    public EndGameIntent(Context context, int score) {
        super(context, EndGame.class);
        this.putExtra(SCORE, score);
    }
}
