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
                    Long.valueOf(i), getContext());
            sum += i;

            if(sum >= 500 && sum < 2000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[18]);
            } else if (sum >= 2000 && sum < 5000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[19]);
            } else if (sum >= 5000 && sum < 10000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[20]);
            } else if (sum >= 10000 && sum < 20000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[21]);
            } else if (sum >= 20000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[22]);
            }
        }
    }
}