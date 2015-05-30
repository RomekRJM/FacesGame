package rjm.romek.facegame.achievement.updater;

import org.apache.commons.lang3.StringUtils;

import rjm.romek.facegame.model.Achievement;
import rjm.romek.facegame.model.Difficulty;

public class DifficultyUpdate implements Update<Difficulty> {

    @Override
    public void update(Difficulty update, Achievement achievement) {
        String achievementData = achievement.getData();

        if(StringUtils.isNumeric(achievementData)) {
            achievementData = Difficulty.values()[Integer.valueOf(achievementData)].name();
        }

        if(achievement.getData()  == null ||
                update.ordinal() > Difficulty.valueOf(
                        achievementData).ordinal() ) {
            achievement.setData(update.name());
        }
    }
}
