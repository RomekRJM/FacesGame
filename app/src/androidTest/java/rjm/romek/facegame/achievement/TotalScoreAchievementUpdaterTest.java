package rjm.romek.facegame.achievement;

import java.util.ArrayList;
import java.util.List;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;
import static rjm.romek.facegame.achievement.AchievementManager.checkAchievementsForUpdates;

public class TotalScoreAchievementUpdaterTest extends AchievementTest {
    public void testUnlockedAllInRightOrder() {
        List<String> unlockedAchievementsNames = new ArrayList<>();
        long sum = 0;
        long step = 2500;

        for (int i = 0; sum <= 125000; ++i) {
            sum += step;
            unlockedAchievementsNames.addAll(checkAchievementsForUpdates(
                    Long.valueOf(step), getContext()));

            if(sum >= 2500 && sum < 5000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[23]);
            } else if (sum >= 5000 && sum < 10000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[24]);
            } else if (sum >= 10000 && sum < 20000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[25]);
            } else if (sum >= 20000 && sum < 50000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[26]);
            } else if (sum >= 50000 && sum < 75000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[27]);
            } else if (sum >= 75000 && sum < 125000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[28]);
            } else if (sum >= 125000) {
                containsAllAchievement(unlockedAchievementsNames, achievements[29]);
            }
        }
    }
}