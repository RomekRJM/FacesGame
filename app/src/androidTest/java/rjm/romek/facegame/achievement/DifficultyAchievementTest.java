package rjm.romek.facegame.achievement;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;
import static rjm.romek.facegame.achievement.AchievementManager.checkAchievementsForUpdates;
import static rjm.romek.facegame.utils.TestUtils.createQuestion;

public class DifficultyAchievementTest extends AchievementTest {

    public void testHardcore() {
        Set<Question> questions = new LinkedHashSet<>();
        questions.add(createQuestion(true, 1000, Difficulty.HARDCORE));
        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(questions, getContext());

        containsAllAchievement(unlockedAchievementsNames, achievements[30]);
    }

    public void testNormalAndHard() {
        Set<Question> questions = new LinkedHashSet<>();
        questions.add(createQuestion(true, 1000, Difficulty.NORMAL));
        questions.add(createQuestion(true, 1000, Difficulty.HARD));
        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(questions, getContext());

        containsAllAchievement(unlockedAchievementsNames, achievements[28], achievements[29]);
        doesNotContainGivenAchievements(unlockedAchievementsNames, achievements[30]);
    }

}