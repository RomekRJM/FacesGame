package rjm.romek.facegame.ui.intent;

import android.content.Context;
import android.content.Intent;

import rjm.romek.facegame.ui.activity.GameLoadingScreen;

public class GameLoadingIntent extends Intent {
    public GameLoadingIntent(Context context) {
        super(context, GameLoadingScreen.class);
    }
}
