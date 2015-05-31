package rjm.romek.facegame.achievement;

import java.util.List;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;
import static rjm.romek.facegame.achievement.AchievementManager.checkAchievementsForUpdates;

public class ScoreAchievementUpdaterTest extends AchievementTest {
    public void testUnlockedAllInRightOrder() {
        List<String> unlockedAchievementsNames;

        for (int i = 0; i <= 12000; i+=250) {
            unlockedAchievementsNames = checkAchievementsForUpdates(
                    i, getContext());

            switch (i) {
                case 250:
                    containsAllAchievement(unlockedAchievementsNames, achievements[14]);
                    break;
                case 500:
                    containsAllAchievement(unlockedAchievementsNames, achievements[15]);
                    break;
                case 750:
                    containsAllAchievement(unlockedAchievementsNames, achievements[16]);
                    break;
                case 1000:
                    containsAllAchievement(unlockedAchievementsNames, achievements[17]);
                    break;
                case 2000:
                    containsAllAchievement(unlockedAchievementsNames, achievements[18]);
                    break;
                case 3000:
                    containsAllAchievement(unlockedAchievementsNames, achievements[19]);
                    break;
                case 5000:
                    containsAllAchievement(unlockedAchievementsNames, achievements[20]);
                    break;
                case 8000:
                    containsAllAchievement(unlockedAchievementsNames, achievements[21]);
                    break;
                case 12000:
                    containsAllAchievement(unlockedAchievementsNames, achievements[22]);
                    break;
                default:
                    doesNotContainGivenAchievements(unlockedAchievementsNames, achievements[14],
                            achievements[15], achievements[16], achievements[17], achievements[18],
                            achievements[19], achievements[20], achievements[21], achievements[22]);
                    break;
            }
        }
    }
}