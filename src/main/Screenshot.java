package main;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by Kyle on 6/4/2017.
 */
public class Screenshot {
    private ComplexNumber coordinateOfTopLeftPixel;
    private int zoomFactor;
    private int iterations;
    private BufferedImage theImage;
    private ComplexNumber mandelbrotExponent;
    private double mandelbrotExponentReal;

    public Screenshot(BufferedImage theImage, ComplexNumber coordinateOfTopLeftPixel, int zoomFactor, int iterations, ComplexNumber mandelbrotExponent) {
        this.theImage = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < 1080; row++) {
            for (int column = 0; column < 1920; column++) {
                this.theImage.setRGB(column, row, theImage.getRGB(column, row));
            }
        }
        this.coordinateOfTopLeftPixel = new ComplexNumber(coordinateOfTopLeftPixel.getRealComponent(), coordinateOfTopLeftPixel.getImaginaryCoefficient());
        this.zoomFactor = zoomFactor;
        this.iterations = iterations;
        this.mandelbrotExponent = new ComplexNumber(mandelbrotExponent.getRealComponent(), mandelbrotExponent.getImaginaryCoefficient());
    }

    public Screenshot(BufferedImage theImage, ComplexNumber coordinateOfTopLeftPixel, int zoomFactor, int iterations, double mandelbrotExponentReal) {
        this.theImage = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < 1080; row++) {
            for (int column = 0; column < 1920; column++) {
                this.theImage.setRGB(column, row, theImage.getRGB(column, row));
            }
        }
        this.coordinateOfTopLeftPixel = new ComplexNumber(coordinateOfTopLeftPixel.getRealComponent(), coordinateOfTopLeftPixel.getImaginaryCoefficient());
        this.zoomFactor = zoomFactor;
        this.iterations = iterations;
        this.mandelbrotExponentReal = mandelbrotExponentReal;
    }

    public void drawToFile(int name, long time) {
        try {
            File file = new File("screenshots/" + time + "__" + name + ".png");
            file.createNewFile();
            boolean b = ImageIO.write(theImage, "png", file);

            String mandelbrotExponentString;
            if (mandelbrotExponent == null) {
                mandelbrotExponentString = "Mandelbrot Exponent: " + mandelbrotExponentReal;
            } else {
                mandelbrotExponentString = "Mandelbrot Exponent: " + mandelbrotExponent.getRealComponent() + " + " + mandelbrotExponent.getImaginaryCoefficient() + "i";
            }

            String screenshotInfoString = "Screenshot " + time + "__" + name + ":\r\n" +
                    mandelbrotExponentString + "\r\n" +
                    "Top Left Coordinates: " + coordinateOfTopLeftPixel.getRealComponent() + " + " + coordinateOfTopLeftPixel.getImaginaryCoefficient() + "i\r\n" +
                    "Zoom (width): 2^" + zoomFactor + "\r\n" +
                    "# of iterations: " + iterations + "\r\n\r\n";

            Files.write(Paths.get("screenshots/screenshotInfo.txt"),
                    screenshotInfoString.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
