package rjm.romek.facegame.achievement;

import android.content.Context;

import rjm.romek.facegame.data.AchievementContract;
import rjm.romek.facegame.model.Achievement;

public abstract class AchievementUpdater<C, U> {

    public void updateAchievement(String achievementName, C change, Context context) {
        if (!changeAffectsAchievement(change)) return;

        AchievementContract achievementContract = new AchievementContract(context);
        Achievement achievement = achievementContract.findByName(achievementName);
        U update = transform(change);
        update(update, achievement);
        achievement.setUnlocked(meetsCondition(achievement));
        achievementContract.updateAchievement(achievement);
    }

    public abstract boolean changeAffectsAchievement(Object change);

    public abstract U transform(C change);

    public abstract void update(U update, Achievement achievement);

    public abstract boolean meetsCondition(Achievement achievement);
}
