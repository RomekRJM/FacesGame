package rjm.romek.facegame.achievement;

import java.util.List;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;
import static rjm.romek.facegame.achievement.AchievementManager.checkAchievementsForUpdates;
import static rjm.romek.facegame.utils.TestUtils.createCorrectQuestions;

public class TotalCorrectAnswerAchievementTest extends AchievementTest {
    public void testUnlockedFirstOnly() {
        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(
                createCorrectQuestions(99), getContext());
        containsAllAchievement(unlockedAchievementsNames, achievements[5]);
        doesNotContainGivenAchievements(unlockedAchievementsNames, achievements[6]);
    }

    public void testUnlockedFirstThenSecond() {
        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(
                createCorrectQuestions(30), getContext());
        containsAllAchievement(unlockedAchievementsNames, achievements[5]);

        unlockedAchievementsNames = checkAchievementsForUpdates(
                createCorrectQuestions(70), getContext());
        containsAllAchievement(unlockedAchievementsNames, achievements[6]);
    }

    public void testUnlockedAll() {
        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(
                createCorrectQuestions(1000), getContext());
        containsAllAchievement(unlockedAchievementsNames,
                achievements[4], achievements[5], achievements[6], achievements[7]);
    }
}