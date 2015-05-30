package rjm.romek.facegame.achievement;

import java.util.Date;
import java.util.List;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;
import static rjm.romek.facegame.achievement.AchievementManager.checkAchievementsForUpdates;
import static rjm.romek.facegame.utils.TestUtils.createAndSaveQuestion;

public class CountriesCorrectlyGuessedAchievementTest extends AchievementTest {
    public void testNone() {
        createAndSaveQuestion(getContext(), "Poland", "Finland", new Date());
        createAndSaveQuestion(getContext(), "Cyprus", "Monako", new Date());
        createAndSaveQuestion(getContext(), "Poland", "Monako", new Date());

        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(questionService, getContext());
        doesNotContainGivenAchievements(unlockedAchievementsNames,
                achievements[39], achievements[40], achievements[41], achievements[42]);
    }

    public void testOne() {
        createAndSaveQuestion(getContext(), "Poland", "Poland", new Date());
        createAndSaveQuestion(getContext(), "Poland", "Monako", new Date());
        createAndSaveQuestion(getContext(), "Monako", "Monako", new Date());
        createAndSaveQuestion(getContext(), "France", "France", new Date());
        createAndSaveQuestion(getContext(), "Slovenia", "Slovenia", new Date());
        createAndSaveQuestion(getContext(), "Slovakia", "Slovakia", new Date());
        createAndSaveQuestion(getContext(), "Czech Republic", "Czech Republic", new Date());
        createAndSaveQuestion(getContext(), "Hungary", "Hungary", new Date());
        createAndSaveQuestion(getContext(), "Romania", "Romania", new Date());
        createAndSaveQuestion(getContext(), "Italy", "Italy", new Date());
        createAndSaveQuestion(getContext(), "Spain", "Spain", new Date());
        createAndSaveQuestion(getContext(), "Serbia", "Serbia", new Date());

        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(questionService, getContext());
        containsAllAchievement(unlockedAchievementsNames, achievements[39]);
        doesNotContainGivenAchievements(unlockedAchievementsNames,
                achievements[40], achievements[41], achievements[42]);
    }
}