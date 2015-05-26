package rjm.romek.facegame.achievement;

import java.util.Set;

import rjm.romek.facegame.achievement.activator.OnSetActivate;
import rjm.romek.facegame.achievement.condition.IsGreaterOrEqualDifficulty;
import rjm.romek.facegame.achievement.updater.DifficultyUpdate;
import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;

public class DifficultyAchievementUpdaterImpl extends AchievementUpdater<Set<Question>, Difficulty> {

    public DifficultyAchievementUpdaterImpl(Difficulty difficulty) {
        super(new IsGreaterOrEqualDifficulty(difficulty), new DifficultyUpdate(), new OnSetActivate());
    }

    @Override
    protected Difficulty transform(Set<Question> change) {
        Difficulty difficulty = Difficulty.EASY;

        for(Question question : change) {
            Difficulty currentDifficulty = question.getDifficulty();
            if(question.getDifficulty().ordinal() > difficulty.ordinal()) {
                difficulty = currentDifficulty;
            }
        }

        return difficulty;
    }
}
