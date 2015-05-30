package rjm.romek.facegame.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import java.io.IOException;
import java.util.LinkedHashSet;

import rjm.romek.facegame.R;
import rjm.romek.facegame.model.Question;
import rjm.romek.facegame.service.QuestionService;
import rjm.romek.facegame.service.QuestionServiceImpl;
import rjm.romek.facegame.ui.global.Global;
import rjm.romek.facegame.ui.intent.GameIntent;
import rjm.romek.facegame.ui.loader.LoadQuestionTask;
import rjm.romek.facegame.ui.loader.LoadQuestionTaskListener;

public class GameLoadingScreen extends Activity implements LoadQuestionTaskListener {
    private QuestionService questionService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            questionService = new QuestionServiceImpl(getBaseContext(), Global.countries);
        } catch (IOException e) {
        }

        setContentView(R.layout.loading_screen);

        LoadQuestionTask loadQuestionTask = new LoadQuestionTask(questionService);
        loadQuestionTask.setListener(this);
        loadQuestionTask.execute(new LinkedHashSet<Question>());
    }

    @Override
    public void loadingComplete(Question result) {
        Global.firstQuestion = result;
        startActivity(new GameIntent(this));
    }
}
