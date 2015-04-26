package rjm.romek.facegame.ui.intent;

import android.content.Context;
import android.content.Intent;

import rjm.romek.facegame.ui.activity.StartupLoadingScreen;

public class QuitIntent extends Intent {
    public static final String QUIT = "quit";

    public QuitIntent(Context context) {
        super(context, StartupLoadingScreen.class);
        this.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.putExtra(QUIT, true);
    }
}
