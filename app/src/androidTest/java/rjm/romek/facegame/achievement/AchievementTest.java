package rjm.romek.facegame.achievement;

import android.test.AndroidTestCase;

import java.util.List;

import rjm.romek.facegame.data.DbHelper;
import rjm.romek.facegame.model.Achievement;
import rjm.romek.facegame.ui.global.Global;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;

public class AchievementTest extends AndroidTestCase {
    @Override
    public void setUp() {
        Global.isRunningTests = true;
        for(Achievement a : achievements) {
            a.setUnlocked(false);
            a.setData("0");
        }
        DbHelper.getInstance(getContext()).cleanUp();
    }

    protected void containsAllAchievement(List<String> unlockedAchievementsNames, Achievement ... achievements) {
        for(Achievement achievement : achievements) {
            if(!unlockedAchievementsNames.contains(achievement.getName())) {
                fail(achievement.getName() + " should be unlocked.");
            }
        }
    }

    protected void doesNotContainGivenAchievements(List<String> unlockedAchievementsNames, Achievement ... achievements) {
        for(Achievement achievement : achievements) {
            if(unlockedAchievementsNames.contains(achievement.getName())) {
                fail(achievement.getName() + " shouldn't be unlocked.");
            }
        }
    }
}
