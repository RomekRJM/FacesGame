package rjm.romek.facegame.ui.manager;

import android.widget.TextView;

import rjm.romek.facegame.model.Question;
import rjm.romek.facegame.service.ScoreService;
import rjm.romek.facegame.service.ScoreServiceImpl;

public class ScoreManager {
    private TextView scoreTextView;
    private ScoreService scoreService;

    public ScoreManager(TextView scoreTextView) {
        this.scoreTextView = scoreTextView;
        this.scoreService = new ScoreServiceImpl();
    }

    public void updateScore(Question question) {
        scoreService.addQuestion(question);
        StringBuilder sb = new StringBuilder();

        if (scoreService.getMultiplier() >= 1) {

            if (scoreService.getMultiplier() > 1) {
                sb.append(scoreService.getMultiplier()).append("x");
            }

            sb.append(scoreService.getMultiplicand());

            if (scoreService.getCurrentScore() > 0) {
                sb.append(" + ").append(scoreService.getCurrentScore());
            }

        } else {
            sb.append(scoreService.getTotalScore());
        }

        scoreTextView.setText(sb.toString());
    }

    public ScoreService getScoreService() {
        return scoreService;
    }
}
