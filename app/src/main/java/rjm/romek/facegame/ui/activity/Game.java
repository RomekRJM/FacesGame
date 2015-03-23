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
import rjm.romek.facegame.ui.listener.SurfaceLayoutChangeListener;
import rjm.romek.facegame.ui.manager.ScoreManager;
import rjm.romek.facegame.ui.timer.TimerThread;
import rjm.romek.facegame.ui.timer.TimerThreadListener;
import rjm.romek.facegame.ui.views.SelfAwareSurfaceView;
import rjm.romek.source.model.Country;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Game extends Activity implements OnClickListener, TimerThreadListener, SurfaceLayoutChangeListener {

    private QuestionService questionService;
    private FlagService flagService;
    private PhotoService photoService;
    private Set<Question> questions;
    private Iterator<Question> questionsIterator;
    private Question currentQuestion;
    private List<Button> buttonList;
    private ImageView portrait;
    private SelfAwareSurfaceView timerSurface;
    private int questionIndex;
    private int clickedIndex;
    private GamePhase gamePhase;
    private TimerThread timerThread;
    private ScoreManager scoreManager;

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
                startTimer();
                paintQuestion();
                break;
        }

    }

    private void startTimer() {
        if(timerThread == null || !timerThread.isAlive()) {
            timerThread = new TimerThread(timerSurface, currentQuestion.getDifficulty().getTime());
            timerThread.setTimerThreadListener(this);
            timerThread.start();
        }
    }

    private void redrawTimer() {
        if(timerThread != null || !timerThread.isAlive()) {
            timerThread.redrawCanvas();
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

        stopTimer();
        clickedIndex = buttonList.indexOf((Button)v);
        currentQuestion.answer(clickedIndex, timerThread.getTimePassed());
        gamePhase = GamePhase.ANSWER_GIVEN;
        mainGameLoop();
	}

    @Override
    public void layoutChanged() {
        redrawTimer();
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
        scoreManager = new ScoreManager(createScoreTextView());
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

    SelfAwareSurfaceView createSurfaceView() {
        SelfAwareSurfaceView surfaceView = (SelfAwareSurfaceView)findViewById(R.id.timerSurface);
        surfaceView.setSurfaceSizeChangeListener(this);
        return surfaceView;
    }

    ImageView createImageView() {
        return (ImageView)findViewById(R.id.imageView1);
    }

    TextView createScoreTextView() {
        return (TextView)findViewById(R.id.scoreTextView);
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
                button.getBackground().clearColorFilter();
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
        final int green = Color.parseColor("#11FF11");
        final int red = Color.parseColor("#FF1111");

        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(700);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(2);
        animation.setRepeatMode(Animation.REVERSE);

        buttonList.get(clickedIndex).getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0x6495ED));

        animation.setAnimationListener(
              new Animation.AnimationListener() {

                  @Override
                  public void onAnimationStart(Animation animation) {
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
                      paintScore();
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

    public void paintScore() {
        scoreManager.updateScore(currentQuestion);
    }

    public void goToNextQuestion() {
        ++questionIndex;

        if(questionIndex >= questions.size()) {
            startActivity(new EndGameIntent(this,
                    questionService.countCorrectAnswered(questions),
                    scoreManager.getScoreService().getTotalScore()));
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
