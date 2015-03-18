package rjm.romek.facegame.service;

import android.graphics.Color;

public class GreenRedGamma implements ColorService {

    @Override
    public int getColor(int step, int maxSteps) {
        int r = 0;
        int g = 0;
        int half = (maxSteps/2);
        int stepSize = 256 / half;
        if(step <= half) {
            g = 255;
            r = step * stepSize;
        } else {
            g = 256 - ((step-half)*stepSize);
            r = 255;
        }

        return Color.rgb(normalize(r),normalize(g),0);
    }

    @Override
    public int getColor(float completed) {
        int r = 0;
        int g = 0;
        if(completed <= 0.5) {
            g = 255;
            r = Math.round(510 * completed);
        } else {
            g = (int)Math.round(510*(1-completed));
            r = 255;
        }

        return Color.rgb(normalize(r),normalize(g),0);
    }

    public int normalize(int num) {
        if(num > 255) return 255;
        if(num < 0) return 0;

        return num;
    }

}
