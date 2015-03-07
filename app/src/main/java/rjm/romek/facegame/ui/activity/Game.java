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
import rjm.romek.facegame.ui.intent.EndGameIntent;
import rjm.romek.source.model.Country;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
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
    private int clickedIndex;
    private GamePhase gamePhase;
    private Drawable buttonBackground;

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
            questionIndex = 0;
        }

        switch(gamePhase) {
            case ANSWER_GIVEN:
                paintAfterAnswer();
                break;
            case WAITING_FOR_ANSWER:
                runLogic();
                paintQuestion();
                break;
        }

    }

    @Override
	public void onClick(View v) {
        if(!(v instanceof Button)) {
            return;
        }

        clickedIndex = buttonList.indexOf((Button)v);
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

    void paintQuestion() {
        portrait.setImageBitmap(photoService.readFromAssets(currentQuestion.getPerson().getName()));
        Iterator<Country> countryIterator = currentQuestion.getCountries().iterator();

        buttonList = createButtons();
        for(Button button : buttonList) {
            if(countryIterator.hasNext()) {
                Country country = countryIterator.next();
                button.setText(country.getName());
                Bitmap flagBitmap = photoService.readFromAssets(country.getFlag());
                Drawable flagDrawable = new BitmapDrawable(null, flagBitmap);
                flagDrawable.setBounds(0, 0, 2*flagBitmap.getWidth(), 2*flagBitmap.getHeight());
                button.setCompoundDrawables(flagDrawable, null, null, null);
            } else {
                button.setVisibility(View.GONE);
            }
        }
    }

    void paintAfterAnswer() {
        final Button clicked = buttonList.get(clickedIndex);
        final int green = Color.parseColor("#11FF00");
        final int red = Color.parseColor("#FF0000");

        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(8);
        animation.setRepeatMode(Animation.REVERSE);

        animation.setAnimationListener(
              new Animation.AnimationListener() {
                  private long started;

                  @Override
                  public void onAnimationStart(Animation animation) {
                      started = System.currentTimeMillis();
                  }

                  @Override
                  public void onAnimationEnd(Animation animation) {
                      clicked.getBackground().clearColorFilter();
                      goToNextQuestion();
                      mainGameLoop();
                  }

                  @Override
                  public void onAnimationRepeat(Animation animation) {
                      if(System.currentTimeMillis() - started < 2000) return;

                      int color = 0;
                      if(currentQuestion.isCorrectlyAnswered()) {
                          color = green;
                      } else {
                          color = red;
                      }
                      clicked.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                      clicked.invalidate();
                  }
              }
        );

        clicked.startAnimation(animation);
    }

    public void goToNextQuestion() {
        ++questionIndex;

        if(questionIndex >= questions.size()) {
            startActivity(new EndGameIntent(this,
                    questionService.countCorrectAnswered(questions)));
        } else {
            gamePhase = GamePhase.WAITING_FOR_ANSWER;
        }
    }

}
