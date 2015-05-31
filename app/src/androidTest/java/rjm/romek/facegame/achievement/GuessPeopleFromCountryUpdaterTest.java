package rjm.romek.facegame.achievement;

import java.util.Date;
import java.util.List;

import rjm.romek.facegame.model.Achievement;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;
import static rjm.romek.facegame.achievement.AchievementManager.checkAchievementsForUpdates;
import static rjm.romek.facegame.utils.TestUtils.createAndSaveQuestion;

public class GuessPeopleFromCountryUpdaterTest extends AchievementTest {

    public void testPoland() {
        checkPeopleFromCountryUnlocked("Poland", achievements[34]);
    }

    public void testUSA() {
        checkPeopleFromCountryUnlocked("United States", achievements[33]);
    }

    public void testNetherlands() {
        checkPeopleFromCountryUnlocked("Netherlands", achievements[32]);
    }

    public void testRussia() {
        checkPeopleFromCountryUnlocked("Russia", achievements[31]);
    }

    private void checkPeopleFromCountryUnlocked(String country, Achievement achievement) {
        for(int i=0; i<3; ++i) {
            createAndSaveQuestion(getContext(), country, country, null, new Date());
        }

        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(
                questionService, getContext());

        containsAllAchievement(unlockedAchievementsNames, achievement);
    }

}
