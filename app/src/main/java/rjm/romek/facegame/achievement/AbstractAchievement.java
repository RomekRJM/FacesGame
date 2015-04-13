package rjm.romek.facegame.achievement;

import android.content.Context;

import rjm.romek.facegame.data.AchievementContract;
import rjm.romek.facegame.model.Achievement;

public abstract class AbstractAchievement<U> {
    public void updateAchievement(Achievement achievement, U update, Context context) {
        AchievementContract achievementContract = new AchievementContract(context);
        achievementContract.findByName(achievement.getName());
        update(update, achievement);
        achievementContract.updateAchievement(achievement);
    }

    public abstract void update(U update, Achievement achievement);
}
