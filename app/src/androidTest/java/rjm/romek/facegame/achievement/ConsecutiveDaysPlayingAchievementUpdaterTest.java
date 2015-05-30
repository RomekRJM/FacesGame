package rjm.romek.facegame.achievement;

import java.util.Date;
import java.util.List;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;
import static rjm.romek.facegame.achievement.AchievementManager.checkAchievementsForUpdates;
import static rjm.romek.facegame.utils.TestUtils.createAndSaveQuestion;

public class ConsecutiveDaysPlayingAchievementUpdaterTest extends AchievementTest {
    public void testWeekOfPlaying() {
        long now = System.currentTimeMillis();
        long dayInMillis = 86400000;

        for(int i=0; i<7; ++i) {
            createAndSaveQuestion(getContext(), "Poland", "Finland", new Date(now - i*dayInMillis));
        }

        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(
                questionService, getContext());

        containsAllAchievement(unlockedAchievementsNames,
                achievements[35], achievements[36], achievements[37], achievements[38]);
    }
}