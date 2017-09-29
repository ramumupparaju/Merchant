package com.incon.connect.dto.addoffer;

/**
 * Created by PC on 9/28/2017.
 */

public class AddOffer {
    private String name = "";
    private boolean checked = false;

    public AddOffer() {
    }

    public AddOffer(String name) {
        this.name = name;
    }

    public AddOffer(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String toString() {
        return name;
    }

    public void toggleChecked() {
        checked = !checked;
    }
}
