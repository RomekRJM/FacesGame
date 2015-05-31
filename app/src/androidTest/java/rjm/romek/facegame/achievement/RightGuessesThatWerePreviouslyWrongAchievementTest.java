package rjm.romek.facegame.achievement;

import java.util.Date;
import java.util.List;

import static rjm.romek.facegame.achievement.AchievementManager.achievements;
import static rjm.romek.facegame.achievement.AchievementManager.checkAchievementsForUpdates;
import static rjm.romek.facegame.utils.TestUtils.createAndSaveQuestion;

public class RightGuessesThatWerePreviouslyWrongAchievementTest extends AchievementTest {
    public void testRightNumberOfCountries() {
        long now = System.currentTimeMillis();

        createAndSaveQuestion(getContext(), "Poland", "Poland", "a", new Date(++now));
        createAndSaveQuestion(getContext(), "Poland", "Monako", "a", new Date(++now));
        createAndSaveQuestion(getContext(), "Poland", "Poland", "a", new Date(++now)); // 1
        createAndSaveQuestion(getContext(), "Poland", "Poland", "a", new Date(++now)); // 1
        createAndSaveQuestion(getContext(), "Slovakia", "Slovakia", "c", new Date(++now));
        createAndSaveQuestion(getContext(), "France", "France", "b", new Date(++now));
        createAndSaveQuestion(getContext(), "France", "France", "b", new Date(++now));
        createAndSaveQuestion(getContext(), "Czech Republic", "Hungary", "d", new Date(++now));
        createAndSaveQuestion(getContext(), "Czech Republic", "Czech Republic", "d", new Date(++now)); // 2
        createAndSaveQuestion(getContext(), "Hungary", "Romania", "d", new Date(++now));
        createAndSaveQuestion(getContext(), "Hungary", "Hungary", "e", new Date(++now));
        createAndSaveQuestion(getContext(), "Italy", "Hungary", "f", new Date(++now));
        createAndSaveQuestion(getContext(), "Italy", "Italy", "f", new Date(++now)); // 3
        createAndSaveQuestion(getContext(), "Spain", "Serbia", "g", new Date(++now));
        createAndSaveQuestion(getContext(), "Spain", "Spain", "g", new Date(++now)); // 4
        createAndSaveQuestion(getContext(), "Spain", "Serbia", "i", new Date(++now));
        createAndSaveQuestion(getContext(), "Spain", "Spain", "i", new Date(++now)); // 5

        List<String> unlockedAchievementsNames = checkAchievementsForUpdates(questionService, getContext());
        containsAllAchievement(unlockedAchievementsNames, achievements[46]);
        doesNotContainGivenAchievements(unlockedAchievementsNames,
                achievements[47], achievements[48], achievements[49]);
    }
}