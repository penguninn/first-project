package com.daipc.modelUI;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Model_Menu {

    private String icon;
    private String name;
    private MenuType type;

    // Default constructor
    public Model_Menu() {
    }

    // Parameterized constructor
    public Model_Menu(String icon, String name, MenuType type) {
        this.icon = icon;
        this.name = name;
        this.type = type;
    }

    // Getter for icon
    public String getIcon() {
        return icon;
    }

    // Setter for icon
    public void setIcon(String icon) {
        this.icon = icon;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for type
    public MenuType getType() {
        return type;
    }

    // Setter for type
    public void setType(MenuType type) {
        this.type = type;
    }

    // Converts the icon path to an Icon object
    public Icon toIcon() {
        try {
            return new ImageIcon(getClass().getResource("/com/daipc/icon/" + icon + ".png"));
        } catch (Exception e) {
            System.err.println("Icon not found: " + e.getMessage());
            return null; // or you can return a default icon
        }
    }

    // Enum for menu type
    public enum MenuType {
        TITLE, MENU, EMPTY
    }

}
