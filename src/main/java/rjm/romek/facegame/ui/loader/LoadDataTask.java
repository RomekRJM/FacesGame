package rjm.romek.facegame.ui.loader;

import java.io.IOException;

import rjm.romek.facegame.data.Parameters;
import rjm.romek.source.gen.CountriesDeserializer;
import android.content.Context;
import android.os.AsyncTask;

public class LoadDataTask extends AsyncTask<Void, Void, LoadDataTaskResult> {

	private CountriesDeserializer deserializer;
	private Context context;
	private Parameters parameters;
	private LoadDataTaskListener listener;

	public LoadDataTask(Context context) {
		this.context = context;
		this.deserializer = new CountriesDeserializer();
		this.parameters = new Parameters();
	}
	
	@Override
	protected LoadDataTaskResult doInBackground(Void... arg0) {
		LoadDataTaskResult result = null;
		
		try {
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result = new LoadDataTaskResultCorrect(deserializer.deserialize(
					context.getAssets().open(parameters.getCountryListFile())));
		} catch (IOException e) {
			result = new LoadDataTaskResultException(e);
		}
		
		return result;
	}
	
	@Override
	protected void onPostExecute(LoadDataTaskResult result) {
		if(result instanceof LoadDataTaskResultCorrect) {
			fireLoadingComplete((LoadDataTaskResultCorrect)result);
		} else if (result instanceof LoadDataTaskResultException) {
			fireLoadingFailed((LoadDataTaskResultException)result);
		}
	}
	
	private void fireLoadingComplete(LoadDataTaskResultCorrect result) {
		if(listener != null) {
			listener.loadingComplete(result);
		}
	}
	
	private void fireLoadingFailed(LoadDataTaskResultException exception) {
		if(listener != null) {
			listener.loadingFailed(exception);
		}
	}
}