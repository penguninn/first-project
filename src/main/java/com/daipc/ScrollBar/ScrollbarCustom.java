
package com.daipc.ScrollBar;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

public class ScrollbarCustom extends JScrollBar {
    public ScrollbarCustom() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(6, 6));
        setForeground(new Color(48, 144, 216));
    }
}
