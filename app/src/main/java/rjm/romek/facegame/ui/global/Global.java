package rjm.romek.facegame.ui.global;

import android.app.Application;

import java.util.Set;

import rjm.romek.source.model.Country;

public class Global extends Application {
    public static Set<Country> countries;
    public static boolean isRunningTests;
}
