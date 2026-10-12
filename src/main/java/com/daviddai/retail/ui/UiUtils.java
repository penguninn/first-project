package com.daviddai.retail.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;

public final class UiUtils {

    private static final String ICON_PATH = "/com/daviddai/retail/icon/";

    private UiUtils() {
    }

    public static ImageIcon loadIcon(String iconName) {
        if (iconName == null)
            return null;
        String filename = iconName.endsWith(".png") ? iconName : iconName + ".png";
        var url = UiUtils.class.getResource(ICON_PATH + filename);
        if (url == null) {
            System.err.println("[UiUtils] Icon không tồn tại: " + ICON_PATH + filename);
            return null;
        }
        return new ImageIcon(url);
    }

    public static String gennerateCodeFromName(String name) {
        String result = name;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        long time = Long.parseLong(LocalDateTime.now().format(formatter));
        while (time != 0) {
            int mod = (int) (time % 16);
            switch (mod) {
                case 10 -> result += "A";
                case 11 -> result += "B";
                case 12 -> result += "C";
                case 13 -> result += "D";
                case 14 -> result += "E";
                case 15 -> result += "F";
                default -> {
                    result += mod;
                }
            }
            time /= 16;
        }
        return result;
    }
}
