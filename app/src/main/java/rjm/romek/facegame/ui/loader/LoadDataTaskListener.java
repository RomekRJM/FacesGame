package rjm.romek.facegame.ui.loader;

public interface LoadDataTaskListener {

    void loadingComplete(LoadDataTaskResultCorrect result);
    void loadingFailed(LoadDataTaskResultException exception);

}
