package rjm.romek.facegame.achievement;

import android.test.AndroidTestCase;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import rjm.romek.facegame.data.DbHelper;
import rjm.romek.facegame.model.Achievement;
import rjm.romek.facegame.model.Question;
import rjm.romek.facegame.ui.global.Global;

import static rjm.romek.facegame.achievement.AchievementManager.*;
import static rjm.romek.facegame.utils.TestUtils.*;

public class GuessingSpreeAchievementTest extends AndroidTestCase {

    @Override
    public void setUp() {
        Global.isRunningTests = true;
        for(Achievement a : achievements) {
            a.setUnlocked(false);
            a.setData("0");
        }
        DbHelper.getInstance(getContext()).cleanUp();
    }

    public void testUnlockedFirst() {
        Set<Question> set = createQuestionSet(new boolean[] {false, true, true, true, false});
        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(set, getContext());
        containsAllAchievement(unlockedAchievementsNames, achievements[0]);
    }

    public void testLocked() {
        Set<Question> set = createQuestionSet(new boolean[] {true, true, false, true, false});
        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(set, getContext());
        doesNotContainGivenAchievements(unlockedAchievementsNames, achievements[0]);
    }

    public void testUnlockedAll() {
        boolean [] tenTrue = new boolean[10];
        Arrays.fill(tenTrue, true);
        Set<Question> set = createQuestionSet(tenTrue);
        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(set, getContext());
        containsAllAchievement(unlockedAchievementsNames,
                achievements[0], achievements[1], achievements[2], achievements[3]);
    }

    private void containsAllAchievement(List<String> unlockedAchievementsNames, Achievement ... achievements) {
        for(Achievement achievement : achievements) {
            if(!unlockedAchievementsNames.contains(achievement.getName())) {
                fail(achievement.getName() + " should be unlocked.");
            }
        }
    }

    private void doesNotContainGivenAchievements(List<String> unlockedAchievementsNames, Achievement ... achievements) {
        for(Achievement achievement : achievements) {
            if(unlockedAchievementsNames.contains(achievement.getName())) {
                fail(achievement.getName() + " shouldn't be unlocked.");
            }
        }
    }
}