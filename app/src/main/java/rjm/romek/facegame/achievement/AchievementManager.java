package rjm.romek.facegame.achievement;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import rjm.romek.facegame.model.Achievement;

public class AchievementManager {
    public static final Achievement[] achievements = new Achievement[]{
            new Achievement("Grucho", "Guess 2 in a row", "face_1.png", "0", new GuessingSpreeAchievement(2)),
            new Achievement("Teether", "Guess 5 in a row", "face_2.png", "0", new GuessingSpreeAchievement(5)),
            new Achievement("Grumpo", "Guess 7 in a row", "face_3.png", "0", new GuessingSpreeAchievement(7)),
            new Achievement("Huggy", "Guess 10 in a row", "face_4.png", "0", new GuessingSpreeAchievement(10)),
            new Achievement("Shamo", "Have total 30 correct guesses", "face_5.png", "0", new TotalCorrectAnswerAchievement(30)),
            new Achievement("Kissulla", "Have total 100 correct guesses", "face_6.png", "0", new TotalCorrectAnswerAchievement(100)),
            new Achievement("Silencio", "Have total 250 correct guesses", "face_7.png", "0", new TotalCorrectAnswerAchievement(250)),
            new Achievement("Yawner", "Have total 500 correct guesses", "face_8.png", "0", new TotalCorrectAnswerAchievement(500)),
            new Achievement("Blusho", "Have total 1000 correct guesses", "face_9.png", "0", new TotalCorrectAnswerAchievement(1000))
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
