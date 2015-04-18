package rjm.romek.facegame.achievement;

import android.content.Context;

import rjm.romek.facegame.achievement.condition.Condition;
import rjm.romek.facegame.achievement.updater.Update;
import rjm.romek.facegame.data.AchievementContract;
import rjm.romek.facegame.model.Achievement;

public abstract class AchievementUpdater<C, U> {

    protected Condition condition;
    protected Update update;

    public AchievementUpdater(Condition condition, Update update) {
        this.condition = condition;
        this.update = update;
    }

    public boolean updateAchievement(Achievement achievement, C change, Context context) {
        if (!changeAffectsAchievement(change)) return false;

        AchievementContract achievementContract = new AchievementContract(context);
        Achievement dbAchievement = achievementContract.findByName(achievement.getName());
        boolean wasUnlocked = dbAchievement.isUnlocked();
        U updateValue = transform(change);
        update.update(updateValue, dbAchievement);
        dbAchievement.setUnlocked(condition.meetsCondition(dbAchievement));
        achievementContract.updateAchievement(dbAchievement);
        mergeAchievements(dbAchievement, achievement);

        return !wasUnlocked && achievement.isUnlocked();
    }

    protected abstract boolean changeAffectsAchievement(Object change);

    protected abstract U transform(C change);

    private void mergeAchievements(Achievement source, Achievement destination) {
        destination.setData(source.getData());
        destination.setUnlocked(source.isUnlocked());
        destination.setLastModified(source.getLastModified());
    }

}
