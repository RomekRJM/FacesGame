package rjm.romek.facegame.ui.activity;

import rjm.romek.facegame.R;
import rjm.romek.facegame.model.Difficulty;
import rjm.romek.facegame.model.Question;
import rjm.romek.facegame.service.PhotoService;
import rjm.romek.facegame.service.PhotoServiceImpl;
import rjm.romek.facegame.service.QuestionService;
import rjm.romek.facegame.service.QuestionServiceImpl;
import rjm.romek.facegame.ui.global.Global;
import rjm.romek.source.model.Country;

import android.app.Activity;
import android.graphics.Bitmap;
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
    private Question currentQuestion;
    private List<Button> buttonList;
    private ImageView portrait;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
        mainGameLoop();
	}

    public void mainGameLoop() {
        if(questions == null) {
            init();
        }

        runLogic();
        repaint();
    }

    @Override
	public void onClick(View v) {
	}

    void init() {
        try {
            photoService = new PhotoServiceImpl(getAssets());
            questionService = createQuestionService();
            questions = questionService.generateQuestions(Difficulty.HARD);
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
        return buttonList;
    }

    ImageView createImageView() {
        return (ImageView)findViewById(R.id.imageView1);
    }

    void runLogic() {
        Iterator<Question> iterator = questions.iterator();

        if(iterator.hasNext()) {
            currentQuestion = iterator.next();
        }
    }

    void repaint() {
        portrait.setImageBitmap(photoService.readFromAssets(currentQuestion.getPerson().getFileUuid()));
        Iterator<Country> countryIterator = currentQuestion.getCountries().iterator();

        for(Button button : buttonList) {
            if(countryIterator.hasNext()) {
                Country country = countryIterator.next();
                button.setText(country.getName());
            } else {
                button.setVisibility(View.GONE);
            }
        }
    }

}
