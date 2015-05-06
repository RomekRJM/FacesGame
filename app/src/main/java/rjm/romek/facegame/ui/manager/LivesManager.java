package rjm.romek.facegame.ui.manager;

import android.app.Activity;
import android.view.View;

import rjm.romek.facegame.R;
import rjm.romek.facegame.model.Question;

public class LivesManager {
    private int lives;
    private Activity activity;
    private final int [] livesIcons = new int [] {
            R.id.ball0, R.id.ball1, R.id.ball2, R.id.ball3, R.id.ball4
    };

    public LivesManager(Activity activity) {
        this.lives = 3;
        this.activity = activity;
    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    public boolean update(Question question) {

        if(!question.isCorrectlyAnswered()) {
            --lives;
        }

        for(int i = 0; i < livesIcons.length; ++i) {
            if(i >= lives) {
                activity.findViewById(livesIcons[i]).setVisibility(View.INVISIBLE);
            } else {
                activity.findViewById(livesIcons[i]).setVisibility(View.VISIBLE);
            }
        }

        return isGameOver();
    }
}
