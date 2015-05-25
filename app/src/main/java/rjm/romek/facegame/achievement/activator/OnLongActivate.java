package rjm.romek.facegame.achievement.activator;

public class OnLongActivate implements Activate {
    @Override
    public boolean changeAffectsAchievement(Object change) {
        return change != null && change instanceof Long;
    }
}
