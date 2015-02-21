package rjm.romek.facegame.ui.activity;

import rjm.romek.facegame.R;
import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.GamePhase;
import rjm.romek.facegame.model.Question;
import rjm.romek.facegame.service.PhotoService;
import rjm.romek.facegame.service.PhotoServiceImpl;
import rjm.romek.facegame.service.QuestionService;
import rjm.romek.facegame.service.QuestionServiceImpl;
import rjm.romek.facegame.ui.global.Global;
import rjm.romek.source.model.Country;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Game extends Activity implements OnClickListener {

    private QuestionService questionService;
    private PhotoService photoService;
    private Set<Question> questions;
    private Iterator<Question> questionsIterator;
    private Question currentQuestion;
    private List<Button> buttonList;
    private ImageView portrait;
    private int questionIndex;
    private GamePhase gamePhase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
        mainGameLoop();
	}

    public void mainGameLoop() {
        if(questions == null) {
            init();
            gamePhase = GamePhase.WAITING_FOR_ANSWER;
            questionIndex = -1;
        }

        ++questionIndex;

        if(questionIndex >= questions.size()) {
            System.out.println(questionService.countCorrectAnswered(questions) + "/"
                    + questions.size() + " answered correctly.");
        }

        switch(gamePhase) {
            case ANSWER_GIVEN:
                paintAfterAnswer(); // block for animation duration
                gamePhase = GamePhase.WAITING_FOR_ANSWER;
            case WAITING_FOR_ANSWER:
                runLogic();
                paintNextQuestion();
                break;
        }

    }

    @Override
	public void onClick(View v) {
        if(!(v instanceof Button)) {
            return;
        }

        int clickedIndex = buttonList.indexOf((Button)v);
        currentQuestion.answer(clickedIndex);
        gamePhase = GamePhase.ANSWER_GIVEN;
        mainGameLoop();
	}

    void init() {
        try {
            photoService = new PhotoServiceImpl(getAssets());
            questionService = createQuestionService();
            questions = questionService.generateQuestions(Difficulty.EASY);
            questionsIterator = questions.iterator();
        } catch (IOException e) {
        }

        buttonList = createButtons();
        portrait = createImageView();
    }

    QuestionService createQuestionService() throws IOException {
        return new QuestionServiceImpl(getAssets(),
                Global.countries);
    }

    List<Button> createButtons() {
        List<Button> buttonList = new ArrayList<Button>();
        buttonList.add((Button)findViewById(R.id.button1));
        buttonList.add((Button)findViewById(R.id.button2));
        buttonList.add((Button)findViewById(R.id.button3));
        buttonList.add((Button)findViewById(R.id.button4));

        for(Button b: buttonList) {
            b.setOnClickListener(this);
        }

        return buttonList;
    }

    ImageView createImageView() {
        return (ImageView)findViewById(R.id.imageView1);
    }

    void runLogic() {
        if(questionsIterator.hasNext()) {
            currentQuestion = questionsIterator.next();
        }
    }

    void paintNextQuestion() {
        portrait.setImageBitmap(photoService.readFromAssets(currentQuestion.getPerson().getFileUuid()));
        Iterator<Country> countryIterator = currentQuestion.getCountries().iterator();

        for(Button button : buttonList) {
            if(countryIterator.hasNext()) {
                Country country = countryIterator.next();
                button.setText(country.getName());
                Bitmap flagBitmap = photoService.readFromAssets(country.getFlag());
                Drawable flagDrawable = new BitmapDrawable(null, flagBitmap);
                flagDrawable.setBounds(0, 0, flagBitmap.getWidth(), flagBitmap.getHeight());
                button.setCompoundDrawables(null, flagDrawable, null, null);
            } else {
                button.setVisibility(View.GONE);
            }
        }
    }

    void paintAfterAnswer() {
        if(currentQuestion.isCorrectlyAnswered()) {
            System.out.println("Correct!");
        } else {
            System.out.println("Wrong! Correct was: " + currentQuestion.getCorrectAnswer().getName());
        }
    }
}
