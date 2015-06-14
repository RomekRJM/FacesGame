package rjm.romek.facegame.achievement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import rjm.romek.facegame.model.Achievement;
import rjm.romek.facegame.model.Question;

import static rjm.romek.facegame.achievement.AchievementManager.*;
import static rjm.romek.facegame.utils.TestUtils.*;

public class GuessingSpreeAchievementTest extends AchievementTest {

    public void testUnlockedFirst() {
        Set<Question> set = createQuestionSet(new boolean[] {false, true, true, true, true, false});
        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(set, getContext());
        containsAllAchievement(unlockedAchievementsNames, achievements[0]);
    }

    public void testLocked() {
        Set<Question> set = createQuestionSet(new boolean[] {true, true, false, true, true});
        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(set, getContext());
        doesNotContainGivenAchievements(unlockedAchievementsNames, achievements[0]);
    }

    public void testUnlockedAll() {
        Set<Question> set = createCorrectQuestions(15);
        List<String> unlockedAchievementsNames = new ArrayList<>();
        Achievement [] shouldBeUnlocked = new Achievement[] {
                achievements[0], achievements[1], achievements[2], achievements[3], achievements[4]
        };

        for(int i=0; i<shouldBeUnlocked.length; ++i) {
            unlockedAchievementsNames.addAll(checkAchievementsForUpdates(set, getContext()));
        }

        containsAllAchievement(unlockedAchievementsNames, shouldBeUnlocked);
    }
}