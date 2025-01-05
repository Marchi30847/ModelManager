package data.enums;

import java.awt.*;

public enum Palette {
    BACKGROUND(new Color(215, 217, 224)),
    SELECTED(new Color(91, 95, 198)),
    HEADER_TABLE_ROW(new Color(245, 245, 245)),
    ODD_TABLE_ROW(new Color(240, 240, 240)),
    EVEN_TABLE_ROW(new Color(220, 220, 220));

    private final Color color;

    Palette(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
