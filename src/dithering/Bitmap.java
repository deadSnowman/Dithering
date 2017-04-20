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
  //private int[] pixels;
  private String fileName;
  public BufferedImage image = null;
  public BufferedImage writeImage = null;
  
  public Bitmap(String fileName) {
    this.fileName = fileName;
    
    try {
      image = ImageIO.read(new File("./res/bitmaps/" + fileName));
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
  
  void deepCopy(BufferedImage imageToCopy) {
    ColorModel cm = imageToCopy.getColorModel();
    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
    WritableRaster raster = imageToCopy.copyData(null);
    this.writeImage = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
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
    System.out.println("converting to grayscale...");
    
    // start dithering
    int matrixSize = 2;
    int[] ditherMatrix = {0, 3, 2, 1};
    
    for (int i = 0; i < this.getWidth(); i+=2) {
      for (int j = 0; j < this.getHeight(); j+=2) {
	Color currentColor = new Color(this.writeImage.getRGB(i, j));
	Color rightColor = new Color(this.writeImage.getRGB(i+1, j));
	Color downColor = new Color(this.writeImage.getRGB(i, j+1));
	Color downAndRightColor = new Color(this.writeImage.getRGB(i+1, j+1));
	
	int currentGreyValue = currentColor.getRed(); // all RGB values are the same b/c greyscale
	int rightGreyValue = currentColor.getRed();
	int downGreyValue = currentColor.getRed();
	int downAndRightValue = currentColor.getRed();
	
	
	
	//Color avg = new Color();
	
      }
    }
    
  }
  
  public void floydSteinbergDither(boolean l) {
    
    deepCopy(this.image); // use original image (start fresh)
    
    if(l) {
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
      System.out.println("converting to grayscale...");
    }
    else {
      // not lumos
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
    
    
    // start dithering
    // Moves verticaly, then horizontally
    /*for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {*/
    for (int j = 0; j < this.getHeight(); j++) {
      for (int i = 0; i < this.getWidth(); i++) {
	
	// get original pixel
	Color oldPixel = new Color(this.writeImage.getRGB(i, j));
	
	// new pixel = find closest palette color (in this case, black or white)
	Color newPixel = (oldPixel.getRed() <= 127) ? new Color(0, 0, 0): new Color(255, 255, 255);
	
	// set quantized pixel
	this.writeImage.setRGB(i, j, newPixel.getRGB());
	
	// get quantization error
	int quantError = oldPixel.getRGB() - newPixel.getRGB();
	
	// the dithering stuffs -> Floyd–Steinberg algorithm
	if((i+1) < this.getWidth())
	  this.writeImage.setRGB(i+1, j, (int) (this.writeImage.getRGB(i+1, j) + quantError * 7.0 / 16));
	if((i-1) > 0 && (j+1) < this.getHeight())
	  this.writeImage.setRGB(i-1, j+1, (int) (this.writeImage.getRGB(i-1, j+1) + quantError * 3.0 / 16));
	if((j+1) < this.getHeight())
	  this.writeImage.setRGB(i, j+1, (int) (this.writeImage.getRGB(i, j+1) + quantError * 5.0 / 16));
	if((i+1) < this.getWidth() && (j+1) < this.getHeight())
	  this.writeImage.setRGB(i+1, j+1, (int) (this.writeImage.getRGB(i+1, j+1) + quantError * 1.0 / 16));
	
      }
    }
    
    /*for (int j = 0; j < this.getHeight(); j++) {
      for (int i = 0; i < this.getWidth(); i++) {
	
	// get original pixel
	Color oldPixel = new Color(this.writeImage.getRGB(i, j));
	
	// new pixel = find closest palette color (in this case, black or white)
	Color newPixel = (oldPixel.getRed() <= 127) ? new Color(0, 0, 0): new Color(255, 255, 255);
	
	// set quantized pixel
	this.writeImage.setRGB(i, j, newPixel.getRGB());
	
	// get quantization error
	int quantError = oldPixel.getRGB() - newPixel.getRGB();
	
	// the dithering stuffs -> Floyd–Steinberg algorithm
	if((i+1) < this.getWidth()) {
	  
	  Color tmp = new Color(this.writeImage.getRGB(i+1, j));
	  int tmpRed = (int) (tmp.getRed() + quantError * 7.0 / 16);
	  int tmpGreen = (int) (tmp.getGreen() + quantError * 7.0 / 16);
	  int tmpBlue = (int) (tmp.getBlue() + quantError * 7.0 / 16);
	  Color newTmp = new Color(tmpRed, tmpGreen, tmpBlue);
	  
	  this.writeImage.setRGB(i+1, j, newTmp.getRGB());
	  
	  
	 //this.writeImage.setRGB(i+1, j, addToColor(new Color(this.writeImage.getRGB(i+1, j)), (int) (quantError * 7.0 / 16)).getRGB());
	  
	  //this.writeImage.setRGB(i+1, j, (int) (this.writeImage.getRGB(i+1, j) + quantError * 7.0 / 16));
	}
	if((i-1) > 0 && (j+1) < this.getHeight()) {
	  
	  Color tmp = new Color(this.writeImage.getRGB(i-1, j+1));
	  int tmpRed = (int) (tmp.getRed() + quantError * 3.0 / 16);
	  int tmpGreen = (int) (tmp.getGreen() + quantError * 3.0 / 16);
	  int tmpBlue = (int) (tmp.getBlue() + quantError * 3.0 / 16);
	  Color newTmp = new Color(tmpRed, tmpGreen, tmpBlue);
	  
	  this.writeImage.setRGB(i-1, j+1, newTmp.getRGB());
	  
	  //this.writeImage.setRGB(i-1, j+1, (int) (this.writeImage.getRGB(i-1, j+1) + quantError * 3.0 / 16));
	}
	if((j+1) < this.getHeight()) {
	  
	  Color tmp = new Color(this.writeImage.getRGB(i, j+1));
	  int tmpRed = (int) (tmp.getRed() + quantError * 5.0 / 16);
	  int tmpGreen = (int) (tmp.getGreen() + quantError * 5.0 / 16);
	  int tmpBlue = (int) (tmp.getBlue() + quantError * 5.0 / 16);
	  Color newTmp = new Color(tmpRed, tmpGreen, tmpBlue);
	  
	  this.writeImage.setRGB(i, j+1, newTmp.getRGB());
	  
	  //this.writeImage.setRGB(i, j+1, (int) (this.writeImage.getRGB(i, j+1) + quantError * 5.0 / 16));
	}
	if((i+1) < this.getWidth() && (j+1) < this.getHeight()) {
	  
	  Color tmp = new Color(this.writeImage.getRGB(i+1, j+1));
	  int tmpRed = (int) (tmp.getRed() + quantError * 1.0 / 16);
	  int tmpGreen = (int) (tmp.getGreen() + quantError * 1.0 / 16);
	  int tmpBlue = (int) (tmp.getBlue() + quantError * 1.0 / 16);
	  Color newTmp = new Color(tmpRed, tmpGreen, tmpBlue);
	  
	  this.writeImage.setRGB(i+1, j+1, newTmp.getRGB());
	  
	  //this.writeImage.setRGB(i+1, j+1, (int) (this.writeImage.getRGB(i+1, j+1) + quantError * 1.0 / 16));
	}
	
      }
    }*/
    
  }
  
  public Color addToColor(Color pixel, int value) {
    int tmpRed = (int) (pixel.getRed() + value);
    int tmpGreen = (int) (pixel.getGreen() + value);
    int tmpBlue = (int) (pixel.getBlue() + value);
    
    Color newTmp = new Color(tmpRed, tmpGreen, tmpBlue);
    
    return newTmp;
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
