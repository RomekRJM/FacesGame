package rjm.romek.facegame.ui.intent;

import rjm.romek.facegame.ui.activity.Game;
import android.content.Context;
import android.content.Intent;

public class GameIntent extends Intent {
	public GameIntent(Context context) {
		super(context, Game.class);
	}
}
