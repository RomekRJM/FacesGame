package rjm.romek.facegame.ui.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import rjm.romek.facegame.R;
import rjm.romek.facegame.ui.intent.AboutIntent;
import rjm.romek.facegame.ui.intent.CollectableIntent;
import rjm.romek.facegame.ui.intent.GameLoadingIntent;
import rjm.romek.facegame.ui.intent.QuitIntent;
import rjm.romek.facegame.ui.intent.TopScoreIntent;

public class MainMenu extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu);
        findViewById(R.id.new_game_button).setOnClickListener(this);
        findViewById(R.id.records_button).setOnClickListener(this);
        findViewById(R.id.collectable_button).setOnClickListener(this);
        findViewById(R.id.about_the_game_button).setOnClickListener(this);
        findViewById(R.id.quit_button).setOnClickListener(this);

        showVersion();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_game_button:
                startActivity(new GameLoadingIntent(this));
                break;

            case R.id.records_button:
                startActivity(new TopScoreIntent(this));
                break;

            case R.id.collectable_button:
                startActivity(new CollectableIntent(this));
                break;

            case R.id.about_the_game_button:
                startActivity(new AboutIntent(this));
                break;

            case R.id.quit_button:
                startActivity(new QuitIntent(this));
                break;
        }

    }

    private void showVersion() {
        TextView versionText = (TextView) findViewById(R.id.versionText);
        try {
            versionText.setText(getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            versionText.setText("");
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new QuitIntent(this));
    }
}
