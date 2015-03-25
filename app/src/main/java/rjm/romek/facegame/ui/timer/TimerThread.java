package rjm.romek.facegame.ui.timer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

import rjm.romek.facegame.service.ColorService;
import rjm.romek.facegame.service.GreenRedGamma;

public class TimerThread extends Thread {

    private final long time;
    private final long INTERVAL = 20;
    private SurfaceView surfaceView;
    private boolean running;
    private long timePassed;
    private Paint paint;
    private ColorService colorService;
    private TimerThreadListener listener;

    public TimerThread(SurfaceView surfaceView, long time) {
        this.time = time;
        this.surfaceView = surfaceView;
        this.paint = new Paint();
        this.colorService = new GreenRedGamma();
        this.timePassed = 0;
    }

    public void setTimerThreadListener(TimerThreadListener listener) {
        this.listener = listener;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        setRunning(true);

        while (running) {
            long startTime = System.currentTimeMillis();

            redrawCanvas();

            try {
                sleep(INTERVAL);
            } catch (InterruptedException e) {
            }

            long endTime = System.currentTimeMillis();
            timePassed += (endTime - startTime);

            if (timePassed >= time) {
                timePassed = time;
                redrawCanvas();
                setRunning(false);
                fireTimeoutEvent();
            }
        }
    }

    public long getTimePassed() {
        return timePassed;
    }

    private void fireTimeoutEvent() {
        if (listener != null) {
            listener.timeout();
        }
    }

    public void redrawCanvas() {
        Canvas canvas = surfaceView.getHolder().lockCanvas();

        if (canvas != null) {
            synchronized (surfaceView.getHolder()) {
                drawTime(canvas);
            }
            surfaceView.getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void drawTime(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        float completed = (float) timePassed / time;
        int right = Math.round(completed * width);

        paint.setColor(colorService.getColor(completed));
        canvas.drawRect(0, 0, width, height, paint);

        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, right, height, paint);
    }
}
