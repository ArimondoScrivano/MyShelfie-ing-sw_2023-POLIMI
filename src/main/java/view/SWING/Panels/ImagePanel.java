package view.SWING.Panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private Image backgroundImage;

    //TODO accedere a immagine tramite resources
    public ImagePanel() {
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\pietr\\Desktop\\MyShelfie\\Graphical_resources\\misc\\sfondo parquet.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);  // Imposta le dimensioni preferite del pannello
    }
}
