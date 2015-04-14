package rjm.romek.facegame.ui.intent;

import android.content.Context;
import android.content.Intent;

import rjm.romek.facegame.ui.activity.EndGame;

public class EndGameIntent extends Intent {
    public static final String SCORE = "score";
    public static final String CORRECT_ANSWERS = "correct";
    public static final String UNLOCKED_ACHIEVEMENTS = "unlocked";

    public EndGameIntent(Context context, int correctAnswers, long score,
                         String[] unlockedAchievementsNames) {
        super(context, EndGame.class);
        this.putExtra(CORRECT_ANSWERS, correctAnswers);
        this.putExtra(SCORE, score);
        this.putExtra(UNLOCKED_ACHIEVEMENTS, unlockedAchievementsNames);
    }
}
