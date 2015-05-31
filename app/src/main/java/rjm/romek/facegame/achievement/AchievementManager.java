package rjm.romek.facegame.achievement;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import rjm.romek.facegame.model.Achievement;
import rjm.romek.facegame.model.Difficulty;

public class AchievementManager {
    public static final Achievement[] achievements = new Achievement[]{
            new Achievement(1, "Grucho", "Guess 4 in a row", "face_1.png", "0", new GuessingSpreeAchievement(4)),
            new Achievement(2, "Teether", "Guess 5 in a row", "face_2.png", "0", new GuessingSpreeAchievement(5)),
            new Achievement(3, "Grumpo", "Guess 7 in a row", "face_3.png", "0", new GuessingSpreeAchievement(7)),
            new Achievement(4, "Huggy", "Guess 10 in a row", "face_4.png", "0", new GuessingSpreeAchievement(10)),
            new Achievement(5, "Toldu", "Guess 15 in a row", "face_37.png", "0", new GuessingSpreeAchievement(15)),
            new Achievement(6, "Shamo", "Have total 30 correct guesses", "face_5.png", "0", new TotalCorrectAnswerAchievement(30)),
            new Achievement(7, "Kissulla", "Have total 100 correct guesses", "face_6.png", "0", new TotalCorrectAnswerAchievement(100)),
            new Achievement(8, "Silencio", "Have total 250 correct guesses", "face_7.png", "0", new TotalCorrectAnswerAchievement(250)),
            new Achievement(9, "Yawner", "Have total 500 correct guesses", "face_8.png", "0", new TotalCorrectAnswerAchievement(500)),
            new Achievement(10, "Blusho", "Have total 1000 correct guesses", "face_9.png", "0", new TotalCorrectAnswerAchievement(1000)),
            new Achievement(11, "Positivo", "Complete 1 game", "face_10.png", "0", new TotalGamesAchievementUpdater(1)),
            new Achievement(12, "Prayen", "Complete 10 games", "face_11.png", "0", new TotalGamesAchievementUpdater(10)),
            new Achievement(13, "Cooka", "Complete 30 games", "face_12.png", "0", new TotalGamesAchievementUpdater(30)),
            new Achievement(14, "Weeper", "Complete 75 games", "face_13.png", "0", new TotalGamesAchievementUpdater(75)),
            new Achievement(15, "Zigzag", "Reach score 150", "face_14.png", "0", new ScoreAchievementUpdater(150)),
            new Achievement(16, "Afraido", "Reach score 400", "face_15.png", "0", new ScoreAchievementUpdater(400)),
            new Achievement(17, "Yuppz", "Reach score 750", "face_16.png", "0", new ScoreAchievementUpdater(750)),
            new Achievement(18, "Unlucko", "Reach score 1000", "face_17.png", "0", new ScoreAchievementUpdater(1000)),
            new Achievement(19, "Watchy", "Reach score 2000", "face_18.png", "0", new ScoreAchievementUpdater(2000)),
            new Achievement(20, "Hertz", "Reach score 3000", "face_38.png", "0", new ScoreAchievementUpdater(3000)),
            new Achievement(21, "Buhow", "Reach score 5000", "face_40.png", "0", new ScoreAchievementUpdater(5000)),
            new Achievement(22, "Dragoni", "Reach score 8000", "face_44.png", "0", new ScoreAchievementUpdater(8000)),
            new Achievement(23, "Confidencio", "Reach score 12000", "face_45.png", "0", new ScoreAchievementUpdater(12000)),
            new Achievement(24, "Onetimo", "Gather 500 points in any number of games", "face_19.png", "0", new TotalScoreAchievementUpdater(500)),
            new Achievement(25, "Ohmydo", "Gather 2000 points in any number of games", "face_20.png", "0", new TotalScoreAchievementUpdater(2000)),
            new Achievement(26, "Karminia", "Gather 5000 points in any number of games", "face_21.png", "0", new TotalScoreAchievementUpdater(5000)),
            new Achievement(27, "Rosario", "Gather 10000 points in any number of games", "face_22.png", "0", new TotalScoreAchievementUpdater(10000)),
            new Achievement(28, "Amphetamo", "Gather 20000 points in any number of games", "face_23.png", "0", new TotalScoreAchievementUpdater(20000)),
            new Achievement(29, "Weeders", "Gather 50000 points in any number of games", "face_46.png", "0", new TotalScoreAchievementUpdater(50000)),
            new Achievement(30, "Roino", "Gather 100000 points in any number of games", "face_47.png", "0", new TotalScoreAchievementUpdater(100000)),
            new Achievement(31, "Coolejandro", "Reach normal difficulty.", "face_24.png", "EASY", new DifficultyAchievementUpdaterImpl(Difficulty.NORMAL)),
            new Achievement(32, "Wack", "Reach hard difficulty.", "face_25.png", "EASY", new DifficultyAchievementUpdaterImpl(Difficulty.HARD)),
            new Achievement(33, "Piranhers", "Reach hardcore difficulty.", "face_26.png", "EASY", new DifficultyAchievementUpdaterImpl(Difficulty.HARDCORE)),
            new Achievement(34, "Volodia", "Guess 3 different people from Russia.", "face_28.png", "0", new GuessPeopleFromCountryUpdater(3, "Russia")),
            new Achievement(35, "Hangovery", "Guess 3 different people from Netherlands.", "face_29.png", "0", new GuessPeopleFromCountryUpdater(3, "Netherlands")),
            new Achievement(36, "Fabulo", "Guess 3 different people from USA.", "face_42.png", "0", new GuessPeopleFromCountryUpdater(3, "United States")),
            new Achievement(37, "Huzar", "Guess 3 different people from Poland.", "face_43.png", "0", new GuessPeopleFromCountryUpdater(3, "Poland")),
            new Achievement(38, "Kubilaj", "Guess 3 different people from Mongolia.", "face_48.png", "0", new GuessPeopleFromCountryUpdater(3, "Mongolia")),
            new Achievement(39, "Gimper", "Play two days in a row.", "face_41.png", "0", new ConsecutiveDaysPlayingAchievementUpdater(2)),
            new Achievement(40, "Septiceye", "Play three days in a row.", "face_68.png", "0", new ConsecutiveDaysPlayingAchievementUpdater(3)),
            new Achievement(41, "Pewds", "Play four days in a row.", "face_69.png", "0", new ConsecutiveDaysPlayingAchievementUpdater(4)),
            new Achievement(42, "Danil", "Play five days in a row.", "face_39.png", "0", new ConsecutiveDaysPlayingAchievementUpdater(5)),
            new Achievement(43, "Laggy", "Guess people from 10 different countries.", "face_27.png", "0", new CountriesCorrectlyGuessedAchievementUpdater(10)),
            new Achievement(44, "Occlusio", "Guess people from 25 different countries.", "face_30.png", "0", new CountriesCorrectlyGuessedAchievementUpdater(25)),
            new Achievement(45, "Nowatchy", "Guess people from 50 different countries.", "face_31.png", "0", new CountriesCorrectlyGuessedAchievementUpdater(50)),
            new Achievement(46, "Omnibi", "Guess people from 100 different countries.", "face_32.png", "0", new CountriesCorrectlyGuessedAchievementUpdater(100)),
            new Achievement(47, "Laughy", "Guess 5 people, that you haven't previously.", "face_33.png", "0", new RightGuessesThatWerePreviouslyWrongAchievementUpdater(5)),
            new Achievement(48, "Pinkulla", "Guess 10 people, that you haven't previously.", "face_34.png", "0", new RightGuessesThatWerePreviouslyWrongAchievementUpdater(10)),
            new Achievement(49, "Vomito", "Guess 20 people, that you haven't previously.", "face_35.png", "0", new RightGuessesThatWerePreviouslyWrongAchievementUpdater(20)),
            new Achievement(50, "Angrezzi", "Guess 40 people, that you haven't previously.", "face_36.png", "0", new RightGuessesThatWerePreviouslyWrongAchievementUpdater(40)),
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
