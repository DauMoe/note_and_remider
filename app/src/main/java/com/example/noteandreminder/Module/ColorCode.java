package com.example.noteandreminder.Module;

import java.io.Serializable;

public class ColorCode implements Serializable {
    private int ID;
    private String nameColor, backgroundColorCode, contentColorCode;

    public ColorCode(int ID, String nameColor, String backgroundColorCode, String contentColorCode) {
        this.ID = ID;
        this.nameColor = nameColor;
        this.backgroundColorCode = backgroundColorCode;
        this.contentColorCode = contentColorCode;
    }

    public ColorCode() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNameColor() {
        return nameColor;
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    public String getBackgroundColorCode() {
        return backgroundColorCode;
    }

    public void setBackgroundColorCode(String backgroundColorCode) {
        this.backgroundColorCode = backgroundColorCode;
    }

    public String getContentColorCode() {
        return contentColorCode;
    }

    public void setContentColorCode(String contentColorCode) {
        this.contentColorCode = contentColorCode;
    }
}
