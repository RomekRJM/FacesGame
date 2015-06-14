package rjm.romek.facegame.achievement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;
import static rjm.romek.facegame.achievement.AchievementManager.checkAchievementsForUpdates;
import static rjm.romek.facegame.utils.TestUtils.createAndSaveQuestion;

public class ConsecutiveDaysPlayingAchievementUpdaterTest extends AchievementTest {
    public void testWeekOfPlaying() {
        long now = System.currentTimeMillis();
        long dayInMillis = 86400000;
        List<String> unlockedAchievementsNames = new ArrayList<>();

        for(int i=0; i<7; ++i) {
            createAndSaveQuestion(getContext(), "Poland", "Finland", null,
                    new Date(now - i*dayInMillis));
            unlockedAchievementsNames.addAll(checkAchievementsForUpdates(
                    questionService, getContext()));
        }

        containsAllAchievement(unlockedAchievementsNames,
                achievements[38], achievements[39], achievements[40], achievements[41]);
    }
}