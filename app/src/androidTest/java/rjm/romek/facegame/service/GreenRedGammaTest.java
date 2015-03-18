package rjm.romek.facegame.service;

import android.content.res.AssetManager;
import android.graphics.Color;

import junit.framework.TestCase;

public class GreenRedGammaTest extends TestCase {
    private GreenRedGamma greenRedGamma;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        greenRedGamma = new GreenRedGamma();
    }

    public void testGenerations() {
        assertEquals(Color.parseColor("#00FF00"), greenRedGamma.getColor(0, 5));
        assertEquals(Color.parseColor("#80FF00"), greenRedGamma.getColor(1, 5));
        assertEquals(Color.parseColor("#FFFF00"), greenRedGamma.getColor(2, 5));
        assertEquals(Color.parseColor("#FF8000"), greenRedGamma.getColor(3, 5));
        assertEquals(Color.parseColor("#FF0000"), greenRedGamma.getColor(4, 5));
    }

    public void testGenerationsFloat() {
        assertEquals(Color.parseColor("#00FF00"), greenRedGamma.getColor(0));
        assertEquals(Color.parseColor("#80FF00"), greenRedGamma.getColor(1f/4));
        assertEquals(Color.parseColor("#FFFF00"), greenRedGamma.getColor(2f/4));
        assertEquals(Color.parseColor("#FF8000"), greenRedGamma.getColor(3f/4));
        assertEquals(Color.parseColor("#FF0000"), greenRedGamma.getColor(1));
    }
}