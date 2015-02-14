package rjm.romek.facegame.ui.intent;

import rjm.romek.facegame.ui.activity.MainMenu;
import android.content.Context;
import android.content.Intent;

public class MainMenuIntent extends Intent {
	public MainMenuIntent(Context context) {
		super(context, MainMenu.class);
	}
}
