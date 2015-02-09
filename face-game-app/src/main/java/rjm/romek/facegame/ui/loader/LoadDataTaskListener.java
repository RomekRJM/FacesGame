package rjm.romek.facegame.ui.loader;

public interface LoadDataTaskListener {
	
	public void loadingComplete(LoadDataTaskResultCorrect result);
	public void loadingFailed(LoadDataTaskResultException exception);
	
}
