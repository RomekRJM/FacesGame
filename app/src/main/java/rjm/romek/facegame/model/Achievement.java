package rjm.romek.facegame.model;

import android.content.Context;

import java.util.Date;

import rjm.romek.facegame.achievement.AchievementUpdater;

public class Achievement<C> {
    private String name;
    private String description;
    private String prize;
    private String data;
    private Boolean unlocked;
    private Date lastModified;

    private AchievementUpdater updater;

    public Achievement() {
    }

    public Achievement(String name, String description, String prize,
                       String data, AchievementUpdater updater) {
        this.name = name;
        this.description = description;
        this.prize = prize;
        this.data = data;
        this.unlocked = false;
        this.updater = updater;
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

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public boolean updateAchievement(C change, Context context) {
        boolean wasUnlocked = isUnlocked();
        updater.updateAchievement(this, change, context);
        return !wasUnlocked && isUnlocked();
    }
}