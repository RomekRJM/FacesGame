package rjm.romek.facegame.ui.manager;

import android.view.View;
import android.widget.RelativeLayout;

import rjm.romek.facegame.R;
import rjm.romek.facegame.model.Difficulty;

public class DifficultyManager {
    private View topGameLayout;
    private View topBackground;

    public DifficultyManager(View topGameLayout, View topBackground) {
        this.topGameLayout = topGameLayout;
        this.topBackground = topBackground;
    }

    public void update(Difficulty difficulty) {
        topBackground.setBackgroundResource(R.color.black);
        topBackground.invalidate();

        topBackground.setBackgroundResource(R.color.blue_diff);
        int width = topGameLayout.getWidth() *
                (difficulty.ordinal() + 1) / Difficulty.values().length;
        topBackground.setMinimumHeight(topGameLayout.getHeight());
        topBackground.setMinimumWidth(width);
        topBackground.invalidate();
    }
}
