package rjm.romek.facegame.ui.loader;

import android.os.AsyncTask;

import java.util.Set;

import rjm.romek.facegame.model.Question;
import rjm.romek.facegame.service.QuestionService;

public class LoadQuestionTask extends AsyncTask<Set<Question>, Void, Question>{

    private QuestionService questionService;
    private LoadQuestionTaskListener listener;

    public LoadQuestionTask(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    protected Question doInBackground(Set<Question>... params) {
        return questionService.generateQuestion(params[0]);
    }

    @Override
    protected void onPostExecute(Question result) {
        fireLoadingComplete(result);
    }

    public void setListener(LoadQuestionTaskListener listener) {
        this.listener = listener;
    }

    private void fireLoadingComplete(Question result) {
        if (listener != null) {
            listener.loadingComplete(result);
        }
    }
}
