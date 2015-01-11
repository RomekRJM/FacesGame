package rjm.romek.facegame.activity;

import java.util.Set;

import rjm.romek.source.gen.CountriesDeserializer;
import rjm.romek.source.model.Country;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

public class LoadingScreen extends Activity {
	
	private ProgressBar progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	private class LoadDataTask extends AsyncTask<Void, Integer, Set<Country>> {

		@Override
		protected void onPreExecute() {
			CountriesDeserializer deserializer = new CountriesDeserializer();
		}
		
		@Override
		protected Set<Country> doInBackground(Void... arg0) {
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Set<Country> result) {
			super.onPostExecute(result);
		}
	}
}
