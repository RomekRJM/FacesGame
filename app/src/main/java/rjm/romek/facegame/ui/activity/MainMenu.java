package rjm.romek.facegame.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import rjm.romek.facegame.R;
import rjm.romek.facegame.ui.intent.GameIntent;
import rjm.romek.facegame.ui.intent.QuitIntent;

public class MainMenu extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu);
        findViewById(R.id.new_game_button).setOnClickListener(this);
        findViewById(R.id.records_button).setOnClickListener(this);
        findViewById(R.id.about_the_game_button).setOnClickListener(this);
        findViewById(R.id.quit_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_game_button:
                startActivity(new GameIntent(this));
                break;

            case R.id.records_button:
                break;

            case R.id.about_the_game_button:
                break;

            case R.id.quit_button:
                startActivity(new QuitIntent(this));
                break;
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new QuitIntent(this));
    }
}
