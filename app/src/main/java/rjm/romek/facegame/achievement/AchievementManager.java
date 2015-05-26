package rjm.romek.facegame.achievement;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import rjm.romek.facegame.model.Achievement;
import rjm.romek.facegame.model.Difficulty;

public class AchievementManager {
    public static final Achievement[] achievements = new Achievement[]{
            new Achievement(1, "Grucho", "Guess 3 in a row", "face_1.png", "0", new GuessingSpreeAchievement(3)),
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
            new Achievement(18, "Watchy", "Reach score 1200", "face_18.png", "0", new ScoreAchievementUpdater(1200)),
            new Achievement(19, "Onetimo", "Gather 500 points in any number of games", "face_19.png", "0", new TotalScoreAchievementUpdater(500)),
            new Achievement(20, "Ohmydo", "Gather 2000 points in any number of games", "face_20.png", "0", new TotalScoreAchievementUpdater(2000)),
            new Achievement(21, "Karminia", "Gather 5000 points in any number of games", "face_21.png", "0", new TotalScoreAchievementUpdater(5000)),
            new Achievement(22, "Rosario", "Gather 10000 points in any number of games", "face_22.png", "0", new TotalScoreAchievementUpdater(10000)),
            new Achievement(23, "Amphetamo", "Gather 20000 points in any number of games", "face_23.png", "0", new TotalScoreAchievementUpdater(20000)),
            new Achievement(24, "Coolejandro", "Reach normal difficulty.", "face_24.png", "EASY", new DifficultyAchievementUpdaterImpl(Difficulty.NORMAL)),
            new Achievement(25, "Wack", "Reach hard difficulty.", "face_25.png", "EASY", new DifficultyAchievementUpdaterImpl(Difficulty.HARD)),
            new Achievement(26, "Piranhers", "Reach hardcore difficulty.", "face_26.png", "EASY", new DifficultyAchievementUpdaterImpl(Difficulty.HARDCORE)),
            new Achievement(28, "Volodia", "Guess 3 different people from Russia.", "face_28.png", "0", new GuessPeopleFromCountryUpdater(3, "Russia")),
            new Achievement(29, "Hangovery", "Guess 3 different people from Netherlands.", "face_29.png", "0", new GuessPeopleFromCountryUpdater(3, "Netherlands")),
            new Achievement(42, "Fabulo", "Guess 3 different people from USA.", "face_42.png", "0", new GuessPeopleFromCountryUpdater(3, "United States")),
            new Achievement(43, "Huzar", "Guess 3 different people from Poland.", "face_43.png", "0", new GuessPeopleFromCountryUpdater(3, "Poland"))
    };

    public static List<String> checkAchievementsForUpdates(Object update, Context context) {

        List<String> unlockedAchievements = new ArrayList<>();
        for (Achievement achievement : achievements) {
            if (achievement.updateAchievement(update, context)) {
                unlockedAchievements.add(achievement.getName());
                //Games.Achievements.unlock(mGoogleApiClient, "my_achievement_id");
            }
        }

        return unlockedAchievements;
    }

}
