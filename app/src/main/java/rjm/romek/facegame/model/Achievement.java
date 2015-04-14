package rjm.romek.facegame.model;

import android.content.Context;

import rjm.romek.facegame.achievement.AchievementUpdater;

public class Achievement {
    private String name;
    private String description;
    private String prize;
    private String data;
    private Boolean unlocked;

    private AchievementUpdater updater;

    public Achievement() {
    }

    public Achievement(String name, String description, String prize, AchievementUpdater updater) {
        this.name = name;
        this.description = description;
        this.prize = prize;
        this.data = null;
        this.unlocked = false;
        this.updater = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setUnlocked(Boolean unlocked) {
        this.unlocked = unlocked;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public String getPrize() {
        return prize;
    }

    public String getDescription() {
        return description;
    }

    public boolean updateAchievement(Object update, Context context) {
        boolean wasUnlocked = isUnlocked();
        updater.updateAchievement(name, update, context);
        return !wasUnlocked && isUnlocked();
    }
}