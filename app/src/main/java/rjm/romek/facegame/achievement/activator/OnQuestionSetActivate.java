package rjm.romek.facegame.achievement.activator;

import java.util.Set;

public class OnQuestionSetActivate implements Activate {
    @Override
    public boolean changeAffectsAchievement(Object change) {
        return change != null && Set.class.isAssignableFrom(change.getClass());
    }
}
