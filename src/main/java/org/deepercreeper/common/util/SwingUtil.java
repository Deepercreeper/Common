package org.deepercreeper.common.util;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SwingUtil {
    private SwingUtil() {}

    public static void center(@NotNull Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(screenSize.width / 2 - window.getWidth() / 2, screenSize.height / 2 - window.getHeight() / 2);
    }
}
