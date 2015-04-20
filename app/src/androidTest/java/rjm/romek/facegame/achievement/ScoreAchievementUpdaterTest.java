package rjm.romek.facegame.achievement;

import java.util.List;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;
import static rjm.romek.facegame.achievement.AchievementManager.checkAchievementsForUpdates;

public class ScoreAchievementUpdaterTest extends AchievementTest {
    public void testUnlockedAllInRightOrder() {
        List<String> unlockedAchievementsNames;

        for (int i = 0; i <= 1250; i+=250) {
            unlockedAchievementsNames = checkAchievementsForUpdates(
                    Long.valueOf(i), getContext());

            switch (i) {
                case 250:
                    containsAllAchievement(unlockedAchievementsNames, achievements[13]);
                    break;
                case 500:
                    containsAllAchievement(unlockedAchievementsNames, achievements[14]);
                    break;
                case 750:
                    containsAllAchievement(unlockedAchievementsNames, achievements[15]);
                    break;
                case 1000:
                    containsAllAchievement(unlockedAchievementsNames, achievements[16]);
                    break;
                case 1250:
                    containsAllAchievement(unlockedAchievementsNames, achievements[17]);
                    break;
                default:
                    doesNotContainGivenAchievements(unlockedAchievementsNames, achievements[13],
                            achievements[14], achievements[15], achievements[16], achievements[17]);
                    break;
            }
        }
    }
}