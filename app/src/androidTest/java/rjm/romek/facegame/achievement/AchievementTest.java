package rjm.romek.facegame.achievement;

import android.test.AndroidTestCase;

import java.io.IOException;
import java.util.List;

import rjm.romek.facegame.data.DbHelper;
import rjm.romek.facegame.model.Achievement;
import rjm.romek.facegame.service.QuestionService;
import rjm.romek.facegame.service.QuestionServiceImpl;
import rjm.romek.facegame.common.Global;
import rjm.romek.facegame.utils.TestUtils;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;

public class AchievementTest extends AndroidTestCase {

    protected QuestionService questionService;

    @Override
    public void setUp() throws IOException {
        Global.isRunningTests = true;
        for(Achievement a : achievements) {
            a.setUnlocked(false);
            a.setData("0");
        }
        DbHelper.getInstance(getContext()).cleanUp();
        questionService = new QuestionServiceImpl(getContext(),
                TestUtils.loadCountries(getContext().getAssets()));
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
