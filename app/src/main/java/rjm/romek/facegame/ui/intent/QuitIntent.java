package rjm.romek.facegame.ui.intent;

import rjm.romek.facegame.ui.activity.LoadingScreen;

import android.content.Context;
import android.content.Intent;

public class QuitIntent extends Intent {
	public static final String QUIT = "quit";
	
	public QuitIntent(Context context) {
		super(context, LoadingScreen.class);
		this.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.putExtra(QUIT, true);
	}
}
