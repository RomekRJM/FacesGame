package rjm.romek.facegame.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ProgressBar;

import rjm.romek.facegame.R;
import rjm.romek.facegame.ui.global.Global;
import rjm.romek.facegame.ui.intent.MainMenuIntent;
import rjm.romek.facegame.ui.intent.QuitIntent;
import rjm.romek.facegame.ui.loader.LoadDataTask;
import rjm.romek.facegame.ui.loader.LoadDataTaskListener;
import rjm.romek.facegame.ui.loader.LoadDataTaskResultCorrect;
import rjm.romek.facegame.ui.loader.LoadDataTaskResultException;

public class LoadingScreen extends Activity implements LoadDataTaskListener {
    private LoadingScreen _this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isCloseAppIntent()) {
            finish();
            return;
        }

        _this = this;
        setContentView(R.layout.loading_screen);

        LoadDataTask loadDataTask = new LoadDataTask(_this);
        loadDataTask.setListener(_this);
        loadDataTask.execute();
    }

    @Override
    public void loadingComplete(LoadDataTaskResultCorrect result) {
        Global.countries = result.getResult();
        startActivity(new MainMenuIntent(_this));
    }

    @Override
    public void loadingFailed(LoadDataTaskResultException exception) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(R.string.loading_screen_dialog_failed_exit,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new QuitIntent(_this));
                    }
                });

    }

    public boolean isCloseAppIntent() {
        return getIntent().getBooleanExtra(QuitIntent.QUIT, false);
    }
}
