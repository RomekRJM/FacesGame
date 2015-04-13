package rjm.romek.facegame.model;

public class Achievement {
    private String name;
    private String description;
    private String prize;
    private String data;
    private Boolean isUnlocked;

    public Achievement() {

    }

    public Achievement(String name, String description, String prize) {
        this.name = name;
        this.description = description;
        this.prize = prize;
        this.data = null;
        this.isUnlocked = false;
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

    public void setIsUnlocked(Boolean isUnlocked) {
        this.isUnlocked = isUnlocked;
    }

    public boolean isUnlocked() {
        return isUnlocked;
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
}