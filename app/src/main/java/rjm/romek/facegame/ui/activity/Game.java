package rjm.romek.facegame.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import rjm.romek.facegame.R;
import rjm.romek.facegame.achievement.AchievementManager;
import rjm.romek.facegame.common.Global;
import rjm.romek.facegame.model.GamePhase;
import rjm.romek.facegame.model.Question;
import rjm.romek.facegame.service.FlagService;
import rjm.romek.facegame.service.FlagServiceImpl;
import rjm.romek.facegame.service.PhotoService;
import rjm.romek.facegame.service.PhotoServiceImpl;
import rjm.romek.facegame.service.QuestionService;
import rjm.romek.facegame.service.QuestionServiceImpl;
import rjm.romek.facegame.ui.intent.EndGameIntent;
import rjm.romek.facegame.ui.intent.MainMenuIntent;
import rjm.romek.facegame.ui.listener.SurfaceLayoutChangeListener;
import rjm.romek.facegame.ui.loader.LoadQuestionTask;
import rjm.romek.facegame.ui.loader.LoadQuestionTaskListener;
import rjm.romek.facegame.ui.manager.DifficultyManager;
import rjm.romek.facegame.ui.manager.LivesManager;
import rjm.romek.facegame.ui.manager.ScoreManager;
import rjm.romek.facegame.ui.timer.TimerThread;
import rjm.romek.facegame.ui.timer.TimerThreadListener;
import rjm.romek.facegame.ui.views.SelfAwareSurfaceView;
import rjm.romek.source.model.Country;

