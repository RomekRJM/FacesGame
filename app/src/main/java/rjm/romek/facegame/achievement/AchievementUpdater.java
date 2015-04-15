package rjm.romek.facegame.achievement;

import android.content.Context;

import rjm.romek.facegame.data.AchievementContract;
import rjm.romek.facegame.model.Achievement;

public abstract class AchievementUpdater<C, U> {

    public void updateAchievement(Achievement achievement, C change, Context context) {
        if (!changeAffectsAchievement(change)) return;

        AchievementContract achievementContract = new AchievementContract(context);
        Achievement dbAchievement = achievementContract.findByName(achievement.getName());
        U update = transform(change);
        update(update, dbAchievement);
        achievement.setUnlocked(meetsCondition(dbAchievement));
        achievementContract.updateAchievement(dbAchievement);
        mergeAchievements(dbAchievement, achievement);
    }

    protected abstract boolean changeAffectsAchievement(Object change);

    protected abstract U transform(C change);

    protected abstract void update(U update, Achievement achievement);

    protected abstract boolean meetsCondition(Achievement achievement);

    private void mergeAchievements(Achievement source, Achievement destination) {
        destination.setData(source.getData());
        destination.setUnlocked(source.isUnlocked());
        destination.setLastModified(source.getLastModified());
    }
}
