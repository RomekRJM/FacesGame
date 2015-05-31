package rjm.romek.facegame.achievement;

import android.content.Context;

import java.util.Date;

import rjm.romek.facegame.achievement.activator.Activate;
import rjm.romek.facegame.achievement.condition.Condition;
import rjm.romek.facegame.achievement.updater.Update;
import rjm.romek.facegame.data.AchievementContract;
import rjm.romek.facegame.model.Achievement;

public abstract class AchievementUpdater<C, U> {

    protected Condition condition;
    protected Update update;
    protected Activate activate;

    public AchievementUpdater(Condition condition, Update update, Activate activate) {
        this.condition = condition;
        this.update = update;
        this.activate = activate;
    }

    public boolean updateAchievement(Achievement achievement, C change, Context context) {
        if (!activate.changeAffectsAchievement(change)) return false;

        AchievementContract achievementContract = new AchievementContract(context);
        Achievement dbAchievement = achievementContract.findByName(achievement.getName());

        boolean wasUnlocked = dbAchievement.isUnlocked();

        U updateValue = transform(change);
        update.update(updateValue, dbAchievement);

        dbAchievement.setUnlocked(condition.meetsCondition(dbAchievement));

        boolean justUnlocked = !wasUnlocked && dbAchievement.isUnlocked();
        if(justUnlocked) {
            dbAchievement.setLastModified(new Date());
        }

        achievementContract.updateAchievement(dbAchievement);
        mergeAchievements(dbAchievement, achievement);

        return justUnlocked;
    }

    protected abstract U transform(C change);

    private void mergeAchievements(Achievement source, Achievement destination) {
        destination.setData(source.getData());
        destination.setUnlocked(source.isUnlocked());
        destination.setLastModified(source.getLastModified());
    }
}