public class Game extends Activity implements OnClickListener,
        TimerThreadListener, SurfaceLayoutChangeListener, LoadQuestionTaskListener {

    private QuestionService questionService;
    private FlagService flagService;
    private PhotoService photoService;
    private Set<Question> questions;
    private Question currentQuestion;
    private Question nextQuestion;
    private List<Button> buttonList;
    private ImageView portrait;
    private SelfAwareSurfaceView timerSurface;
    private int clickedIndex;
    private GamePhase gamePhase;
    private TimerThread timerThread;
    private ScoreManager scoreManager;
    private LivesManager livesManager;
    private DifficultyManager difficultyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        mainGameLoop();
    }

    public void mainGameLoop() {
        if (questions == null) {
            init();
            gamePhase = GamePhase.WAITING_FOR_ANSWER;
        }

        switch (gamePhase) {
            case ANSWER_GIVEN:
                generateNextQuestion();
                paintAfterAnswer();
                break;
            case WAITING_FOR_ANSWER:
                runLogic();
                startTimer();
                paintQuestion();
                break;
        }

    }

    private void generateNextQuestion() {
        LoadQuestionTask loadQuestionTask = new LoadQuestionTask(questionService);
        loadQuestionTask.setListener(this);
        loadQuestionTask.execute(questions);
    }

    private void startTimer() {
        if (timerThread == null || !timerThread.isAlive()) {
            timerThread = new TimerThread(timerSurface, currentQuestion.getDifficulty().getTime());
            timerThread.setTimerThreadListener(this);
            timerThread.start();
        }
    }

    private void redrawTimer() {
        if (timerThread != null && !timerThread.isAlive()) {
            timerThread.redrawCanvas();
        }
    }

    private void stopTimer() {
        if (timerThread != null && timerThread.isAlive()) {
            timerThread.setRunning(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (!(v instanceof Button) || (gamePhase == GamePhase.ANSWER_GIVEN)) {
            return;
        }

        stopTimer();
        clickedIndex = buttonList.indexOf(v);
        currentQuestion.answer(clickedIndex, timerThread.getTimePassed());
        gamePhase = GamePhase.ANSWER_GIVEN;
        mainGameLoop();
    }

    @Override
    public void layoutChanged() {
        redrawTimer();
        difficultyManager.update(currentQuestion.getDifficulty());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    @Override
    public void loadingComplete(Question question) {
        setNextQuestion(question);
    }

    @Override
    public void onBackPressed() {
        startActivity(new MainMenuIntent(this));
    }

    private synchronized Question getNextQuestion() {
        return nextQuestion;
    }

    private synchronized void setNextQuestion(Question question) {
        nextQuestion = question;
    }

    void init() {
        try {
            photoService = new PhotoServiceImpl(getAssets());
            flagService = new FlagServiceImpl();
            questionService = createQuestionService();
            questions = new LinkedHashSet<>();
            setNextQuestion(Global.firstQuestion);
        } catch (IOException e) {
        }

        portrait = createImageView();
        timerSurface = createSurfaceView();
        scoreManager = new ScoreManager(createScoreTextView(), getBaseContext());
        livesManager = new LivesManager(this);
        difficultyManager = new DifficultyManager(createTopGameLayout(), createTopBackground());
    }

    QuestionService createQuestionService() throws IOException {
        return new QuestionServiceImpl(getBaseContext(), Global.countries);
    }

    List<Button> createButtons() {
        List<Button> buttonList = new ArrayList<>();
        buttonList.add((Button) findViewById(R.id.button1));
        buttonList.add((Button) findViewById(R.id.button2));
        buttonList.add((Button) findViewById(R.id.button3));
        buttonList.add((Button) findViewById(R.id.button4));

        for (Button b : buttonList) {
            b.setOnClickListener(this);
        }

        return buttonList;
    }

    SelfAwareSurfaceView createSurfaceView() {
        SelfAwareSurfaceView surfaceView = (SelfAwareSurfaceView) findViewById(R.id.timerSurface);
        surfaceView.setSurfaceSizeChangeListener(this);
        return surfaceView;
    }

    ImageView createImageView() {
        return (ImageView) findViewById(R.id.imageViewPortrait);
    }

    View createTopGameLayout() {
        return findViewById(R.id.topGameLayout);
    }

    View createTopBackground() {
        return findViewById(R.id.topBackground);
    }

    TextView createScoreTextView() {
        return (TextView) findViewById(R.id.scoreTextView);
    }

    void runLogic() {
        currentQuestion = getNextQuestion();
        questions.add(currentQuestion);
    }

    void paintQuestion() {
        portrait.setImageBitmap(photoService.readFromAssets(currentQuestion.getPerson().getName()));
        Iterator<Country> countryIterator = currentQuestion.getCountries().iterator();

        buttonList = createButtons();
        for (Button button : buttonList) {
            if (countryIterator.hasNext()) {
                button.setVisibility(View.VISIBLE);
                button.getBackground().clearColorFilter();
                Country country = countryIterator.next();
                button.setText(country.getName());
                String flagFileName = flagService.changeNameToFileName(country);
                Bitmap flagBitmap = photoService.readFromAssets(flagFileName);
                Drawable flagDrawable = new BitmapDrawable(null, flagBitmap);
                flagDrawable.setBounds(0, 0, 2 * flagBitmap.getWidth(), 2 * flagBitmap.getHeight());
                button.setCompoundDrawables(flagDrawable, null, null, null);
            } else {
                button.setVisibility(View.GONE);
            }
        }

        difficultyManager.update(currentQuestion.getDifficulty());
    }

    void paintAfterAnswer() {
        final ImageView blinkingView = portrait;
        final int green = getResources().getColor(R.color.green_correct);
        final int red = getResources().getColor(R.color.red_wrong);

        Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(700);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(2);
        animation.setRepeatMode(Animation.REVERSE);

        if (!currentQuestion.isTimedOut()) {
            buttonList.get(clickedIndex).getBackground()
                    .setColorFilter(new LightingColorFilter(0xFFFFFFFF, getResources().getColor(R.color.blue_marked)));
        }

        animation.setAnimationListener(
                new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        int color;
                        if (currentQuestion.isCorrectlyAnswered()) {
                            color = green;
                        } else {
                            color = red;
                        }
                        paintSymbol();
                        blinkingView.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                        blinkingView.invalidate();
                        paintScore();
                        paintLives();

                        if (livesManager.isGameOver()) {
                            goToEndGame();
                        } else {
                            paintNextQuestionButton(blinkingView);
                        }
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

        if (currentQuestion.isCorrectlyAnswered()) {
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
        for (Button button : buttonList) {
            button.setVisibility(View.GONE);
        }

        final Button nextButton = new Button(this);
        nextButton.setText(getString(R.string.main_menu_next));
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                blinkingView.clearColorFilter();
                nextButton.setVisibility(View.GONE);
                gamePhase = GamePhase.WAITING_FOR_ANSWER;
                mainGameLoop();
            }
        });

        TableLayout layout = (TableLayout) findViewById(R.id.buttonTableLayout);
        layout.addView(nextButton);
    }

    public void paintScore() {
        scoreManager.updateScore(currentQuestion);
    }

    private void paintLives() {
        livesManager.update(currentQuestion);
    }

    public void goToEndGame() {
        final Activity _this = this;
        final Long totalScore = scoreManager.getScoreService().getTotalScore();

        questionService.saveQuestions(questions);

        final List<String> unlockedAchievementsNames =
                AchievementManager.checkAchievementsForUpdates(questions, getBaseContext());
        unlockedAchievementsNames.addAll(
                AchievementManager.checkAchievementsForUpdates(totalScore, getBaseContext()));
        unlockedAchievementsNames.addAll(
                AchievementManager.checkAchievementsForUpdates(questionService, getBaseContext()));

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(new EndGameIntent(_this,
                        questionService.countCorrectAnswered(questions),
                        totalScore,
                        unlockedAchievementsNames.toArray(
                                new String[unlockedAchievementsNames.size()])));
            }
        }, 700);

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

    @Override
    protected void onResume() {
        super.onResume();
        if(timerThread != null && timerThread.isAlive()) {
            timerThread.unpause();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(timerThread != null && timerThread.isAlive()) {
            timerThread.pause();
        }
    }

}
