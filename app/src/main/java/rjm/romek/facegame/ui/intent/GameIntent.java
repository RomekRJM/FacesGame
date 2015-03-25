package rjm.romek.facegame.ui.intent;

import android.content.Context;
import android.content.Intent;

import rjm.romek.facegame.ui.activity.Game;

public class GameIntent extends Intent {
    public GameIntent(Context context) {
        super(context, Game.class);
    }
}
