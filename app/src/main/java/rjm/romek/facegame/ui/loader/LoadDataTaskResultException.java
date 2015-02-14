package rjm.romek.facegame.ui.loader;

public class LoadDataTaskResultException implements LoadDataTaskResult<Exception> {
	
	private Exception exception;

	public LoadDataTaskResultException(Exception exception) {
		this.exception = exception;
	}
	
	@Override
	public Exception getResult() {
		return exception;
	}

}
