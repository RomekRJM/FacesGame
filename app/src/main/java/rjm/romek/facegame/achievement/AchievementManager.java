package rjm.romek.facegame.achievement;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import rjm.romek.facegame.model.Achievement;

public class AchievementManager {
    public static final Achievement[] achievements = new Achievement[]{
            new Achievement(1, "Grucho", "Guess 2 in a row", "face_1.png", "0", new GuessingSpreeAchievement(2)),
            new Achievement(2, "Teether", "Guess 5 in a row", "face_2.png", "0", new GuessingSpreeAchievement(5)),
            new Achievement(3, "Grumpo", "Guess 7 in a row", "face_3.png", "0", new GuessingSpreeAchievement(7)),
            new Achievement(4, "Huggy", "Guess 10 in a row", "face_4.png", "0", new GuessingSpreeAchievement(10)),
            new Achievement(5, "Shamo", "Have total 30 correct guesses", "face_5.png", "0", new TotalCorrectAnswerAchievement(30)),
            new Achievement(6, "Kissulla", "Have total 100 correct guesses", "face_6.png", "0", new TotalCorrectAnswerAchievement(100)),
            new Achievement(7, "Silencio", "Have total 250 correct guesses", "face_7.png", "0", new TotalCorrectAnswerAchievement(250)),
            new Achievement(8, "Yawner", "Have total 500 correct guesses", "face_8.png", "0", new TotalCorrectAnswerAchievement(500)),
            new Achievement(9, "Blusho", "Have total 1000 correct guesses", "face_9.png", "0", new TotalCorrectAnswerAchievement(1000)),
            new Achievement(10, "Positivo", "Complete 1 game", "face_10.png", "0", new TotalGamesAchievementUpdater(1)),
            new Achievement(11, "Prayen", "Complete 5 games", "face_11.png", "0", new TotalGamesAchievementUpdater(10)),
            new Achievement(12, "Cooka", "Complete 25 games", "face_12.png", "0", new TotalGamesAchievementUpdater(25)),
            new Achievement(13, "Weeper", "Complete 75 games", "face_13.png", "0", new TotalGamesAchievementUpdater(75)),
            new Achievement(14, "Zigzag", "Reach score 250", "face_14.png", "0", new ScoreAchievementUpdater(250)),
            new Achievement(15, "Afraido", "Reach score 500", "face_15.png", "0", new ScoreAchievementUpdater(500)),
            new Achievement(16, "Yuppz", "Reach score 750", "face_16.png", "0", new ScoreAchievementUpdater(750)),
            new Achievement(17, "Unlucko", "Reach score 1000", "face_17.png", "0", new ScoreAchievementUpdater(1000)),
            new Achievement(18, "Watchy", "Reach score 1200", "face_18.png", "0", new ScoreAchievementUpdater(1200))
    };

    public static String[] checkAchievementsForUpdates(Object update, Context context) {

        List<String> unlockedAchievements = new ArrayList<>();
        for (Achievement achievement : achievements) {
            if (achievement.updateAchievement(update, context)) {
                unlockedAchievements.add(achievement.getName());
            }
        }

        return unlockedAchievements.toArray(new String[unlockedAchievements.size()]);
    }

}
