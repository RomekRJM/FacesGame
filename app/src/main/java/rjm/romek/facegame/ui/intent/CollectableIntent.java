package rjm.romek.facegame.ui.intent;

import android.content.Context;
import android.content.Intent;

import rjm.romek.facegame.ui.activity.Collectable;

public class CollectableIntent extends Intent{
    public CollectableIntent(Context context) {
        super(context, Collectable.class);
    }
}
