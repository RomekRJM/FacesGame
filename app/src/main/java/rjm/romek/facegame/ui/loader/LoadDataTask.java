package rjm.romek.facegame.ui.loader;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import rjm.romek.facegame.common.Parameters;
import rjm.romek.source.gen.CountriesDeserializer;

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
        LoadDataTaskResult result;

        try {
            result = new LoadDataTaskResultCorrect(deserializer.deserialize(
                    context.getAssets().open(parameters.getCountryListFile())));
        } catch (IOException e) {
            result = new LoadDataTaskResultException(e);
        }

        return result;
    }

    @Override
    protected void onPostExecute(LoadDataTaskResult result) {
        if (result instanceof LoadDataTaskResultCorrect) {
            fireLoadingComplete((LoadDataTaskResultCorrect) result);
        } else if (result instanceof LoadDataTaskResultException) {
            fireLoadingFailed((LoadDataTaskResultException) result);
        }
    }

    public void setListener(LoadDataTaskListener listener) {
        this.listener = listener;
    }

    private void fireLoadingComplete(LoadDataTaskResultCorrect result) {
        if (listener != null) {
            listener.loadingComplete(result);
        }
    }

    private void fireLoadingFailed(LoadDataTaskResultException exception) {
        if (listener != null) {
            listener.loadingFailed(exception);
        }
    }
}