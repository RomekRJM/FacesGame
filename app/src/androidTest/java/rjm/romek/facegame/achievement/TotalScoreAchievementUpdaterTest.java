package rjm.romek.facegame.achievement;

import java.util.List;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;
import static rjm.romek.facegame.achievement.AchievementManager.checkAchievementsForUpdates;

public class TotalScoreAchievementUpdaterTest extends AchievementTest {
    public void testUnlockedAllInRightOrder() {
        List<String> unlockedAchievementsNames;
        int sum = 0;

        for (int i = 0; i <= 20; ++i) {
            unlockedAchievementsNames = checkAchievementsForUpdates(
                    i, getContext());
            sum += i;

            if(sum >= 500 && sum < 2000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[23]);
            } else if (sum >= 2000 && sum < 5000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[24]);
            } else if (sum >= 5000 && sum < 10000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[25]);
            } else if (sum >= 10000 && sum < 20000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[26]);
            } else if (sum >= 20000 && sum < 50000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[27]);
            } else if (sum >= 50000 && sum < 100000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[28]);
            } else if (sum >= 100000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[29]);
            }
        }
    }
}