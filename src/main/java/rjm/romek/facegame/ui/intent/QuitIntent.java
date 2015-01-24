package rjm.romek.facegame.ui.intent;

import rjm.romek.facegame.ui.activity.MainMenu;
import android.content.Context;
import android.content.Intent;

public class QuitIntent extends Intent {
	public QuitIntent(Context context) {
		super(context, MainMenu.class);
	}
}
