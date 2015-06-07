package rjm.romek.facegame.common;

import android.app.Activity;
import android.widget.Button;

public interface GooglePlayable {
    Activity getActivity();
    Button getShareButton();
    boolean needsPublishing();
    boolean isEmpty();
}
