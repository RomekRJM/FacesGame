package rjm.romek.facegame.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

import rjm.romek.facegame.ui.listener.SurfaceLayoutChangeListener;

public class SelfAwareSurfaceView extends SurfaceView {

    private SurfaceLayoutChangeListener listener;

    public SelfAwareSurfaceView(Context context) {
        super(context);
    }

    public SelfAwareSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfAwareSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setSurfaceSizeChangeListener(SurfaceLayoutChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed) {
            fireLayoutChangedEvent();
        }
    }

    private void fireLayoutChangedEvent() {
        if(listener != null) {
            listener.layoutChanged();
        }
    }

}