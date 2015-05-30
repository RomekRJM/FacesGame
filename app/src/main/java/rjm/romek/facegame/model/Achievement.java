package rjm.romek.facegame.model;

import android.content.Context;

import java.util.Date;

import rjm.romek.facegame.achievement.AchievementUpdater;

public class Achievement<C> {
    private Integer id;
    private String name;
    private String description;
    private String prize;
    private String data;
    private Boolean unlocked;
    private Date lastModified;

    private AchievementUpdater updater;

    public Achievement() {
    }

    public Achievement(Integer id, String name, String description, String prize,
                       String data, AchievementUpdater updater) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prize = prize;
        this.data = data;
        this.unlocked = false;
        this.lastModified = new Date();
        this.updater = updater;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return updater.updateAchievement(this, change, context);
    }

    public String getAchievementFamily() {
        return updater.getAchievementFamily();
    }
}