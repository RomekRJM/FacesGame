package rjm.romek.facegame.ui.activity;

import rjm.romek.facegame.R;
import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.GamePhase;
import rjm.romek.facegame.model.Question;
import rjm.romek.facegame.service.FlagService;
import rjm.romek.facegame.service.FlagServiceImpl;
import rjm.romek.facegame.service.PhotoService;
import rjm.romek.facegame.service.PhotoServiceImpl;
import rjm.romek.facegame.service.QuestionService;
import rjm.romek.facegame.service.QuestionServiceImpl;
import rjm.romek.facegame.ui.global.Global;
import rjm.romek.facegame.ui.intent.EndGameIntent;
import rjm.romek.facegame.ui.timer.TimerThread;
import rjm.romek.facegame.ui.timer.TimerThreadListener;
import rjm.romek.source.model.Country;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Game extends Activity implements OnClickListener, TimerThreadListener {

    private QuestionService questionService;
    private FlagService flagService;
    private PhotoService photoService;
    private Set<Question> questions;
    private Iterator<Question> questionsIterator;
    private Question currentQuestion;
    private List<Button> buttonList;
    private ImageView portrait;
    private SurfaceView timerSurface;
    private int questionIndex;
    private int clickedIndex;
    private GamePhase gamePhase;
    private TimerThread timerThread;

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
                startTimer();
                runLogic();
                paintQuestion();
                break;
        }

    }

    private void startTimer() {
        if(timerThread == null || !timerThread.isAlive()) {
            timerThread = new TimerThread(timerSurface, 5000);
            timerThread.setTimerThreadListener(this);
            timerThread.start();
        }
    }

    private void stopTimer() {
        if(timerThread != null || timerThread.isAlive()) {
            timerThread.setRunning(false);
        }
    }

    @Override
	public void onClick(View v) {
        if(!(v instanceof Button) || (gamePhase == GamePhase.ANSWER_GIVEN)) {
            return;
        }

        clickedIndex = buttonList.indexOf((Button)v);
        currentQuestion.answer(clickedIndex);
        gamePhase = GamePhase.ANSWER_GIVEN;
        stopTimer();
        mainGameLoop();
	}

    void init() {
        try {
            photoService = new PhotoServiceImpl(getAssets());
            flagService = new FlagServiceImpl();
            questionService = createQuestionService();
            questions = questionService.generateQuestions(Difficulty.EASY);
            questionsIterator = questions.iterator();
        } catch (IOException e) {
        }

        portrait = createImageView();
        timerSurface = createSurfaceView();
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

    SurfaceView createSurfaceView() {
        return (SurfaceView)findViewById(R.id.timerSurface);
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
                button.setVisibility(View.VISIBLE);
                Country country = countryIterator.next();
                button.setText(country.getName());
                String flagFileName = flagService.changeNameToFileName(country);
                Bitmap flagBitmap = photoService.readFromAssets(flagFileName);
                Drawable flagDrawable = new BitmapDrawable(null, flagBitmap);
                flagDrawable.setBounds(0, 0, 2*flagBitmap.getWidth(), 2*flagBitmap.getHeight());
                button.setCompoundDrawables(flagDrawable, null, null, null);
            } else {
                button.setVisibility(View.GONE);
            }
        }
    }

    void paintAfterAnswer() {
        final ImageView blinkingView = portrait;
        final int green = Color.parseColor("#11FF00");
        final int red = Color.parseColor("#FF8888");

        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(700);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(3);
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
                      int color = 0;
                      if(currentQuestion.isCorrectlyAnswered()) {
                          color = green;
                      } else {
                          color = red;
                      }
                      paintSymbol();
                      blinkingView.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                      blinkingView.invalidate();
                      paintNextQuestionButton(blinkingView);
                  }

                  @Override
                  public void onAnimationRepeat(Animation animation) {
                  }
              }
        );

        blinkingView.startAnimation(animation);
    }

    public void paintSymbol() {
        Bitmap portraitWithSymbol = photoService.readFromAssets(currentQuestion.getPerson().getName())
                .copy(Bitmap.Config.ARGB_8888, true);
        String symbolPath;

        if(currentQuestion.isCorrectlyAnswered()) {
            symbolPath = "icons/right.png";
        } else {
            symbolPath = "icons/wrong.png";
        }

        int symbolSize = Math.min(portraitWithSymbol.getWidth(), portraitWithSymbol.getHeight());
        Bitmap symbol = Bitmap.createScaledBitmap(photoService.readFromAssets(symbolPath),
                symbolSize, symbolSize, false);
        Canvas canvas = new Canvas(portraitWithSymbol);
        canvas.drawBitmap(symbol, null, new Rect(
                (portraitWithSymbol.getWidth() - symbol.getWidth()) / 2,
                (portraitWithSymbol.getHeight() - symbol.getHeight()) / 2,
                (portraitWithSymbol.getWidth() + symbol.getWidth()) / 2,
                (portraitWithSymbol.getHeight() + symbol.getHeight()) / 2
        ), new Paint());
        portrait.setImageBitmap(portraitWithSymbol);
        portrait.invalidate();
    }

    public void paintNextQuestionButton(final ImageView blinkingView) {
        for(Button button : buttonList) {
            button.setVisibility(View.GONE);
        }

        final Button nextButton = new Button(this);
        nextButton.setText(getString(R.string.main_menu_next));
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                blinkingView.clearColorFilter();
                nextButton.setVisibility(View.GONE);
                goToNextQuestion();
                mainGameLoop();
            }
        });

        TableLayout layout = (TableLayout) findViewById(R.id.buttonTableLayout);
        layout.addView(nextButton);
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

    @Override
    public void timeout() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentQuestion.setTimedOut(true);
                gamePhase = GamePhase.ANSWER_GIVEN;
                mainGameLoop();
            }
        });
    }
}
