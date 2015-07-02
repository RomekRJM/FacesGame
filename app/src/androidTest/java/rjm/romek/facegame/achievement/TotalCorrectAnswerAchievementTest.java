package rjm.romek.facegame.achievement;

import java.util.ArrayList;
import java.util.List;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;
import static rjm.romek.facegame.achievement.AchievementManager.checkAchievementsForUpdates;
import static rjm.romek.facegame.utils.TestUtils.createCorrectQuestions;

public class TotalCorrectAnswerAchievementTest extends AchievementTest {
    public void testUnlockedFirstOnly() {
        List<String> unlockedAchievementsNames = checkIfUnlockedManyTimes(49, 2);
        containsAllAchievement(unlockedAchievementsNames, achievements[5]);
        doesNotContainGivenAchievements(unlockedAchievementsNames, achievements[6]);
    }

    public void testUnlockedFirstThenSecond() {
        List<String> unlockedAchievementsNames = checkIfUnlockedManyTimes(50, 1);
        containsAllAchievement(unlockedAchievementsNames, achievements[5]);

        unlockedAchievementsNames = checkIfUnlockedManyTimes(10, 10);
        containsAllAchievement(unlockedAchievementsNames, achievements[6]);
    }

    public void testUnlockedAll() {
        List<String> unlockedAchievementsNames = checkIfUnlockedManyTimes(10, 100);
        containsAllAchievement(unlockedAchievementsNames, achievements[5], achievements[6],
                achievements[7], achievements[8], achievements[9]);
    }

    private List<String> checkIfUnlockedManyTimes(int correct, int checkNumber) {
        List<String> unlockedAchievementsNames = new ArrayList<>();

        for(int i=0; i<checkNumber; ++i) {
            unlockedAchievementsNames.addAll(checkAchievementsForUpdates(
                    createCorrectQuestions(correct), getContext()));
        }

        return unlockedAchievementsNames;
    }
}