package rjm.romek.facegame.achievement;

import rjm.romek.facegame.achievement.activator.OnQuestionServiceActivate;
import rjm.romek.facegame.achievement.condition.IsGreaterOrEqualCondition;
import rjm.romek.facegame.achievement.updater.MaxUpdate;
import rjm.romek.facegame.service.QuestionService;

public abstract class SqlPoweredUpdater extends AchievementUpdater<QuestionService, Long> {

    public SqlPoweredUpdater(long value) {
        super(new IsGreaterOrEqualCondition(value), new MaxUpdate(), new OnQuestionServiceActivate());
    }
}
