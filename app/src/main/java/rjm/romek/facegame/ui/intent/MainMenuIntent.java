package rjm.romek.facegame.ui.intent;

import android.content.Context;
import android.content.Intent;

import rjm.romek.facegame.ui.activity.MainMenu;

public class MainMenuIntent extends Intent {
    public MainMenuIntent(Context context) {
        super(context, MainMenu.class);
    }
}
