package view.SWING.Panels;

import javax.swing.*;
import java.awt.*;

/**
 * The ImagePanel class extends JPanel and represents a panel that displays an image.
 */
    public class ImagePanel extends JPanel {
        private final Image image;



        /**
         * Constructs an ImagePanel with the specified image.
         *
         * @param image the image to be displayed in the panel
         */
        public ImagePanel(Image image) {
            this.image = image;
        }




        /**
         * Overrides the paintComponent method to paint the image on the panel.
         *
         * @param g the Graphics context in which to paint
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }

