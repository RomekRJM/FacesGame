package rjm.romek.facegame.achievement;

import java.util.Set;

import rjm.romek.facegame.model.Question;

public class GuessingSpreeAchievement extends MaxAchievementUpdater {

    public GuessingSpreeAchievement(long value) {
        super(value);
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
