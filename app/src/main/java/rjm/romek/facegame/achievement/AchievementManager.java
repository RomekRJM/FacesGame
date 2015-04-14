package rjm.romek.facegame.achievement;

import rjm.romek.facegame.model.Achievement;

public class AchievementManager {
    public static final Achievement[] achievements = new Achievement[]{
            new Achievement("Grucho", "Guess 3 in a row", "face_1.png", new MaxAchievementUpdater(3)),
            new Achievement("Teether", "Guess 5 in a row", "face_2.png", new MaxAchievementUpdater(5)),
            new Achievement("Grumpo", "Guess 7 in a row", "face_3.png", new MaxAchievementUpdater(7)),
            new Achievement("Huggy", "Guess 10 in a row", "face_4.png", new MaxAchievementUpdater(10)),
            new Achievement("Shamo", "Have total 30 correct guesses", "face_5.png", new CounterAchievementUpdater(30)),
            new Achievement("Kissulla", "Have total 100 correct guesses", "face_6.png", new CounterAchievementUpdater(100)),
            new Achievement("Silencio", "Have total 250 correct guesses", "face_7.png", new CounterAchievementUpdater(250)),
            new Achievement("Yawner", "Have total 500 correct guesses", "face_8.png", new CounterAchievementUpdater(500)),
            new Achievement("Blusho", "Have total 1000 correct guesses", "face_9.png", new CounterAchievementUpdater(1000))
    };


}
