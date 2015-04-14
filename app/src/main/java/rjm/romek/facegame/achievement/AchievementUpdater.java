package rjm.romek.facegame.achievement;

import android.content.Context;

import rjm.romek.facegame.data.AchievementContract;
import rjm.romek.facegame.model.Achievement;

public abstract class AchievementUpdater<U> {

    public void updateAchievement(String achievementName, U update, Context context) {
        AchievementContract achievementContract = new AchievementContract(context);
        Achievement achievement = achievementContract.findByName(achievementName);
        update(update, achievement);
        achievement.setUnlocked(meetsCondition(achievement));
        achievementContract.updateAchievement(achievement);
    }

    public abstract void update(U update, Achievement achievement);
    public abstract boolean meetsCondition(Achievement achievement);
}
