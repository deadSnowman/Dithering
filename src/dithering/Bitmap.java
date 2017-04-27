/*
 * Grayscale conversion and dithering program for multimedia class
 * this is a modification of the Bitmap class from the DunCrawl project
 */
package dithering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Bitmap class This is used for reading a bitmap image, and performing
 * operations on the image (converting to grayscale, dithering)
 *
 * @author seth & anna
 */
class Bitmap {

    private int width;
    private int height;
    //private int[] pixels;
    private String fileName;
    public BufferedImage image = null;
    public BufferedImage writeImage = null;
    public BufferedImage imageCopy = null;
    public BufferedImage bayerImage = null;

    public Bitmap(String fileName) {
        this.fileName = fileName;

        try {
            image = ImageIO.read(new File(fileName));
            //this.writeImage = this.image; // doesn't make deep copy, just points to this.image

            // Create deep copy of the image
            deepCopy(image);

            width = image.getWidth();
            height = image.getHeight();

            //pixels = new int[width * height];
        } catch (IOException ex) {
            Logger.getLogger(Bitmap.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
        //this.pixels = new int[width * height];
        this.image = null;
        this.fileName = null;
    }
    
    public void bayerBitmap(int width, int height) {
        //this.pixels = new int[width * height];
        this.bayerImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    void deepCopy(BufferedImage imageToCopy) {
        ColorModel cm = imageToCopy.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = imageToCopy.copyData(null);
        this.writeImage = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    void deepImageCopy(BufferedImage imageToCopy) {
        ColorModel cm = imageToCopy.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = imageToCopy.copyData(null);
        this.imageCopy = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * Creates a grayscale image using the "average method" This averages the R,
     * G, and B values -> (R+G+B)/3
     *
     * Loop through the bitmap image, getting each pixel color, and set the new
     * colors in writeImage
     */
    public void convertToGrayscaleAverage() {
        deepCopy(this.image); // use original image (start fresh)

        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                Color currentColor = new Color(this.writeImage.getRGB(i, j));
                int newValue = ((currentColor.getRed() + currentColor.getGreen() + currentColor.getBlue()) / 3);
                Color replaceColor = new Color(newValue, newValue, newValue);
                this.writeImage.setRGB(i, j, replaceColor.getRGB());
            }
        }
    }

    /**
     * Creates a grayscale image using the "lumosity method" This multiplies the
     * RGB values by weights to account for human perception (R * .21, G * .72,
     * B * .07)
     *
     * Loop through the bitmap image, getting each pixel color, and set the new
     * colors in writeImage
     */
    public void convertToGrayscaleLumosity() {
        deepCopy(this.image); // use original image (start fresh)

        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                Color currentColor = new Color(this.writeImage.getRGB(i, j));
                // Use the same weights that Gimp uses
                int newR = (int) (currentColor.getRed() * 0.21);
                int newG = (int) (currentColor.getGreen() * 0.72);
                int newB = (int) (currentColor.getBlue() * 0.07);
                Color replaceColor = new Color(newR + newG + newB, newR + newG + newB, newR + newG + newB);
                this.writeImage.setRGB(i, j, replaceColor.getRGB());
            }
        }
    }

    public void floydSteinbergDither(boolean l) {

        deepCopy(this.image); // use original image (start fresh)

        if (l) {
            // first convert to greyscale
            for (int i = 0; i < this.getWidth(); i++) {
                for (int j = 0; j < this.getHeight(); j++) {
                    Color currentColor = new Color(this.writeImage.getRGB(i, j));
                    // Use the same weights that Gimp uses
                    int newR = (int) (currentColor.getRed() * 0.21);
                    int newG = (int) (currentColor.getGreen() * 0.72);
                    int newB = (int) (currentColor.getBlue() * 0.07);
                    Color replaceColor = new Color(newR + newG + newB, newR + newG + newB, newR + newG + newB);
                    this.writeImage.setRGB(i, j, replaceColor.getRGB());
                }
            }
        } else {
            // not lumos
            for (int i = 0; i < this.getWidth(); i++) {
                for (int j = 0; j < this.getHeight(); j++) {
                    Color currentColor = new Color(this.writeImage.getRGB(i, j));
                    int newValue = ((currentColor.getRed() + currentColor.getGreen() + currentColor.getBlue()) / 3);
                    Color replaceColor = new Color(newValue, newValue, newValue);
                    this.writeImage.setRGB(i, j, replaceColor.getRGB());
                }
            }
        }

        deepImageCopy(writeImage);

        // start dithering
        // Moves verticaly, then horizontally
        for (int j = 0; j < this.getHeight(); j++) {
            for (int i = 0; i < this.getWidth(); i++) {

                // get original pixel
                Color oldPixel = new Color(this.imageCopy.getRGB(i, j));

                // new pixel = find closest palette color (in this case, black or white)
                Color newPixel = (oldPixel.getRed() <= 127) ? new Color(0, 0, 0) : new Color(255, 255, 255);

                // set quantized pixel
                this.writeImage.setRGB(i, j, newPixel.getRGB());

                // get quantization error
                int quantError = oldPixel.getRed() - newPixel.getRed();
                
                Color color;
                int rgb;

                // the dithering stuffs -> Floyd–Steinberg algorithm
                if ((i + 1) < this.getWidth()) {
                    color = new Color(this.imageCopy.getRGB(i + 1, j));
                    rgb = color.getRed();
                    rgb = (rgb) + (int)(quantError * 7.0 / 16);
                    if(rgb > 255) 
                        rgb = 255;
                    else if (rgb < 0) rgb = 0;
                    this.imageCopy.setRGB(i + 1, j, new Color(rgb,rgb,rgb).getRGB());
                }
                if ((i - 1) > 0 && (j + 1) < this.getHeight()) {
                    color = new Color(this.imageCopy.getRGB(i - 1, j + 1));
                    rgb = color.getRed();
                    rgb = (rgb) + (int)(quantError * 3.0 / 16);
                    if(rgb > 255) 
                        rgb = 255;
                    else if (rgb < 0) rgb = 0;
                    this.imageCopy.setRGB(i - 1, j + 1, new Color(rgb,rgb,rgb).getRGB());
                }
                if ((j + 1) < this.getHeight()) {
                    color = new Color(this.imageCopy.getRGB(i, j + 1));
                    rgb = color.getRed();
                    rgb = (rgb) + (int)(quantError * 5.0 / 16);
                    if(rgb > 255) 
                        rgb = 255;
                    else if (rgb < 0) rgb = 0;
                    this.imageCopy.setRGB(i, j + 1, new Color(rgb,rgb,rgb).getRGB());
                }
                if ((i + 1) < this.getWidth() && (j + 1) < this.getHeight()) {
                    color = new Color(this.imageCopy.getRGB(i + 1, j + 1));
                    rgb = color.getRed();
                    rgb = (rgb) + (int)(quantError * 1.0 / 16);
                    if(rgb > 255) 
                        rgb = 255;
                    else if (rgb < 0) rgb = 0;
                    this.imageCopy.setRGB(i + 1, j + 1, new Color(rgb,rgb,rgb).getRGB());
                }
            }
        }

    }
    
    public void bayerDither(boolean l) {
        deepCopy(this.image); // use original image (start fresh)

        if (l) {
            // first convert to greyscale
            for (int i = 0; i < this.getWidth(); i++) {
                for (int j = 0; j < this.getHeight(); j++) {
                    Color currentColor = new Color(this.writeImage.getRGB(i, j));
                    // Use the same weights that Gimp uses
                    int newR = (int) (currentColor.getRed() * 0.21);
                    int newG = (int) (currentColor.getGreen() * 0.72);
                    int newB = (int) (currentColor.getBlue() * 0.07);
                    Color replaceColor = new Color(newR + newG + newB, newR + newG + newB, newR + newG + newB);
                    this.writeImage.setRGB(i, j, replaceColor.getRGB());
                }
            }
        } else {
            // not lumos
            for (int i = 0; i < this.getWidth(); i++) {
                for (int j = 0; j < this.getHeight(); j++) {
                    Color currentColor = new Color(this.writeImage.getRGB(i, j));
                    int newValue = ((currentColor.getRed() + currentColor.getGreen() + currentColor.getBlue()) / 3);
                    Color replaceColor = new Color(newValue, newValue, newValue);
                    this.writeImage.setRGB(i, j, replaceColor.getRGB());
                }
            }
        }
        //4x as large matrix since each pixel will be replaced by 2x2
//        bayerBitmap(this.getWidth()*2, this.getHeight()*2); //sets up global variable bayerImage 
        deepImageCopy(writeImage);
        // start dithering
        // Moves verticaly, then horizontally
        /*for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {*/
        Color black = new Color(0,0,0);
        Color white = new Color(255,255,255);
//        int x = 0, y = 0;

        int bayer[][] = {{1,3},{4,2}};
        Color color;
        int c;
        
               
        Color colorResult;
        
        for (int j = 0; j < this.getHeight(); j++) {
            for (int i = 0; i < this.getWidth(); i++) {

                color = new Color(imageCopy.getRGB(i,j));
                c = color.getRed();
                
                c = c + (c*bayer[i%2][j%2]) / 5;
                
                colorResult = (c<= 127) ? new Color(black.getRGB()) : new Color(white.getRGB());
                
                writeImage.setRGB(i, j, colorResult.getRGB());
                // get original pixel
//                Color pixel = new Color(this.imageCopy.getRGB(i, j));
//                double pixelVal = (double)pixel.getRed();
                
                //top left = 0 top right = 1 bottom right = 2 bottom left = 3
//                boolean[] square = bayerSquare(pixelVal);
                
                
//                if(square[0]) { //top left
//                    this.bayerImage.setRGB(x, y, black.getRGB());
//                } else {
//                    this.bayerImage.setRGB(x, y, white.getRGB());
//                }
//                if(square[1]) { //top right
//                    this.bayerImage.setRGB(x+1, y, black.getRGB());
//                } else {
//                    this.bayerImage.setRGB(x+1, y, white.getRGB());
//                }
//                if(square[2]) { //bottom right
//                    this.bayerImage.setRGB(x+1, y+1, black.getRGB());
//                } else {
//                    this.bayerImage.setRGB(x+1, y+1, white.getRGB());
//                }
//                if(square[3]) { //bottom left
//                    this.bayerImage.setRGB(x, y+1, black.getRGB());
//                } else {
//                    this.bayerImage.setRGB(x, y+1, white.getRGB());
//                }
                
//                x+=2; //move over horizontally twice in bayer image matrix
            }
//            x=0; //start at the beginning of the next row
//            y+=2; //move over vertically twice in bayer image matrix
        }
    }
    
//    public boolean[] bayerSquare(double color) {
//        double value = color/255.0;
//        
//        boolean[] square = new boolean[4]; //all initialized to false
//        double[] coefficient = new double[4];
//        coefficient[0] = 0.2;
//        coefficient[1] = 0.8;
//        coefficient[2] = 0.4;
//        coefficient[3] = 0.6;
//        
//        
//        square[0] = value > coefficient[0];
//        square[1] = value > coefficient[1];
//        square[2] = value > coefficient[2];
//        square[3] = value > coefficient[3];
//            
//        return square;
//    }

    public void writeBitmap(String writeFileName) {
        File output = new File(writeFileName);

        try {
            ImageIO.write(this.writeImage, "png", output);
            System.out.println("Image written: " + writeFileName + "\"");
        } catch (IOException ex) {
            Logger.getLogger(Bitmap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//
//    public void writeBayerBitmap(String writeFileName) {
//        File output = new File("./res/bitmaps/" + writeFileName);
//
//        try {
//            ImageIO.write(this.bayerImage, "png", output);
//            System.out.println("Image written: \"./res/bitmaps/" + writeFileName + "\"");
//        } catch (IOException ex) {
//            Logger.getLogger(Bitmap.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

}
