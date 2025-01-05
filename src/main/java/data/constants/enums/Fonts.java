package data.constants.enums;

import java.awt.*;

public enum Fonts {
    HEADER_FONT(
            new Font("Georgia", Font.BOLD, 25),
            new Color(55, 71, 79)
    ),
    BODY_FONT(
            new Font("Open Sans", Font.PLAIN, 18),
            new Color(55, 71, 79)
    ),
    SELECTED_BODY_FONT(
            new Font("Open Sans", Font.ITALIC, 18),
            new Color(255, 255, 255)
    ),
    BUTTON_FONT(
            new Font("Georgia", Font.PLAIN, 25),
            new Color(55, 71, 79)
    );

    private final Font font;
    private final Color color;

    Fonts(Font font, Color color) {
        this.font = font;
        this.color = color;
    }

    public static void applyToComponent(Component component, Fonts font) {
        component.setFont(font.font);
        component.setForeground(font.color);
    }
}
