/*
 * Grayscale conversion and dithering program for multimedia class
 */
package dithering;

import java.util.ArrayList;

/**
 * Main class for testing Bitmap methods
 * @author seth
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    
    ArrayList<Bitmap> bitmaps = new ArrayList<>();
    
    Bitmap image3 = new Bitmap("wings/mtg__gift_of_orzhova_by_algenpfleger-d5sj6ir.jpg"); // wings
    Bitmap image5 = new Bitmap("apocalyptic/oh_god_where_is_my_gun_by_alexiuss-d7smmkm.jpg"); // apocalyptic
    Bitmap image6 = new Bitmap("forest/Photo by Ray Bilcliff- (15).jpg"); // forest
    Bitmap image7 = new Bitmap("lenna/Lenna.png"); // lenna
    
    bitmaps.add(image3);
    bitmaps.add(image5);
    bitmaps.add(image6);
    bitmaps.add(image7);

    // Iterate through bitmaps list, convert, and write converted grayscale images to their associated folders
    for(Bitmap b : bitmaps) {
      System.out.println("Width: " + b.getWidth() + ", " + "height: " + b.getHeight());      
      b.convertToGrayscaleAverage();
      b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/out.png");
      b.convertToGrayscaleLumosity();
      b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/out2.png");
      b.floydSteinbergDither();
      b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/out3.png");
      
      System.out.println();
    }
    
    // Just test 1 image
    /*Bitmap image = new Bitmap("wings/mtg__gift_of_orzhova_by_algenpfleger-d5sj6ir.jpg"); // wings
    System.out.println("Width: " + image.getWidth() + ", " + "height: " + image.getHeight());      
    image.convertToGrayscaleAverage();
    image.writeBitmap(image.getFileName().substring(0, image.getFileName().lastIndexOf("/")) + "/out.png");
    image.convertToGrayscaleLumosity();
    image.writeBitmap(image.getFileName().substring(0, image.getFileName().lastIndexOf("/")) + "/out2.png");
    image.floydSteinbergDither();
    image.writeBitmap(image.getFileName().substring(0, image.getFileName().lastIndexOf("/")) + "/out3.png");*/
    
  }
  
}
