package rjm.romek.facegame.common;

import android.app.Application;

import java.util.Set;

import rjm.romek.facegame.model.Question;
import rjm.romek.source.model.Country;

public class Global extends Application {
    public static Set<Country> countries;
    public static boolean isRunningTests;
    public static Question firstQuestion;
}
