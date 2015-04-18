package rjm.romek.facegame.achievement;

import java.util.Set;

import rjm.romek.facegame.achievement.condition.IsGreaterCondition;
import rjm.romek.facegame.achievement.updater.MaxUpdate;
import rjm.romek.facegame.model.Question;

public class GuessingSpreeAchievement extends QuestionCounterAchievementUpdater {

    public GuessingSpreeAchievement(long value) {
        super(new IsGreaterCondition(value), new MaxUpdate());
    }

    @Override
    protected Long transform(Set<Question> change) {
        long currentSpree = 0;
        long longestSpree = 0;

        for(Question q : change) {
            if(q.isCorrectlyAnswered()) {
                ++currentSpree;
            }

            if(currentSpree > longestSpree) {
                longestSpree = currentSpree;
            }

            if(!q.isCorrectlyAnswered()) {
                currentSpree = 0;
            }
        }

        return longestSpree;
    }
}
