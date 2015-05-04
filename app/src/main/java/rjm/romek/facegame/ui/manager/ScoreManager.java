package rjm.romek.facegame.ui.manager;

import android.content.Context;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;

import rjm.romek.facegame.R;
import rjm.romek.facegame.model.Question;
import rjm.romek.facegame.service.ScoreService;
import rjm.romek.facegame.service.ScoreServiceImpl;

public class ScoreManager implements Animator.AnimatorListener {
    private final TextView scoreTextView;
    private final ScoreService scoreService;
    private final Context context;
    private final int defaultColor;

    public ScoreManager(TextView scoreTextView, Context context) {
        this.scoreTextView = scoreTextView;
        this.scoreService = new ScoreServiceImpl();
        this.context = context;
        this.defaultColor = scoreTextView.getCurrentTextColor();
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
        Techniques techniques;

        if(question.isCorrectlyAnswered()){
            scoreTextView.setTextColor(context.getResources().getColor(R.color.green_correct));
            techniques = Techniques.FlipInX;
        } else {
            scoreTextView.setTextColor(context.getResources().getColor(R.color.red_wrong));
            techniques = Techniques.Shake;
        }

        YoYo.with(techniques)
                .withListener(this)
                .duration(700)
                .playOn(scoreTextView);
    }

    public ScoreService getScoreService() {
        return scoreService;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        scoreTextView.setTextColor(defaultColor);
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
