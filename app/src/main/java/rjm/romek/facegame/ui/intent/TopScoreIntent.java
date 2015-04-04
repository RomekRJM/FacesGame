package rjm.romek.facegame.ui.intent;

import android.content.Context;
import android.content.Intent;

import rjm.romek.facegame.ui.activity.TopScore;

public class TopScoreIntent extends Intent {
    public TopScoreIntent(Context context) {
        super(context, TopScore.class);
    }
}