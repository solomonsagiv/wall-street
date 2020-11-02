package gui;

import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {

    private int radius;


    public RoundedBorder(int radius) {
        this.radius = radius;
    }


    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius - 2, this.radius - 15, this.radius - 2, this.radius - 15);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width, height, radius + 10, radius + 10);
    }
}
