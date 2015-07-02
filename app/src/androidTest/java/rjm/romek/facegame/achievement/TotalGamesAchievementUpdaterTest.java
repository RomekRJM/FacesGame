package rjm.romek.facegame.achievement;

import java.util.List;
import java.util.Set;

import rjm.romek.facegame.model.Question;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;
import static rjm.romek.facegame.achievement.AchievementManager.checkAchievementsForUpdates;
import static rjm.romek.facegame.utils.TestUtils.createCorrectQuestions;
import static rjm.romek.facegame.utils.TestUtils.createQuestionSet;

public class TotalGamesAchievementUpdaterTest extends AchievementTest {

    public void testUnlockedAllInRightOrder() {
        Set<Question> game = createCorrectQuestions(1);
        List<String> unlockedAchievementsNames;

        for (int i = 1; i <= 75; ++i) {
            unlockedAchievementsNames = checkAchievementsForUpdates(
                    game, getContext());

            switch(i) {
                case 10:
                    containsAllAchievement(unlockedAchievementsNames, achievements[10]);
                    break;
                case 25:
                    containsAllAchievement(unlockedAchievementsNames, achievements[11]);
                    break;
                case 50:
                    containsAllAchievement(unlockedAchievementsNames, achievements[12]);
                    break;
                case 75:
                    containsAllAchievement(unlockedAchievementsNames, achievements[13]);
                    break;
                default:
                    doesNotContainGivenAchievements(unlockedAchievementsNames,
                            achievements[10], achievements[11], achievements[12], achievements[13]);
                    break;
            }
        }
    }

}