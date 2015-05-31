package rjm.romek.facegame.achievement;

import java.util.Date;
import java.util.List;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;
import static rjm.romek.facegame.achievement.AchievementManager.checkAchievementsForUpdates;
import static rjm.romek.facegame.utils.TestUtils.createAndSaveQuestion;

public class CountriesCorrectlyGuessedAchievementTest extends AchievementTest {
    public void testNone() {
        createAndSaveQuestion(getContext(), "Poland", "Finland", null, new Date());
        createAndSaveQuestion(getContext(), "Cyprus", "Monako", null, new Date());
        createAndSaveQuestion(getContext(), "Poland", "Monako", null, new Date());

        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(questionService, getContext());
        doesNotContainGivenAchievements(unlockedAchievementsNames,
                achievements[42], achievements[43], achievements[44], achievements[45]);
    }

    public void testOne() {
        createAndSaveQuestion(getContext(), "Poland", "Poland", null, new Date());
        createAndSaveQuestion(getContext(), "Poland", "Monako", null, new Date());
        createAndSaveQuestion(getContext(), "Monako", "Monako", null, new Date());
        createAndSaveQuestion(getContext(), "France", "France", null, new Date());
        createAndSaveQuestion(getContext(), "Slovenia", "Slovenia", null, new Date());
        createAndSaveQuestion(getContext(), "Slovakia", "Slovakia", null, new Date());
        createAndSaveQuestion(getContext(), "Czech Republic", "Czech Republic", null, new Date());
        createAndSaveQuestion(getContext(), "Hungary", "Hungary", null, new Date());
        createAndSaveQuestion(getContext(), "Romania", "Romania", null, new Date());
        createAndSaveQuestion(getContext(), "Italy", "Italy", null, new Date());
        createAndSaveQuestion(getContext(), "Spain", "Spain", null, new Date());
        createAndSaveQuestion(getContext(), "Serbia", "Serbia", null, new Date());

        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(questionService, getContext());
        containsAllAchievement(unlockedAchievementsNames, achievements[42]);
        doesNotContainGivenAchievements(unlockedAchievementsNames,
                achievements[43], achievements[44], achievements[45]);
    }
}