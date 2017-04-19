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
 * Bitmap class
 * This is used for reading a bitmap image, and performing operations on the image
 * (converting to grayscale, dithering)
 * @author seth
 */
class Bitmap {

  private int width;
  private int height;
  private int[] pixels;
  private String fileName;
  private BufferedImage image = null;
  private BufferedImage writeImage = null;
  
  public Bitmap(String fileName) {
    this.fileName = fileName;
    
    try {
      image = ImageIO.read(new File("./res/bitmaps/" + fileName));
      //this.writeImage = this.image; // doesn't make deep copy, just points to this.image
      
      // Create deep copy of the image
      deepCopy(image);      
  
      width = image.getWidth();
      height = image.getHeight();
      
      pixels = new int[width * height];
    } catch (IOException ex) {
      Logger.getLogger(Bitmap.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }
  public Bitmap(int width, int height) {
    this.width = width;
    this.height = height;
    this.pixels = new int[width * height];
    this.image = null;
    this.fileName = null;
  }
  
  private void deepCopy(BufferedImage imageToCopy) {
    ColorModel cm = imageToCopy.getColorModel();
    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
    WritableRaster raster = imageToCopy.copyData(null);
    this.writeImage = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
  }
  
  public String getFileName() {
    return fileName;
  }

  public int getHeight() {
    return height;
  }

  public int[] getPixels() {
    return pixels;
  }
  
  public int getPixel(int x, int y) {
    return pixels[x + y * width];
  }
  
  public void setPixel(int x, int y, int value) {
    pixels[x + y * width] = value;
  }

  public int getWidth() {
    return width;
  }
  
  /**
   * Creates a grayscale image using the "average method"
   * This averages the R, G, and B values -> (R+G+B)/3
   * 
   * Loop through the bitmap image, getting each pixel color,
   * and set the new colors in writeImage
   */
  public void convertToGrayscaleAverage() {
    deepCopy(this.image); // use original image (start fresh)
    
    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
	Color currentColor = new Color(this.writeImage.getRGB(i, j));
	int newValue = ((currentColor.getRed() + currentColor.getGreen() + currentColor.getBlue())/3);
	Color replaceColor = new Color(newValue, newValue, newValue);
	this.writeImage.setRGB(i, j, replaceColor.getRGB());
      }
    }
    System.out.println("converting to grayscale...");
  }
  
  /**
   * Creates a grayscale image using the "lumosity method"
   * This multiplies the RGB values by weights to account for human perception (R * .21, G * .72, B * .07)
   * 
   * Loop through the bitmap image, getting each pixel color,
   * and set the new colors in writeImage
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
    System.out.println("converting to grayscale...");
  }
  
  public void ditherImage() {
    deepCopy(this.image); // use original image (start fresh)
    
    /*
    to do:
    loop through image, creating matrixes of 4 pixels
    associate a parttern based off of average value of pixels in each matrix
    */
  }
  
  public void writeBitmap(String writeFileName) {
    File output = new File("./res/bitmaps/" + writeFileName);
    
    try {
      ImageIO.write(this.writeImage, "png", output);
      System.out.println("Image written: \"./res/bitmaps/" + writeFileName + "\"");
    } catch (IOException ex) {
      Logger.getLogger(Bitmap.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }
  
}
