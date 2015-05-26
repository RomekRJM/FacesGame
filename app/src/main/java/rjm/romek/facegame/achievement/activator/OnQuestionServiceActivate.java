package rjm.romek.facegame.achievement.activator;

import rjm.romek.facegame.service.QuestionService;

public class OnQuestionServiceActivate implements Activate {

    @Override
    public boolean changeAffectsAchievement(Object change) {
        return change != null && QuestionService.class.isAssignableFrom(change.getClass());
    }
}
