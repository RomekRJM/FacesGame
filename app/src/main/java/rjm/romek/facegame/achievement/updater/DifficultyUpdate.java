package rjm.romek.facegame.achievement.updater;

import rjm.romek.facegame.model.Achievement;
import rjm.romek.facegame.model.Difficulty;

public class DifficultyUpdate implements Update<Difficulty> {

    @Override
    public void update(Difficulty update, Achievement achievement) {
        if(achievement.getData()  == null ||
                update.ordinal() > Difficulty.valueOf(
                        achievement.getData()).ordinal() ) {
            achievement.setData(update.name());
        }
    }
}
