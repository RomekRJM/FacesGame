package rjm.romek.facegame.ui.intent;

import android.content.Context;
import android.content.Intent;

import rjm.romek.facegame.ui.activity.About;

public class AboutIntent extends Intent {
    public AboutIntent(Context context) {
        super(context, About.class);
    }
}
