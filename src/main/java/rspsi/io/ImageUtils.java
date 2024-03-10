package rspsi.io;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {
    /**
     * Converts an Image to a BufferedImage.
     *
     * @param img The Image to be converted.
     * @return The BufferedImage converted from the Image.
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bImage;
    }
}