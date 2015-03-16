package rjm.romek.facegame.service;

import android.graphics.Color;

public class GreenRedGamma implements ColorService {

    @Override
    public int getColor(int step, int maxSteps) {
        int r = 0;
        int g = 0;
        int stepSize = 256 / (maxSteps/2);
        if(step <= maxSteps/2) {
            g = 255;
            r = step * stepSize;
        } else {
            g = 256 - ((step-2)*stepSize);
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
