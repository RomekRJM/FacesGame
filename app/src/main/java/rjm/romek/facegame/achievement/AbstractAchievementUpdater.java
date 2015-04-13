package rjm.romek.facegame.achievement;

import android.content.Context;

import rjm.romek.facegame.data.AchievementContract;
import rjm.romek.facegame.model.Achievement;

public abstract class AbstractAchievementUpdater<U> {
    protected final String achievementName;

    protected AbstractAchievementUpdater(String achievementName) {
        this.achievementName = achievementName;
    }

    public void updateAchievement(U update, Context context) {
        AchievementContract achievementContract = new AchievementContract(context);
        Achievement achievement = achievementContract.findByName(achievementName);
        update(update, achievement);
        achievement.setUnlocked(meetsCondition(achievement));
        achievementContract.updateAchievement(achievement);
    }

    public abstract void update(U update, Achievement achievement);
    public abstract boolean meetsCondition(Achievement achievement);
}
