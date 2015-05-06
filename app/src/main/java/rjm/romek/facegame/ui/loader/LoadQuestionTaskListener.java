package rjm.romek.facegame.ui.loader;

import rjm.romek.facegame.model.Question;

public interface LoadQuestionTaskListener {
    void loadingComplete(Question question);
}
